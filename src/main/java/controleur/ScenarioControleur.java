package controleur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import modele.Scenario;
import vue.ModeSelection;
import vue.Root;
import vue.ScenarioPreview;

import java.io.FileNotFoundException;

public class ScenarioControleur implements EventHandler<ActionEvent> {

    /**
     * Gère les événements déclenchés par les boutons.
     *
     * @param event l'événement déclenché
     */
    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() instanceof ToggleButton btn) {
            try {
                // Efface le contenu de la partie droite de l'interface et ajoute un aperçu du scénario sélectionné
                Root.getRightField().clear();
                Root.getRightField().add(new ScenarioPreview(new Scenario((String) btn.getUserData())));
                // Définit le scénario sélectionné
                Root.setSelectedScn((String) btn.getUserData());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else if (event.getSource() instanceof Button) {
            // Efface le contenu de la partie droite de l'interface et ajoute la vue de sélection du mode
            Root.getRightField().clear();
            Root.getRightField().add(new ModeSelection());
        }
    }
}
