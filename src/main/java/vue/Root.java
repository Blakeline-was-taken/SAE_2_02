package vue;

import controleur.ScenarioControleur;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
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
        initSelectionView();

        HBox panel = new HBox(50);
        rightField = panel.getChildren();
        getChildren().addAll(leftField, panel);
    }

    public static void initSelectionView(){
        leftField.getChildren().clear();
        leftField.setPadding(new Insets(30));

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
