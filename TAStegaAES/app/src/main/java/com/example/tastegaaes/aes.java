package com.example.tastegaaes;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.Arrays;
import java.util.Base64;

public class aes {


    public static final int sizeblock = 16;

    int pakairounds;
    //kunci enkripsi round
    byte[][] enkripsikunci;
    //kunci dekripsi round
    byte[][] dekripkunci;

//     AES S-box hex ke dec. dilakukan dengan mencari panjang array ke ...
    public static final int panjang = 4, kolom = sizeblock / panjang, gen_poli = 0x11B;                // generator plinomial  GF(2^8)

    static final int[] Sbox = {
            //0     1    2      3     4    5     6     7      8    9     A      B    C     D     E     F
            0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab, 0x76,
            0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4, 0x72, 0xc0,
            0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31, 0x15,
            0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2, 0x75,
            0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f, 0x84,
            0x53, 0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58, 0xcf,
            0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85, 0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f, 0xa8,
            0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc, 0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3, 0xd2,
            0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19, 0x73,
            0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8, 0x14, 0xde, 0x5e, 0x0b, 0xdb,
            0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c, 0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4, 0x79,
            0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9, 0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae, 0x08,
            0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6, 0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b, 0x8a,
            0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61, 0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d, 0x9e,
            0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94, 0x9b, 0x1e, 0x87, 0xe9, 0xce, 0x55, 0x28, 0xdf,
            0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb, 0x16 };

//    static final byte[] Sbox = {
//            99, 124, 119, 123, -14, 107, 111, -59, 48, 1, 103, 43, -2, -41, -85, 118,
//            -54, -126, -55, 125, -6, 89, 71, -16, -83, -44, -94, -81, -100, -92, 114, -64,
//            -73, -3, -109, 38, 54, 63, -9, -52, 52, -91, -27, -15, 113, -40, 49, 21,
//            4, -57, 35, -61, 24, -106, 5, -102, 7, 18, -128, -30, -21, 39, -78, 117,
//            9, -125, 44, 26, 27, 110, 90, -96, 82, 59, -42, -77, 41, -29, 47, -124,
//            83, -47, 0, -19, 32, -4, -79, 91, 106, -53, -66, 57, 74, 76, 88, -49,
//            -48, -17, -86, -5, 67, 77, 51, -123, 69, -7, 2, 127, 80, 60, -97, -88,
//            81, -93, 64, -113, -110, -99, 56, -11, -68, -74, -38, 33, 16, -1, -13, -46,
//            -51, 12, 19, -20, 95, -105, 68, 23, -60, -89, 126, 61, 100, 93, 25, 115,
//            96, -127, 79, -36, 34, 42, -112, -120, 70, -18, -72, 20, -34, 94, 11, -37,
//            -32, 50, 58, 10, 73, 6, 36, 92, -62, -45, -84, 98, -111, -107, -28, 121,
//            -25, -56, 55, 109, -115, -43, 78, -87, 108, 86, -12, -22, 101, 122, -82, 8,
//            -70, 120, 37, 46, 28, -90, -76, -58, -24, -35, 116, 31, 75, -67, -117, -118,
//            112, 62, -75, 102, 72, 3, -10, 14, 97, 53, 87, -71, -122, -63, 29, -98,
//            -31, -8, -104, 17, 105, -39, -114, -108, -101, 30, -121, -23, -50, 85, 40, -33,
//            -116, -95, -119, 13, -65, -26, 66, 104, 65, -103, 45, 15, -80, 84, -69, 22 };

    //    AES invers S-box. dilakukan dengan mencari panjang array ke ...

