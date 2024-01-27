module com.example.finaldes {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.finaldes to javafx.fxml;
    exports com.example.finaldes;
}