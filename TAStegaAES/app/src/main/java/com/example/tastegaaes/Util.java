package com.example.tastegaaes;


import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Util {

    public static int bedabit(byte[] arr1, byte[] arr2)
    {
        int beda=0;
        for (int i=0;i<arr1.length;i++)
        {
            int bit1=Byte.toUnsignedInt(arr1[i]);
            int bit2=Byte.toUnsignedInt(arr2[i]);
            beda+=Integer.bitCount(bit1^bit2);
        }
        return beda;
    }

    public static byte[] flipBit(byte[] byteArr, int i)
    {
        byte[] newByteArr = byteArr.clone();
        int a;

        for (a=0;a<i;a++){
            int b = Byte.toUnsignedInt(newByteArr[a / Byte.SIZE]);
            newByteArr[a / Byte.SIZE] = (byte) (b ^ (1 << (Byte.SIZE - 1 - a % Byte.SIZE)));

        }

        return newByteArr;
    }

    public static String byteArrtoString(byte[] arr)
    {
        String res = "";
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<arr.length; i++) {

            int n = (int) arr[i];

            if(n<0) n += 256;
            sb.append((char) n);
        }
        res = sb.toString();
        return res;

    }

    public static String ByteArrkeHex(byte[] arr)
    {
        StringBuffer hexString = new StringBuffer();
        String res="";
        for (int i = 0; i < arr.length; i++) {
            if ((0xff & arr[i]) < 0x10) {
                hexString.append("0"
                        + Integer.toHexString((0xFF & arr[i])));
            } else {
                hexString.append(Integer.toHexString(0xFF & arr[i])).append("");
            }
        }
        res=hexString.toString();

        return res;

    }

    public static String convertStringToBinary(String input) {

        StringBuilder result = new StringBuilder();
        char[] chars = input.toCharArray();
        for (char aChar : chars) {
            result.append(
                    String.format("%8s", Integer.toBinaryString(aChar))   // char -> int, auto-cast
                            .replaceAll(" ", "0")                         // zero pads
            );
        }
        return result.toString();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String prettyBinary(String binary, int blockSize, String separator) {

        List<String> result = new ArrayList<>();
        int index = 0;
        while (index < binary.length()) {
            result.add(binary.substring(index, Math.min(index + blockSize, binary.length())));
            index += blockSize;
        }

        return result.stream().collect(Collectors.joining(separator));
    }


    public static String ByteArraykeString(byte[] data) {
        String hasil = "";
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<data.length; i++) {
            int n = (int) data[i];
            if(n<0)
                n += 256;
            sb.append((char) n);
        }
        hasil = sb.toString();
        return hasil;
    }

    public static byte[] StringkeByteArray(String s){
        byte[] temp = new byte[s.length()];

        for(int i=0;i<s.length();i++){
            temp[i] = (byte) s.charAt(i);

        }
        return temp;


    }

}