    static final int[] inversSbox = {
            0x52, 0x09, 0x6A, 0xD5, 0x30, 0x36, 0xA5, 0x38, 0xBF, 0x40, 0xA3, 0x9E, 0x81, 0xF3, 0xD7, 0xFB,
            0x7C, 0xE3, 0x39, 0x82, 0x9B, 0x2F, 0xFF, 0x87, 0x34, 0x8E, 0x43, 0x44, 0xC4, 0xDE, 0xE9, 0xCB,
            0x54, 0x7B, 0x94, 0x32, 0xA6, 0xC2, 0x23, 0x3D, 0xEE, 0x4C, 0x95, 0x0B, 0x42, 0xFA, 0xC3, 0x4E,
            0x08, 0x2E, 0xA1, 0x66, 0x28, 0xD9, 0x24, 0xB2, 0x76, 0x5B, 0xA2, 0x49, 0x6D, 0x8B, 0xD1, 0x25,
            0x72, 0xF8, 0xF6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xD4, 0xA4, 0x5C, 0xCC, 0x5D, 0x65, 0xB6, 0x92,
            0x6C, 0x70, 0x48, 0x50, 0xFD, 0xED, 0xB9, 0xDA, 0x5E, 0x15, 0x46, 0x57, 0xA7, 0x8D, 0x9D, 0x84,
            0x90, 0xD8, 0xAB, 0x00, 0x8C, 0xBC, 0xD3, 0x0A, 0xF7, 0xE4, 0x58, 0x05, 0xB8, 0xB3, 0x45, 0x06,
            0xD0, 0x2C, 0x1E, 0x8F, 0xCA, 0x3F, 0x0F, 0x02, 0xC1, 0xAF, 0xBD, 0x03, 0x01, 0x13, 0x8A, 0x6B,
            0x3A, 0x91, 0x11, 0x41, 0x4F, 0x67, 0xDC, 0xEA, 0x97, 0xF2, 0xCF, 0xCE, 0xF0, 0xB4, 0xE6, 0x73,
            0x96, 0xAC, 0x74, 0x22, 0xE7, 0xAD, 0x35, 0x85, 0xE2, 0xF9, 0x37, 0xE8, 0x1C, 0x75, 0xDF, 0x6E,
            0x47, 0xF1, 0x1A, 0x71, 0x1D, 0x29, 0xC5, 0x89, 0x6F, 0xB7, 0x62, 0x0E, 0xAA, 0x18, 0xBE, 0x1B,
            0xFC, 0x56, 0x3E, 0x4B, 0xC6, 0xD2, 0x79, 0x20, 0x9A, 0xDB, 0xC0, 0xFE, 0x78, 0xCD, 0x5A, 0xF4,
            0x1F, 0xDD, 0xA8, 0x33, 0x88, 0x07, 0xC7, 0x31, 0xB1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xEC, 0x5F,
            0x60, 0x51, 0x7F, 0xA9, 0x19, 0xB5, 0x4A, 0x0D, 0x2D, 0xE5, 0x7A, 0x9F, 0x93, 0xC9, 0x9C, 0xEF,
            0xA0, 0xE0, 0x3B, 0x4D, 0xAE, 0x2A, 0xF5, 0xB0, 0xC8, 0xEB, 0xBB, 0x3C, 0x83, 0x53, 0x99, 0x61,
            0x17, 0x2B, 0x04, 0x7E, 0xBA, 0x77, 0xD6, 0x26, 0xE1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0C, 0x7D};

//    static final byte[] inversSbox = {
//            82, 9, 106, -43, 48, 54, -91, 56, -65, 64, -93, -98, -127, -13, -41, -5,
//            124, -29, 57, -126, -101, 47, -1, -121, 52, -114, 67, 68, -60, -34, -23, -53,
//            84, 123, -108, 50, -90, -62, 35, 61, -18, 76, -107, 11, 66, -6, -61, 78,
//            8, 46, -95, 102, 40, -39, 36, -78, 118, 91, -94, 73, 109, -117, -47, 37,
//            114, -8, -10, 100, -122, 104, -104, 22, -44, -92, 92, -52, 93, 101, -74, -110,
//            108, 112, 72, 80, -3, -19, -71, -38, 94, 21, 70, 87, -89, -115, -99, -124,
//            -112, -40, -85, 0, -116, -68, -45, 10, -9, -28, 88, 5, -72, -77, 69, 6,
//            -48, 44, 30, -113, -54, 63, 15, 2, -63, -81, -67, 3, 1, 19, -118, 107,
//            58, -111, 17, 65, 79, 103, -36, -22, -105, -14, -49, -50, -16, -76, -26, 115,
//            -106, -84, 116, 34, -25, -83, 53, -123, -30, -7, 55, -24, 28, 117, -33, 110,
//            71, -15, 26, 113, 29, 41, -59, -119, 111, -73, 98, 14, -86, 24, -66, 27,
//            -4, 86, 62, 75, -58, -46, 121, 32, -102, -37, -64, -2, 120, -51, 90, -12,
//            31, -35, -88, 51, -120, 7, -57, 49, -79, 18, 16, 89, 39, -128, -20, 95,
//            96, 81, 127, -87, 25, -75, 74, 13, 45, -27, 122, -97, -109, -55, -100, -17,
//            -96, -32, 59, 77, -82, 42, -11, -80, -56, -21, -69, 60, -125, 83, -103, 97,
//            23, 43, 4, 126, -70, 119, -42, 38, -31, 105, 20, 99, 85, 33, 12, 125 };


//    AES Rcon key expansion
    static final int[] rcon = {
        0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8,
        0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3,
        0x7d, 0xfa, 0xef, 0xc5, 0x91 };



