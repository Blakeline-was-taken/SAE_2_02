package vue;

import controleur.ScenarioControleur;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Classe représentant la racine de l'interface graphique.
 */
public class Root extends HBox {
    private static ObservableList<Node> rightField;
    private static final ScenarioControleur scnControleur = new ScenarioControleur();
    private static String selectedScn;
    private final static VBox leftField = new VBox(20);

    /**
     * Constructeur de la classe Root.
     */
    public Root() {
        HBox panel = new HBox(50);
        rightField = panel.getChildren();
        initSelectionView();
        getChildren().addAll(leftField, panel);
    }

    /**
     * Initialise la vue de sélection des scénarios.
     */
    public static void initSelectionView(){
        leftField.getChildren().clear();
        rightField.clear();
        leftField.setPadding(new Insets(30));
        leftField.setAlignment(Pos.TOP_CENTER);

        Label scnLbl = new Label("Sélectionnez\nun scénario :");
        leftField.getChildren().add(scnLbl);
        ToggleGroup scnGroup = new ToggleGroup();
        for (int i = 0; i<11; i++){
            ToggleButton btn = new ToggleButton("scénario " + i);
            scnGroup.getToggles().add(btn);
            leftField.getChildren().add(btn);
            btn.setUserData("scenario_" + i + ".txt");
            btn.setOnAction(scnControleur);
        }
    }

    /**
     * Initialise la vue de la solution.
     * @param mode Le mode de parcours.
     * @param methode La méthode de parcours.
     */
    public static void initSolutionView(String mode, String methode){
        leftField.getChildren().clear();
        rightField.clear();
        leftField.setPadding(new Insets(10));
        leftField.setAlignment(Pos.CENTER);

        VBox scnBox = createVBoxParametre(getSelectedScn().replace(".txt", "").replace("_", " "), "Scénario sélectionné :");
        VBox modeBox = createVBoxParametre(mode, "Mode de parcours :");
        VBox methodeBox = createVBoxParametre(methode, "Méthode de parcours :");

        Button newSolution = new Button("Nouvelle simulation");
        newSolution.setOnAction(ModeSelection.getModeControleur());
        newSolution.setUserData("New");

        leftField.getChildren().addAll(scnBox, modeBox, methodeBox, newSolution);
    }

    /**
     * Initialise la vue de la solution avec des paramètres supplémentaires.
     * @param mode Le mode de parcours.
     * @param methode La méthode de parcours.
     * @param opti Le type d'optimisation.
     * @param nbSol Le nombre de solutions.
     * @param typeSol Le type de solutions.
     */
    public static void initSolutionView(String mode, String methode, String opti, String nbSol, String typeSol){
        leftField.getChildren().clear();
        rightField.clear();
        leftField.setPadding(new Insets(10));
        leftField.setAlignment(Pos.CENTER);

        VBox scnBox = createVBoxParametre(getSelectedScn().replace(".txt", "").replace("_", " "), "Scénario sélectionné :");
        VBox modeBox = createVBoxParametre(mode, "Mode de parcours :");
        VBox optiBox = createVBoxParametre(opti, "Type d'optimisation :");
        VBox methodeBox = createVBoxParametre(methode, "Méthode de parcours :");
        VBox nbSolBox;
        if (Integer.parseInt(nbSol) >= 1999999) {
            nbSolBox = createVBoxParametre("Toutes", "Nombre de solutions :");
        } else {
            nbSolBox = createVBoxParametre(nbSol, "Nombre de solutions :");
        }
        VBox typeSolBox = createVBoxParametre(typeSol, "Type de solutions :");

        Button newSolution = new Button("Nouvelle simulation");
        newSolution.setOnAction(ModeSelection.getModeControleur());
        newSolution.setUserData("New");

        leftField.getChildren().addAll(scnBox, modeBox, optiBox, methodeBox, typeSolBox, nbSolBox, newSolution);
    }

    /**
     * Crée un VBox contenant un paramètre et son intitulé.
     * @param param La valeur du paramètre.
     * @param intitule L'intitulé du paramètre.
     * @return Le VBox contenant le paramètre.
     */
    private static VBox createVBoxParametre(String param, String intitule){
        VBox paramBox = new VBox();
        Label paramTitleLbl = new Label(intitule);
        Label paramLbl = new Label(param);
        paramLbl.setStyle("-fx-text-fill: #3b3bb4; -fx-font-weight: bold");
        paramBox.setPadding(new Insets(15));
        paramBox.getChildren().addAll(paramTitleLbl, paramLbl);
        return paramBox;
    }

    /**
     * Définit le scénario sélectionné.
     * @param scnStr Le nom du scénario sélectionné.
     */
    public static void setSelectedScn(String scnStr){
        selectedScn = scnStr;
    }

    /**
     * Obtient le scénario sélectionné.
     * @return Le nom du scénario sélectionné.
     */
    public static String getSelectedScn(){
        return selectedScn;
    }

    /**
     * Obtient le contrôleur du scénario.
     * @return Le contrôleur du scénario.
     */
    public static ScenarioControleur getScnControleur() {
        return scnControleur;
    }

    /**
     * Obtient les éléments de l'interface situés à droite.
     * @return Les éléments de l'interface situés à droite.
     */
    public static ObservableList<Node> getRightField(){
        return rightField;
    }
}
