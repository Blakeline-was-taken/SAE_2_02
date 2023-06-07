package core;

import module.Quete;
import module.Scenario;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.TreeMap;

public class Speedrun extends BaseMoteurOptimal{

    private final TreeMap<Integer, Integer> dureeSolutions = new TreeMap<>();

    public Speedrun(int idScenario) throws FileNotFoundException {
        super(idScenario);
    }

    public Speedrun(int idScenario, int nombre_solutions) throws FileNotFoundException {
        super(idScenario, nombre_solutions);
    }

    @Override
    public ArrayList<Integer>[] run(boolean isEfficace, boolean bestSolutions) {
        meilleuresSolutions = bestSolutions;
        for (int idQuete : scenario.getAccessibleQuetes()){
            Quete current = scenario.getQuete(idQuete);
            ArrayList<Integer> current_solution = new ArrayList<>();
            current_solution.add(idQuete);
            int duree = joueur.distance(current) + current.getDuree();
            if (isEfficace){
                parcoursEfficace(idQuete, current_solution, duree, current.getExp());
            } else {
                parcoursExhaustif(idQuete, current_solution, duree);
            }
        }
        return solutions;
    }

    private void parcoursEfficace(int idQuete, ArrayList<Integer> solution, int duree, int exp) {
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
        if (solutionValide(duree)){
            if (idQuete == 0){
                insererSolution(solution, duree);
            } else {
                currentScenario.validerQuete(idQuete);
                for (int nextQueteId : currentScenario.getAccessibleQuetes()){
                    if (nextQueteId != 0 || scenario.getQuete(nextQueteId).getExp() <= exp){
                        ArrayList<Integer> nextSolution = new ArrayList<>(solution);
                        nextSolution.add(nextQueteId);
                        Quete nextQuete = scenario.getQuete(nextQueteId);
                        int nextDuree = duree + scenario.getQuete(idQuete).distance(nextQuete) + nextQuete.getDuree();
                        parcoursEfficace(nextQueteId, nextSolution, nextDuree, exp + nextQuete.getExp());
                    }
                }
            }
        }
    }

    private void parcoursExhaustif(int idQuete, ArrayList<Integer> solution, int duree){
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
        if (solutionValide(duree)){
            if (idQuete == 0){
                insererSolution(solution, duree);
            } else {
                currentScenario.validerQuete(idQuete);
                if (currentScenario.getAccessibleQuetes().contains(0) && currentScenario.getAccessibleQuetes().size() == 1) {
                    ArrayList<Integer> nextSolution = new ArrayList<>(solution);
                    nextSolution.add(0);
                    Quete nextQuete = scenario.getQuete(0);
                    int nextDuree = duree + scenario.getQuete(idQuete).distance(nextQuete) + nextQuete.getDuree();
                    parcoursExhaustif(0, nextSolution, nextDuree);
                } else {
                    for (int nextQueteId : currentScenario.getAccessibleQuetes()){
                        if (nextQueteId != 0){
                            ArrayList<Integer> nextSolution = new ArrayList<>(solution);
                            nextSolution.add(nextQueteId);
                            Quete nextQuete = scenario.getQuete(nextQueteId);
                            int nextDuree = duree + scenario.getQuete(idQuete).distance(nextQuete) + nextQuete.getDuree();
                            parcoursExhaustif(nextQueteId, nextSolution, nextDuree);
                        }
                    }
                }
            }
        }
    }

    private void insererSolution(ArrayList<Integer> solution, int duree){
        int insertPos = -1;
        int lastPos = nbSolutions-1;
        for (int i = 0; i < nbSolutions ; i++) {
            if (insertPos == -1){
                if (meilleuresSolutions && (dureeSolutions.get(i) == null || dureeSolutions.get(i) > duree)) {
                    insertPos = i;
                } else if (!meilleuresSolutions && (dureeSolutions.get(i) == null || dureeSolutions.get(i) < duree)) {
                    insertPos = i;
                }
            }
            if (dureeSolutions.get(i) == null) {
                lastPos = i;
                break;
            }
        }
        if (insertPos != -1){
            while (lastPos != insertPos){
                ArrayList<Integer> s = solutions[lastPos];
                int d = (dureeSolutions.get(lastPos) == null) ? -1 : dureeSolutions.get(lastPos);
                solutions[lastPos] = solutions[lastPos-1];
                dureeSolutions.put(lastPos, dureeSolutions.get(lastPos-1));
                solutions[lastPos-1] = s;
                if (d != -1){
                    dureeSolutions.put(lastPos-1, d);
                } else {
                    dureeSolutions.remove(lastPos-1);
                }
                lastPos--;
            }
            solutions[insertPos] = solution;
            dureeSolutions.put(insertPos, duree);
        }
    }

    private boolean solutionValide(int duree){
        if (meilleuresSolutions){
            return solutions[nbSolutions-1] == null || dureeSolutions.get(nbSolutions-1) >= duree;
        } else {
            return solutions[nbSolutions-1] == null || dureeSolutions.get(nbSolutions-1) <= duree;
        }
    }
}
