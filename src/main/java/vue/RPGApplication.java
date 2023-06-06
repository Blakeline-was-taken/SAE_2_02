package vue;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class RPGApplication extends Application {

    @Override
    public void start(Stage stage){
        HBox root = new Root();

        Scene scene = new Scene(root, 1280, 720);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String [] args){
        Application.launch(args);
    }
}
