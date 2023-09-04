package com.example.lastproblemofme;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ChainingHashMapGUI extends Application {
    private ChainingMap<Integer, String> hashMap;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Chaining HashMap GUI");

        hashMap = new ChainingMap<>(100); // Initial size of the hash table

        BorderPane border = new BorderPane();

        // Create a VBox for controls
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10, 10, 10, 10));

        Label titleLabel = new Label("Chaining HashMap Operations");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField keyField = new TextField();
        keyField.setPromptText("Enter an integer key");
        TextField valueField = new TextField();
        valueField.setPromptText("Enter a string value");

        Button insertButton = new Button("Insert");
        insertButton.setOnAction(e -> {
            try {
                int key = Integer.parseInt(keyField.getText());
                String value = valueField.getText();
                hashMap.insert(key, value);
                keyField.clear();
                valueField.clear();
                showAlert(Alert.AlertType.INFORMATION, "Insertion Successful", "Key-Value pair inserted successfully.");
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter a valid integer key.");
            }
        });

        TextField retrieveKeyField = new TextField();
        retrieveKeyField.setPromptText("Enter a key to retrieve values");
        Button retrieveButton = new Button("Retrieve");
        retrieveButton.setOnAction(e -> {
            try {
                int key = Integer.parseInt(retrieveKeyField.getText());
                List<String> values = hashMap.retrieve(key);
                if (!values.isEmpty()) {
                    showAlert(Alert.AlertType.INFORMATION, "Values Retrieved", "Values for key " + key + ": " + String.join(", ", values));
                } else {
                    showAlert(Alert.AlertType.INFORMATION, "No Values Found", "No values found for key " + key);
                }
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter a valid integer key.");
            }
        });

        vbox.getChildren().addAll(
                titleLabel,
                new HBox(keyField, valueField, insertButton),
                new HBox(retrieveKeyField, retrieveButton)
        );

        border.setCenter(vbox);

        Scene scene = new Scene(border, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

class ChainingMap<Key, Value> {
    private static class Node<Key, Value> {
        private final Key key;
        private final Value value;

        public Node(Key key, Value value) {
            this.key = key;
            this.value = value;
        }
    }

    private final List<List<Node<Key, Value>>> hashTable;
    private final int tableSize;

    public ChainingMap(int size) {
        tableSize = size;
        hashTable = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            hashTable.add(new ArrayList<>());
        }
    }

    private int getIndex(Key key) {
        return key.hashCode() % tableSize;
    }

    public void insert(Key key, Value value) {
        int index = getIndex(key);
        Node<Key, Value> newNode = new Node<>(key, value);
        hashTable.get(index).add(newNode);
    }

    public List<String> retrieve(Key key) {
        List<String> values = new ArrayList<>();
        int index = getIndex(key);
        if (hashTable.get(index).isEmpty()) {
            return values;
        }

        for (Node<Key, Value> node : hashTable.get(index)) {
            if (node.key.equals(key)) {
                values.add(node.value.toString());
            }
        }
        return values;
    }
}
