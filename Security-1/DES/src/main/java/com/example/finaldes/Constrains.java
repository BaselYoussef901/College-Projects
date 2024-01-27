package com.example.finaldes;

import java.util.HashMap;
import java.util.Map;

public class Constrains {
    protected static final int []PermutationBOX = {
            16, 7, 20, 21, 29, 12, 28, 17,
            1, 15, 23, 26, 5, 18, 31, 10,
            2, 8, 24, 14, 32, 27, 3, 9,
            19, 13, 30, 6, 22, 11, 4, 25
    };
    protected static final int []InitialPermutation = {
            58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6,
            64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17, 9,  1,
            59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5,
            63, 55, 47, 39, 31, 23, 15, 7
    };
    protected static final int []FinalPermutation = {
            40, 8, 48, 16, 56, 24, 64, 32,
            39, 7, 47, 15, 55, 23, 63, 31,
            38, 6, 46, 14, 54, 22, 62, 30,
            37, 5, 45, 13, 53, 21, 61, 29,
            36, 4, 44, 12, 52, 20, 60, 28,
            35, 3, 43, 11, 51, 19, 59, 27,
            34, 2, 42, 10, 50, 18, 58, 26,
            33, 1, 41, 9,  49, 17, 57, 25
    };
    protected static final int []PermutedChoice1 = {
            57, 49, 41, 33, 25, 17, 9,
            1,  58, 50, 42, 34, 26, 18,
            10, 2,  59, 51, 43, 35, 27,
            19, 11, 3,  60, 52, 44, 36,
            63, 55, 47, 39, 31, 23, 15,
            7,  62, 54, 46, 38, 30, 22,
            14, 6,  61, 53, 45, 37, 29,
            21, 13, 5,  28, 20, 12, 4
    };
    protected static final int []PermutedChoice2 = {
            14, 17, 11, 24, 1,  5,  3,  28,
            15, 6,  21, 10, 23, 19, 12, 4,
            26, 8,  16, 7,  27, 20, 13, 2,
            41, 52, 31, 37, 47, 55, 30, 40,
            51, 45, 33, 48, 44, 49, 39, 56,
            34, 53, 46, 42, 50, 36, 29, 32
    };
    protected static final int []Expansion = {
            32, 1,  2,  3,  4,  5,
            4,  5,  6,  7,  8,  9,
            8,  9,  10, 11, 12, 13,
            12, 13, 14, 15, 16, 17,
            16, 17, 18, 19, 20, 21,
            20, 21, 22, 23, 24 ,25,
            24, 25, 26, 27, 28, 29,
            28, 29, 30, 31, 32, 1
    };
    protected static final int[][][] S_BOXES = {
            {
                    {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
                    {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
                    {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
                    {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
            },
            {
                    {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
                    {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
                    {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
                    {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
            },
            {
                    {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
                    {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
                    {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
                    {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}
            },
            {
                    {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
                    {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
                    {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
                    {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}
            },
            {
                    {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
                    {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
                    {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
                    {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}
            },
            {
                    {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
                    {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
                    {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
                    {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}
            },
            {
                    {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
                    {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
                    {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
                    {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}
            },
            {
                    {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
                    {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
                    {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
                    {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
            }
    };
    protected static final int[] ShiftIterations = {
            1, 1, 2, 2, 2, 2, 2, 2,
            1, 2, 2, 2, 2, 2, 2, 1
    };
    protected static final int ROUNDS = 16;













    /** Hexadecimal to Binary Conversion **/
    protected String hex2bin(String s){
        Map<Character,String> Hexa = new HashMap<>();
        Hexa.put('0',"0000");
        Hexa.put('1',"0001");
        Hexa.put('2',"0010");
        Hexa.put('3',"0011");
        Hexa.put('4',"0100");
        Hexa.put('5',"0101");
        Hexa.put('6',"0110");
        Hexa.put('7',"0111");
        Hexa.put('8',"1000");
        Hexa.put('9',"1001");
        Hexa.put('A',"1010");
        Hexa.put('B',"1011");
        Hexa.put('C',"1100");
        Hexa.put('D',"1101");
        Hexa.put('E',"1110");
        Hexa.put('F',"1111");

        StringBuilder Binary = new StringBuilder();
        for(int i=0; i<s.length(); i++)
            Binary.append(Hexa.get(s.charAt(i)));
        return Binary.toString();
    }
    /** Binary to Hexadecimal Conversion **/
    protected String bin2hex(String s){
        Map<String,Character> Binary = new HashMap<>();
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
        for(int i=0; i<s.length(); i+=4){
            String ch = "";
            ch += s.charAt(i);
            ch += s.charAt(i+1);
            ch += s.charAt(i+2);
            ch += s.charAt(i+3);
            Hexa.append(Binary.get(ch));
        }
        return Hexa.toString();
    }
    /** Binary to Decimal Conversion **/
    protected int bin2dec(String s){
        int decimal = 0;
        int power = 0;
        for(int i=s.length()-1; i>=0; i--){
            if(s.charAt(i) == '1')
                decimal += (int) Math.pow(2,power);
            power++;
        }
        //return String.valueOf(decimal);
        return decimal;
    }
    /** Decimal to Binary Conversion **/
    protected String dec2bin(int s){
        String binary = Integer.toBinaryString(s);

        int padding = 4 - binary.length();
        StringBuilder Binary8bits = new StringBuilder();
        for(int i=0; i<padding; i++)
            Binary8bits.append("0");
        Binary8bits.append(binary);
        return Binary8bits.toString();
    }
    /** Binary to ASCII Conversion **/
    protected String bin2ascii(String s) {
        StringBuilder ASCII = new StringBuilder();
        for(int i=0; i<s.length(); i+=8){
            String ch = "";
            ch += s.charAt(i);
            ch += s.charAt(i+1);
            ch += s.charAt(i+2);
            ch += s.charAt(i+3);
            ch += s.charAt(i+4);
            ch += s.charAt(i+5);
            ch += s.charAt(i+6);
            ch += s.charAt(i+7);
            int num = bin2dec(ch);
            ASCII.append((char)num);
        }
        return ASCII.toString();
    }
    /** ASCII to Binary Conversion **/
    protected String ascii2bin(String s) {
        StringBuilder binary = new StringBuilder();
        for (char c : s.toCharArray()) {
            String binaryStr = dec2bin((int) c);
            int padding = 8 - binaryStr.length();
            for(int i=0; i<padding; i++)
                binary.append("0");
            binary.append(binaryStr);
        }
        return binary.toString();
    }
}
