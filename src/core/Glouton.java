package core;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Glouton extends BaseMoteur{

    public Glouton(int idScenario) throws FileNotFoundException {
        super(idScenario);
    }

    public Glouton(int idScenario, int nombre_solutions) throws FileNotFoundException {
        super(idScenario, nombre_solutions);
    }

    @Override
    public ArrayList<Integer>[] run(boolean isEfficace) {
        ArrayList <Integer> solution = new ArrayList<>();
        while (!scenario.getQuetesValidees().contains(0)){
            int current;
            if (isEfficace){
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

    @Override
    protected int getNextEfficace() {
        if (scenario.getAccessibleQuetes().contains(0) && joueur.getExp() >= scenario.getQuete(0).getExp()){
            return 0;
        }
        return queteLaPlusProche();
    }

    @Override
    protected int getNextExhaustif() {
        if (scenario.getAccessibleQuetes().contains(0) && scenario.getAccessibleQuetes().size() == 1){
            return 0;
        }
        return queteLaPlusProche();
    }
}
