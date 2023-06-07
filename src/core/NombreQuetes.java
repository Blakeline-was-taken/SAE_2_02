package core;

import modele.Quete;
import modele.Scenario;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class NombreQuetes extends BaseMoteurOptimal{

    public NombreQuetes(int idScenario) throws FileNotFoundException {
        super(idScenario);
    }

    public NombreQuetes(int idScenario, int nombre_solutions) throws FileNotFoundException {
        super(idScenario, nombre_solutions);
    }

    @Override
    public ArrayList<Integer>[] run(boolean isEfficace, boolean bestSolutions) {
        meilleuresSolutions = bestSolutions;
        for (int idQuete : scenario.getAccessibleQuetes()){
            Quete current = scenario.getQuete(idQuete);
            ArrayList<Integer> current_solution = new ArrayList<>();
            current_solution.add(idQuete);
            if (isEfficace){
                parcoursEfficace(idQuete, current_solution, current.getExp());
            } else {
                parcoursExhaustif(idQuete, current_solution);
            }
        }
        return solutions;
    }

    private void parcoursEfficace(int idQuete, ArrayList<Integer> solution, int exp) {
        Scenario currentScenario;
        try {
            currentScenario = new Scenario(scenarioName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        for (int previousQueteId : solution){
            currentScenario.validerQuete(previousQueteId);
        }

        // On vérifie que, si on a déjà suffisamment de solutions, notre solution n'est pas plus longue que la pire
        if (solutionValide(solution)){
            if (idQuete == 0){
                insererSolution(solution);
            } else {
                currentScenario.validerQuete(idQuete);
                for (int nextQueteId : currentScenario.getAccessibleQuetes()){
                    if (nextQueteId != 0 || scenario.getQuete(nextQueteId).getExp() <= exp){
                        ArrayList<Integer> nextSolution = new ArrayList<>(solution);
                        nextSolution.add(nextQueteId);
                        Quete nextQuete = scenario.getQuete(nextQueteId);
                        parcoursEfficace(nextQueteId, nextSolution, exp + nextQuete.getExp());
                    }
                }
            }
        }
    }

    private void parcoursExhaustif(int idQuete, ArrayList<Integer> solution){
        Scenario currentScenario;
        try {
            currentScenario = new Scenario(scenarioName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        for (int previousQueteId : solution){
            currentScenario.validerQuete(previousQueteId);
        }

        // On vérifie que, si on a déjà suffisamment de solutions, notre solution n'est pas plus longue que la pire
        if (solutionValide(solution)){
            if (idQuete == 0){
                insererSolution(solution);
            } else {
                currentScenario.validerQuete(idQuete);
                if (currentScenario.getAccessibleQuetes().contains(0) && currentScenario.getAccessibleQuetes().size() == 1) {
                    ArrayList<Integer> nextSolution = new ArrayList<>(solution);
                    nextSolution.add(0);
                    parcoursExhaustif(0, nextSolution);
                } else {
                    for (int nextQueteId : currentScenario.getAccessibleQuetes()){
                        if (nextQueteId != 0){
                            ArrayList<Integer> nextSolution = new ArrayList<>(solution);
                            nextSolution.add(nextQueteId);
                            parcoursExhaustif(nextQueteId, nextSolution);
                        }
                    }
                }
            }
        }
    }

    private void insererSolution(ArrayList<Integer> solution){
        int insertPos = -1;
        int lastPos = nbSolutions-1;
        for (int i = 0; i < nbSolutions ; i++) {
            if (insertPos == -1){
                if (meilleuresSolutions && (solutions[i] == null || solutions[i].size() > solution.size())) {
                    insertPos = i;
                } else if (!meilleuresSolutions && (solutions[i] == null || solutions[i].size() < solution.size())) {
                    insertPos = i;
                }
            }
            if (solutions[i] == null) {
                lastPos = i;
                break;
            }
        }
        if (insertPos != -1){
            while (lastPos != insertPos){
                ArrayList<Integer> s = solutions[lastPos];
                solutions[lastPos] = solutions[lastPos-1];
                solutions[lastPos-1] = s;
                lastPos--;
            }
            solutions[insertPos] = solution;
        }
    }

    private boolean solutionValide(ArrayList<Integer> solution){
        if (meilleuresSolutions){
            return solutions[nbSolutions-1] == null || solutions[nbSolutions-1].size() >= solution.size();
        } else {
            return solutions[nbSolutions-1] == null || solutions[nbSolutions-1].size() <= solution.size();
        }
    }
}
