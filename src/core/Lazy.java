package core;

import modele.Quete;
import modele.Scenario;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.TreeMap;

public class Lazy extends BaseMoteurOptimal{

    private final TreeMap<Integer, Integer> deplacementsSolutions = new TreeMap<>();

    public Lazy(int idScenario) throws FileNotFoundException {
        super(idScenario);
    }

    public Lazy(int idScenario, int nombre_solutions) throws FileNotFoundException {
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
                parcoursEfficace(idQuete, current_solution, current.getExp(), joueur.distance(current));
            } else {
                parcoursExhaustif(idQuete, current_solution, joueur.distance(current));
            }
        }
        return solutions;
    }

    private void parcoursEfficace(int idQuete, ArrayList<Integer> solution, int exp, int deplacements) {
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
        if (solutionValide(deplacements)){
            if (idQuete == 0){
                insererSolution(solution, deplacements);
            } else {
                currentScenario.validerQuete(idQuete);
                for (int nextQueteId : currentScenario.getAccessibleQuetes()){
                    if (nextQueteId != 0 || scenario.getQuete(nextQueteId).getExp() <= exp){
                        ArrayList<Integer> nextSolution = new ArrayList<>(solution);
                        nextSolution.add(nextQueteId);
                        Quete nextQuete = scenario.getQuete(nextQueteId);
                        int nextDeplacements = deplacements + scenario.getQuete(idQuete).distance(nextQuete);
                        parcoursEfficace(nextQueteId, nextSolution, exp + nextQuete.getExp(), nextDeplacements);
                    }
                }
            }
        }
    }

    private void parcoursExhaustif(int idQuete, ArrayList<Integer> solution, int deplacements){
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
        if (solutionValide(deplacements)){
            if (idQuete == 0){
                insererSolution(solution, deplacements);
            } else {
                currentScenario.validerQuete(idQuete);
                if (currentScenario.getAccessibleQuetes().contains(0) && currentScenario.getAccessibleQuetes().size() == 1) {
                    ArrayList<Integer> nextSolution = new ArrayList<>(solution);
                    nextSolution.add(0);
                    Quete nextQuete = scenario.getQuete(0);
                    int nextDeplacements = deplacements + scenario.getQuete(idQuete).distance(nextQuete);
                    parcoursExhaustif(0, nextSolution, nextDeplacements);
                } else {
                    for (int nextQueteId : currentScenario.getAccessibleQuetes()){
                        if (nextQueteId != 0){
                            ArrayList<Integer> nextSolution = new ArrayList<>(solution);
                            nextSolution.add(nextQueteId);
                            Quete nextQuete = scenario.getQuete(nextQueteId);
                            int nextDeplacements = deplacements + scenario.getQuete(idQuete).distance(nextQuete);
                            parcoursExhaustif(nextQueteId, nextSolution, nextDeplacements);
                        }
                    }
                }
            }
        }
    }

    private void insererSolution(ArrayList<Integer> solution, int deplacements){
        int insertPos = -1;
        int lastPos = nbSolutions-1;
        for (int i = 0; i < nbSolutions ; i++) {
            if (insertPos == -1){
                if (meilleuresSolutions && (solutions[i] == null || deplacementsSolutions.get(i) > deplacements)) {
                    insertPos = i;
                } else if (!meilleuresSolutions && (solutions[i] == null || deplacementsSolutions.get(i) < deplacements)) {
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
                int d = (deplacementsSolutions.get(lastPos) == null) ? -1 : deplacementsSolutions.get(lastPos);
                solutions[lastPos] = solutions[lastPos-1];
                deplacementsSolutions.put(lastPos, deplacementsSolutions.get(lastPos-1));
                solutions[lastPos-1] = s;
                if (d != -1){
                    deplacementsSolutions.put(lastPos-1, d);
                } else {
                    deplacementsSolutions.remove(lastPos-1);
                }
                lastPos--;
            }
            solutions[insertPos] = solution;
            deplacementsSolutions.put(insertPos, deplacements);
        }
    }

    private boolean solutionValide(int deplacements){
        if (meilleuresSolutions){
            return solutions[nbSolutions-1] == null || deplacementsSolutions.get(nbSolutions-1) >= deplacements;
        } else {
            return solutions[nbSolutions-1] == null || deplacementsSolutions.get(nbSolutions-1) <= deplacements;
        }
    }
}
