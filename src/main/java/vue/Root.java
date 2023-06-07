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

public class Root extends HBox {
    private static ObservableList <Node> rightField;
    private static final ScenarioControleur scnControleur = new ScenarioControleur();
    private static String selectedScn;
    private final static VBox leftField = new VBox(20);
    public Root() {
        HBox panel = new HBox(50);
        rightField = panel.getChildren();
        initSelectionView();
        getChildren().addAll(leftField, panel);
    }

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

    public static void initSolutionView(String mode, String methode){
        leftField.getChildren().clear();
        rightField.clear();
        leftField.setPadding(new Insets(10));
        leftField.setAlignment(Pos.CENTER);

        VBox scnBox = new VBox();
        Label scnTitleLbl = new Label("Scénario sélectionné :");
        Label scnLbl = new Label(getSelectedScn().replace(".txt", "").replace("_", " "));
        scnLbl.setStyle("-fx-text-fill: #3b3bb4; -fx-font-weight: bold");
        scnBox.setPadding(new Insets(20));
        scnBox.getChildren().addAll(scnTitleLbl, scnLbl);

        VBox modeBox = new VBox();
        Label modeTitleLbl = new Label("Mode de parcours :");
        Label modeLbl = new Label(mode);
        modeLbl.setStyle("-fx-text-fill: #3b3bb4; -fx-font-weight: bold");
        modeBox.setPadding(new Insets(20));
        modeBox.getChildren().addAll(modeTitleLbl, modeLbl);

        VBox methodeBox = new VBox();
        Label methodeTitleLbl = new Label("Méthode de parcours :");
        Label methodeLbl = new Label(methode);
        methodeLbl.setStyle("-fx-text-fill: #3b3bb4; -fx-font-weight: bold");
        methodeBox.setPadding(new Insets(20));
        methodeBox.getChildren().addAll(methodeTitleLbl, methodeLbl);

        Button newSolution = new Button("Nouvelle simulation");
        newSolution.setOnAction(ModeSelection.getModeControleur());
        newSolution.setUserData("New");

        leftField.getChildren().addAll(scnBox, modeBox, methodeBox, newSolution);
    }

    public static void setSelectedScn(String scnStr){
        selectedScn = scnStr;
    }

    public static String getSelectedScn(){
        return selectedScn;
    }

    public static ScenarioControleur getScnControleur() {
        return scnControleur;
    }

    public static ObservableList<Node> getRightField(){
        return rightField;
    }
}
