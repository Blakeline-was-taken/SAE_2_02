package core;

import module.Joueur;
import module.Quete;
import module.Scenario;

import java.io.FileNotFoundException;
import java.net.IDN;
import java.util.TreeSet;

public abstract class BaseMoteur {

    protected final Joueur joueur;
    protected final Scenario scenario;

    protected BaseMoteur(int idScenario) throws FileNotFoundException {
        joueur = new Joueur();
        scenario = new Scenario("scenario_"+idScenario+".txt");
    }

    public int queteLaPlusProche(){
        TreeSet <Integer> quetesAccessibles = scenario.getAccessibleQuetes();
        int closest_id = quetesAccessibles.first();
        quetesAccessibles.remove(closest_id);
        for (int IDQuete : quetesAccessibles){
            if (joueur.distance(scenario.getQuete(IDQuete)) < joueur.distance(scenario.getQuete(closest_id))){
                closest_id = IDQuete;
            }
        }
        return closest_id;
    }

    public abstract int[] run(boolean isEfficace);

    protected abstract int getNextEfficace();

    protected abstract int getNextExhaustif();

    public Joueur getJoueur(){
        return joueur;
    }

    public Scenario getScenario(){
        return scenario;
    }
}
