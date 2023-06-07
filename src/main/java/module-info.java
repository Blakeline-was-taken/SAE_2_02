module com.example.test {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.junit.jupiter.api;
    requires org.junit.platform.commons;

    opens com.example.test to javafx.fxml;
    exports vue;
    exports modele;
    exports core;
    exports controleur;
}