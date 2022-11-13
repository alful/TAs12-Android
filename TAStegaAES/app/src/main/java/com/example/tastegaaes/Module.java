package com.example.tastegaaes;


import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Module {

    double meanSquaredError=0.0;
    double meanSquaredErrors=0.0;
    public String binertoeightbiner(String biner){
        // biner pixel 8
        String nol = "0";
        int a = biner.length();
        for (int k = 0; k < 8-a; k++){
            biner = nol.concat(biner);
        }
//        Log.d("TAG", "binertoeightbiner: "+biner);
        return biner;
    }

    public Integer binertointeger (String biner) {
        //biner ke int
        char[] numbers = biner.toCharArray();
        Integer hasil =0;
        int count = 0;
        Log.d("TAG", "numbsr: "+numbers.length);

        for (int i = numbers.length - 1; i >= 0; i--) {
            Log.d("TAG", "not1w "+i+": "+numbers[i]);

            if (numbers[i] == '1'){
                hasil += (int) Math.pow(2, count);
                Log.d("TAG", "binertointeger: "+hasil);
            }
            count++;
        }
//        Log.d("TAG", "binint: "+hasil);

        return hasil;
    }


    public String stringtobiner (String psn){
        //mengubah dari string ke binary

        StringBuilder result = new StringBuilder();
        char[] chars = psn.toCharArray();
        for (char aChar : chars) {
            result.append(
                    String.format("%8s", Integer.toBinaryString(aChar))
                            .replaceAll(" ", "0")
            );
        }
        return result.toString();


    }



    public double hitungPSNR(Double mse) { //image1 , image2

//
        if (mse == 0) {
            return 0.0;
        }
        double psnr = 10 * StrictMath.log10((255*255) / mse);

        return psnr;
    }

    public double hitungMSE(Bitmap Cover_Image, Bitmap Stego_Image) { //image1 , image2

        meanSquaredError=0.0;
        double totalRed = 0;
        double totalGreen = 0;
        double totalBlue = 0;
        double msk=0;
        meanSquaredErrors=0.0;

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

                final int red = r1 - r2;
                final int green = g1 - g2;
                final int blue = b1 - b2;

                totalRed += red*red;
                totalGreen += green*green;
                totalBlue += blue*blue;

                final int totl=cover-stego;
                msk=totl*totl;

            }
        }

        meanSquaredError = (totalRed+totalGreen+totalBlue) / (Cover_Image.getWidth() * Cover_Image.getHeight());
//
        meanSquaredErrors = msk / (Cover_Image.getWidth() * Cover_Image.getHeight());

        Log.d("TAG", "hitungMSE: "+meanSquaredErrors);
        if (meanSquaredError == 0) {
            return 0.0;
        }

        return meanSquaredError;
    }



    public int sumlsb(Bitmap image){
        //total semua lsb yang tersedia
        int sumlsb = (image.getHeight()*image.getWidth());

        return sumlsb;
    }


}
