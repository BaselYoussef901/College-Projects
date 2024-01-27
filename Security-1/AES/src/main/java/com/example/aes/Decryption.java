package com.example.aes;

public class Decryption {
    private String CipherText;
    private Constrains CONST = new Constrains();
    private String[] Keys;
    private int Padding = 0;
    private int ROUNDS = -1;
    private static final String[][] invS_BOX = {
            {"52", "09", "6A", "D5", "30", "36", "A5", "38", "BF", "40", "A3", "9E", "81", "F3", "D7", "FB"},
            {"7C", "E3", "39", "82", "9B", "2F", "FF", "87", "34", "8E", "43", "44", "C4", "DE", "E9", "CB"},
            {"54", "7B", "94", "32", "A6", "C2", "23", "3D", "EE", "4C", "95", "0B", "42", "FA", "C3", "4E"},
            {"08", "2E", "A1", "66", "28", "D9", "24", "B2", "76", "5B", "A2", "49", "6D", "8B", "D1", "25"},
            {"72", "F8", "F6", "64", "86", "68", "98", "16", "D4", "A4", "5C", "CC", "5D", "65", "B6", "92"},
            {"6C", "70", "48", "50", "FD", "ED", "B9", "DA", "5E", "15", "46", "57", "A7", "8D", "9D", "84"},
            {"90", "D8", "AB", "00", "8C", "BC", "D3", "0A", "F7", "E4", "58", "05", "B8", "B3", "45", "06"},
            {"D0", "2C", "1E", "8F", "CA", "3F", "0F", "02", "C1", "AF", "BD", "03", "01", "13", "8A", "6B"},
            {"3A", "91", "11", "41", "4F", "67", "DC", "EA", "97", "F2", "CF", "CE", "F0", "B4", "E6", "73"},
            {"96", "AC", "74", "22", "E7", "AD", "35", "85", "E2", "F9", "37", "E8", "1C", "75", "DF", "6E"},
            {"47", "F1", "1A", "71", "1D", "29", "C5", "89", "6F", "B7", "62", "0E", "AA", "18", "BE", "1B"},
            {"FC", "56", "3E", "4B", "C6", "D2", "79", "20", "9A", "DB", "C0", "FE", "78", "CD", "5A", "F4"},
            {"1F", "DD", "A8", "33", "88", "07", "C7", "31", "B1", "12", "10", "59", "27", "80", "EC", "5F"},
            {"60", "51", "7F", "A9", "19", "B5", "4A", "0D", "2D", "E5", "7A", "9F", "93", "C9", "9C", "EF"},
            {"A0", "E0", "3B", "4D", "AE", "2A", "F5", "B0", "C8", "EB", "BB", "3C", "83", "53", "99", "61"},
            {"17", "2B", "04", "7E", "BA", "77", "D6", "26", "E1", "69", "14", "63", "55", "21", "0C", "7D"}
    };
    private static final String[][] invMix_Columns = {
            {"0E", "0B", "0D", "09"},
            {"09", "0E", "0B", "0D"},
            {"0D", "09", "0E", "0B"},
            {"0B", "0D", "09", "0E"}
    };
    private String convert(String[] SR_LAYER) {
        String result = "";
        for (String sr : SR_LAYER)
            result += sr;
        return result;
    }
    private String[] back(String KA_LAYER){
        String[] result = new String[4];
        int idx = 0;
        for(int i=0; i<KA_LAYER.length(); i+=32)
            result[idx++] = KA_LAYER.substring(i, i+32);
        return result;
    }

    private String KEY_ADDITION(String bText, String bKey) {
        return CONST.KEY_ADDITION(bText, bKey);
    }

    private String BYTE_SUBSTITUTION(String bWord, String[][] S_BOX) {
        return CONST.BYTE_SUBSTITUTION(bWord, S_BOX);
    }

    private String[] SHIFT_ROWS(String bLine) {
        return CONST.SHIFT_ROWS(bLine, false);
    }

    private String MIX_COLUMNS(String[] Table, String[][] MixColumns) {
        return CONST.MIX_COLUMNS(Table, MixColumns);
    }

    protected String ___Decrypt___() {
        String bCipherText, KA_LAYER, BS_LAYER, MC_LAYER;
        String[] SR_LAYER;

        //bCipherText = CONST.ascii2bin(this.CipherText);
        bCipherText = CipherText;
        KA_LAYER = KEY_ADDITION(bCipherText, Keys[this.ROUNDS]);
        SR_LAYER = SHIFT_ROWS(KA_LAYER);
        BS_LAYER = BYTE_SUBSTITUTION(convert(SR_LAYER), invS_BOX);

        for(int currentRound = this.ROUNDS-1; currentRound>=1; --currentRound){
            KA_LAYER = KEY_ADDITION(BS_LAYER, Keys[currentRound]);
            MC_LAYER = MIX_COLUMNS(back(KA_LAYER), invMix_Columns);
            SR_LAYER = SHIFT_ROWS(MC_LAYER);
            BS_LAYER = BYTE_SUBSTITUTION(convert(SR_LAYER), invS_BOX);
        }

        KA_LAYER = KEY_ADDITION(BS_LAYER, Keys[0]);
        return CONST.bin2asciii(KA_LAYER);
    }





    public Decryption(Pair<String, Integer> C_Text, Pair<String[], Integer> generatedKeys) {
        this.CipherText = C_Text.getFirst();
        this.Padding = C_Text.getSecond();
        this.Keys = generatedKeys.getFirst();
        this.ROUNDS = generatedKeys.getSecond();

    }

    public static void main(String[] args) {
        Constrains CONST = new Constrains();
        String Key = "AABBCCDDEEFFGGHH";
        KeyGeneration KG = new KeyGeneration(Key);
        Pair<String[], Integer> Keys = KG.___generateKeys___();

        String PlainText = "ABCDEFGHIJKLMNOP";
        Encryption EN = new Encryption(new Pair(PlainText, 0), Keys);
        String Encryption = EN.___Encrypt___();
        System.out.println("Encryption: " + CONST.bin2ascii(Encryption));

        Decryption DE = new Decryption(new Pair<>(Encryption, 0), Keys);
        String Decryption = DE.___Decrypt___();
        System.out.println("Decryption: " + CONST.bin2ascii(Decryption));
    }
}
