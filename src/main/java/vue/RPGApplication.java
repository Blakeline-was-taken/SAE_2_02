package vue;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;

/**
 * Classe principale de l'application RPG.
 */
public class RPGApplication extends Application {

    /**
     * Méthode principale qui lance l'application.
     * @param args Les arguments de la ligne de commande.
     */
    public static void main(String [] args){
        Application.launch(args);
    }

    /**
     * Méthode de démarrage de l'application.
     * @param stage La fenêtre principale de l'application.
     */
    @Override
    public void start(Stage stage){
        HBox root = new Root();

        Scene scene = new Scene(root, 1280, 720);
        scene.getStylesheets().add((new File("css"+File.separator+"style.css")).toURI().toString());
        stage.setScene(scene);
        stage.show();
    }
}
