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
    public ModeSelection(){
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

    public static ObservableList<Node> getAfterModeField(){
        return afterModeField;
    }

    public static ObservableList<Node> getAfterMethodField(){
        return afterMethodField;
    }

    public static ObservableList<Node> getAfterOptiField(){
        return afterOptiField;
    }

    public static ObservableList<Node> getAfterNbSolField(){
        return afterNbSolField;
    }

    public static void addMethodePanel(ObservableList<Node> field){
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

    public static void addOptimisationPanel(ObservableList<Node> field){
        VBox optiCol = new VBox(50);
        optiCol.setPadding(new Insets(30));

        Label optiLbl = new Label("Sélectionnez\nl'élément à\noptimiser :");
        optiCol.getChildren().add(optiLbl);

        ToggleButton dureeBtn = new ToggleButton("Duree");
        dureeBtn.setOnAction(modeControleur);
        dureeBtn.setUserData("Duree");

        ToggleButton expBtn = new ToggleButton("Experience");
        expBtn.setOnAction(modeControleur);
        expBtn.setUserData("Exp");

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

    public static void addNbSolutionPanel(ObservableList<Node> field){
        VBox nbSolCol = new VBox(30);
        nbSolCol.setPadding(new Insets(30));

        Label typeLbl = new Label("Sélectionnez\nle type de\nsolution :");

        typeCmb = new ComboBox<>();
        typeCmb.getItems().addAll("Meilleures", "Pires");
        typeCmb.getSelectionModel().select(0);

        Label nbSolLbl = new Label("Nombre de\nsolutions :");

        nbSolTxt = new TextField("5");
        Button txtSolBtn = new Button("Afficher ces solutions");
        txtSolBtn.setOnAction(modeControleur);
        txtSolBtn.setUserData("Texte");

        Button allSolBtn = new Button("Afficher toutes les solutions");
        allSolBtn.setOnAction(modeControleur);
        allSolBtn.setUserData("Toutes");

        nbSolCol.getChildren().addAll(typeLbl, typeCmb, new Separator(), nbSolLbl, nbSolTxt, txtSolBtn, allSolBtn);

        HBox afterNbSolBox = new HBox();
        afterNbSolField = afterNbSolBox.getChildren();

        field.addAll(new Separator(Orientation.VERTICAL), nbSolCol, afterNbSolBox);
    }

    public static void showSolution(ObservableList<Node> field){
        HBox solBox = new HBox();
        solBox.setPadding(new Insets(50));
        solBox.setAlignment(Pos.CENTER);

        Button submitBtn = new Button("Valider les paramètres");
        submitBtn.setOnAction(modeControleur);
        submitBtn.setUserData("Valider");

        solBox.getChildren().add(submitBtn);

        field.addAll(new Separator(Orientation.VERTICAL), solBox);
    }

    public static String getSelectedMode() {
        return selectedMode;
    }

    public static void setSelectedMode(String selectedMode) {
        ModeSelection.selectedMode = selectedMode;
    }

    public static String getSelectedMethod() {
        return selectedMethod;
    }

    public static void setSelectedMethod(String selectedMethod) {
        ModeSelection.selectedMethod = selectedMethod;
    }

    public static String getSelectedOpti() {
        return selectedOpti;
    }

    public static void setSelectedOpti(String selectedOpti) {
        ModeSelection.selectedOpti = selectedOpti;
    }

    public static int getSelectedNbSol() {
        return selectedNbSol;
    }

    public static void setSelectedNbSol(int selectedNbSol) {
        ModeSelection.selectedNbSol = selectedNbSol;
    }

    public static String getSelectedTypeSol() {
        return selectedTypeSol;
    }

    public static void setSelectedTypeSol(String selectedTypeSol) {
        ModeSelection.selectedTypeSol = selectedTypeSol;
    }

    public static String getTypeSol() {
        return typeCmb.getSelectionModel().getSelectedItem();
    }

    public static String getNbSol() {
        return nbSolTxt.getText();
    }
}