    //untuk shiftrow // berapa banyak shiftnya
    static final int[] geser_baris = {0, 1, 2, 3};
    //utk polinom, hasil penjabaran lanjut
    static final int[] hasil_akhir = new int[256];
    //utk polinom, perpangkatan , jika nilai adalah 5 maka X^2 dan X^0
    static final int[] perpangkatan =  new int[256];
    //implementasi gf(2^8) = galieo field
    static {
        int i, j;
        // melakukan perkalian
        //hasil dari perkalian
        //perkalian biner
        hasil_akhir[0] = 1;
        for (i = 1; i < 256; i++) {
            j = (hasil_akhir[i-1] << 1) ^ hasil_akhir[i-1];
            if ((j & 0x100) != 0)
                j ^= gen_poli;
            hasil_akhir[i] = j;
        }
        for (i = 1; i < 255; i++)
            perpangkatan[hasil_akhir[i]] = i;
    }

    public aes() {
    }


    //polinomial jika benar maka kiri jika !=0 maka kanan/bawah
    static final int kali (int a, int b) {
//        Log.d("TAG", "mulasa: "+alog[(log[2 & 0xFF] + log[98 & 0xFF]) % 255]);
//        Log.d("TAG", "mulasb: "+alog[(log[2 & 0xFF]) % 255]);
//        Log.d("TAG", "mulasc: "+alog[(log[98 & 0xFF]) % 255]);
//        Log.d("TAG", "mulasd: "+alog[(log[98 & 0xFF]) ]);
//        Log.d("TAG", "mulasde: "+alog[(log[98 & 0xFF]) ]);
        //operasi perkalian mixcolumn perkalian biner desimal
        return (a != 0 && b != 0) ?
                hasil_akhir[(perpangkatan[a & 0xFF] + perpangkatan[b & 0xFF]) % 255] :
                0;
    }


    public byte[] AddroundKey(byte[] state,int round,int mode)
    {
        byte [] a = new byte[sizeblock];
        byte [] Ker;

        int i;

        if (mode!=0)
        {
            Ker=dekripkunci[round];
        }
        else {
            Ker = enkripsikunci[round];
        }

        for (i=0;i<sizeblock;i++)
        {
            a[i] = (byte)(state[i] ^ Ker[i]);
        }
        return (a);
    }


    public byte[] SubBytes(byte[] state, int round)
    {
        byte [] tempa = new byte[sizeblock];
        int i;

        for (i = 0; i < sizeblock; i++)
            tempa[i] = (byte) Sbox[state[i] & 0xFF];

        return (tempa);
    }

