package core;

import modele.Joueur;
import modele.Quete;
import modele.Scenario;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Classe abstraite représentant une base pour les moteurs du jeu.
 * Cette classe contient les fonctionnalités communes aux moteurs de jeu spécifiques.
 */
public abstract class BaseMoteur {

    /**
     * Le joueur associé au moteur.
     */
    protected final Joueur joueur;

    /**
     * Le scénario associé au moteur.
     */
    protected final Scenario scenario;

    /**
     * Le nombre de solutions possibles.
     */
    protected final int nbSolutions;

    /**
     * Les solutions possibles.
     */
    protected final ArrayList<Integer>[] solutions;

    /**
     * Indique si les meilleures solutions doivent être recherchées.
     */
    protected boolean meilleuresSolutions;

    /**
     * Constructeur de la classe BaseMoteur.
     *
     * @param idScenario L'identifiant du scénario à charger.
     * @throws FileNotFoundException Si le fichier de scénario n'est pas trouvé.
     */
    protected BaseMoteur(int idScenario) throws FileNotFoundException {
        joueur = new Joueur();
        scenario = new Scenario("scenario_" + idScenario + ".txt");
        nbSolutions = 1;
        solutions = new ArrayList[1];
    }

    /**
     * Constructeur de la classe BaseMoteur.
     *
     * @param idScenario       L'identifiant du scénario à charger.
     * @param nombre_solutions Le nombre de solutions à générer.
     * @throws FileNotFoundException Si le fichier de scénario n'est pas trouvé.
     */
    protected BaseMoteur(int idScenario, int nombre_solutions) throws FileNotFoundException {
        joueur = new Joueur();
        scenario = new Scenario("scenario_" + idScenario + ".txt");
        nbSolutions = nombre_solutions;
        solutions = new ArrayList[nombre_solutions];
    }

    /**
     * Retourne l'identifiant de la quête la plus proche du joueur.
     *
     * @return L'identifiant de la quête la plus proche.
     */
    public int queteLaPlusProche() {
        TreeSet<Integer> quetesAccessibles = scenario.getAccessibleQuetes();
        if (quetesAccessibles.first() == 0) {
            quetesAccessibles.remove(0);
        }
        int closest_id = quetesAccessibles.first();
        quetesAccessibles.remove(closest_id);
        for (int IDQuete : quetesAccessibles) {
            if (IDQuete != 0 && joueur.distance(scenario.getQuete(IDQuete)) < joueur.distance(scenario.getQuete(closest_id))) {
                closest_id = IDQuete;
            }
        }
        return closest_id;
    }

    /**
     * Méthode abstraite qui exécute le moteur du jeu.
     *
     * @param isEfficace      Indique si le moteur doit être exécuté de manière efficace.
     * @param bestSolutions   Indique si les meilleures solutions doivent être recherchées.
     * @return Les solutions générées par le moteur.
     */
    public abstract ArrayList<Integer>[] run(boolean isEfficace, boolean bestSolutions);

    /**
     * Retourne le joueur associé au moteur.
     *
     * @return Le joueur associé au moteur.
     */
    public Joueur getJoueur() {
        return joueur;
    }

    /**
     * Retourne le scénario associé au moteur.
     *
     * @return Le scénario associé au moteur.
     */
    public Scenario getScenario() {
        return scenario;
    }

    /**
     * Retourne le nombre de solutions possibles.
     *
     * @return Le nombre de solutions possibles.
     */
    public int getNbSolutions() {
        return nbSolutions;
    }

    /**
     * Calcule la durée totale d'une solution.
     *
     * @param solution La solution pour laquelle calculer la durée.
     * @return La durée totale de la solution.
     */
    public int calculDuree(ArrayList<Integer> solution) {
        int duree = 0;
        Joueur j = new Joueur();
        for (int IDQuete : solution) {
            Quete current = scenario.getQuete(IDQuete);
            duree += j.deplacer(current) + current.getDuree();
        }
        return duree;
    }
}
