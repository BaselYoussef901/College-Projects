package com.example.problemofme3;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Random;

public class SkipListApp extends Application {
    private SkipList<Integer> skipList = new SkipList<>();
    private TextArea outputTextArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("SkipList GUI");


        Button insertButton = new Button("Insert");
        Button deleteButton = new Button("Delete");
        Button searchButton = new Button("Search");
        Button displayButton = new Button("Display SkipList");
        TextField valueTextField = new TextField();
        outputTextArea = new TextArea();


        insertButton.setOnAction(e -> insertValue(valueTextField.getText()));
        deleteButton.setOnAction(e -> deleteValue(valueTextField.getText()));
        searchButton.setOnAction(e -> searchValue(valueTextField.getText()));
        displayButton.setOnAction(e -> displaySkipList());


        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(
                new Label("Enter a value:"),
                valueTextField,
                insertButton,
                deleteButton,
                searchButton,
                displayButton,
                outputTextArea
        );

        // Create the scene
        Scene scene = new Scene(vbox, 700, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void insertValue(String value) {
        try {
            int intValue = Integer.parseInt(value);
            int level = new Random().nextInt(4) + 1; // Random level between 1 and 4
            skipList.insert(intValue, level);
            updateOutput("Inserted: " + intValue + " with level " + level);
        } catch (NumberFormatException e) {
            updateOutput("Invalid input. Please enter an integer.");
        }
    }

    private void deleteValue(String value) {
        try {
            int intValue = Integer.parseInt(value);
            if (skipList.delete(intValue)) {
                updateOutput("Deleted: " + intValue);
            } else {
                updateOutput("Value not found: " + intValue);
            }
        } catch (NumberFormatException e) {
            updateOutput("Invalid input. Please enter an integer.");
        }
    }

    private void searchValue(String value) {
        try {
            int intValue = Integer.parseInt(value);
            if (skipList.search(intValue)) {
                updateOutput("Found: " + intValue);
            } else {
                updateOutput("Not found: " + intValue);
            }
        } catch (NumberFormatException e) {
            updateOutput("Invalid input. Please enter an integer.");
        }
    }

    private void displaySkipList() {
        outputTextArea.clear();
        outputTextArea.appendText("SkipList:\n");
        skipList.display(outputTextArea);
    }

    private void updateOutput(String message) {
        outputTextArea.appendText(message + "\n");
    }

    public static class SkipList<T extends Comparable<T>> {
        private Node<T> head = new Node<>(null, MAX_LEVEL);
        private int level = 1;
        private static final int MAX_LEVEL = 16;

        public void insert(T value, int level) {
            if (level > MAX_LEVEL) {
                level = MAX_LEVEL;
            }

            Node<T>[] update = new Node[MAX_LEVEL];
            Node<T> current = head;

            for (int i = level - 1; i >= 0; i--) {
                while (current.next[i] != null && current.next[i].value.compareTo(value) < 0) {
                    current = current.next[i];
                }
                update[i] = current;
            }

            Node<T> newNode = new Node<>(value, level);

            for (int i = 0; i < level; i++) {
                newNode.next[i] = update[i].next[i];
                update[i].next[i] = newNode;
            }

            if (level > this.level) {
                this.level = level;
            }
        }

        public boolean delete(T value) {
            Node<T>[] update = new Node[MAX_LEVEL];
            Node<T> current = head;

            for (int i = level - 1; i >= 0; i--) {
                while (current.next[i] != null && current.next[i].value.compareTo(value) < 0) {
                    current = current.next[i];
                }
                update[i] = current;
            }

            current = current.next[0];

            if (current != null && current.value.compareTo(value) == 0) {
                for (int i = 0; i < level; i++) {
                    if (update[i].next[i] != current) {
                        break;
                    }
                    update[i].next[i] = current.next[i];
                }

                // Update the SkipList level if needed
                while (level > 1 && head.next[level - 1] == null) {
                    level--;
                }

                return true;
            }

            return false;
        }

        public boolean search(T value) {
            Node<T> current = head;

            for (int i = level - 1; i >= 0; i--) {
                while (current.next[i] != null && current.next[i].value.compareTo(value) < 0) {
                    current = current.next[i];
                }
            }

            current = current.next[0];

            return current != null && current.value.compareTo(value) == 0;
        }

        public void display(TextArea textArea) {
            for (int i = level - 1; i >= 0; i--) {
                Node<T> current = head;
                textArea.appendText("Level " + (i + 1) + ": ");
                while (current.next[i] != null) {
                    textArea.appendText(current.next[i].value + " ");
                    current = current.next[i];
                }
                textArea.appendText("\n");
            }
        }

        private static class Node<T> {
            T value;
            Node<T>[] next;

            Node(T value, int level) {
                this.value = value;
                this.next = new Node[level];
            }
        }
    }
}
