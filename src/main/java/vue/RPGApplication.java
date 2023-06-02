package vue;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RPGApplication extends Application {

    @Override
    public void start(Stage stage) {
        VBox root = new VBox();
        root.getChildren().add(new Label("En construction"));
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 150, 150);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String [] args){
        Application.launch(args);
    }
}
