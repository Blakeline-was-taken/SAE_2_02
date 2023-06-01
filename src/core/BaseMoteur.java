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
        return 0;
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
