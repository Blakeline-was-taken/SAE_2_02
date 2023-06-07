package main.java.core;

import main.java.modele.Quete;
import main.java.modele.Scenario;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Le moteur Lazy parcourt le scénario en faisant le moins de déplacements possibles.
 * Il génère des solutions optimales en termes de nombre de déplacements effectués.
 */
public class Lazy extends BaseMoteurOptimal {

    /**
     * TreeMap stockant les déplacements associés à chaque solution générée.
     * La clé est l'index de la solution et la valeur est le nombre de déplacements effectués.
     */
    private final TreeMap<Integer, Integer> deplacementsSolutions = new TreeMap<>();

    /**
     * Constructeur de la classe Lazy.
     *
     * @param idScenario l'identifiant du scénario
     * @throws FileNotFoundException si le fichier du scénario n'est pas trouvé
     */
    public Lazy(int idScenario) throws FileNotFoundException {
        super(idScenario);
    }

    /**
     * Constructeur de la classe Lazy.
     *
     * @param idScenario       l'identifiant du scénario
     * @param nombre_solutions le nombre de solutions à générer
     * @throws FileNotFoundException si le fichier du scénario n'est pas trouvé
     */
    public Lazy(int idScenario, int nombre_solutions) throws FileNotFoundException {
        super(idScenario, nombre_solutions);
    }

    /**
     * Exécute le moteur Lazy pour générer les solutions optimales.
     *
     * @param isEfficace    indique si le parcours doit être efficace (true) ou exhaustif (false)
     * @param bestSolutions indique si seules les meilleures solutions doivent être conservées (true) ou non (false)
     * @return un tableau contenant les solutions générées
     */
    @Override
    public ArrayList<Integer>[] run(boolean isEfficace, boolean bestSolutions) {
        meilleuresSolutions = bestSolutions;
        for (int idQuete : scenario.getAccessibleQuetes()) {
            Quete current = scenario.getQuete(idQuete);
            ArrayList<Integer> current_solution = new ArrayList<>();
            current_solution.add(idQuete);
            if (isEfficace) {
                parcoursEfficace(idQuete, current_solution, current.getExp(), joueur.distance(current));
            } else {
                parcoursExhaustif(idQuete, current_solution, joueur.distance(current));
            }
        }
        return solutions;
    }

    /**
     * Parcours efficace du scénario pour générer les solutions optimales.
     *
     * @param idQuete     l'identifiant de la quête actuelle
     * @param solution    la solution actuelle
     * @param exp         l'expérience accumulée
     * @param deplacements le nombre de déplacements effectués
     */
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

    /**
     * Parcours exhaustif du scénario pour générer les solutions optimales.
     *
     * @param idQuete     l'identifiant de la quête actuelle
     * @param solution    la solution actuelle
     * @param deplacements le nombre de déplacements effectués
     */
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

    /**
     * Insère une solution dans le tableau des solutions générées.
     *
     * @param solution    la solution à insérer
     * @param deplacements le nombre de déplacements effectués dans la solution
     */
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

    /**
     * Vérifie si une solution est valide en fonction du nombre de déplacements.
     *
     * @param deplacements le nombre de déplacements de la solution à vérifier
     * @return true si la solution est valide, false sinon
     */
    private boolean solutionValide(int deplacements){
        if (meilleuresSolutions){
            return solutions[nbSolutions-1] == null || deplacementsSolutions.get(nbSolutions-1) >= deplacements;
        } else {
            return solutions[nbSolutions-1] == null || deplacementsSolutions.get(nbSolutions-1) <= deplacements;
        }
    }
}
