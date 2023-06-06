package vue;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Root extends HBox {
    private static ObservableList <Node> field;
    public Root(){
        // Scenario selection
        VBox scnBox = new VBox();
        ToggleGroup scnGroup = new ToggleGroup();
        for (int i = 0; i<11; i++){
            ToggleButton btn = new ToggleButton("scénario " + i);
            scnGroup.getToggles().add(btn);
            scnBox.getChildren().add(btn);
        }

        HBox panel = new HBox();
        field = panel.getChildren();
        getChildren().addAll(scnBox, panel);
    }
}
