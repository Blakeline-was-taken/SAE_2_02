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
        ArrayList<Integer> solution = new ArrayList<>();
        if (isEfficace) {
            // Solution efficace
            TreeMap<Integer, Integer> chemin = dijkstra(0);

            // On utilise le chemin pour générer la solution, et dans le même temps on regard le total d'exp
            int totalExp = 0;
            int lastID = chemin.get(-1);
            int requiredExp = scenario.getQuete(lastID).getExp();
            while (lastID != 0) {
                solution.add(lastID);
                totalExp += scenario.getQuete(lastID).getExp();
                lastID = chemin.get(lastID);
            }
            solution.add(lastID);

            // Si nous n'avons pas assez d'exp pour faire la quête finale, on essaye de rajouter une quête à la solution
            // de sorte à ce qu'elle reste optimale en terme de durée
            while (totalExp < requiredExp) {
                scenario.clearQuetesValidees();
                joueur.deplacer(new int[]{0, 0});

                // [Id de la quête à rajouter, Durée ajoutée par le détour que cause l'ajout de la quête,
                //  Index de la position où insérer la quête dans la solution, Exp ajouté par la quête]
                int[] bonus = new int[4];
                for (int IDQuete : solution) {
                    if (IDQuete != 0) {
                        joueur.deplacer(scenario.getQuete(IDQuete));
                        scenario.validerQuete(IDQuete);
                        int IDBonus = queteLaPlusProche();
                        Quete bonusQuete = scenario.getQuete(IDBonus);
                        int duree = joueur.deplacer(bonusQuete) + bonusQuete.getDuree() + scenario.getQuete(IDQuete + 1).distance(bonusQuete);
                        // On ajoute une quête uniquement elle permets davoir assez d'exp et qu'elle cause le moins grand détour
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
            // Solution exhaustive
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
            // La valeur vaut 0 si l'identifiant est celui du début, sinon elle vaut infini.
            int value = (id == queteDebut) ? 0 : Integer.MAX_VALUE;
            dist.put(id, value);
        }
        ArrayList<Integer> restants = new ArrayList<>();
        restants.add(queteDebut);

        while (!restants.isEmpty()) {
            // On prends la première quête de la liste
            int IDCurrent = restants.get(0);
            Quete current = scenario.getQuete(IDCurrent);
            restants.remove(0);

            int[][] precond = current.getCond();
            // Si une quête est présente dans la deuxième liste, alors cette quête requiert plusieurs quêtes
            // pour pouvoir être faite.
            if (precond[1][0] != 0) {
                ArrayList<Integer>[] chemins = new ArrayList[2];
                // On parcours le reste du scenario pour chacune des quêtes présentes dans les préconditions
                for (int i = 0; i < 2; i++) {
                    TreeMap<Integer, Integer> chemin = dijkstra(precond[i][0]);
                    if (precond[i][1] != 0) {
                        // Dans le cas où on peut faire une quête ou une autre, on va regarder quel chemin prends le
                        // moins de temps et garder celui-là.
                        TreeMap<Integer, Integer> chemin2 = dijkstra(precond[i][1]);
                        if (calculDuree(solutionDePredecesseur(chemin)) > calculDuree(solutionDePredecesseur(chemin2))) {
                            chemin = chemin2;
                        }
                    }
                    chemins[i] = solutionDePredecesseur(chemin);
                }
                // Maintenant qu'on a nos deux chemins optimaux, on va essayer de les assembler de la meilleure façon possible.
                TreeMap<Integer, Integer> predRestants = new TreeMap<>();
                int nextKey = -1;
                while (!chemins[0].isEmpty() && !chemins[1].isEmpty()) {
                    if (chemins[0].get(0).equals(chemins[1].get(0))) {
                        // Si les deux prochaines quêtes sont les mêmes, on peut rejoindre les chemins.
                        predRestants.put(nextKey, chemins[0].get(0));
                        nextKey = predRestants.get(nextKey);
                        chemins[0].remove(0);
                        chemins[1].remove(0);
                    } else {
                        int idQuete0 = chemins[0].get(0);
                        int idQuete1 = chemins[1].get(0);
                        // On regarde quelle quête est la plus proche de la dernière (ou du point de départ)
                        boolean plusProche0;
                        if (nextKey == -1) {
                            int min_dist = Integer.min(joueur.distance(scenario.getQuete(idQuete0)), joueur.distance(scenario.getQuete(idQuete1)));
                            plusProche0 = joueur.distance(scenario.getQuete(idQuete0)) == min_dist;
                        } else {
                            Quete nextQuete = scenario.getQuete(nextKey);
                            int min_dist = Integer.min(nextQuete.distance(scenario.getQuete(idQuete0)), nextQuete.distance(scenario.getQuete(idQuete1)));
                            plusProche0 = nextQuete.distance(scenario.getQuete(idQuete0)) == min_dist;
                        }
                        // On ajoute la quête la plus proche au chemin
                        if (plusProche0) {
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
                // On ajoute le reste du chemin où il reste encore des quêtes à la solution
                while (!chemins[0].isEmpty()) {
                    predRestants.put(nextKey, chemins[0].get(0));
                    nextKey = predRestants.get(nextKey);
                    chemins[0].remove(0);
                }
                while (!chemins[1].isEmpty()) {
                    predRestants.put(nextKey, chemins[1].get(0));
                    nextKey = predRestants.get(nextKey);
                    chemins[1].remove(0);
                }
                // On ajoute les prédécesseurs générés à ceux qu'on avait déjà trouvés
                for (int key : predRestants.keySet()) {
                    pred.put(key, predRestants.get(key));
                }
                pred.put(nextKey, IDCurrent);
                // On peut terminer la boucle étant donné qu'on a tous les prédecesseurs depuis le début jusqu'à la fin
                restants.clear();
            } else if (precond[0][0] == 0) {
                // S'il n'y a pas de précondition alors on a trouvé le début
                dist.put(-1, current.distance(joueur) + current.getDuree());
                pred.put(-1, IDCurrent);
            } else {
                // On applique l'algorithme de dijkstra aux préconditions
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

    private ArrayList<Integer> solutionDePredecesseur(TreeMap<Integer, Integer> pred) {
        ArrayList<Integer> solution = new ArrayList<>();
        int LastID = pred.get(-1);
        while (LastID != 0 && pred.containsKey(LastID)) {
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
        if (scenario.getAccessibleQuetes().contains(0) && scenario.getAccessibleQuetes().size() == 1) {
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