    public byte[] ShiftRows(byte[] state, int round)
    {
        byte [] a = new byte[sizeblock];
        int    i, k, row;


        // ShiftRows
        for (i = 0; i < sizeblock; i++) {
            row = i % panjang;
            k = (i + (geser_baris[row] * panjang)) % sizeblock;    // get shifted byte index
            a[i] = state[k];
//                Log.d("TAG", "enkrpsishift: "+a[i]);

        }
        return (a);
    }

    public byte[] MixColumn(byte[] state, int round)
    {
        byte [] tempa = new byte[sizeblock];
        int    i, col;

        for (col = 0; col < kolom; col++) {
            i = col * panjang;        // index col -> ngecasting hex ke desimal
            tempa[i]   = (byte)(kali(0x02,state[i]) ^ kali(0x03,state[i+1]) ^ state[i+2] ^ state[i+3]);
            tempa[i+1] = (byte)(state[i] ^ kali(0x02,state[i+1]) ^ kali(0x03,state[i+2]) ^ state[i+3]);
            tempa[i+2] = (byte)(state[i] ^ state[i+1] ^ kali(0x02,state[i+2]) ^ kali(0x03,state[i+3]));
            tempa[i+3] = (byte)(kali(0x03,state[i]) ^ state[i+1] ^ state[i+2] ^ kali(0x02,state[i+3]));
        }

        return (tempa);
    }


//    public byte[] enkrpsi(byte[] teksasli) {
//        byte [] a = new byte[sizeblock];
//        byte [] tempa = new byte[sizeblock];
//
//        //teks asli dijadikan sebuah state dan XOR dengan ADDROUNDKEY // -> (byte) nanti jd hexa
//        a=AddroundKey(teksasli,0,0);
//
//        // Looping untuk Round 1 - min terakhir karena round akhir tidak pakai mix
//        for (int r = 1; r < pakairounds; r++) {
//            //kunci yang dipakai
//
//            // SubBytes dengan S-Box  //masking
//            tempa=SubBytes(a,r);
//
//            // ShiftRows
//            a=ShiftRows(tempa,r);
//
//
//            // MixColumns (state) ke ta
//            tempa=MixColumn(a,r);
//
//            // AddRoundKey
//            a=AddroundKey(tempa,r,0);
//        }
//
//        // Round AKhir
//        // SubBytes Akhr
//        a=SubBytes(a,pakairounds);
//
//        // ShiftRowsAkhir
//        tempa=ShiftRows(a,pakairounds);
//
//        // AddRoundKey
//        a=AddroundKey(tempa,pakairounds,0);
//        return (a);
//    }


    public byte[] enkrpsi(byte[] teksasli) {
        byte [] a = new byte[sizeblock];
        byte [] tempa = new byte[sizeblock];
        byte [] Ker;
        int    i, k, row, col;



        //teks asli dijadikan sebuah state dan XOR dengan ADDROUNDKEY // -> (byte) nanti jd hexa
        Ker = enkripsikunci[0];
        for (i = 0; i < sizeblock; i++)
            a[i] = (byte)(teksasli[i] ^ Ker[i]);

        // Looping untuk Round 1 - min terakhir karena round akhir tidak pakai mix
        for (int r = 1; r < pakairounds; r++) {
            //kunci yang dipakai
            Ker = enkripsikunci[r];

            // SubBytes dengan S-Box  //masking
            for (i = 0; i < sizeblock; i++)
                tempa[i] = (byte) Sbox[a[i] & 0xFF];

            // ShiftRows
            for (i = 0; i < sizeblock; i++) {
                row = i % panjang;
                k = (i + (geser_baris[row] * panjang)) % sizeblock;    // get shifted byte index
                a[i] = tempa[k];
//                Log.d("TAG", "enkrpsishift: "+a[i]);

            }

            // MixColumns (state) ke ta
            for (col = 0; col < kolom; col++) {
                i = col * panjang;        // index col -> ngecasting hex ke desimal
                tempa[i]   = (byte)(kali(0x02,a[i]) ^ kali(0x03,a[i+1]) ^ a[i+2] ^ a[i+3]);
                tempa[i+1] = (byte)(a[i] ^ kali(0x02,a[i+1]) ^ kali(0x03,a[i+2]) ^ a[i+3]);
                tempa[i+2] = (byte)(a[i] ^ a[i+1] ^ kali(0x02,a[i+2]) ^ kali(0x03,a[i+3]));
                tempa[i+3] = (byte)(kali(0x03,a[i]) ^ a[i+1] ^ a[i+2] ^ kali(0x02,a[i+3]));
            }

            // AddRoundKey
            for (i = 0; i < sizeblock; i++)
                a[i] = (byte)(tempa[i] ^ Ker[i]);
        }

        // Round AKhir
        Ker = enkripsikunci[pakairounds];

        // SubBytes Akhr
        for (i = 0; i < sizeblock; i++)
            a[i] = (byte) Sbox[a[i] & 0xFF];

        // ShiftRowsAkhir
        for (i = 0; i < sizeblock; i++) {
            row = i % panjang;
            k = (i + (geser_baris[row] * panjang)) % sizeblock;
            tempa[i] = a[k];
        }

        // AddRoundKey
        for (i = 0; i < sizeblock; i++)
            a[i] = (byte)(tempa[i] ^ Ker[i]);
        return (a);
    }




