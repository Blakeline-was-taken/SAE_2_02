module com.example.sae_2_ {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.junit.jupiter.api;


    opens vue to javafx.fxml;
    exports vue;
}