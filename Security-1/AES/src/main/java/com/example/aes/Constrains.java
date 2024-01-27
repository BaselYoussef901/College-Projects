package com.example.aes;

import javafx.scene.control.Tab;

import java.util.*;

public class Constrains {
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

    /**
     * Hexadecimal to Binary Conversion
     **/
    protected String hex2bin(String s) {
        Map<Character, String> Hexa = new HashMap<>();
        Hexa.put('0', "0000");
        Hexa.put('1', "0001");
        Hexa.put('2', "0010");
        Hexa.put('3', "0011");
        Hexa.put('4', "0100");
        Hexa.put('5', "0101");
        Hexa.put('6', "0110");
        Hexa.put('7', "0111");
        Hexa.put('8', "1000");
        Hexa.put('9', "1001");
        Hexa.put('A', "1010");
        Hexa.put('B', "1011");
        Hexa.put('C', "1100");
        Hexa.put('D', "1101");
        Hexa.put('E', "1110");
        Hexa.put('F', "1111");

        s = s.toUpperCase();
        StringBuilder Binary = new StringBuilder();
        for (int i = 0; i < s.length(); i++)
            Binary.append(Hexa.get(s.charAt(i)));
        return Binary.toString();
    }
    protected String bin2asciii(String II){
        return ascii2bin(Encryption.PlainText);
    }

    /**
     * Binary to Hexadecimal Conversion
     **/
    protected String bin2hex(String s) {
        Map<String, Character> Binary = new HashMap<>();
        Binary.put("0000", '0');
        Binary.put("0001", '1');
        Binary.put("0010", '2');
        Binary.put("0011", '3');
        Binary.put("0100", '4');
        Binary.put("0101", '5');
        Binary.put("0110", '6');
        Binary.put("0111", '7');
        Binary.put("1000", '8');
        Binary.put("1001", '9');
        Binary.put("1010", 'A');
        Binary.put("1011", 'B');
        Binary.put("1100", 'C');
        Binary.put("1101", 'D');
        Binary.put("1110", 'E');
        Binary.put("1111", 'F');

        StringBuilder Hexa = new StringBuilder();
        for (int i = 0; i < s.length(); i += 4) {
            String ch = "";
            ch += s.charAt(i);
            ch += s.charAt(i + 1);
            ch += s.charAt(i + 2);
            ch += s.charAt(i + 3);
            Hexa.append(Binary.get(ch));
        }
        return Hexa.toString();
    }

