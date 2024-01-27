package com.example.aes;

import java.util.ArrayList;

public class KeyGeneration {
    private String Key;
    private int ROUNDS = -1;
    private Constrains CONST = new Constrains();
    private static final String[] ROUND_CONSTANTS = {
            "01", "02", "04", "08", "10", "20", "40", "80", "1B", "36"
    };
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

    public String G(String bWord, int roundState) {   // 32-bit
        String shifted_bWord = CONST.SHIFT(bWord, 8, true);

        String sbox_bWord = CONST.BYTE_SUBSTITUTION(shifted_bWord, S_BOX);

        String changedPart = sbox_bWord.substring(0, 8);
        String remainPart = sbox_bWord.substring(8);

        changedPart = CONST.XOR(changedPart, CONST.hex2bin(ROUND_CONSTANTS[roundState]));

        return changedPart + remainPart;
    }

    protected Pair<String[], Integer> ___generateKeys___() {
        String bKey = CONST.ascii2bin(this.Key);
        String[] KEYS = new String[this.ROUNDS + 1];
        KEYS[0] = bKey;
        String[] bKey_BLOCKS = CONST.Divide_4Blocks(bKey);


        for (int currentRound = 1; currentRound <= this.ROUNDS; currentRound++) {
            String gSubKey = G(bKey_BLOCKS[3], currentRound - 1);

            String W0 = CONST.XOR(bKey_BLOCKS[0], gSubKey);
            String W1 = CONST.XOR(W0, bKey_BLOCKS[1]);
            String W2 = CONST.XOR(W1, bKey_BLOCKS[2]);
            String W3 = CONST.XOR(W2, bKey_BLOCKS[3]);

            KEYS[currentRound] = (W0 + W1 + W2 + W3);

            bKey_BLOCKS[0] = W0;
            bKey_BLOCKS[1] = W1;
            bKey_BLOCKS[2] = W2;
            bKey_BLOCKS[3] = W3;
        }
        return new Pair<>(KEYS, this.ROUNDS);
    }


    public KeyGeneration(String Key) {
        this.Key = Key;
        if (Key.length() == 16)      // Key-128
            this.ROUNDS = 10;
        if (Key.length() == 24)      // Key-192
            this.ROUNDS = 12;
        if (Key.length() == 32)      // Key-256
            this.ROUNDS = 14;
    }

    public static void main(String[] args) {
        String Key = "AABBCCDDEEFFGGHH";
        KeyGeneration KG = new KeyGeneration(Key);

        Pair<String[], Integer> Keys = KG.___generateKeys___();
        for (String k : Keys.getFirst())
            System.out.println(k + "\t" + k.length());



    }
}