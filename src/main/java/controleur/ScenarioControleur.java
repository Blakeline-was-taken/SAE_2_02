package controleur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import module.Scenario;
import vue.ModeSelection;
import vue.Root;
import vue.ScenarioPreview;

import java.io.FileNotFoundException;

public class ScenarioControleur implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event){
        if (event.getSource() instanceof ToggleButton btn){
            try {
                Root.getField().clear();
                Root.getField().add(new ScenarioPreview(new Scenario((String) btn.getUserData())));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else if (event.getSource() instanceof Button) {
            Root.getField().clear();
            Root.getField().add(new ModeSelection());
        }
    }
}
