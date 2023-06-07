package core;

import modele.Joueur;
import modele.Quete;
import modele.Scenario;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.TreeSet;

public abstract class BaseMoteur {

    protected final Joueur joueur;
    protected final Scenario scenario;
    protected final int nbSolutions;
    protected final ArrayList<Integer>[] solutions;
    protected boolean meilleuresSolutions;

    protected BaseMoteur(int idScenario) throws FileNotFoundException {
        joueur = new Joueur();
        scenario = new Scenario("scenario_"+idScenario+".txt");
        nbSolutions = 1;
        solutions = new ArrayList[1];
    }

    protected BaseMoteur(int idScenario, int nombre_solutions) throws FileNotFoundException {
        joueur = new Joueur();
        scenario = new Scenario("scenario_"+idScenario+".txt");
        nbSolutions = nombre_solutions;
        solutions = new ArrayList[nombre_solutions];
    }

    public int queteLaPlusProche(){
        TreeSet <Integer> quetesAccessibles = scenario.getAccessibleQuetes();
        if (quetesAccessibles.first() == 0){
            quetesAccessibles.remove(0);
        }
        int closest_id = quetesAccessibles.first();
        quetesAccessibles.remove(closest_id);
        for (int IDQuete : quetesAccessibles){
            if (IDQuete != 0 && joueur.distance(scenario.getQuete(IDQuete)) < joueur.distance(scenario.getQuete(closest_id))){
                closest_id = IDQuete;
            }
        }
        return closest_id;
    }

    public abstract ArrayList<Integer>[] run(boolean isEfficace, boolean bestSolutions);

    public Joueur getJoueur(){
        return joueur;
    }

    public Scenario getScenario(){
        return scenario;
    }

    public int getNbSolutions(){
        return nbSolutions;
    }

    public int calculDuree(ArrayList <Integer> solution){
        int duree = 0;
        Joueur j = new Joueur();
        for (int IDQuete : solution){
            Quete current = scenario.getQuete(IDQuete);
            duree += j.deplacer(current) + current.getDuree();
        }
        return duree;
    }
}
