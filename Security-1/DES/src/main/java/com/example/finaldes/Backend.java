package com.example.finaldes;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import java.util.ArrayList;

public class Backend {
    DataEncryptionStandard DES;
    class Table{
        private SimpleStringProperty round;
        private SimpleStringProperty Data1;
        private SimpleStringProperty Data2;
        private SimpleStringProperty Data3;
        private SimpleStringProperty Data4;
        private SimpleStringProperty Data5;
        private SimpleStringProperty Data6;
        private SimpleStringProperty Data7;
        public Table(String round, String Binary, String Decimal, String D3){
            this.round = new SimpleStringProperty(round);
            this.Data1 = new SimpleStringProperty(Binary);
            this.Data2 = new SimpleStringProperty(Decimal);
            this.Data3 = new SimpleStringProperty(D3);
        }
        public Table(String round, String D1, String D2, String D3, String D4, String D5, String D6, String D7){
            this(round,D1,D2,D3);
            this.Data4 = new SimpleStringProperty(D4);
            this.Data5 = new SimpleStringProperty(D5);
            this.Data6 = new SimpleStringProperty(D6);
            this.Data7 = new SimpleStringProperty(D7);
        }
        public SimpleStringProperty roundProperty() {
            return round;
        }
        public SimpleStringProperty data1Property() {
            return Data1;
        }
        public SimpleStringProperty data2Property() {
            return Data2;
        }
        public SimpleStringProperty data3Property() {
            return Data3;
        }
        public SimpleStringProperty data4Property() {
            return Data4;
        }
        public SimpleStringProperty data5Property() {
            return Data5;
        }
        public SimpleStringProperty data6Property() {
            return Data6;
        }
        public SimpleStringProperty data7Property() {
            return Data7;
        }
    }
    public Scene Details(DataEncryptionStandard DES, ArrayList<String> Keys_Text){
        this.DES = DES;

        TableView<Table> tableView = new TableView<>();
        tableView.setItems(getSampleData(Keys_Text));

        // Create columns
        TableColumn<Table, String> roundsCol = new TableColumn<>("Rounds");
        roundsCol.setCellValueFactory(cellData -> cellData.getValue().roundProperty());

        TableColumn<Table, String> binaryCol = new TableColumn<>("Binary");
        binaryCol.setCellValueFactory(cellData -> cellData.getValue().data1Property());

        TableColumn<Table, String> decimalCol = new TableColumn<>("Decimal");
        decimalCol.setCellValueFactory(cellData -> cellData.getValue().data2Property());

        TableColumn<Table, String> hexaCol = new TableColumn<>("Hexa");
        hexaCol.setCellValueFactory(cellData -> cellData.getValue().data3Property());

        TableColumn<Table, String> asciiCol = new TableColumn<>("ASCII");
        asciiCol.setCellValueFactory(cellData -> cellData.getValue().data3Property());

        if(DES.format.equals("Hexa"))
            tableView.getColumns().addAll(roundsCol, binaryCol, decimalCol, hexaCol);
        else
            tableView.getColumns().addAll(roundsCol, binaryCol, decimalCol, asciiCol);

        VBox root = new VBox(10);
        root.getChildren().add(tableView);

        Scene KeyScene = new Scene(root, 800,600);
        return KeyScene;

    }

