package com.example.problemofme2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.*;

public class PrimMSTVisualization extends Application {
    private Pane graphPane = new Pane();
    private int vertices;
    private List<Edge> edges = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Prim's MST Visualization");

        BorderPane borderPane = new BorderPane();

        VBox inputVBox = new VBox(10);
        inputVBox.setPadding(new Insets(10));

        Label inputLabel = new Label("Enter the number of vertices and edges:");
        TextField inputField = new TextField();
        inputField.setPromptText("Vertices Edges");

        Button drawButton = new Button("Draw MST");

        inputVBox.getChildren().addAll(inputLabel, inputField, drawButton);

        borderPane.setLeft(inputVBox);
        borderPane.setCenter(graphPane);

        drawButton.setOnAction(event -> {
            String input = inputField.getText();
            String[] inputParts = input.split(" ");
            if (inputParts.length != 2) {
                showAlert("Invalid input format. Please enter vertices and edges.");
                return;
            }

            vertices = Integer.parseInt(inputParts[0]);
            int edgeCount = Integer.parseInt(inputParts[1]);

            // Clear the previous graph
            graphPane.getChildren().clear();
            edges.clear();

            // Create input dialogs for edge details
            for (int i = 0; i < edgeCount; i++) {
                TextInputDialog edgeDialog = new TextInputDialog();
                edgeDialog.setTitle("Edge Input");
                edgeDialog.setHeaderText("Enter edge details in the format: from to weight");
                Optional<String> edgeResult = edgeDialog.showAndWait();

                if (edgeResult.isPresent()) {
                    String[] edgeInput = edgeResult.get().split(" ");
                    char from = edgeInput[0].charAt(0);
                    char to = edgeInput[1].charAt(0);
                    int weight = Integer.parseInt(edgeInput[2]);
                    edges.add(new Edge(from, to, weight));
                }
            }

            // Perform MST calculations (Prim's algorithm)
            List<Edge> mstEdges = performMSTCalculations(vertices, edges);

            // Draw the MST graph
            drawGraph(vertices, mstEdges);
        });

        Scene scene = new Scene(borderPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private List<Edge> performMSTCalculations(int vertices, List<Edge> edges) {
        List<Edge> mst = new ArrayList<>();

        // Create an adjacency list to represent the graph
        List<List<Edge>> adjacencyList = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            adjacencyList.add(new ArrayList<>());
        }

        for (Edge edge : edges) {
            adjacencyList.get(edge.from - 'A').add(edge);
            adjacencyList.get(edge.to - 'A').add(new Edge(edge.to, edge.from, edge.weight));
        }

        // Prim's algorithm for MST
        boolean[] visited = new boolean[vertices];
        PriorityQueue<Edge> minHeap = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));

        visited[0] = true;
        for (Edge edge : adjacencyList.get(0)) {
            minHeap.offer(edge);
        }

        while (!minHeap.isEmpty()) {
            Edge edge = minHeap.poll();
            if (!visited[edge.to - 'A']) {
                visited[edge.to - 'A'] = true;
                mst.add(edge);
                for (Edge neighbor : adjacencyList.get(edge.to - 'A')) {
                    if (!visited[neighbor.to - 'A']) {
                        minHeap.offer(neighbor);
                    }
                }
            }
        }

        return mst;
    }

    private void drawGraph(int vertices, List<Edge> edges) {
        double centerX = 400;
        double centerY = 300;
        double radius = 200;

        // Draw vertices as circles
        Map<Character, Circle> vertexCircles = new HashMap<>();
        for (int i = 0; i < vertices; i++) {
            double angle = 2 * Math.PI * i / vertices;
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);
            Circle circle = new Circle(x, y, 20);
            circle.setFill(Color.RED);
            circle.setStroke(Color.ALICEBLUE);
            circle.setStrokeWidth(2);
            graphPane.getChildren().add(circle);
            vertexCircles.put((char) ('A' + i), circle);

            // Label for vertex
            Label label = new Label(String.valueOf((char) ('A' + i)));
            label.setLayoutX(x - 5);
            label.setLayoutY(y - 15);
            graphPane.getChildren().add(label);
        }

        // Draw edges of the MST
        for (Edge edge : edges) {
            Line line = new Line(
                    vertexCircles.get(edge.from).getCenterX(),
                    vertexCircles.get(edge.from).getCenterY(),
                    vertexCircles.get(edge.to).getCenterX(),
                    vertexCircles.get(edge.to).getCenterY()
            );
            line.setStroke(Color.BLACK);
            line.setStrokeWidth(2);
            graphPane.getChildren().add(line);
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    static class Edge {
        char from, to;
        int weight;

        Edge(char from, char to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }
}
