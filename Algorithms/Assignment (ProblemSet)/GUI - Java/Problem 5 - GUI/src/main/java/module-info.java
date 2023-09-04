module com.example.problemofme2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.problemofme2 to javafx.fxml;
    exports com.example.problemofme2;
}