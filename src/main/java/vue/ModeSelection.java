package vue;

import controleur.ModeSelectionControleur;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * La classe ModeSelection représente la vue permettant de sélectionner le mode de parcours.
 * Elle hérite de la classe HBox.
 */
public class ModeSelection extends HBox {
    private static final ModeSelectionControleur modeControleur = new ModeSelectionControleur();
    private static ObservableList<Node> afterModeField = (new HBox()).getChildren();
    private static ObservableList<Node> afterMethodField = (new HBox()).getChildren();
    private static ObservableList<Node> afterOptiField = (new HBox()).getChildren();
    private static ObservableList<Node> afterNbSolField = (new HBox()).getChildren();
    private static String selectedMode;
    private static String selectedMethod;
    private static String selectedOpti;
    private static int selectedNbSol;
    private static String selectedTypeSol;
    private static ComboBox<String> typeCmb;
    private static TextField nbSolTxt;

    /**
     * Constructeur de la classe ModeSelection.
     */
    public ModeSelection() {
        VBox modeCol = new VBox(50);
        modeCol.setPadding(new Insets(30));

        Label modeLbl = new Label("Sélectionnez\nun mode\nde parcours :");
        modeCol.getChildren().add(modeLbl);

        ToggleButton gloutonBtn = new ToggleButton("Glouton");
        gloutonBtn.setOnAction(modeControleur);
        gloutonBtn.setUserData("Glouton");

        ToggleButton optBtn = new ToggleButton("Optimisé");
        optBtn.setOnAction(modeControleur);
        optBtn.setUserData("Optimise");

        ToggleGroup modeGroup = new ToggleGroup();
        modeGroup.setUserData("Mode");
        modeGroup.getToggles().addAll(gloutonBtn, optBtn);

        modeCol.getChildren().addAll(gloutonBtn, optBtn);

        HBox afterModeBox = new HBox();
        afterModeField = afterModeBox.getChildren();

        getChildren().addAll(new Separator(Orientation.VERTICAL), modeCol, afterModeBox);
    }

    /**
     * Ajoute un panneau de sélection de méthode de parcours.
     *
     * @param field La liste des nœuds à ajouter après le panneau de sélection de méthode.
     */
    public static void addMethodePanel(ObservableList<Node> field) {
        VBox methodCol = new VBox(50);
        methodCol.setPadding(new Insets(30));

        Label methodLbl = new Label("Sélectionnez\nune méthode\nde parcours :");
        methodCol.getChildren().add(methodLbl);

        ToggleButton effBtn = new ToggleButton("Efficace");
        effBtn.setOnAction(modeControleur);
        effBtn.setUserData("Efficace");

        ToggleButton exhBtn = new ToggleButton("Exhaustif");
        exhBtn.setOnAction(modeControleur);
        exhBtn.setUserData("Exhaustif");

        ToggleGroup methGroup = new ToggleGroup();
        methGroup.setUserData("Methode");
        methGroup.getToggles().addAll(effBtn, exhBtn);

        methodCol.getChildren().addAll(effBtn, exhBtn);

        HBox afterMethodBox = new HBox();
        afterMethodField = afterMethodBox.getChildren();

        field.addAll(new Separator(Orientation.VERTICAL), methodCol, afterMethodBox);
    }

    /**
     * Ajoute un panneau de sélection d'optimisation.
     *
     * @param field La liste des nœuds à ajouter après le panneau de sélection d'optimisation.
     */
    public static void addOptimisationPanel(ObservableList<Node> field) {
        VBox optiCol = new VBox(50);
        optiCol.setPadding(new Insets(30));

        Label optiLbl = new Label("Sélectionnez\nl'élément à\noptimiser :");
        optiCol.getChildren().add(optiLbl);

        ToggleButton dureeBtn = new ToggleButton("Duree");
        dureeBtn.setOnAction(modeControleur);
        dureeBtn.setUserData("Duree");

        ToggleButton expBtn = new ToggleButton("Nombre de quêtes");
        expBtn.setOnAction(modeControleur);
        expBtn.setUserData("NbQuest");

        ToggleButton distBtn = new ToggleButton("Distance");
        distBtn.setOnAction(modeControleur);
        distBtn.setUserData("Dist");

        ToggleGroup optiGroup = new ToggleGroup();
        optiGroup.setUserData("Opti");
        optiGroup.getToggles().addAll(dureeBtn, expBtn, distBtn);

        optiCol.getChildren().addAll(dureeBtn, expBtn, distBtn);

        HBox afterOptiBox = new HBox();
        afterOptiField = afterOptiBox.getChildren();

        field.addAll(new Separator(Orientation.VERTICAL), optiCol, afterOptiBox);
    }

