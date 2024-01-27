package com.example.finaldes;

import java.util.ArrayList;
import java.util.Collections;

public class DataEncryptionStandard {
    private final Constrains CONST = new Constrains();

    public DataEncryptionStandard(String format) {
        this.format = format;
    }

    private ArrayList<String> __generateKeys__(String Key) {
        // 1. Getting Binary Key
        Key = handleFormat(Key, "is" + this.format);

        // 2. PC1
        Key = __permute__(Key, Constrains.PermutedChoice1, 56);

        // Split Key
        String LeftKey = Key.substring(0, Key.length() / 2);
        String RightKey = Key.substring(Key.length() / 2);

        // generated key at each round
        ArrayList<String> generatedKeys = new ArrayList<>();
        for (int i = 0; i < Constrains.ROUNDS; i++) {
            LeftKey = __shiftLeft__(LeftKey, Constrains.ShiftIterations[i]);
            RightKey = __shiftLeft__(RightKey, Constrains.ShiftIterations[i]);

            String KeyRI = __permute__(LeftKey + RightKey, Constrains.PermutedChoice2, 48);
            generatedKeys.add(KeyRI);
        }
        return generatedKeys;
    }

    public String encrypt(String Text, String Key) {
        ArrayList<String> generateKeys = __generateKeys__(Key);
        FX_Keys = generateKeys;
        // Input Handler
        ArrayList<Pair<String, Integer>> Texts = handleInput(Text);
        String Encryption = "";
        for (int i = 0; i < Texts.size(); i++) {
            String X = handleFormat(DES(Texts.get(i).getFirst(), generateKeys), "to" + format);
            Encryptions.add(new Pair<>(X, Texts.get(i).getSecond()));
            Encryption += X;
        }
        return Encryption;
    }

    public String decrypt(String Text, String Key) {
        ArrayList<String> generateKeys = __generateKeys__(Key);
        Collections.reverse(generateKeys);
        FX_Keys = generateKeys;
        // Decryption Handler
        String Decryption = "";
        for (int i = 0; i < Encryptions.size(); i++) {
            String Y = handleFormat(DES(Encryptions.get(i).getFirst(), generateKeys), "to" + format);
            Y = Y.substring(0, Y.length() - Encryptions.get(i).getSecond());
            Decryption += Y;
        }
        return Decryption;
    }

















    protected String format;
    private ArrayList<Pair<String, Integer>> Encryptions = new ArrayList<>();
    protected ArrayList<String> FX_Keys;
    protected ArrayList<Pair<String,String>> FX_LR;
    protected ArrayList<String>FX_RightEXP, FX_SBOX, FX_P, FX_Expansion_xor_Keyi, FX_Left_xor_PBOX;


    private int multiple(int number, int base) {
        return number % base == 0 ? 0 : (((number + (base - 1)) / base) * base);
    }

    private ArrayList<Pair<String, Integer>> handleInput(String Text) {
        ArrayList<Pair<String, Integer>> texts = new ArrayList<>();
        if (format.equals("Hexa")) {
            int N = multiple(Text.length(), 16);
            Integer padding = N - Text.length();
            for (int i = 0; i < padding; i++)
                Text += '0';
            if (N > 0) {
                for (int i = 0; i < N - 16; i += 16)
                    texts.add(new Pair<>(Text.substring(i, i + 16), 0));
                texts.add(new Pair<>(Text.substring(N - 16, N), padding));
            } else
                texts.add(new Pair<>(Text, 0));
        } else if (format.equals("ASCII")) {
            int N = multiple(Text.length(), 8);
            Integer padding = N - Text.length();
            for (int i = 0; i < padding; i++)
                Text += '@';
            if (N > 0) {
                for (int i = 0; i < N - 8; i += 8)
                    texts.add(new Pair<>(Text.substring(i, i + 8), 0));
                texts.add(new Pair<>(Text.substring(N - 8, N), padding));
            } else
                texts.add(new Pair<>(Text, 0));
        }
        return texts;
    }

    protected String handleFormat(String Conversion, String format) {
        if (format.equals("isDecimal"))
            return CONST.dec2bin(Integer.parseInt(Conversion));
        if (format.equals("isHexa"))
            return CONST.hex2bin(Conversion);
        if (format.equals("isASCII"))
            return CONST.ascii2bin(Conversion);
        if (format.equals("toDecimal"))
            return String.valueOf(CONST.bin2dec(Conversion));
        if (format.equals("toHexa"))
            return CONST.bin2hex(Conversion);
        if (format.equals("toASCII"))
            return CONST.bin2ascii(Conversion);
        return "UwU";
    }

