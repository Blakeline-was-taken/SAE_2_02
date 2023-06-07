package main.java.core;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Classe représentant le moteur glouton du jeu.
 * Cette classe hérite de la classe de base BaseMoteur.
 */
public class Glouton extends BaseMoteur {

    /**
     * Constructeur de la classe Glouton.
     *
     * @param idScenario L'identifiant du scénario à charger.
     * @throws FileNotFoundException Si le fichier de scénario n'est pas trouvé.
     */
    public Glouton(int idScenario) throws FileNotFoundException {
        super(idScenario);
    }

    /**
     * Constructeur de la classe Glouton.
     *
     * @param idScenario       L'identifiant du scénario à charger.
     * @param nombre_solutions Le nombre de solutions à générer.
     * @throws FileNotFoundException Si le fichier de scénario n'est pas trouvé.
     */
    public Glouton(int idScenario, int nombre_solutions) throws FileNotFoundException {
        super(idScenario, nombre_solutions);
    }

    /**
     * Exécute le moteur glouton pour générer les solutions.
     *
     * @param isEfficace     Indique si le mode de génération est efficace.
     * @param bestSolutions  Indique si les meilleures solutions doivent être générées.
     * @return Un tableau de listes d'entiers représentant les solutions générées.
     */
    @Override
    public ArrayList<Integer>[] run(boolean isEfficace, boolean bestSolutions) {
        ArrayList<Integer> solution = new ArrayList<>();
        while (!scenario.getQuetesValidees().contains(0)) {
            int current;
            if (isEfficace) {
                current = getNextEfficace();
            } else {
                current = getNextExhaustif();
            }
            joueur.deplacer(scenario.getQuete(current));
            joueur.addExp(scenario.getQuete(current).getExp());
            scenario.validerQuete(current);
            solution.add(current);
        }
        solutions[0] = solution;
        return solutions;
    }

    /**
     * Retourne l'identifiant de la prochaine quête à sélectionner de manière efficace.
     *
     * @return L'identifiant de la prochaine quête à sélectionner.
     */
    protected int getNextEfficace() {
        if (scenario.getAccessibleQuetes().contains(0) && joueur.getExp() >= scenario.getQuete(0).getExp()) {
            return 0;
        }
        return queteLaPlusProche();
    }

    /**
     * Retourne l'identifiant de la prochaine quête à sélectionner de manière exhaustive.
     *
     * @return L'identifiant de la prochaine quête à sélectionner.
     */
    protected int getNextExhaustif() {
        if (scenario.getAccessibleQuetes().contains(0) && scenario.getAccessibleQuetes().size() == 1) {
            return 0;
        }
        return queteLaPlusProche();
    }
}

