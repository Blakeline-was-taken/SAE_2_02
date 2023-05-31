package module;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

public class Scenario {
    private final TreeMap<Integer, Quete> quetes;
    private final TreeMap<Integer, TreeSet<Integer>> successeurs;
    private final TreeSet<Integer> quetesValidees;

    public Scenario(String nomFichier) throws FileNotFoundException {
        quetes = new TreeMap<>();
        successeurs = new TreeMap<>();
        quetesValidees = new TreeSet<>();

        File fichier = new File("scenarios"+File.separator+nomFichier);
        Scanner scan = new Scanner(fichier);
        while (scan.hasNext()){
            Scanner quete = new Scanner(scan.nextLine());
            quete.useDelimiter("\\|");
            int id = quete.nextInt();

            String posStr = quete.next().replace("(", "").replace(")", "").replace(" ", "");
            Scanner posScan = new Scanner(posStr);
            posScan.useDelimiter(",");
            int [] position = {posScan.nextInt(), posScan.nextInt()};

            String preStr = quete.next().replace("(", "").replace(")", "");
            Scanner preScan = new Scanner(preStr);
            preScan.useDelimiter(",");
            int [][] precondition = new int[2][2];
            int i = 0;
            while (preScan.hasNext()){
                String current = preScan.next();
                if (!current.equals("")) {
                    precondition[i / 2][i % 2] = Integer.parseInt(current);
                }
                i++;
            }

            int duree = quete.nextInt();
            int experience = quete.nextInt();
            String intitule = quete.next();

            quetes.put(id, new Quete(position, precondition, duree, experience, intitule));
        }
        initVoisins();
    }

    private void initVoisins(){
        for (int key : quetes.keySet()){
            successeurs.put(key, new TreeSet<>());
        }
        for (int key : quetes.keySet()){
            int [][] pre = quetes.get(key).getCond();
            for (int i = 0; i < 2; i++){
                for (int j = 0; j < 2; j++){
                    if (pre[i][j] != 0){
                        successeurs.get(pre[i][j]).add(key);
                    }
                }
            }
        }
    }

    public void validerQuete(int queteId){
        quetesValidees.add(queteId);
    }

    public TreeSet <Integer> getAccessibleQuetes(){
        TreeSet <Integer> quetesAccassibles = new TreeSet<>();
        for (int IDQuete : quetes.keySet()){
            if (!quetesValidees.contains(IDQuete)){
                int[][] precond = quetes.get(IDQuete).getCond();
                boolean[] validate = new boolean[2];
                for (int i = 0; i < 2; i++){
                    validate[i] = precond[i][0] == 0 || quetesValidees.contains(precond[i][0]) || quetesValidees.contains(precond[i][1]);
                }
                if (validate[0] && validate[1]) {
                    quetesAccassibles.add(IDQuete);
                }
            }
        }
        return quetesAccassibles;
    }

    public Quete getQuete(int queteId){
        return quetes.get(queteId);
    }

    public TreeMap<Integer, Quete> getQuetes() {
        return quetes;
    }

    public TreeMap<Integer, TreeSet<Integer>> getSuccesseurs() {
        return successeurs;
    }

    public TreeSet<Integer> getQuetesValidees() {
        return quetesValidees;
    }
}
