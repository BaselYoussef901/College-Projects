package com.example.aes;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class GUI extends Application {

    private static final String ALGORITHM = "AES";
    private static final String CHARSET = "UTF-8";

    private enum Representation {
        ASCII, HEX
    }

    private Representation currentRepresentation = Representation.ASCII;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("AES Encryption/Decryption GUI");

        Label inputLabel = new Label("Enter text:");
        TextArea inputTextArea = new TextArea();
        inputTextArea.setWrapText(true);

        Label keyLabel = new Label("Enter key:");
        TextField keyTextField = new TextField();

        Label encryptionOutputLabel = new Label("Encryption Result:");
        TextArea encryptionOutputTextArea = new TextArea();
        encryptionOutputTextArea.setEditable(false);
        encryptionOutputTextArea.setWrapText(true);

        Label decryptionOutputLabel = new Label("Decryption Result:");
        TextArea decryptionOutputTextArea = new TextArea();
        decryptionOutputTextArea.setEditable(false);
        decryptionOutputTextArea.setWrapText(true);

        RadioButton asciiRadioButton = new RadioButton("ASCII");
        RadioButton hexRadioButton = new RadioButton("Hexadecimal");

        ToggleGroup representationToggleGroup = new ToggleGroup();
        asciiRadioButton.setToggleGroup(representationToggleGroup);
        hexRadioButton.setToggleGroup(representationToggleGroup);

        asciiRadioButton.setSelected(true);

        representationToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (asciiRadioButton.isSelected()) {
                currentRepresentation = Representation.ASCII;
            } else if (hexRadioButton.isSelected()) {
                currentRepresentation = Representation.HEX;
            }
        });

        Button encryptButton = new Button("Encrypt");
        Button decryptButton = new Button("Decrypt");

        encryptButton.setOnAction(e -> {
            String inputText = inputTextArea.getText();
            String key = keyTextField.getText();

            try {
                String paddedInput = padInput(inputText);
                String encryptedText = encrypt(paddedInput, key);
                encryptionOutputTextArea.setText(encodeResult(encryptedText));
            } catch (Exception ex) {
                handleException(ex, encryptionOutputTextArea);
            }
        });

        decryptButton.setOnAction(e -> {
            String encryptedText = encryptionOutputTextArea.getText();
            String key = keyTextField.getText();

            try {
                String decryptedText = decrypt(decodeResult(encryptedText), key);
                decryptionOutputTextArea.setText(decryptedText);
            } catch (Exception ex) {
                handleException(ex, decryptionOutputTextArea);
            }
        });

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.addRow(0, inputLabel, inputTextArea);
        gridPane.addRow(1, keyLabel, keyTextField);
        gridPane.addRow(2, asciiRadioButton, hexRadioButton);
        gridPane.addRow(3, encryptButton, decryptButton);
        gridPane.addRow(4, encryptionOutputLabel, encryptionOutputTextArea);
        gridPane.addRow(5, decryptionOutputLabel, decryptionOutputTextArea);

        Scene scene = new Scene(gridPane, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static String padInput(String input) {
        if (input.length() == 16) {
            return input;
        }

        int length = input.length();
        int paddingLength = 16 - (length % 16);
        StringBuilder paddedInput = new StringBuilder(input);
        for (int i = 0; i < paddingLength; i++) {
            paddedInput.append('*');
        }
        return paddedInput.toString();
    }

    private static String encrypt(String data, String key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(CHARSET), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] encryptedBytes = cipher.doFinal(data.getBytes(CHARSET));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    private static String decrypt(String data, String key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(CHARSET), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] decodedBytes = Base64.getDecoder().decode(data);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes, CHARSET);
    }

    private String encodeResult(String result) {
        if (currentRepresentation == Representation.HEX) {
            return bytesToHex(result.getBytes());
        } else {
            return result;
        }
    }

    private String decodeResult(String result) throws UnsupportedEncodingException {
        if (currentRepresentation == Representation.HEX) {
            return new String(hexToBytes(result), CHARSET);
        } else {
            return result;
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexStringBuilder = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            hexStringBuilder.append(String.format("%02x", b));
        }
        return hexStringBuilder.toString();
    }

    private static byte[] hexToBytes(String hex) {
        int length = hex.length();
        byte[] bytes = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return bytes;
    }

    private static void handleException(Exception ex, TextArea outputTextArea) {
        if (ex instanceof UnsupportedEncodingException) {
            outputTextArea.setText("Error: Unsupported Encoding");
        } else {
            outputTextArea.setText("Error: " + ex.getMessage());
        }
    }
}