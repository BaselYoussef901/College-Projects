package com.example.aes;

import java.security.Key;
import java.util.ArrayList;

public class Encryption {
    static String PlainText;
    private Constrains CONST = new Constrains();
    private String[] Keys;
    private int Padding = 0;
    private int ROUNDS = -1;
    private static final String[][] S_BOX = {
            {"63", "7C", "77", "7B", "F2", "6B", "6F", "C5", "30", "01", "67", "2B", "FE", "D7", "AB", "76"},
            {"CA", "82", "C9", "7D", "FA", "59", "47", "F0", "AD", "D4", "A2", "AF", "9C", "A4", "72", "C0"},
            {"B7", "FD", "93", "26", "36", "3F", "F7", "CC", "34", "A5", "E5", "F1", "71", "D8", "31", "15"},
            {"04", "C7", "23", "C3", "18", "96", "05", "9A", "07", "12", "80", "E2", "EB", "27", "B2", "75"},
            {"09", "83", "2C", "1A", "1B", "6E", "5A", "A0", "52", "3B", "D6", "B3", "29", "E3", "2F", "84"},
            {"53", "D1", "00", "ED", "20", "FC", "B1", "5B", "6A", "CB", "BE", "39", "4A", "4C", "58", "CF"},
            {"D0", "EF", "AA", "FB", "43", "4D", "33", "85", "45", "F9", "02", "7F", "50", "3C", "9F", "A8"},
            {"51", "A3", "40", "8F", "92", "9D", "38", "F5", "BC", "B6", "DA", "21", "10", "FF", "F3", "D2"},
            {"CD", "0C", "13", "EC", "5F", "97", "44", "17", "C4", "A7", "7E", "3D", "64", "5D", "19", "73"},
            {"60", "81", "4F", "DC", "22", "2A", "90", "88", "46", "EE", "B8", "14", "DE", "5E", "0B", "DB"},
            {"E0", "32", "3A", "0A", "49", "06", "24", "5C", "C2", "D3", "AC", "62", "91", "95", "E4", "79"},
            {"E7", "C8", "37", "6D", "8D", "D5", "4E", "A9", "6C", "56", "F4", "EA", "65", "7A", "AE", "08"},
            {"BA", "78", "25", "2E", "1C", "A6", "B4", "C6", "E8", "DD", "74", "1F", "4B", "BD", "8B", "8A"},
            {"70", "3E", "B5", "66", "48", "03", "F6", "0E", "61", "35", "57", "B9", "86", "C1", "1D", "9E"},
            {"E1", "F8", "98", "11", "69", "D9", "8E", "94", "9B", "1E", "87", "E9", "CE", "55", "28", "DF"},
            {"8C", "A1", "89", "0D", "BF", "E6", "42", "68", "41", "99", "2D", "0F", "B0", "54", "BB", "16"}
    };
    private static final String[][] Mix_Columns = {
            {"02", "03", "01", "01"},
            {"01", "02", "03", "01"},
            {"01", "01", "02", "03"},
            {"03", "01", "01", "02"}
    };

    private String convert(String[] SR_LAYER) {
        String result = "";
        for (String sr : SR_LAYER)
            result += sr;
        return result;
    }

    private String KEY_ADDITION(String bText, String bKey) {
        return CONST.KEY_ADDITION(bText, bKey);
    }

    private String BYTE_SUBSTITUTION(String bWord, String[][] S_BOX) {
        return CONST.BYTE_SUBSTITUTION(bWord, S_BOX);
    }

    private String[] SHIFT_ROWS(String bLine) {
        return CONST.SHIFT_ROWS(bLine, true);
    }

    private String MIX_COLUMNS(String[] Table, String[][] MixColumns) {
        return CONST.MIX_COLUMNS(Table, MixColumns);
    }


    protected String ___Encrypt___() {
        String bPlainText, KA_LAYER, BS_LAYER, MC_LAYER;
        String[] SR_LAYER;

        bPlainText = CONST.ascii2bin(this.PlainText);

        KA_LAYER = KEY_ADDITION(bPlainText, Keys[0]);

        for (int currentRound = 1; currentRound <= this.ROUNDS - 1; currentRound++) {
            BS_LAYER = BYTE_SUBSTITUTION(KA_LAYER, S_BOX);
            SR_LAYER = SHIFT_ROWS(BS_LAYER);
            MC_LAYER = MIX_COLUMNS(SR_LAYER, Mix_Columns);
            KA_LAYER = KEY_ADDITION(MC_LAYER, Keys[currentRound]);
        }

        BS_LAYER = BYTE_SUBSTITUTION(KA_LAYER, S_BOX);
        SR_LAYER = SHIFT_ROWS(BS_LAYER);
        KA_LAYER = KEY_ADDITION(convert(SR_LAYER), Keys[ROUNDS]);

        return KA_LAYER;
    }

    public Encryption(Pair<String, Integer> P_Text, Pair<String[], Integer> generatedKeys) {
        this.PlainText = P_Text.getFirst();
        this.Padding = P_Text.getSecond();
        this.Keys = generatedKeys.getFirst();
        this.ROUNDS = generatedKeys.getSecond();

    }

//    public static void main(String[] args) {
//        Constrains CONST = new Constrains();
//
//        String Key = "AABBCCDDEEFFGGHH";
//        KeyGeneration KG = new KeyGeneration(Key);
//        Pair<String[], Integer> Keys = KG.___generateKeys___();
//
//        String PlainText = "ABCDEFGHIJKLMNOP";
//        Encryption EN = new Encryption(new Pair(PlainText, 0), Keys);
//        String Encryption = EN.___Encrypt___();
//        System.out.println("Encryption: " + CONST.bin2ascii(Encryption));
//    }

}