    public byte[] InvSubBytes(byte[] state, int round)
    {
        byte [] tempa = new byte[sizeblock];
        int i;

        for (i = 0; i < sizeblock; i++)
            tempa[i] = (byte) inversSbox[state[i] & 0xFF];

        return (tempa);
    }

    public byte[] InvShiftRows(byte[] state, int round)
    {
        byte [] tempa = new byte[sizeblock];
        int i, k, row;


        // ShiftRows
        for (i = 0; i < sizeblock; i++) {
            row = i % panjang;
            // Shift index byte
            k = (i + sizeblock - (geser_baris[row] * panjang)) % sizeblock;
            tempa[i] = state[k];
//                Log.d("TAG", "enkrpsishift: "+a[i]);

        }
        return (tempa);
    }

    public byte[] InvMixColumn(byte[] state, int round)
    {
        byte [] a = new byte[sizeblock];
        int i, col;

        for (col = 0; col < kolom; col++) {
            i = col * panjang;        // start index col
            a[i]   = (byte)(kali(0x0e,state[i]) ^ kali(0x0b,state[i+1]) ^ kali(0x0d,state[i+2]) ^ kali(0x09,state[i+3]));
            a[i+1] = (byte)(kali(0x09,state[i]) ^ kali(0x0e,state[i+1]) ^ kali(0x0b,state[i+2]) ^ kali(0x0d,state[i+3]));
            a[i+2] = (byte)(kali(0x0d,state[i]) ^ kali(0x09,state[i+1]) ^ kali(0x0e,state[i+2]) ^ kali(0x0b,state[i+3]));
            a[i+3] = (byte)(kali(0x0b,state[i]) ^ kali(0x0d,state[i+1]) ^ kali(0x09,state[i+2]) ^ kali(0x0e,state[i+3]));
        }

        return (a);
    }


