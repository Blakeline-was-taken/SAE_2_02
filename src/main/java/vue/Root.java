package vue;

import controleur.ScenarioControleur;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Root extends HBox {
    private static ObservableList <Node> field;
    private static final ScenarioControleur scnControleur = new ScenarioControleur();
    public Root() {
        // Scenario selection
        VBox scnBox = new VBox(20);
        ToggleGroup scnGroup = new ToggleGroup();
        for (int i = 0; i<11; i++){
            ToggleButton btn = new ToggleButton("scénario " + i);
            scnGroup.getToggles().add(btn);
            scnBox.getChildren().add(btn);
            btn.setUserData("scenario_" + i + ".txt");
            btn.setOnAction(scnControleur);
        }

        HBox panel = new HBox();
        field = panel.getChildren();
        getChildren().addAll(scnBox, panel);
    }

    public static ScenarioControleur getScnControleur() {
        return scnControleur;
    }

    public static ObservableList<Node> getField(){
        return field;
    }
}
