package com.example.tastegaaes;

import static com.example.tastegaaes.aes.static_byteArrayToString;
import static com.example.tastegaaes.aes.static_stringToByteArray;

import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Module {

    public String binertoeightbiner(String biner){ //mengubah biner dari pixel menajdi 8 kompenen yang sama semua
        String nol = "0";
        int a = biner.length();
        for (int k = 0; k < 8-a; k++){
            biner = nol.concat(biner);
        }
//        Log.d("TAG", "binertoeightbiner: "+biner);
        return biner;
    }

    public Integer binertointeger (String biner) { //mengubah dari binari ke integer
        char[] numbers = biner.toCharArray();
        Integer hasil =0;
        int count = 0;
        for (int i = numbers.length - 1; i >= 0; i--) {
            if (numbers[i] == '1') hasil += (int) Math.pow(2, count);
            count++;
        }
//        Log.d("TAG", "binint: "+hasil);

        return hasil;
    }

//    public String binertostring(String psn1) { //mengubah dari binari ke string sekalian di embed dengan 0000000000
//
//        String pesan = psn1;
//        StringBuilder sb = new StringBuilder();
//        char[] chars = pesan.replaceAll("\\s", "").toCharArray();
//
//        int[] mapping = {1, 2, 4, 8, 16, 32, 64, 128};
//
//        for (int j = 0; j < chars.length; j += 8) { // j di tambah sebanyak 8 dan j kurang dari panjang pesan
//
//            int idx = 0;
//            int sum = 0;
//
//            for (int i = 7; i >= 0; i--) {
//                if (chars[i + j] == '1') {
//                    sum += mapping[idx];
//                }
//                idx++;
//            }
//
//            if (sum == 0 ){
//
//                j = chars.length;
//            }
////            Log.d("TAG", "binertostring: "+sb.append(Character.toChars(sum)));
//
////            sb.append(Character.toChars(sum).);
//            sb.append(Character.toChars(sum));
//        }
//
//        return sb.toString();
//    }

    public String stringtobiner (String psn){ //mengubah dari string ke binary
//        String hasil = "";
//        ArrayList<String> messageList = new ArrayList<String>();
//        byte[] bytes = psn.getBytes();
//
//        StringBuilder biner = new StringBuilder();
//        for (byte b : bytes) {
//            int val = b;
//            for (int i = 0; i < 8; i++) {
//                biner.append((val & 128) == 0 ? 0 : 1);
//                val <<= 1;
//            }
//        }
//        messageList.add(biner.toString());
//        for (String object : messageList) {
//            hasil +=object;
//        }
//
//        Log.d("TAG", "stringtobiner: "+hasil);
//        return hasil;

        StringBuilder result = new StringBuilder();
        char[] chars = psn.toCharArray();
        for (char aChar : chars) {
            result.append(
                    String.format("%8s", Integer.toBinaryString(aChar))   // char -> int, auto-cast
                            .replaceAll(" ", "0")                         // zero pads
            );
        }
        return result.toString();


//        aes aes = this;
//        if(psn.length()/16 > ((int) psn.length()/16)) {
//            int rest = psn.length()-((int) psn.length()/16)*16;
//            for(int i=0; i<rest; i++)
//                psn += " ";
//        }
//        int nParts = (int) psn.length()/16;
//        byte[] res = new byte[psn.length()];
//        String partStr = "";
//        byte[] partByte = new byte[16];
//        for(int p=0; p<nParts; p++) {
//            partStr = psn.substring(p*16, p*16+16);
//            partByte = static_stringToByteArray(partStr);
//            for(int b=0; b<16; b++)
//                res[p*16+b] = partByte[b];
//        }
//        return static_byteArrayToString(res);

    }



    public double hitungPSNR(Bitmap Cover_Image, Bitmap Stego_Image) { //image1 , image2

        double totalRed = 0;
        double totalGreen = 0;
        double totalBlue = 0;

        for (int i = 0; i < Cover_Image.getWidth(); i++) {
            for (int j = 0; j < Cover_Image.getHeight(); j++) {

                int cover = Cover_Image.getPixel(i, j);
                int r1 = (cover>>16)&0xff;
                int g1 = (cover>>8)&0xff;
                int b1 = (cover)&0xff;

                int stego = Stego_Image.getPixel(i, j);
                int r2 = (stego>>16)&0xff;
                int g2 = (stego>>8)&0xff;
                int b2 = (stego)&0xff;

                final int red = r2 - r1;
                final int green = g2 - g1;
                final int blue = b2 - b1;

                totalRed += red*red;
                totalGreen += green*green;
                totalBlue += blue*blue;
            }
        }

        double meanSquaredError = (totalRed+totalGreen+totalBlue) / (Cover_Image.getWidth() * Cover_Image.getHeight()*3);

        if (meanSquaredError == 0) {
            return 0.0;
        }
        double psnr = 10 * StrictMath.log10((255*255) / meanSquaredError);

        return psnr;
    }

    public int sumlsb(Bitmap image){ //total semua lsb yang tersedia
        int sumlsb = (image.getHeight()*image.getWidth()*3);

        return sumlsb;
    }


}