    public byte[] dekrpsi(byte[] chiper) {
        byte [] a = new byte[sizeblock];
        byte [] tempa = new byte[sizeblock];



        // chiper jadikan state XOR addroundkey
        a=AddroundKey(chiper,0,1);

        // Round utk decrypt
        for (int r = 1; r < pakairounds; r++) {

            // Inverse ShiftRows
            tempa=InvShiftRows(a,r);

            // Inverse SubBytes dengan Inveres Sbox
            a=InvSubBytes(tempa,r);

            // AddRound Key
            tempa=AddroundKey(a,r,1);

            // Inverse MixColum
            a=InvMixColumn(tempa,r);
        }

        // Round Akhir No MIxColumn

        tempa=InvShiftRows(a,pakairounds);
        a=InvSubBytes(tempa,pakairounds);
        tempa=AddroundKey(a,pakairounds,1);


        return (tempa);
    }


//    public byte[] dekrpsi(byte[] chiper) {
//        byte [] a = new byte[sizeblock];
//        byte [] tempa = new byte[sizeblock];
//        byte [] Kdr;
//        int    i, k, row, col;
//
//
//
//        // chiper jadikan state XOR addroundkey
//        Kdr = dekripkunci[0];
//        for (i = 0; i < sizeblock; i++)
//            a[i] = (byte)(chiper[i] ^ Kdr[i]);
//
//        // Round utk decrypt
//        for (int r = 1; r < pakairounds; r++) {
//            Kdr = dekripkunci[r];            // keys
//
//            // Inverse ShiftRows
//            for (i = 0; i < sizeblock; i++) {
//                row = i % panjang;
//                // Shift index byte
//                k = (i + sizeblock - (geser_baris[row] * panjang)) % sizeblock;
//                tempa[i] = a[k];
//            }
//
//            // Inverse SubBytes dengan Inveres Sbox
//            for (i = 0; i < sizeblock; i++)
//                a[i] = inversSbox[tempa[i] & 0xFF];
//
//            // AddRound Key
//            for (i = 0; i < sizeblock; i++)
//                tempa[i] = (byte)(a[i] ^ Kdr[i]);
//
//            // Inverse MixColum
//            for (col = 0; col < kolom; col++) {
//                i = col * panjang;        // start index col
//                a[i]   = (byte)(kali(0x0e,tempa[i]) ^ kali(0x0b,tempa[i+1]) ^ kali(0x0d,tempa[i+2]) ^ kali(0x09,tempa[i+3]));
//                a[i+1] = (byte)(kali(0x09,tempa[i]) ^ kali(0x0e,tempa[i+1]) ^ kali(0x0b,tempa[i+2]) ^ kali(0x0d,tempa[i+3]));
//                a[i+2] = (byte)(kali(0x0d,tempa[i]) ^ kali(0x09,tempa[i+1]) ^ kali(0x0e,tempa[i+2]) ^ kali(0x0b,tempa[i+3]));
//                a[i+3] = (byte)(kali(0x0b,tempa[i]) ^ kali(0x0d,tempa[i+1]) ^ kali(0x09,tempa[i+2]) ^ kali(0x0e,tempa[i+3]));
//            }
//        }
//
//        // Round Akhir No MIxColumn
//        Kdr = dekripkunci[pakairounds];            // keys get akhuir
//
//        for (i = 0; i < sizeblock; i++) {
//            row = i % panjang;
//            // Shift index byte
//            k = (i + sizeblock - (geser_baris[row] * panjang)) % sizeblock;
//            tempa[i] = a[k];
//        }
//
//        for (i = 0; i < sizeblock; i++)
//            tempa[i] = inversSbox[tempa[i] & 0xFF];
//
//        for (i = 0; i < sizeblock; i++)
//            a[i] = (byte)(tempa[i] ^ Kdr[i]);
//        return (a);
//    }


