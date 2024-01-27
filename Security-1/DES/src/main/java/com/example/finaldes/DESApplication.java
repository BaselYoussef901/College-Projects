package com.example.finaldes;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class DESApplication extends Application {
    private String P,C;
    private DataEncryptionStandard DES;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Data Encryption Standard [DES]");
        Font boldFont = Font.font("System", FontWeight.BOLD, 14);

        // Combo Box
        ComboBox<String> formatComboBox = new ComboBox<>(FXCollections.observableArrayList("Hexa", "ASCII"));
        formatComboBox.setValue("Hexa");
        formatComboBox.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");


        // Buttons
        Button encryptButton = new Button("Encrypt");
        Button decryptButton = new Button("Decrypt");
        Button KeyButton = new Button(" Keys Info ");
        Button Algorithm = new Button(" Algorithm Info ");
        encryptButton.setFont(boldFont);
        decryptButton.setFont(boldFont);
        KeyButton.setFont(boldFont);
        Algorithm.setFont(boldFont);
        Algorithm.setVisible(false);
        KeyButton.setDisable(true);

        // Labels
        Label AlertLabel = new Label("");
        Label TextLabel = new Label("Text: ");
        Label KeyLabel = new Label("Key:  ");
        Label CountTextLabel = new Label("<16>");
        Label CountKayLabel = new Label("<16>");
        Label CipherLabel = new Label("CipherText: ");
        Label PainLabel = new Label("PainText: ");
        Label CipherOutPutLabel = new Label("");
        Label PainTextOutPutLabel = new Label("");
        TextLabel.setFont(boldFont);
        KeyLabel.setFont(boldFont);
        CipherLabel.setFont(boldFont);
        PainLabel.setFont(boldFont);
        CipherOutPutLabel.setFont(boldFont);
        PainTextOutPutLabel.setFont(boldFont);
        CipherLabel.setVisible(false);
        PainLabel.setVisible(false);
        CipherOutPutLabel.setVisible(false);
        PainTextOutPutLabel.setVisible(false);
        CipherOutPutLabel.setStyle("-fx-text-fill: green;");
        PainTextOutPutLabel.setStyle("-fx-text-fill: green;");
        AlertLabel.setPadding(new Insets(5));
        AlertLabel.setStyle("-fx-background-color: lightblue;");
        AlertLabel.setVisible(false);

        // Text Areas
        TextField TextField = new TextField("1234567890ABCDEF");
        TextField.setMaxWidth(Double.MAX_VALUE);
        TextField.setFont(boldFont);
        TextField.setStyle("-fx-text-fill: blue;");

        TextField KeyField = new TextField("AABB09182736CCDD");
        KeyField.setMaxWidth(Double.MAX_VALUE);
        KeyField.setFont(boldFont);
        KeyField.setStyle("-fx-text-fill: red;");

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(3), event -> AlertLabel.setVisible(false))
        );

        // Listener Actions
        KeyButton.setOnAction(event ->{
            FX_KeyGenerator(DES.FX_Keys);
        });
        Algorithm.setOnAction(event ->{
            FX_TextGenerator(DES.FX_LR,DES.FX_RightEXP,DES.FX_SBOX,DES.FX_P,DES.FX_Expansion_xor_Keyi,DES.FX_Left_xor_PBOX);
        });
        encryptButton.setOnAction(event ->{
            DES = new DataEncryptionStandard(formatComboBox.getValue());
            String CipherText = handleEncrypt(TextField.getText(), KeyField.getText());
            CipherOutPutLabel.setText(CipherText);
            CipherLabel.setVisible(true);
            CipherOutPutLabel.setVisible(true);

            TextField.setText(CipherText);
            C = CipherText;
            Algorithm.setVisible(true);
            KeyButton.setDisable(false);

            AlertLabel.setText("new CipherText [Encrypted]");
            AlertLabel.setVisible(true);
            timeline.playFromStart();
        });
        decryptButton.setOnAction(event ->{
            // String PlainText = handleDecrypt(TextField.getText(), KeyField.getText(), formatComboBox.getValue());
            String PlainText = handleDecrypt(C, KeyField.getText());
            PainTextOutPutLabel.setText(PlainText);
            PainLabel.setVisible(true);
            PainTextOutPutLabel.setVisible(true);

            TextField.setText(PlainText);
            P = PlainText;
            Algorithm.setVisible(true);
            KeyButton.setDisable(false);

            AlertLabel.setText("new PlainText [Decrypted]");
            AlertLabel.setVisible(true);
            timeline.playFromStart();
        });
        formatComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            if("Hexa".equals(newValue)){
                TextField.setText("1234567890ABCDEF");
                KeyField.setText("AABB09182736CCDD");
            }else if("ASCII".equals(newValue)){
                TextField.setText("ZXYW9870");
                KeyField.setText("AABB1036");
            }
        });
        TextField.textProperty().addListener((observable, oldValue, newValue) -> {
            CountTextLabel.setText("<" + newValue.length() + ">");
        });
        KeyField.textProperty().addListener((observable, oldValue, newValue) -> {
            CountKayLabel.setText("<" + newValue.length() + ">");
        });

        // Separators
        Separator SP1 = new Separator();
        SP1.setStyle("-fx-background-color: cyan; -fx-border-style: solid; -fx-border-color: cyan; -fx-border-width: 1;");

        // Layout
        HBox ButtonDetails = new HBox(10);
        ButtonDetails.setAlignment(Pos.CENTER);
        ButtonDetails.setMaxWidth(Double.MAX_VALUE);
        ButtonDetails.getChildren().addAll(Algorithm, KeyButton);

        HBox TextBox = new HBox(10);
        TextBox.setMaxWidth(Double.MAX_VALUE);
        TextBox.getChildren().addAll(TextLabel, TextField, CountTextLabel, AlertLabel);

        HBox KeyBox = new HBox(10);
        KeyBox.setMaxWidth(Double.MAX_VALUE);
        KeyBox.getChildren().addAll(KeyLabel, KeyField, CountKayLabel);

        HBox Choices = new HBox(10, encryptButton, decryptButton, formatComboBox);
        Choices.setAlignment(Pos.CENTER);
        Choices.setPadding(new Insets(20));

        HBox Encryption = new HBox(10);
        Encryption.setMaxWidth(Double.MAX_VALUE);
        Encryption.getChildren().addAll(CipherLabel, CipherOutPutLabel);

        HBox Decryption = new HBox(10);
        Decryption.setMaxWidth(Double.MAX_VALUE);
        Decryption.getChildren().addAll(PainLabel, PainTextOutPutLabel);


        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setMaxWidth(Double.MAX_VALUE);

        root.getChildren().addAll(ButtonDetails, SP1, TextBox, KeyBox, Choices, Encryption, Decryption);

        primaryStage.setScene(new Scene(root, 500, 800));
        primaryStage.show();
    }

    private String handleEncrypt(String Text, String Key){
        return DES.encrypt(Text, Key);
    }
    private String handleDecrypt(String Text, String Key){
        return DES.decrypt(Text, Key);
    }
    private void FX_KeyGenerator(ArrayList<String>generatedKeys){
        Backend backend = new Backend();
        Scene KeyScene = backend.Details(DES, generatedKeys);

        Stage KeyStage = new Stage();
        KeyStage.setTitle("Key Details..");
        KeyStage.setScene(KeyScene);
        KeyStage.show();
    }
    private void FX_TextGenerator(ArrayList<Pair<String,String>>D1,ArrayList<String>D2,ArrayList<String>D3,ArrayList<String>D4,ArrayList<String>D5,ArrayList<String>D6){
        Backend backend = new Backend();
        Scene TextScene = backend.AlgorithmDetails(D1,D2,D3,D4,D5,D6);

        Stage TextStage = new Stage();
        TextStage.setTitle("Encryption/Decryption Details..");
        TextStage.setScene(TextScene);
        TextStage.show();
    }
}
