package core;

import module.Quete;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.TreeMap;

public class Speedrun extends BaseMoteur {

    public Speedrun(int idScenario) throws FileNotFoundException {
        super(idScenario);
    }

    @Override
    public ArrayList<Integer> run(boolean isEfficace) {
        ArrayList <Integer> solution = new ArrayList<>();
        if (isEfficace) {
            TreeMap<Integer, Integer> chemin = dijkstra(0);

            int totalExp = 0;
            int LastID = chemin.get(-1);
            int requiredExp = scenario.getQuete(LastID).getExp();
            while (LastID != 0){
                solution.add(LastID);
                totalExp += scenario.getQuete(LastID).getExp();
                LastID = chemin.get(LastID);
            }
            solution.add(LastID);
            while (totalExp < requiredExp){
                // Pas assez d'exp pour faire la quête finale
                scenario.clearQuetesValidees();
                joueur.deplacer(new int[] {0, 0});

                // [Identifiant de la quête, Durée ajoutée par la quête, Index de position de la quête, Exp rapporté par la quête]
                int [] bonus = new int[4];
                for (int IDQuete : solution){
                    if (IDQuete != 0) {
                        joueur.deplacer(scenario.getQuete(IDQuete));
                        int IDBonus = queteLaPlusProche();
                        Quete bonusQuete = scenario.getQuete(IDBonus);
                        int duree = joueur.deplacer(bonusQuete) + bonusQuete.getDuree() + scenario.getQuete(IDQuete + 1).distance(bonusQuete);
                        if (bonus[0] == 0 || totalExp + bonusQuete.getExp() >= requiredExp && bonus[1] > duree) {
                            bonus[0] = IDBonus;
                            bonus[1] = duree;
                            bonus[2] = IDQuete + 1;
                            bonus[3] = bonusQuete.getExp();
                        }
                    }
                }
                totalExp += bonus[3];
                solution.add(bonus[2], bonus[0]);
            }
        } else {
            while (!scenario.getQuetesValidees().contains(0)) {
                int current = getNextExhaustif();
                joueur.deplacer(scenario.getQuete(current));
                joueur.addExp(scenario.getQuete(current).getExp());
                scenario.validerQuete(current);
                solution.add(current);
            }
        }
        return solution;
    }

    private TreeMap<Integer, Integer> dijkstra(int queteDebut) {
        TreeMap<Integer, Integer> pred = new TreeMap<>();
        TreeMap<Integer, Integer> dist = new TreeMap<>();
        for (int id : scenario.getQuetes().keySet()) {
            int value;
            if (id == queteDebut) {
                value = 0;
            } else {
                value = Integer.MAX_VALUE;
            }
            dist.put(id, value);
        }
        ArrayList<Integer> restants = new ArrayList<>();
        restants.add(queteDebut);

        while (restants.size() > 0) {
            int IDCurrent = restants.get(0);
            Quete current = scenario.getQuete(IDCurrent);
            restants.remove(0);

            int[][] precond = current.getCond();
            if (precond[1][0] != 0) {
                ArrayList[] chemins = new ArrayList[2];
                for (int i = 0; i < 2; i++) {
                    TreeMap<Integer, Integer> chemin = dijkstra(precond[i][0]);
                    if (precond[i][1] != 0){
                        TreeMap<Integer, Integer> chemin2 = dijkstra(precond[i][1]);
                        if (calculDuree(solutionDePredecesseur(chemin)) > calculDuree(solutionDePredecesseur(chemin2))){
                            chemin = chemin2;
                        }
                    }
                    chemins[i] = solutionDePredecesseur(chemin);
                }
                TreeMap<Integer, Integer> predRestants = new TreeMap<>();
                int nextKey = -1;
                while (chemins[0].size() > 0 && chemins[1].size() > 0){
                    if (chemins[0].get(0) == chemins[1].get(0)){
                        predRestants.put(nextKey, (int) chemins[0].get(0));
                        nextKey = predRestants.get(nextKey);
                        chemins[0].remove(0);
                        chemins[1].remove(0);
                    } else {
                        int idQuete0 = (int) chemins[0].get(0);
                        int idQuete1 = (int) chemins[1].get(0);
                        boolean plusProche0;
                        if (nextKey == -1) {
                            int min_dist = Integer.min(joueur.distance(scenario.getQuete(idQuete0)), joueur.distance(scenario.getQuete(idQuete1)));
                            plusProche0 = joueur.distance(scenario.getQuete(idQuete0)) == min_dist;
                        } else {
                            Quete nextQuete = scenario.getQuete(nextKey);
                            int min_dist = Integer.min(nextQuete.distance(scenario.getQuete(idQuete0)), nextQuete.distance(scenario.getQuete(idQuete1)));
                            plusProche0 = nextQuete.distance(scenario.getQuete(idQuete0)) == min_dist;
                        }
                        if (plusProche0){
                            predRestants.put(nextKey, idQuete0);
                            nextKey = predRestants.get(nextKey);
                            chemins[0].remove(0);
                        } else {
                            predRestants.put(nextKey, idQuete1);
                            nextKey = predRestants.get(nextKey);
                            chemins[1].remove(0);
                        }
                    }
                }
                while (chemins[0].size() > 0){
                    predRestants.put(nextKey, (int) chemins[0].get(0));
                    nextKey = predRestants.get(nextKey);
                    chemins[0].remove(0);
                }
                while (chemins[1].size() > 0){
                    predRestants.put(nextKey, (int) chemins[1].get(0));
                    nextKey = predRestants.get(nextKey);
                    chemins[1].remove(0);
                }
                for (int key : predRestants.keySet()){
                    pred.put(key, predRestants.get(key));
                }
                pred.put(nextKey, IDCurrent);
                restants.clear();
            } else if (precond[0][0] == 0) {
                dist.put(-1, current.distance(joueur) + current.getDuree());
                pred.put(-1, IDCurrent);
            } else {
                for (int i = 0; i < 2; i++) {
                    int IDQuete = precond[0][i];
                    if (IDQuete != 0) {
                        int distance = dist.get(IDCurrent) + current.getDuree() + current.distance(scenario.getQuete(IDQuete));
                        if (distance < dist.get(IDQuete)) {
                            dist.put(IDQuete, distance);
                            pred.put(IDQuete, IDCurrent);
                            if (!restants.contains(IDQuete)) {
                                restants.add(IDQuete);
                            }
                        }
                    }
                }
            }
        }
        return pred;
    }

    private ArrayList<Integer> solutionDePredecesseur(TreeMap<Integer, Integer> pred){
        ArrayList <Integer> solution = new ArrayList<>();
        int LastID = pred.get(-1);
        while (LastID != 0 && pred.get(LastID) != null){
            solution.add(LastID);
            LastID = pred.get(LastID);
        }
        solution.add(LastID);
        return solution;
    }

    @Override
    protected int getNextEfficace() {
        return 0;
    }

    @Override
    protected int getNextExhaustif() {
        if (scenario.getAccessibleQuetes().contains(0) && scenario.getAccessibleQuetes().size() == 1){
            return 0;
        }
        int queteLaPlusCourte = 0;
        int dureeLaPlusCourte = Integer.MAX_VALUE;
        for (int IDQuete : scenario.getAccessibleQuetes()) {
            if (IDQuete != 0) {
                Quete current = scenario.getQuete(IDQuete);
                int duree = joueur.distance(current) + current.getDuree();
                if (queteLaPlusCourte == 0 || dureeLaPlusCourte > duree) {
                    queteLaPlusCourte = IDQuete;
                    dureeLaPlusCourte = duree;
                }
            }
        }
        return queteLaPlusCourte;
    }
}
