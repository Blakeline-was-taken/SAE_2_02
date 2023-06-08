module vue {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.junit.jupiter.api;

    opens vue to javafx.fxml;
    exports vue;
    exports modele;
    exports core;
    exports controleur;
}