    /**
     * Ajoute un panneau de sélection du nombre de solutions.
     *
     * @param field La liste des nœuds à ajouter après le panneau de sélection du nombre de solutions.
     */
    public static void addNbSolutionPanel(ObservableList<Node> field) {
        VBox nbSolCol = new VBox(30);
        nbSolCol.setPadding(new Insets(30));

        Label typeLbl = new Label("Sélectionnez\nle type de\nsolution :");

        typeCmb = new ComboBox<>();
        typeCmb.getItems().addAll("Meilleures", "Pires");
        typeCmb.getSelectionModel().select(0);

        Label nbSolLbl = new Label("Nombre de\nsolutions :");
        nbSolTxt = new TextField("5");

        ToggleGroup nbSolGroup = new ToggleGroup();
        ToggleButton txtSolBtn = new ToggleButton("Afficher ces solutions");
        txtSolBtn.setOnAction(modeControleur);
        txtSolBtn.setUserData("Texte");

        ToggleButton allSolBtn = new ToggleButton("Afficher toutes les solutions");
        allSolBtn.setOnAction(modeControleur);
        allSolBtn.setUserData("Toutes");
        nbSolGroup.getToggles().addAll(txtSolBtn, allSolBtn);
        nbSolGroup.setUserData("NbSol");

        nbSolCol.getChildren().addAll(typeLbl, typeCmb, new Separator(), nbSolLbl, nbSolTxt, txtSolBtn, allSolBtn);

        HBox afterNbSolBox = new HBox();
        afterNbSolField = afterNbSolBox.getChildren();

        field.addAll(new Separator(Orientation.VERTICAL), nbSolCol, afterNbSolBox);
    }

    /**
     * Affiche le panneau de visualisation des solutions.
     *
     * @param field La liste des nœuds à ajouter après le panneau de visualisation des solutions.
     */
    public static void showSolution(ObservableList<Node> field) {
        HBox solBox = new HBox();
        solBox.setPadding(new Insets(50));
        solBox.setAlignment(Pos.CENTER);

        Button submitBtn = new Button("Valider les paramètres");
        submitBtn.setOnAction(modeControleur);
        submitBtn.setUserData("Valider");

        solBox.getChildren().add(submitBtn);

        field.addAll(new Separator(Orientation.VERTICAL), solBox);
    }

    /**
     * Retourne le contrôleur du mode de sélection.
     *
     * @return Le contrôleur du mode de sélection.
     */
    public static ModeSelectionControleur getModeControleur() {
        return modeControleur;
    }

    /**
     * Retourne la liste des nœuds après le panneau de sélection de mode.
     *
     * @return La liste des nœuds après le panneau de sélection de mode.
     */
    public static ObservableList<Node> getAfterModeField() {
        return afterModeField;
    }

    /**
     * Retourne la liste des nœuds après le panneau de sélection de méthode.
     *
     * @return La liste des nœuds après le panneau de sélection de méthode.
     */
    public static ObservableList<Node> getAfterMethodField() {
        return afterMethodField;
    }

    /**
     * Retourne la liste des nœuds après le panneau de sélection d'optimisation.
     *
     * @return La liste des nœuds après le panneau de sélection d'optimisation.
     */
    public static ObservableList<Node> getAfterOptiField() {
        return afterOptiField;
    }

    /**
     * Retourne la liste des nœuds après le panneau de sélection du nombre de solutions.
     *
     * @return La liste des nœuds après le panneau de sélection du nombre de solutions.
     */
    public static ObservableList<Node> getAfterNbSolField() {
        return afterNbSolField;
    }

    /**
     * Retourne le mode de parcours sélectionné.
     *
     * @return Le mode de parcours sélectionné.
     */
    public static String getSelectedMode() {
        return selectedMode;
    }

    /**
     * Définit le mode de parcours sélectionné.
     *
     * @param selectedMode Le mode de parcours sélectionné.
     */
    public static void setSelectedMode(String selectedMode) {
        ModeSelection.selectedMode = selectedMode;
    }

    /**
     * Retourne la méthode de parcours sélectionnée.
     *
     * @return La méthode de parcours sélectionnée.
     */
    public static String getSelectedMethod() {
        return selectedMethod;
    }

    /**
     * Définit la méthode de parcours sélectionnée.
     *
     * @param selectedMethod La méthode de parcours sélectionnée.
     */
    public static void setSelectedMethod(String selectedMethod) {
        ModeSelection.selectedMethod = selectedMethod;
    }

    /**
     * Retourne l'élément à optimiser sélectionné.
     *
     * @return L'élément à optimiser sélectionné.
     */
    public static String getSelectedOpti() {
        return selectedOpti;
    }

    /**
     * Définit l'élément à optimiser sélectionné.
     *
     * @param selectedOpti L'élément à optimiser sélectionné.
     */
    public static void setSelectedOpti(String selectedOpti) {
        ModeSelection.selectedOpti = selectedOpti;
    }

    /**
     * Retourne le nombre de solutions sélectionné.
     *
     * @return Le nombre de solutions sélectionné.
     */
    public static int getSelectedNbSol() {
        return selectedNbSol;
    }

    /**
     * Définit le nombre de solutions sélectionné.
     *
     * @param selectedNbSol Le nombre de solutions sélectionné.
     */
    public static void setSelectedNbSol(int selectedNbSol) {
        ModeSelection.selectedNbSol = selectedNbSol;
    }

    /**
     * Retourne le type de solution sélectionné.
     *
     * @return Le type de solution sélectionné.
     */
    public static String getSelectedTypeSol() {
        return selectedTypeSol;
    }

    /**
     * Définit le type de solution sélectionné.
     *
     * @param selectedTypeSol Le type de solution sélectionné.
     */
    public static void setSelectedTypeSol(String selectedTypeSol) {
        ModeSelection.selectedTypeSol = selectedTypeSol;
    }

    /**
     * Retourne le String de sélection du type de solution.
     *
     * @return Le String de sélection du type de solution.
     */
    public static String getTypeSol() {
        return typeCmb.getSelectionModel().getSelectedItem();
    }

    /**
     * Retourne le String de sélection du nombre de solutions.
     *
     * @return Le String de sélection du nombre de solutions.
     */
    public static String getNbSol() {
        return nbSolTxt.getText();
    }
}