    private String __permute__(String Key, int[] Permutation, int n) {
        StringBuilder permutation = new StringBuilder();
        for (int i = 0; i < n; i++)
            permutation.append(Key.charAt(Permutation[i] - 1));
        return permutation.toString();
    }

    private String __shiftLeft__(String Key, int Shifts) {
        String shift_LeftBits = Key.substring(Shifts);
        String remaining_leftBits = Key.substring(0, Shifts);
        return shift_LeftBits + remaining_leftBits;
    }

    private String __xor__(String a1, String a2) {
        StringBuilder XORs = new StringBuilder();
        for (int i = 0; i < a1.length(); i++) {
            if (a1.charAt(i) == a2.charAt(i))
                XORs.append("0");
            else
                XORs.append("1");
        }
        return XORs.toString();
    }

    private String DES(String Text, ArrayList<String> generatedKeys) {
        FX_LR = new ArrayList<>();
        FX_RightEXP = new ArrayList<>();
        FX_SBOX = new ArrayList<>();
        FX_P = new ArrayList<>();
        FX_Expansion_xor_Keyi = new ArrayList<>();
        FX_Left_xor_PBOX = new ArrayList<>();

        Text = handleFormat(Text, "is" + format);

        // Initial Permutation - IP
        Text = __permute__(Text, Constrains.InitialPermutation, 64);

        // Split - Rounds
        String LeftText = Text.substring(0, Text.length() / 2);
        String RightText = Text.substring(Text.length() / 2);
        FX_LR.add(new Pair<>(LeftText,RightText));
        for (int i = 0; i < Constrains.ROUNDS; i++) {
            // Expansion to Right
            String RightExpansion = __permute__(RightText, Constrains.Expansion, 48);
            FX_RightEXP.add(RightExpansion);

            // XOR (Expansion ^ Key Round i)
            String XOR = __xor__(RightExpansion, generatedKeys.get(i));
            FX_Expansion_xor_Keyi.add(XOR);

            // S-Boxes
            StringBuilder SBox = new StringBuilder();
            for (int k = 0; k < 8; k++) {
                int row = Integer.parseInt(handleFormat(XOR.charAt(k * 6) + "" + XOR.charAt(k * 6 + 5), "toDecimal"));
                int col = Integer.parseInt(handleFormat("" +
                        XOR.charAt(k * 6 + 1) + XOR.charAt(k * 6 + 2) + XOR.charAt(k * 6 + 3) + XOR.charAt(k * 6 + 4), "toDecimal"
                ));
                int s_value = Constrains.S_BOXES[k][row][col];
                SBox.append(handleFormat(String.valueOf(s_value), "isDecimal"));
            }
            FX_SBOX.add(SBox.toString());

            // Permutation Box - P
            String P_BOX = __permute__(SBox.toString(), Constrains.PermutationBOX, 32);
            FX_P.add(P_BOX);

            // XOR (PBOX ^ oldLeft)
            LeftText = __xor__(LeftText, P_BOX);
            FX_Left_xor_PBOX.add(LeftText);

            if (i != 15) {
                String temp = LeftText;
                LeftText = RightText;
                RightText = temp;
            }
            FX_LR.add(new Pair<>(LeftText,RightText));
        }

//        System.out.println("Rounds\tLeft\t\tRight\t\tEXP\t\tE^K\t\tSBOX\t\tP\t\t\tL^P");
//        for(int i=0; i<16; i++)
//            System.out.println(
//                    "Round "+(i+1)+"\t"
//                    +FX_LR.get(i).getFirst()+"\t"
//                    +FX_LR.get(i).getSecond()+"\t"
//                    +FX_RightEXP.get(i)+"\t"
//                    +FX_Expansion_xor_Keyi.get(i)+"\t"
//                    +FX_SBOX.get(i)+"\t"
//                    +FX_P.get(i)+"\t"
//                    +FX_Left_xor_PBOX.get(i)
//            );

        // Final Permutation - FP
        return __permute__(LeftText + RightText, Constrains.FinalPermutation, 64);
    }
}
