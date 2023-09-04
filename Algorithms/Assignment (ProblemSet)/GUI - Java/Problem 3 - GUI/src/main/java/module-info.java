module com.example.problemofme3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.problemofme3 to javafx.fxml;
    exports com.example.problemofme3;
}