    public Scene AlgorithmDetails(ArrayList<Pair<String,String>>LR, ArrayList<String>EXP, ArrayList<String>SBOX, ArrayList<String>P, ArrayList<String>ExpXORkey, ArrayList<String>LxorP){
        ObservableList<Table> tableData = FXCollections.observableArrayList();
        for(int i=0; i<16; i++){
            tableData.add(new Table(
                    "Round " + i,
                    LR.get(i).getFirst(),
                    LR.get(i).getSecond(),
                    EXP.get(i),
                    ExpXORkey.get(i),
                    SBOX.get(i),
                    P.get(i),
                    LxorP.get(i)
            ));
        }
        tableData.add(new Table(
                "Round 16",
                LR.get(16).getFirst(),
                LR.get(16).getSecond(),
                "","","","",""
        ));
        tableData.add(new Table(
                "","","",
                "","","","",""
        ));


        TableView<Table> tableView = new TableView<>();
        tableView.setItems(tableData);

        TableColumn<Table, String> roundsCol = new TableColumn<>("Rounds");
        roundsCol.setCellValueFactory(cellData -> cellData.getValue().roundProperty());

        TableColumn<Table, String> LeftCol = new TableColumn<>("Left");
        LeftCol.setCellValueFactory(cellData -> cellData.getValue().data1Property());

        TableColumn<Table, String> RightCol = new TableColumn<>("Right");
        RightCol.setCellValueFactory(cellData -> cellData.getValue().data2Property());

        TableColumn<Table, String> ExpCol = new TableColumn<>("Expansion-R");
        ExpCol.setCellValueFactory(cellData -> cellData.getValue().data3Property());

        TableColumn<Table, String> EXPxorKeyCol = new TableColumn<>("EXP ^ Key");
        EXPxorKeyCol.setCellValueFactory(cellData -> cellData.getValue().data4Property());

        TableColumn<Table, String> SboxCol = new TableColumn<>("SBox");
        SboxCol.setCellValueFactory(cellData -> cellData.getValue().data5Property());

        TableColumn<Table, String> pCol = new TableColumn<>("Permutation-P");
        pCol.setCellValueFactory(cellData -> cellData.getValue().data6Property());

        TableColumn<Table, String> LxorPCol = new TableColumn<>("oldLeft ^ P");
        LxorPCol.setCellValueFactory(cellData -> cellData.getValue().data7Property());

        tableView.getColumns().addAll(roundsCol, LeftCol, RightCol, ExpCol, EXPxorKeyCol, SboxCol, pCol, LxorPCol);

        VBox root = new VBox(10);
        root.getChildren().add(tableView);

        Scene TextScene = new Scene(root, 800,600);
        return TextScene;
    }










    private ObservableList<Table> getSampleData(ArrayList<String> generatedKeys) {
        ArrayList<String> binaryKeys_onHexa = new ArrayList<>();
        ArrayList<String> binaryKeys_onAscii = new ArrayList<>();
        ArrayList<String> decimalKeys_onHexa = new ArrayList<>();
        ArrayList<String> decimalKeys_onAscii = new ArrayList<>();
        ArrayList<String> hexaKeys = new ArrayList<>();
        ArrayList<String> asciiKeys = new ArrayList<>();

        // For Binary-Hexa
        for(int i=0; i<generatedKeys.size(); i++){
            String OO = "";
            for(int j=0; j<generatedKeys.get(i).length(); j+=4)
                OO += generatedKeys.get(i).substring(j,j+4) + '-';
            binaryKeys_onHexa.add(OO.substring(0, OO.length()-1));
        }

        // For Binary-ASCII
        for(int i=0; i<generatedKeys.size(); i++){
            String OO = "";
            for(int j=0; j<generatedKeys.get(i).length(); j+=8)
                OO += generatedKeys.get(i).substring(j,j+8) + '-';
            binaryKeys_onAscii.add(OO.substring(0, OO.length()-1));
        }

        // For Decimal-Hexa
        for(int i=0; i<generatedKeys.size(); i++){
            String OO = "";
            for(int j=0; j<generatedKeys.get(i).length(); j+=4){
                String op = generatedKeys.get(i).substring(j,j+4);
                OO += DES.handleFormat(op, "toDecimal")+'-';
            }
            decimalKeys_onHexa.add(OO.substring(0,OO.length()-1));
        }
        // For Decimal-ASCII
        for(int i=0; i<generatedKeys.size(); i++){
            String OO = "";
            for(int j=0; j<generatedKeys.get(i).length(); j+=8){
                String op = generatedKeys.get(i).substring(j,j+8);
                OO += DES.handleFormat(op, "toDecimal") + '-';
            }
            decimalKeys_onAscii.add(OO.substring(0,OO.length()-1));
        }
        for (String hexaKey : generatedKeys)
            hexaKeys.add(DES.handleFormat(hexaKey, "toHexa"));
        for (String asciiKey : generatedKeys)
            asciiKeys.add(DES.handleFormat(asciiKey, "toASCII"));



        ObservableList<Table> tableData = FXCollections.observableArrayList();
        if(DES.format.equals("Hexa"))
            for (int i = 1; i <= generatedKeys.size(); i++)
                tableData.add(new Table("Round " + i, binaryKeys_onHexa.get(i-1), decimalKeys_onHexa.get(i-1), hexaKeys.get(i-1)));
        else
            for (int i = 1; i <= generatedKeys.size(); i++)
                tableData.add(new Table("Round " + i, binaryKeys_onAscii.get(i-1), decimalKeys_onAscii.get(i-1), asciiKeys.get(i-1)));
        tableData.add(new Table("", "", "", ""));
        return tableData;
    }
}
