module com.example.problemofme {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.problemofme to javafx.fxml;
    exports com.example.problemofme;
}