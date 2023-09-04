module com.example.lastproblemofme {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.lastproblemofme to javafx.fxml;
    exports com.example.lastproblemofme;
}