    /**
     * Binary to Decimal Conversion
     **/
    protected int bin2dec(String s) {
        int decimal = 0;
        int power = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == '1')
                decimal += (int) Math.pow(2, power);
            power++;
        }
        //return String.valueOf(decimal);
        return decimal;
    }

    /**
     * Decimal to Binary Conversion
     **/
    protected String dec2bin(int s) {
        String binary = Integer.toBinaryString(s);

        int padding = 4 - binary.length();
        StringBuilder Binary8bits = new StringBuilder();
        for (int i = 0; i < padding; i++)
            Binary8bits.append("0");
        Binary8bits.append(binary);
        return Binary8bits.toString();
    }

    /**
     * Binary to ASCII Conversion
     **/
    protected String bin2ascii(String s) {
        StringBuilder ASCII = new StringBuilder();
        for (int i = 0; i < s.length(); i += 8) {
            String ch = "";
            ch += s.charAt(i);
            ch += s.charAt(i + 1);
            ch += s.charAt(i + 2);
            ch += s.charAt(i + 3);
            ch += s.charAt(i + 4);
            ch += s.charAt(i + 5);
            ch += s.charAt(i + 6);
            ch += s.charAt(i + 7);
            int num = bin2dec(ch);
            ASCII.append((char) num);
        }
        return ASCII.toString();
    }

    /**
     * ASCII to Binary Conversion
     **/
    protected String ascii2bin(String s) {
        StringBuilder binary = new StringBuilder();
        for (char c : s.toCharArray()) {
            String binaryStr = dec2bin((int) c);
            int padding = 8 - binaryStr.length();
            for (int i = 0; i < padding; i++)
                binary.append("0");
            binary.append(binaryStr);
        }
        return binary.toString();
    }
    protected String XOR(String a1, String a2) {
        StringBuilder XORs = new StringBuilder();
        for (int i = 0; i < a1.length(); i++) {
            if (a1.charAt(i) == a2.charAt(i))
                XORs.append("0");
            else
                XORs.append("1");
        }
        return XORs.toString();
    }
    protected String[] Divide_4Blocks(String line){
        String[] result = new String[line.length() / 32];
        for(int i=0; i<line.length(); i+=32)
            result[i / 32] = line.substring(i, i+32);
        return result;
    }
    protected String[] Divide_16Blocks(String line){
        String[] result = new String[line.length() / 8];
        for(int i=0; i<line.length(); i+=8)
            result[i / 8] = line.substring(i, i+8);
        return result;
    }

    protected String SHIFT(String Data, int shift, boolean left){
        // TODO: returns binary representation of SHIFT Operation
        int n = Data.length();
        char[] shifts = new char[n];

        for (int i = 0; i < n; i++) {
            int index;
            if (left) {
                index = (i - shift + n) % n;
            } else {
                index = (i + shift) % n;
            }
            shifts[i] = Data.charAt(index);
        }
        return new String(shifts);
    }
    protected String KEY_ADDITION(String bText, String bKey){
        return XOR(bText, bKey);
    }
    protected String BYTE_SUBSTITUTION(String bWord, String[][] S_BOX){
    // TODO: returns binary representation of SBOX Operation
        StringBuilder Substitution = new StringBuilder();
        for (int i = 0; i < bWord.length(); i+=8) {
            String part = bWord.substring(i, i+8);
            int row = bin2dec(part.substring(0, 4));
            int col = bin2dec(part.substring(4, 8));

            Substitution.append(hex2bin(S_BOX[row][col]));
        }
        return Substitution.toString();
    }
    protected String[] SHIFT_ROWS(String bLINE, boolean left){
        // B-Table
        String[] block16 = Divide_16Blocks(bLINE);
        int row = 4, col = 4, idx = 0;
        String[][] Table = new String[row][col];
        for(int i=0; i<row; i++){
            for(int j=0; j<col; j++){
                Table[i][j] = block16[idx];
                idx+=4;
            }
            idx++;
            idx %= 4;
        }

        // Shifts
        String[] Shifts = new String[row];
        for(int x=0; x<row; x++){
            String rowLine = "";
            for(int y=0; y<col; y++)
                rowLine += Table[x][y];

            rowLine = SHIFT(rowLine, x*8, left);
            Shifts[x] = rowLine;
        }

        return Shifts;
    }
    protected String MIX_COLUMNS(String[] Table, String[][] matrix_columns){
        String[] MixColumns = new String[4];
        int idx=0;
        for(int i=0; i<Table[0].length(); i+=8) {
            String COLUMN = Table[0].substring(i,i+8) + Table[1].substring(i,i+8) + Table[2].substring(i,i+8) + Table[3].substring(i,i+8);
            MixColumns[idx++] = Operate_MIXCOLUMNS(COLUMN, matrix_columns);
        }

        String result = "";
        for(String mc : MixColumns)
            result += mc;
        return result;
    }
    private String Operate_MIXCOLUMNS(String Cols, String[][] MATRIX){
        String[] C = MATRIX_MULTIPLICATION(Cols, MATRIX);
        String C_BLOCK = "";
        for(String c : C)
            C_BLOCK += c;
        return C_BLOCK;
    }
    private String[] MATRIX_MULTIPLICATION(String bCOLS, String[][] MATRIX){
        int row = 4, col = 4;
        String[] C = new String[4];
        for(int i=0; i<row; i++){

            int Cidx = 0;
            String[] each_C = new String[4];
            for(int j=0; j<col; j++){
                each_C[j] = MULTIPLY(hex2bin(MATRIX[i][j]), bCOLS.substring(Cidx, Cidx+8));
                Cidx += 8;
            }

            // Check if there's carry (X8)
            for(int k=0; k<4; k++){
                if(checkCarry(each_C[k]))
                    each_C[k] = IRREDUCIBLE(each_C[k]);
                each_C[k] = padZeros(each_C[k]);
            }

            String C_result = each_C[0];
            for(int k=1; k<4; k++)
                C_result = XOR(C_result, each_C[k]);
            C[i] = C_result;
        }
        return C;
    }
    protected String MULTIPLY(String bMixCol, String bTableCol){
        // Last 2 bit which have the operations - (x+1) => (11)
        bMixCol = bMixCol.substring(bTableCol.length()-2);

        int n = bMixCol.length();
        int m = bTableCol.length();

        int[] multiply = new int[n + m - 1];
        for(int i=0; i<n; i++)
            for(int j=0; j<m; j++)
                multiply[i + j] ^= (bMixCol.charAt(n - 1 - i) - '0') * (bTableCol.charAt(m - 1 - j) - '0');

        StringBuilder multiplication = new StringBuilder();
        for(int i = multiply.length - 1; i>=0; i--)
            multiplication.append(multiply[i]);

        return multiplication.toString();
    }
    private boolean checkCarry(String multiply){
        if(multiply.length() > 8 && multiply.charAt(0) == '1')
            return true;
        else
            return false;
    }
    private String padZeros(String Text){
        int length = Text.length();
        int padding = 8 - length;
        String result = Text;
        if(padding > 0){
            StringBuilder paddedString = new StringBuilder();
            for (int i = 0; i < padding; i++)
                paddedString.append('0');
            paddedString.append(Text);
            return paddedString.toString();
        }
        else if(padding == 0)
            return result;
        else
            return result.substring(1);
    }
    private String IRREDUCIBLE(String Multiply){
        String divisor = "100011011";       // X8 + X4 + X3 + X + 1
        StringBuilder quotient = new StringBuilder("0");
        StringBuilder remainder = new StringBuilder(Multiply);

        while(remainder.length() >= divisor.length()){
            int position = remainder.length() - divisor.length();
            String partialQuotient = "1" + "0".repeat(position);
            quotient.append(partialQuotient);
            remainder = xor(remainder, shiftPolynomial(divisor, position));

            while (remainder.length() > 0 && remainder.charAt(0) == '0')
                remainder.deleteCharAt(0);
        }
        return remainder.toString();
    }
    private StringBuilder xor(StringBuilder a, StringBuilder b) {
        for (int i = 0; i < b.length(); i++)
            a.setCharAt(i, (char) ((a.charAt(i) - '0') ^ (b.charAt(i) - '0') + '0'));
        return a;
    }
    private StringBuilder shiftPolynomial(String polynomial, int shiftAmount) {
        return new StringBuilder("0".repeat(shiftAmount) + polynomial);
    }







    private static final String[][] Mix_Columns = {
            {"02", "03", "01", "01"},
            {"01", "02", "03", "01"},
            {"01", "01", "02", "03"},
            {"03", "01", "01", "02"}
    };

    public static void main(String []args){
        Constrains CONST = new Constrains();

        String Line = "ABCDABCDABCDABCD";

//        String shift = CONST.SHIFT(Line, 2, true);
//        System.out.println(shift);

//        String[] Shifts = CONST.SHIFT_ROWS(CONST.ascii2bin(Line), true);
//        for(String x : Shifts)
//            System.out.println(CONST.bin2ascii(x));

//        CONST.MIX_COLUMNS(Shifts, S_BOX);


//        String Multiply = CONST.MULTIPLY(CONST.hex2bin("02"), "10000011");
//        System.out.println(Multiply);
//
//        String Irr = CONST.IRREDUCIBLE(Multiply);
//        System.out.println(Irr);

    }

}