    public void setKey(byte[] key) {
        final int BC = sizeblock / 4;
        final int Klen = key.length;
        final int Nk = Klen / 4;
//        pakairounds = jmlRound(Klen);
        int i, j, r;

        // jumlah round berdasarkan panjang kunci


        pakairounds = 14;

        final int R_KEY = (pakairounds + 1) * BC;

//        arraybytes kunciasli. setiap isi per huruf

        byte[] w0 = new byte[R_KEY];
        byte[] w1 = new byte[R_KEY];
        byte[] w2 = new byte[R_KEY];
        byte[] w3 = new byte[R_KEY];

        // array utk kunci enkripsi dan dekrpsi
        enkripsikunci = new byte[pakairounds + 1][sizeblock];
        dekripkunci = new byte[pakairounds + 1][sizeblock];

        // kunci ke array
        for (i=0, j=0; i < Nk; i++) {
            w0[i] = key[j++];
            w1[i] = key[j++];
            w2[i] = key[j++];
            w3[i] = key[j++];
        }

        // key Expansion
        byte t0, t1, t2, t3, old0;
        for (i = Nk; i < R_KEY; i++) {
            t0 = w0[i-1];
            t1 = w1[i-1];
            t2 = w2[i-1];
            t3 = w3[i-1];    // temp = w[i-1]
            if (i % Nk == 0) {
                old0 = t0;            // t0 utk t3 Sbox
                t0 = (byte)(Sbox[t1 & 0xFF] ^ rcon[(i/Nk)]);    //  XOR konstan byte awal
                t1 = (byte)(Sbox[t2 & 0xFF]);
                t2 = (byte)(Sbox[t3 & 0xFF]);    // RotWord dengan reorder bytes yg sudah dipakai
                t3 = (byte)(Sbox[old0 & 0xFF]);
            }
            else if ((Nk > 6) && (i % Nk == 4)) {
                // temp = SubWord(temp)
                t0 = (byte) Sbox[t0 & 0xFF];
                t1 = (byte) Sbox[t1 & 0xFF];
                t2 = (byte) Sbox[t2 & 0xFF];
                t3 = (byte) Sbox[t3 & 0xFF];
            }
            // w[i] = w[i-Nk] ^ temp
            w0[i] = (byte)(w0[i-Nk] ^ t0);
            w1[i] = (byte)(w1[i-Nk] ^ t1);
            w2[i] = (byte)(w2[i-Nk] ^ t2);
            w3[i] = (byte)(w3[i-Nk] ^ t3);
        }

        // hasil expansion dimasukkan ke kunci enkripsi/dekripsi
        for (r = 0, i = 0; r < pakairounds + 1; r++) {    // utk round
            for (j = 0; j < BC; j++) {        // utk tiap huruf pada round yang dibutukan
                enkripsikunci[r][4*j] = w0[i];
                enkripsikunci[r][4*j+1] = w1[i];
                enkripsikunci[r][4*j+2] = w2[i];
                enkripsikunci[r][4*j+3] = w3[i];
                dekripkunci[pakairounds - r][4*j] = w0[i];
                dekripkunci[pakairounds - r][4*j+1] = w1[i];
                dekripkunci[pakairounds - r][4*j+2] = w2[i];
                dekripkunci[pakairounds - r][4*j+3] = w3[i];
                i++;
            }
        }

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


    public String enkdek(String data, int mode)  {
        aes aes = this;

        if(data.length()/16 > ((int) data.length()/16)) {
            int temps = data.length()-((int) data.length()/16)*16;
            Log.d("TAG", "enkdek3s: "+temps);

            for(int i=0; i<temps; i++)
                data += " ";
        }
        Log.d("TAG", "enkdek: "+data.length()/16);
        Log.d("TAG", "enkdekds: "+data.length());
        Log.d("TAG", "enkdek2: "+((int) data.length()/16));
        int pjgdata = (int) data.length()/16;
        Log.d("TAG", "enkdek3: "+pjgdata);

        byte[] hasil = new byte[data.length()];
        String bagStr = "";
        byte[] bagByt = new byte[16];
        for(int p=0; p<pjgdata; p++) {
            bagStr = data.substring(p*16, p*16+16);
            bagByt = StringkeByteArray(bagStr);
            if(mode==1) bagByt = aes.enkrpsi(bagByt);
            if(mode==2) bagByt = aes.dekrpsi(bagByt);
            for(int b=0; b<16; b++)
                hasil[p*16+b] = bagByt[b];
        }
        return ByteArraykeString(hasil);
    }

    public String Encrypt(String data) {
        Log.d("TAG", "Esasa: "+data.length());

        while((data.length() % 32) != 0)
            data += " ";
        Log.d("TAG", "Esasad: "+data.length());

        return enkdek(data, 1);
    }
    public String Decrypt(String data) {

        return enkdek(data, 2).trim();
    }


}




