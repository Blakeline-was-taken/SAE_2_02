package core;

import module.Joueur;
import module.Quete;
import module.Scenario;

public abstract class BaseMoteur {

    protected final Joueur joueur;
    protected final Scenario scenario;

    protected BaseMoteur(int idScenario){

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
