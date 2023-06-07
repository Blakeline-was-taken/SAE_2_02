package main.java.core;

import java.io.FileNotFoundException;

/**
 * Classe abstraite représentant une base pour les moteurs optimaux du jeu.
 * Cette classe étend la classe de base BaseMoteur.
 */
public abstract class BaseMoteurOptimal extends BaseMoteur {

    /**
     * Le nom du scénario associé au moteur.
     */
    protected final String scenarioName;

    /**
     * Constructeur de la classe BaseMoteurOptimal.
     *
     * @param idScenario L'identifiant du scénario à charger.
     * @throws FileNotFoundException Si le fichier de scénario n'est pas trouvé.
     */
    protected BaseMoteurOptimal(int idScenario) throws FileNotFoundException {
        super(idScenario);
        scenarioName = "scenario_" + idScenario + ".txt";
    }

    /**
     * Constructeur de la classe BaseMoteurOptimal.
     *
     * @param idScenario       L'identifiant du scénario à charger.
     * @param nombre_solutions Le nombre de solutions à générer.
     * @throws FileNotFoundException Si le fichier de scénario n'est pas trouvé.
     */
    protected BaseMoteurOptimal(int idScenario, int nombre_solutions) throws FileNotFoundException {
        super(idScenario, nombre_solutions);
        scenarioName = "scenario_" + idScenario + ".txt";
    }
}

