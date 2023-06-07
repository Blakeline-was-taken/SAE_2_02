package core;

import java.io.FileNotFoundException;

public abstract class BaseMoteurOptimal extends BaseMoteur{
    protected final String scenarioName;

    protected BaseMoteurOptimal(int idScenario) throws FileNotFoundException {
        super(idScenario);
        scenarioName = "scenario_"+idScenario+".txt";
    }

    protected BaseMoteurOptimal(int idScenario, int nombre_solutions) throws FileNotFoundException {
        super(idScenario, nombre_solutions);
        scenarioName = "scenario_"+idScenario+".txt";
    }
}
