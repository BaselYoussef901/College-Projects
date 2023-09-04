package com.example.problemofme;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.*;

public class GraphVisualization extends Application {
    static class Edge
    {
        char from, to;
        int weight;

        Edge(char t, int w) {
            to = t;
            weight = w;
        }

        Edge(char f, char t, int w) {
            from = f;
            to = t;
            weight = w;
        }
    }

    static final int INF = (int) 1e18;
    static final int N = 2 * 100005;

    List<List<Edge>> G = new ArrayList<>();
    int vertices, edges;
    List<Character> path = new ArrayList<>();
    List<Integer> cost = new ArrayList<>();
    boolean[] vis = new boolean[N];

    // JavaFX components
    private Pane graphPane = new Pane();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Use JavaFX dialogs or other input methods for user input

        // Example using an input dialog:
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setTitle("Input");
        inputDialog.setHeaderText("Enter the number of vertices and edges:");
        Optional<String> inputResult = inputDialog.showAndWait();

        if (inputResult.isPresent()) {
            String[] input = inputResult.get().split(" ");
            vertices = Integer.parseInt(input[0]);
            edges = Integer.parseInt(input[1]);

            for (int i = 0; i < N; i++) {
                G.add(new ArrayList<>());
            }

            // Example using another input dialog for edge input:
            for (int i = 0; i < edges; i++) {
                TextInputDialog edgeDialog = new TextInputDialog();
                edgeDialog.setTitle("Input");
                edgeDialog.setHeaderText("Enter edge in the format: from to weight");
                Optional<String> edgeResult = edgeDialog.showAndWait();

                if (edgeResult.isPresent()) {
                    String[] edgeInput = edgeResult.get().split(" ");
                    char f = edgeInput[0].charAt(0);
                    char t = edgeInput[1].charAt(0);
                    int w = Integer.parseInt(edgeInput[2]);
                    G.get(f - 'A').add(new Edge(t, w));
                    G.get(t - 'A').add(new Edge(f, w));
                }
            }

            drawGraph();

            // JavaFX setup
            BorderPane root = new BorderPane();
            ScrollPane scrollPane = new ScrollPane(graphPane);
            root.setCenter(scrollPane);

            Scene scene = new Scene(root, 800, 600);
            primaryStage.setTitle("Graph Visualization");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }

    // Function to draw the graph
    void drawGraph() {
        graphPane.getChildren().clear();

        double centerX = 400;
        double centerY = 300;
        double radius = 200;

        // Draw vertices as circles
        for (int i = 0; i < vertices; i++) {
            double angle = 2 * Math.PI * i / vertices;
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);
            Circle circle = new Circle(x, y, 20);
            circle.setFill(Color.LIGHTBLUE);
            circle.setStroke(Color.BLACK);
            circle.setStrokeWidth(2);
            graphPane.getChildren().add(circle);

            // Label for vertex
            Label label = new Label(Character.toString((char) ('A' + i)));
            label.setLayoutX(x - 5);
            label.setLayoutY(y - 10);
            graphPane.getChildren().add(label);
        }

        // Draw edges
        for (int u = 0; u < vertices; u++) {
            double angleU = 2 * Math.PI * u / vertices;
            double xU = centerX + radius * Math.cos(angleU);
            double yU = centerY + radius * Math.sin(angleU);

            for (Edge e : G.get(u)) {
                int v = e.to - 'A';

                double angleV = 2 * Math.PI * v / vertices;
                double xV = centerX + radius * Math.cos(angleV);
                double yV = centerY + radius * Math.sin(angleV);

                Line line = new Line(xU, yU, xV, yV);
                line.setStroke(Color.BLACK);
                graphPane.getChildren().add(line);

                // Label for edge weight
                double labelX = (xU + xV) / 2;
                double labelY = (yU + yV) / 2;
                Label weightLabel = new Label(Integer.toString(e.weight));
                weightLabel.setLayoutX(labelX - 5);
                weightLabel.setLayoutY(labelY - 10);
                graphPane.getChildren().add(weightLabel);
            }
        }
    }
}
