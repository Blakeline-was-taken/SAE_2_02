package modele;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * La classe Scenario représente un scénario contenant des quêtes, leurs préconditions et leurs dépendances.
 * Elle permet de charger et de manipuler les données d'un scénario à partir d'un fichier.
 */
public class Scenario {

    /**
     * Les quêtes du scénario, indexées par leur identifiant.
     */
    private final TreeMap<Integer, Quete> quetes;

    /**
     * Les successeurs de chaque quête, représentés par un ensemble d'identifiants de quêtes.
     * Les successeurs d'une quête sont les quêtes qui ont cette quête comme précondition.
     */
    private final TreeMap<Integer, TreeSet<Integer>> successeurs;

    /**
     * Les identifiants des quêtes validées dans le scénario.
     */
    private final TreeSet<Integer> quetesValidees;

    /**
     * Constructeur de la classe Scenario.
     * Charge les données du scénario à partir du fichier spécifié.
     *
     * @param nomFichier String - Le nom du fichier contenant les données du scénario.
     * @throws FileNotFoundException - Si le fichier spécifié n'est pas trouvé.
     */
    public Scenario(String nomFichier) throws FileNotFoundException {
        quetes = new TreeMap<>();
        successeurs = new TreeMap<>();
        quetesValidees = new TreeSet<>();

        File fichier = new File("scenarios" + File.separator + nomFichier);
        Scanner scan = new Scanner(fichier);
        while (scan.hasNext()) {
            Scanner quete = new Scanner(scan.nextLine());
            quete.useDelimiter("\\|");
            int id = quete.nextInt();

            String posStr = quete.next().replace("(", "").replace(")", "").replace(" ", "");
            Scanner posScan = new Scanner(posStr);
            posScan.useDelimiter(",");
            int[] position = {posScan.nextInt(), posScan.nextInt()};

            String preStr = quete.next().replace("(", "").replace(")", "").replace(" ", "");
            Scanner preScan = new Scanner(preStr);
            preScan.useDelimiter(",");
            int[][] precondition = new int[2][2];
            int i = 0;
            while (preScan.hasNext()) {
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

    /**
     * Initialise les voisins des quêtes en fonction de leurs préconditions.
     */
    private void initVoisins() {
        for (int key : quetes.keySet()) {
            successeurs.put(key, new TreeSet<>());
        }
        for (int key : quetes.keySet()) {
            int[][] pre = quetes.get(key).getCond();
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    if (pre[i][j] != 0) {
                        successeurs.get(pre[i][j]).add(key);
                    }
                }
            }
        }
    }

    /**
     * Valide une quête en ajoutant son identifiant à la liste des quêtes validées.
     *
     * @param queteId int - L'identifiant de la quête à valider.
     */
    public void validerQuete(int queteId) {
        quetesValidees.add(queteId);
    }

    /**
     * Renvoie un ensemble contenant les identifiants des quêtes accessibles.
     * Une quête est accessible si toutes ses préconditions sont satisfaites.
     *
     * @return TreeSet<Integer> - L'ensemble des identifiants des quêtes accessibles.
     */
    public TreeSet<Integer> getAccessibleQuetes() {
        TreeSet<Integer> quetesAccessibles = new TreeSet<>();
        for (int IDQuete : quetes.keySet()) {
            if (!quetesValidees.contains(IDQuete)) {
                int[][] precond = quetes.get(IDQuete).getCond();
                boolean[] validate = new boolean[2];
                for (int i = 0; i < 2; i++) {
                    validate[i] = precond[i][0] == 0 || quetesValidees.contains(precond[i][0]) || quetesValidees.contains(precond[i][1]);
                }
                if (validate[0] && validate[1]) {
                    quetesAccessibles.add(IDQuete);
                }
            }
        }
        return quetesAccessibles;
    }

    /**
     * Renvoie la quête correspondant à l'identifiant spécifié.
     *
     * @param queteId int - L'identifiant de la quête.
     * @return Quete - La quête correspondante.
     */
    public Quete getQuete(int queteId) {
        return quetes.get(queteId);
    }

    /**
     * Renvoie la liste des quêtes du scénario.
     *
     * @return TreeMap<Integer, Quete> - La liste des quêtes.
     */
    public TreeMap<Integer, Quete> getQuetes() {
        return quetes;
    }

    /**
     * Renvoie la liste des successeurs de chaque quête.
     * Les successeurs d'une quête sont les quêtes qui ont cette quête comme précondition.
     *
     * @return TreeMap<Integer, TreeSet<Integer>> - La liste des successeurs de chaque quête.
     */
    public TreeMap<Integer, TreeSet<Integer>> getSuccesseurs() {
        return successeurs;
    }

    /**
     * Renvoie la liste des quêtes validées.
     *
     * @return TreeSet<Integer> - La liste des quêtes validées.
     */
    public TreeSet<Integer> getQuetesValidees() {
        return quetesValidees;
    }

    /**
     * Efface la liste des quêtes validées.
     */
    public void clearQuetesValidees() {
        quetesValidees.clear();
    }
}
