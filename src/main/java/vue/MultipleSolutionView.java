package vue;

import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import modele.Joueur;
import modele.Quete;
import modele.Scenario;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * La classe MultipleSolutionView représente la vue affichant les solutions multiples d'une quête.
 * Elle hérite de la classe VBox.
 */
public class MultipleSolutionView extends VBox {

    /**
     * Constructeur de la classe MultipleSolutionView.
     *
     * @param solutions Les solutions multiples à afficher.
     * @throws FileNotFoundException si le fichier n'est pas trouvé.
     */
    public MultipleSolutionView(ArrayList<Integer>[] solutions) throws FileNotFoundException {
        setPadding(new Insets(15));

        TableView<SolutionView> solutionTab = new TableView<>();

        TableColumn<SolutionView, Integer> classementSol = new TableColumn<>("Classement");
        classementSol.setCellValueFactory(new PropertyValueFactory<>("classement"));
        classementSol.setPrefWidth(100);

        TableColumn<SolutionView, Integer> solutionSol = new TableColumn<>("Solution");
        solutionSol.setCellValueFactory(new PropertyValueFactory<>("solutionView"));
        solutionSol.setPrefWidth(500);

        TableColumn<SolutionView, Integer> dureeSol = new TableColumn<>("Durée");
        dureeSol.setCellValueFactory(new PropertyValueFactory<>("duree"));
        dureeSol.setPrefWidth(100);

        TableColumn<SolutionView, Integer> distSol = new TableColumn<>("Distance");
        distSol.setCellValueFactory(new PropertyValueFactory<>("distance"));
        distSol.setPrefWidth(100);

        TableColumn<SolutionView, Integer> expSol = new TableColumn<>("Expérience");
        expSol.setCellValueFactory(new PropertyValueFactory<>("exp"));
        expSol.setPrefWidth(100);

        solutionTab.getColumns().addAll(classementSol, solutionSol, dureeSol, distSol, expSol);
        solutionTab.setPrefHeight(700);
        solutionTab.setPrefWidth(1002);

        for (TableColumn<SolutionView, ?> col : solutionTab.getColumns()) {
            col.setResizable(false);
            col.setReorderable(false);
        }

        for (int i = 0; i < solutions.length && solutions[i] != null; i++) {
            SolutionView currentSol = new SolutionView(i + 1, solutions[i], Root.getSelectedScn());
            solutionTab.getItems().add(currentSol);
        }

        getChildren().add(solutionTab);
    }

    /**
     * La classe SolutionView représente une vue d'une solution dans la table des solutions multiples.
     */
    public class SolutionView {
        private final int classement;
        private final ArrayList<Integer> solution;
        private int duree;
        private int distance;
        private int exp;

        /**
         * Constructeur de la classe SolutionView.
         *
         * @param parClassement Le classement de la solution.
         * @param parSolution   La liste des identifiants de quêtes de la solution.
         * @param parScn        Le scénario sélectionné.
         * @throws FileNotFoundException si le fichier n'est pas trouvé.
         */
        public SolutionView(int parClassement, ArrayList<Integer> parSolution, String parScn) throws FileNotFoundException {
            classement = parClassement;
            solution = parSolution;

            Scenario scn = new Scenario(parScn);
            Joueur player = new Joueur();
            duree = 0;
            distance = 0;
            exp = 0;

            for (int idQuete : solution) {
                Quete currentQuest = scn.getQuete(idQuete);
                int currentDist = player.deplacer(currentQuest);
                distance = distance + currentDist;
                duree = duree + currentDist + currentQuest.getDuree();
                if (idQuete != 0) {
                    exp = exp + currentQuest.getExp();
                }
            }
        }

        /**
         * Obtient le classement de la solution.
         *
         * @return Le classement de la solution.
         */
        public int getClassement() {
            return classement;
        }

        /**
         * Obtient la vue de la solution.
         *
         * @return La vue de la solution.
         */
        public String getSolutionView() {
            StringBuilder sol = new StringBuilder();
            int i = 0;
            for (int id : solution) {
                i++;
                if (id != 0) {
                    sol.append(id).append(" -> ");
                } else {
                    sol.append(id);
                }
                if (i % 10 == 0) {
                    sol.append("\n");
                }
            }
            return sol.toString();
        }

        /**
         * Obtient la durée de la solution.
         *
         * @return La durée de la solution.
         */
        public int getDuree() {
            return duree;
        }

        /**
         * Obtient la distance de la solution.
         *
         * @return La distance de la solution.
         */
        public int getDistance() {
            return distance;
        }

        /**
         * Obtient l'expérience de la solution.
         *
         * @return L'expérience de la solution.
         */
        public int getExp() {
            return exp;
        }
    }
}
