package com.example.tastegaaes;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
//import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
//import android.widget.TextView;
import android.widget.Toast;

//import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
//import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
//import java.util.Arrays;
//import java.util.Base64;
import java.util.Random;

import id.zelory.compressor.Compressor;
//import java.util.Scanner;

//import javax.crypto.Cipher;
//import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;

//import id.zelory.compressor.Compressor;

public class EmbedExtract extends AppCompatActivity implements View.OnClickListener {

    //    public static final int IMAGE_PICK = 2;
    private static int RESULT_LOAD_IMG = 1;
    private ProgressDialog progress;
    String status = "-";
    String fname = "";
    String fname2="";
    Double d = 0.0;
    Double psn_r = 0.0;
    int pixel = 150;
    String AES = "AES";
    String output;
    String hsls;
    DataHelper dbHelper;
    int mods=0;
    String fasname="";

    File original,compressImage;
    String pathsd="",namaasli="";

    long starttume,endtime,duration;
    Double akhir,psnrs;
    String path="",chiper="",kunci="",pltext="";
    String SHA="SHA-256";
    StringBuilder atas;
    int tinggi=0,lebar=0,tinggias=0,lebaras=0,ppesan=0;
    long lengthbmp=0,lengthasli=0;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_embed_extract);
        dbHelper = new DataHelper(this);

        Button ChooseImg = (Button)findViewById(R.id.ChooseImgg);
        ChooseImg.setOnClickListener(this);
        Button EncodeProc= (Button)findViewById(R.id.EncodeProcg);
        EncodeProc.setOnClickListener(this);
        Button Resett= (Button)findViewById(R.id.resetg);
        Resett.setOnClickListener(this);
        Button OnlyStegan= (Button)findViewById(R.id.EncodeOnlyg);
        OnlyStegan.setOnClickListener(this);
        Button Extractions= (Button)findViewById(R.id.DecodeProcessh);
        Extractions.setOnClickListener(this);
        EditText txtStatus = (EditText)findViewById(R.id.TextEncodeg);
//        final TextView lblCount = (TextView)findViewById(R.id.tv_char);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Process Embedding Extraction");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ChooseImgg: //di klik menuju choose image
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                // Start the Intent
                startActivityForResult(Intent.createChooser(galleryIntent, "Select Image"), RESULT_LOAD_IMG);
                break;

            case R.id.EncodeProcg: //di klik menuju encode proses
                EditText kuns = (EditText) findViewById(R.id.EncodeKeyg);
                String kuncsas=kuns.getText().toString();
                EditText pesn = (EditText) findViewById(R.id.TextEncodeg);
                String psna=kuns.getText().toString();
                mods=1;

                if (psna.replaceAll(" ", "") == "") {
                    Toast.makeText(getApplicationContext(), "Please write a message", Toast.LENGTH_LONG).show();
                    status = "Please write a message";
                    //text cuma spasi
                    return;
                }

                if (psna.matches("")) {
                    Toast.makeText(getApplicationContext(), "Please write a message", Toast.LENGTH_LONG).show();
                    status = "Please write a message";
                    return;
                }
                if (kuncsas.matches("")) {
                    Toast.makeText(getApplicationContext(), "Please write a key", Toast.LENGTH_LONG).show();
                    status = "Please write a key";
                    return;
                }
                if (kuncsas.replaceAll(" ", "") == "") {
                    Toast.makeText(getApplicationContext(), "Please write a message", Toast.LENGTH_LONG).show();
                    status = "Please write a message";
                    //text cuma spasi
                    return;
                }
                if (kuncsas.length()<3){
                    Toast.makeText(getApplicationContext(), "Please write a 3 word key", Toast.LENGTH_LONG).show();
                    status = "Please write 3 word key";
                    //text cuma spasi
                    return;
                }


                else {
                    MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
                    myAsyncTasks.execute();
                }

                int k=100;
                Random gener = new Random();

                k = gener.nextInt(k);
                String namatxts="Data En= "+k;



                break;


            case R.id.resetg: //di klik menuju encode proses
                EditText txtPesan = (EditText) findViewById(R.id.TextEncodeg);
                EditText txtkey = (EditText) findViewById(R.id.EncodeKeyg);
                ImageView localImageView = (ImageView) findViewById(R.id.ivImageEncodeg);

                txtkey.getText().clear();
                txtPesan.getText().clear();
                localImageView.setImageDrawable(null);


                break;
            case R.id.DecodeProcessh:
                EditText keyh = (EditText) findViewById(R.id.DecodeKeyh);
                String keydec=keyh.getText().toString();
                mods=2;
                if (keydec.matches("")) {
                    Toast.makeText(getApplicationContext(), "Please write a key", Toast.LENGTH_LONG).show();
                    status = "Please write a key";
                    return;
                }
                if (keydec.replaceAll(" ", "") == "") {
                    Toast.makeText(getApplicationContext(), "Please write a message", Toast.LENGTH_LONG).show();
                    status = "Please write a message";
                    //text cuma spasi
                    return;
                }

                else {
                    MyAsyncTasks myAsyncTasks2 = new MyAsyncTasks();
                    myAsyncTasks2.execute();

                }

                break;
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.encode, menu); // untuk tampilan atas pojok kanan
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar Item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        AlertDialog dialog = new AlertDialog.Builder(EmbedExtract.this)
                .setTitle("Hasil Embedding")
                .setMessage("\n Status \t : " + status + " \n MSE \t :  "
                        + d +" \n PSNR \t :  "+ psn_r +"\n\n Stegano Image Name : \n"
                        +"\n Panjang Pesan \t : "+ppesan+" \n"
                        +fname+"\n"+"\n Dimensi Asli \t : "+tinggias+" x "+lebaras+" \n"
                        +"\n Dimensi Stego \t : "+tinggi+" x "+lebar+" \n"
                        +"\n Luas Stego \t : "+tinggias*lebaras+" \n"
                        +"\n Besar File Asli \t : "+lengthasli+" byte \n"
                        +"\n Besar File Stego \t : "+lengthbmp+" byte \n")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            this.finish();
            return true;
        }
        dialog.show();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            String apath=uri.getLastPathSegment();
            pathsd=uri.getLastPathSegment();
            if (uri.getScheme().equals("content")) {
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                try {
                    if (cursor != null && cursor.moveToFirst()) {
                        namaasli=cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        fasname = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } finally {
                    cursor.close();
                }
            }
            if (fasname == null) {
                fasname = uri.getPath();
                namaasli=uri.getPath();
                int cut = fasname.lastIndexOf('/');
                int cuts=namaasli.lastIndexOf('/');
                if (cut != -1) {
                    fasname = fasname.substring(cut + 1);
                    namaasli=namaasli.substring(cuts+1);
                }
            }
            try {

                InputStream imagestream=getContentResolver().openInputStream(uri);
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                Bitmap selectimg=BitmapFactory.decodeStream(imagestream);
                ImageView imageView = (ImageView) findViewById(R.id.ivImageEncodeg);
                imageView.setImageBitmap(bitmap);

                original=new File(uri.getPath().replace("raw",""));
                Toast.makeText(this, "Image Selected", Toast.LENGTH_SHORT).show();
                status = "Image Selected";
            }

            catch (Exception e) {
                Toast.makeText(this, "Incorrect Image Selected", Toast.LENGTH_SHORT).show();
                status = "Incorrect Image Selected";
            }
        }

    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    public void Encodeprocessing(int mods) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        EditText txtPesan = (EditText) findViewById(R.id.TextEncodeg);
        EditText txtkey = (EditText) findViewById(R.id.EncodeKeyg);
        String keys=txtkey.getText().toString();
        aes AES= new aes();

        String pesan = txtPesan.getText().toString();
        pltext = pesan;

        byte[] dataBytes = new byte[1024];


        atas=new StringBuilder();

        if (mods!=2) {

            try {
                MessageDigest digest = MessageDigest.getInstance(SHA);
                digest.update(keys.getBytes());

                byte[] mdbytes = digest.digest();
                Log.d("TAG", "Key Asli    : "+keys);
                Log.d("TAG", "Cek SHA Hex : "+Util.ByteArrkeHex(mdbytes));
                Log.d("TAG", "Cek SHA Word: "+Util.ByteArraykeString(mdbytes));


                AES.setKey(mdbytes);


                while((pesan.length() % 16) != 0)
                    pesan += " ";


                output = AES.Encrypt(pesan);
                ppesan=pesan.length();

                Log.d("TAG", "startEncruptd: " + output);


                double ava=0;
                int ham=0;
                int lenghtbit=(pesan.length()*8);



                atas.append("Key SHA: ").append(Util.ByteArraykeString(mdbytes)).append(System.lineSeparator());
                atas.append("Key Text : ").append(keys).append(System.lineSeparator());
                atas.append("Key SHA Text : ").append(Util.byteArrtoString(mdbytes)).append(System.lineSeparator());
                atas.append("Plain Asli : ").append(pesan).append(System.lineSeparator());
                atas.append("Plain Length : ").append(pesan.length()).append(System.lineSeparator());
                atas.append("Plain Length Bit : ").append(lenghtbit).append(System.lineSeparator());
                atas.append("Plain Asli biner : ").append(Util.rapikanBiner(Util.convertStringToBinary(pesan),8," ")).append(System.lineSeparator());
                atas.append("Key Length : ").append((keys.length())).append(System.lineSeparator());
                atas.append("Key Length Bit : ").append((keys.length()*8)).append(System.lineSeparator());
                atas.append("Key Asli biner : ").append(Util.convertStringToBinary(keys)).append(System.lineSeparator());
                atas.append("Chiper Text : ").append(output).append(System.lineSeparator()).append(System.lineSeparator());




                byte[] textplains=Util.StringkeByteArray(pesan);

                byte[] keysa=Util.StringkeByteArray(keys);
                byte[] outbyte=Util.StringkeByteArray(output);



                for (int i=0;i<21;i++)
                {
                    byte[] flipplain=Util.flipBit(textplains,i);
                    byte[] dataBytes1;

                    aes aesss=new aes();
                    String teks=Util.byteArrtoString(flipplain);

                    String shakeys=Util.ByteArraykeString(mdbytes);


                    atas.append("Plain Flip Ke-"+(i)+" : ").append(Util.rapikanBiner(Util.convertStringToBinary(teks),8," ")).append(System.lineSeparator());
                    atas.append("SHA Ke-"+(i)+" : ").append(Util.rapikanBiner(Util.convertStringToBinary(shakeys),8," ")).append(System.lineSeparator());

                    atas.append("Key Flip Ke-"+(i)+" : ").append(Util.convertStringToBinary(keys)).append(System.lineSeparator());

                    aesss.setKey(mdbytes);
                    String hasilflip=aesss.Encrypt(teks);
                    byte[] hslflip=Util.StringkeByteArray(hasilflip);

                    int bitdif=Util.bedabit(outbyte,hslflip);
                    double avalaneffect;
                    int totalbit=hasilflip.length()*8;
                    avalaneffect= ((double)bitdif/(double)totalbit)*(double)100;
                    Log.d("TAG", "bitdiffer: "+bitdif);

                    Log.d("TAG", "avaeffec ke-"+i+" : "+avalaneffect);
                    Log.d("TAG", "shakeys: "+shakeys);

                    atas.append("Beda Bit Flip Ke-"+(i)+" : ").append(bitdif).append(System.lineSeparator());
                    atas.append("Avalanche Effect Flip-Plain Ke-"+(i)+" : ").append(avalaneffect).append(System.lineSeparator()).append(System.lineSeparator());


                    byte[] flipkey=Util.flipBit(keysa,i);

                    String keyi=Util.byteArrtoString(flipkey);


                    atas.append("Plain Flip Ke-"+(i)+" : ").append(Util.rapikanBiner(Util.convertStringToBinary(pesan),8," ")).append(System.lineSeparator());
                    atas.append("Key Flip Ke-"+(i)+" : ").append(Util.rapikanBiner(Util.convertStringToBinary(keyi),8," ")).append(System.lineSeparator());
                    atas.append("Cek Key-Flip Ke-"+(i)+" : ").append(keyi).append(System.lineSeparator());

                    MessageDigest messageDigest=MessageDigest.getInstance(SHA);
                    dataBytes1=keyi.getBytes();
                    messageDigest.update(dataBytes1, 0, dataBytes1.length);
                    byte[] digestm = messageDigest.digest();

                    aesss.setKey(digestm);
                    hasilflip=aesss.Encrypt(pesan);
                    hslflip=Util.StringkeByteArray(hasilflip);

                    bitdif=Util.bedabit(outbyte,hslflip);

                    totalbit=hasilflip.length()*8;
                    avalaneffect= ((double)bitdif/(double)totalbit)*(double)100;
                    Log.d("TAG", "avaeffec: "+bitdif);

                    Log.d("TAG", "avaeffec: "+bitdif/(totalbit));
                    Log.d("TAG", "avaeffec: "+avalaneffect);

                    atas.append("Beda Bit Flip-KEY Ke-"+(i)+" : ").append(bitdif).append(System.lineSeparator());
                    atas.append("Avalanche Effect Flip-KEY Ke-"+(i)+" : ").append(avalaneffect).append(System.lineSeparator()).append(System.lineSeparator());

                    byte[] flipsha=Util.flipBit(mdbytes,i);
                    aesss.setKey(flipsha);
                    hasilflip=aesss.Encrypt(pesan);
                    hslflip=Util.StringkeByteArray(hasilflip);
                    bitdif=Util.bedabit(outbyte,hslflip);
                    String keysha=Util.byteArrtoString(flipsha);

                    totalbit=hasilflip.length()*8;
                    avalaneffect= ((double)bitdif/(double)totalbit)*(double)100;
                    Log.d("TAG", "avaeffec: "+bitdif);

                    Log.d("TAG", "avaeffec: "+bitdif/(totalbit));
                    Log.d("TAG", "avaeffec: "+avalaneffect);
                    atas.append("Plain Flip Ke-"+(i)+" : ").append(Util.rapikanBiner(Util.convertStringToBinary(pesan),8," ")).append(System.lineSeparator());
                    atas.append("SHA Flip Ke-"+(i)+" : ").append(Util.rapikanBiner(Util.convertStringToBinary(keysha),8," ")).append(System.lineSeparator());
                    atas.append("Cek SHA-Flip Ke-"+(i)+" : ").append(keysha).append(System.lineSeparator());

                    atas.append("Beda Bit Flip-SHA Ke-"+(i)+" : ").append(bitdif).append(System.lineSeparator());
                    atas.append("Avalanche Effect Flip-SHA Ke-"+(i)+" : ").append(avalaneffect).append(System.lineSeparator()).append(System.lineSeparator());

                    StringBuilder heks=new StringBuilder();
                    for (byte msda :digestm)
                    {
                        String h=Integer.toHexString(0xFF & msda);

                        while (h.length()<2)
                            h="0" +h;

                        heks.append(h);
                    }
                    System.out.println("hexsha  "+heks.toString());
                }


                String namatxs="";
                int xz=1000;
                Random geners=new Random();
                xz=geners.nextInt(xz);
                namatxs="Encrypt Data-"+xz;

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        } else
        {
            output=pesan;
        }


        ImageView imageAsli = (ImageView) findViewById(R.id.ivImageEncodeg);
        if (imageAsli.getDrawable() == null) {
            Toast.makeText(getApplicationContext(), "Please attach an image", Toast.LENGTH_LONG).show();
            status = "Please attach an image";
            return;
        }

        Bitmap bitmapAsli = ((BitmapDrawable) imageAsli.getDrawable()).getBitmap();

        bitmapAsli = ((BitmapDrawable) imageAsli.getDrawable()).getBitmap();
        Bitmap Cover_Image = bitmapAsli.copy(Bitmap.Config.ARGB_8888,true);

        Bitmap bitmap = bitmapAsli;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] imageInByte = stream.toByteArray();
        lengthasli = imageInByte.length;

        Module mod = new Module();
        String hasil = mod.stringtobiner(output).concat("00000000000000000000"); // ubah pesan ke binary dan di tambahakn enol di belakgannya
        Log.d("TAG", "Encodeprocessing: "+hasil);
        Log.d("TAG", "Encodeprocessing: "+output.length());
        int pjg_hasil = hasil.length();
        int total_lsb = mod.sumlsb(Cover_Image);
        Log.d("TAG", "totallsb: "+total_lsb);
        Log.d("TAG", "totallsbsd: "+hasil.length());

        Log.d("TAG", "totallsb: "+Cover_Image.getHeight()+" : "+Cover_Image.getWidth());



        if (total_lsb < pjg_hasil) {

            runOnUiThread(new Runnable() {
                public void run() {
                    // runs on UI thread
                    Toast.makeText(getApplicationContext(), "Please select another image", Toast.LENGTH_LONG).show();
                    status = "Please select another image";
                    return;
                }
            });
        }

        else{
            Bitmap Stego_Image = masukkanPesan(hasil); // masuk ke insert message
            Log.d("TAG", "insertsdhdsa: "+masukkanPesan(hasil));
            SaveImage(Stego_Image,mods);
            d = mod.hitungMSE(Cover_Image, Stego_Image); //menghitung PSNR
            psn_r=mod.hitungPSNR(d);
            psnrs=psn_r;
            exporttxt(getApplicationContext(),"/"+" "+fname2+".txt",atas);
            lebaras=bitmapAsli.getWidth();
            tinggias=bitmapAsli.getHeight();
            ////PENTING
            //buat tambahan untuk extract dengan mengambil gambar bitmap baru
            ImageView imgsv=(ImageView) findViewById(R.id.ivImageDecodeh);
            imgsv.setImageBitmap(Stego_Image);




                    runOnUiThread(new Runnable() {
                public void run() {
                    // runs on UI thread
//                    ImageView imageAslibaru = (ImageView) findViewById(R.id.ivImageEncodeStegano);
//
//                    imageAslibaru.setImageBitmap(Stego_Image);

                    Toast.makeText(getApplicationContext(), "Image Saved :  "+fname+" PSNR : "+psn_r, Toast.LENGTH_LONG).show();
                    //startActivity(new Intent(this, MainActivity.class));
                    status = "encoding berhasil";

                }
            });

        }

    }

    private String extractMessage(Bitmap bi) {
        int a,b;

        a=bi.getHeight();
        b=bi.getWidth();
        String extractedText = "";
        Module mod = new Module();


        for (int i = 0; i < a; i++) {
            for (int j = 0; j < b; j++) {
                int pixel = bi.getPixel(j, i);

                int R1 = (pixel >> 16) & 0xff;
                int G1 = (pixel >> 8) & 0xff;
                int B1 = (pixel) & 0xff;

                String r1 = Integer.toBinaryString(R1);

                String g1 = Integer.toBinaryString(G1);

                String b1 = Integer.toBinaryString(B1);


                String rr = mod.binertoeightbiner(r1);
                String R = rr.substring(7, 8);

                String gg = mod.binertoeightbiner(g1);
                String G = gg.substring(7, 8);

                String bb = mod.binertoeightbiner(b1);
                String B = bb.substring(7, 8);
                extractedText += R+G+B;
                Log.d("TAG", "extractedText: : "+extractedText);
                if (extractedText.contains("000000000000000000")){
                    break;
                }



            }
        }

        return extractedText;
    }


    public static String getHash(final String msg) {
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(msg.getBytes());
            byte[] byteData = digest.digest();
            for (byte x : byteData) {
                String str = Integer.toHexString(Byte.toUnsignedInt(x));
                if (str.length() < 2) {
                    sb.append('0');
                }
                sb.append(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }



    public void SaveImage(Bitmap paramBitmap,int modsa) {

        File localFile1;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            if (modsa != 2)
            {
                localFile1 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Image-Stegano-AES");
            }
            else
                localFile1 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Image-Stega-Only");

            int num = 0;


            localFile1.mkdirs();
            if (!localFile1.exists()) {
                localFile1.mkdirs();
            }

            String ph=localFile1.getPath();
            path=ph;
            Log.d("TAG", "SaveImage: "+ph);
            Random generator = new Random();
//        int n = 3;
//        n = generator.nextInt(n);
            fname = "Data - ";
            File localFile2 = new File(localFile1, fname2);
//        scanMedia(localFile2);//untuk melakukan rewrite data jika sama





//        if (localFile2.exists()) {
////            localFile2.delete();
//            fname2=fname+"("+n+")"+".jpg";
//            localFile2 = new File(localFile1, fname2);
//        }
            while(localFile2.exists()) {
                fname2 = fname + (num++)+ ".jpg";
//                fname2 =fname + ".jpg";

                localFile2 = new File(localFile1, fname2);
            }
            Log.d("TAG", "SaveImage: "+fname2);

            try {
                FileOutputStream out = new FileOutputStream(localFile2);


                paramBitmap.compress(Bitmap.CompressFormat.PNG, 85, out);
                lebar=paramBitmap.getWidth();
                tinggi=paramBitmap.getHeight();

                int besar=paramBitmap.getByteCount();


                Bitmap bitmap = paramBitmap;
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] imageInByte = stream.toByteArray();
                lengthbmp = imageInByte.length;


//            Log.e("Dimensions", paramBitmap.getWidth()+" "+paramBitmap.getHeight());
//            Log.e("Dimensions", paramBitmap.getDensity()+" "+paramBitmap.getByteCount());

                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Request permission from the user
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

        }
    }

    private Bitmap masukkanPesan(String pesan) {

        //simpan pesan ke pixel lsb
        ImageView ivLoadImg = findViewById(R.id.ivImageEncodeg);
        Bitmap bit2 = ((BitmapDrawable)ivLoadImg.getDrawable()).getBitmap();
        Bitmap bitmap = bit2;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] imageInByte = stream.toByteArray();
        lengthasli = imageInByte.length;
//
//        try {
//            compressImage=new Compressor(encoding.this).setDestinationDirectoryPath(pathsd).setQuality(90).setCompressFormat(Bitmap.CompressFormat.PNG).compressToFile(original);
//
//            File Final=new File(pathsd,original.getName());
//            Bitmap hasli=BitmapFactory.decodeFile(Final.getAbsolutePath());
//            ivLoadImg.setImageBitmap(hasli);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        bit2 = ((BitmapDrawable) ivLoadImg.getDrawable()).getBitmap();
        ByteArrayOutputStream streams = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, streams);

//        Bitmap bit1 = bitmap.copy(Bitmap.Config.ARGB_8888,true);
        Bitmap bit1 = bit2.copy(Bitmap.Config.ARGB_8888,true);


//
//        Bitmap bit2 = ((BitmapDrawable)ivLoadImg.getDrawable()).getBitmap();
//        //mengcopy bitmap dengan spesifikasi setiap pixel disimpan dalam 4 bytes
//        Bitmap bit1 = bit2.copy(Bitmap.Config.ARGB_8888,true);


        int a,b;


        a=bit1.getHeight();
        b=bit1.getWidth();
        Module mod = new Module();
        int charIndex = 0;
        String r3, g3, b3;
        int pjgpesan = pesan.length();

        for (int i = 0; i < a; i++) {
            for (int j = 0; j < b; j++) {
                // holds the pixel that is currently being processed
                int pixel = bit1.getPixel(j, i);
                // mengambil rgb
                int A = (pixel >> 24) & 0xff;
                int R = (pixel >> 16) & 0xff;
                int G = (pixel >> 8) & 0xff;
                int B = (pixel) & 0xff;
                String r1 = Integer.toBinaryString(R);
                String g1 = Integer.toBinaryString(G);
                String b1 = Integer.toBinaryString(B);

//                String rr = mod.binertoeightbiner(r1);//menjadi binary dengan pjg 8
//                String r2 = rr.substring(0, 6); // mengambil bilai sebanyak 7 dari 8
//                String gg = mod.binertoeightbiner(g1);
//                String g2 = gg.substring(0, 6);
//                String bb = mod.binertoeightbiner(b1);
//                String b2 = bb.substring(0, 6);


                String rr = mod.binertoeightbiner(r1);//menjadi binary dengan pjg 8
                String r2 = rr.substring(0, 7); // mengambil bilai sebanyak 7 dari 8
                String gg = mod.binertoeightbiner(g1);
                String g2 = gg.substring(0, 7);
                String bb = mod.binertoeightbiner(b1);
                String b2 = bb.substring(0, 7);

                //red
                if (charIndex < pjgpesan) {
                    String PesanR = pesan.substring(charIndex, charIndex + 1); // index 0, 1 alias indeks ke - 0;
//                    Log.d("TAG", "insertMessagePesanR: "+PesanR);
                    if(Integer.valueOf(PesanR) ==1){
                        r3 = r2.concat("1"); //mengganti bit belakang menjadi 1
                    }
                    else{
                        r3 = r2.concat("0"); //mengganti bit belaka menjadi 0
                    }
                    R = mod.binertointeger(r3); // nilai pixel R baru
                    charIndex++; // char index di tambah sebanyak 1
                }
                //red
//                if (charIndex < pjgpesan) {
//                    String PesanR = pesan.substring(charIndex, charIndex + 2); // index 0, 1 alias indeks ke - 0;
//                    Log.d("TAG", "insertMessagePesanR: "+PesanR);
//                    if ( Integer.valueOf(PesanR) == 01) {
//                        r3 = r2.concat("01"); //mengganti bit belakang menjadi 01
//                    }
//                    else if( Integer.valueOf(PesanR) == 11) {
//                        r3 = r2.concat("11"); //mengganti bit belakang menjadi 11
//                    }
//                    else if( Integer.valueOf(PesanR) == 10) {
//                        r3 = r2.concat("10"); //mengganti bit belakang menjadi 10
//                    }
//                    else{
//                        r3 = r2.concat("00"); //mengganti bit belaka menjadi 00
//                    }
//                    R = mod.binertointeger(r3); // nilai pixel R baru
//                    charIndex++; // char index di tambah sebanyak 1
//                    charIndex++;
//                }
                //green
                if (charIndex<pjgpesan) {
                    String PesanG = pesan.substring(charIndex, charIndex + 1); // lnjut dari index atasnya
//                    Log.d("TAG", "insertMessagePesanG: "+PesanG);
                    if (Integer.valueOf(PesanG) == 1) {
                        g3 = g2.concat("1");
                    }
                    else{
                        g3 = g2.concat("0");
                    }
                    G = mod.binertointeger(g3);
                    charIndex++; //char index di tambah sebanyak 1
                }

//                //green
//                if (charIndex<pjgpesan) {
//                    String PesanG = pesan.substring(charIndex, charIndex + 2); // lnjut dari index atasnya
//                    Log.d("TAG", "insertMessagePesanG: "+PesanG);
//                    if ( Integer.valueOf(PesanG) == 01) {
//                        g3 = g2.concat("01");
//                    }
//                    else if( Integer.valueOf(PesanG) == 11) {
//                        g3 = g2.concat("11"); //mengganti bit paling belakang menjadi 1
//                    }
//                    else if( Integer.valueOf(PesanG) == 10) {
//                        g3 = g2.concat("10"); //mengganti bit paling belakang menjadi 1
//                    }
//                    else{
//                        g3 = g2.concat("00");
//                    }
//                    G = mod.binertointeger(g3);
//                    charIndex++; //char index di tambah sebanyak 1
//                    charIndex++;
//                }

                //blue
                if (charIndex<pjgpesan){
                    String PesanB = pesan.substring(charIndex, charIndex + 1); // lanjut dari index atasnya
//                    Log.d("TAG", "insertMessagePesanB: "+PesanB);
                    if (Integer.valueOf(PesanB) == 1) {
                        b3 = b2.concat("1");
                    }
                    else{
                        b3 = b2.concat("0");
                    }
                    B = mod.binertointeger(b3);
                    charIndex++; //char index di tambah sebanyak 1
                }

//                //blue
//                if (charIndex<pjgpesan){
//                    String PesanB = pesan.substring(charIndex, charIndex + 2); // lanjut dari index atasnya
//                    Log.d("TAG", "insertMessagePesanB: "+PesanB);
//                    if ( Integer.valueOf(PesanB) == 01) {
//                        b3 = b2.concat("01");
//                    }
//                    else if( Integer.valueOf(PesanB) == 11) {
//                        b3 = b2.concat("11");
//                    }
//                    else if( Integer.valueOf(PesanB) == 10) {
//                        b3 = b2.concat("10");
//                    }
//                    else{
//                        b3 = b2.concat("00");
//                    }
//                    B = mod.binertointeger(b3);
//                    charIndex++; //char index di tambah sebanyak 1
//                    charIndex++;
//                }

                if (charIndex>=pjgpesan){
                    return bit1;
                }

                int rgba = (A<<24)|(R<<16)|(G<<8)|(B); //menggabungkan 3  komponen warna
                bit1.setPixel(j, i, rgba); //settting pixel baru

            }
        }
        Log.d("TAG", "masukkanPesan: "+charIndex);
        return bit1;
    }

    private void scanMedia(File paramFile) {
        sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(paramFile)));
    }

//    private String encrypt (String Data, String password) throws Exception{
//        SecretKey key = generateKey(password);
//        Cipher c = Cipher.getInstance(AES);
//        c.init(Cipher.ENCRYPT_MODE,key);
//        byte[]  encVal = c.doFinal(Data.getBytes());
//        String encryptedValue = android.util.Base64.encodeToString(encVal, android.util.Base64.DEFAULT );
//        return encryptedValue;
//
//    }

//    private SecretKeySpec generateKey(String password) throws Exception {
//        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
//        byte[] bytes = password.getBytes("UTF-8");
//        digest.update(bytes, 0, bytes.length);
//        byte[] key = digest.digest();
//        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
//        return  secretKeySpec;
//    }



    public void exporttxt(Context context, String namafile, StringBuilder isi)
    {
        try {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                // Do the file write
                String path= context.getFilesDir().getAbsolutePath();
//            String pth= Environment.getExternalStoragePublicDirectory();
                File root = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),"Encryptions");
//                root.mkdir();
                //awalnya file rooth pake path
                Log.d("TAG", "generateNoteOnSD: "+root);
                root.mkdirs();

                if (!root.exists()) {
                    root.mkdirs();
                }

                namafile=namafile.split(".jpg")[0]+namafile.split(".jpg")[1];
                File gpxfile = new File(root, namafile);
                FileWriter writer = new FileWriter(gpxfile);
                Log.d("TAG", "generateNoteOnSD: "+namafile);

                writer.append(isi);
                writer.flush();
                writer.close();
//                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();

            } else {
                // Request permission from the user
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }




    private class MyAsyncTasks extends AsyncTask<Void, Void, Void> {
        ProgressDialog pdLoading = new ProgressDialog(EmbedExtract.this);

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(EmbedExtract.this);
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Void doInBackground(Void... voids) {
            try {

//                    starttume=0;
//                    endtime=0;
//                    duration=0;
                akhir=null;
                psnrs=null;
                chiper="";
                kunci="";
                path="";
                pltext="";
                fname="";
                starttume=System.nanoTime();

                if (mods==1) {
                    Encodeprocessing(mods);
                }
                else if (mods==2){
                    DecodeProcessing(mods);
                }
//                    endtime=System.nanoTime();
//                    duration=endtime-starttume;
//                    akhir=(double) duration/1000000000;



            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            progressDialog.dismiss();

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void DecodeProcessing(int mods) {
        EditText keyh = (EditText) findViewById(R.id.DecodeKeyh);
        String keydec=keyh.getText().toString();
        EditText txtResult = (EditText) findViewById(R.id.TextDecodeh);

        ImageView ivImageResult = (ImageView) findViewById(R.id.ivImageDecodeh);
        Bitmap bi3 = ((BitmapDrawable)ivImageResult.getDrawable()).getBitmap();
        Bitmap bi2 = bi3.copy(Bitmap.Config.ARGB_8888,true);
        Module mod = new Module();
        String hasilExtract = extractMessage(bi2);
        Log.d("TAG", "Decodeprocessinsdg: "+hasilExtract.length());

        hasilExtract=hasilExtract.split("000000000000000000")[0];
        int lenghts=hasilExtract.length();
        if (lenghts%8!=0)
        {
            Log.d("TAG", "Decodeprocessing: "+hasilExtract.length());
            if (lenghts%8==5)
            {
                String nol="000";
                Log.d("TAG", "Decodeasdprocessing: "+hasilExtract.length());

                hasilExtract=hasilExtract+nol;
            }
            else if (lenghts%8==4)
            {
                String nol="0000";
                Log.d("TAG", "Decodeasdprocessing: "+hasilExtract.length());

                hasilExtract=hasilExtract+nol;
            }
            else if (lenghts%8==3)
            {
                String nol="00000";
                Log.d("TAG", "Decodeasdprocessing: "+hasilExtract.length());

                hasilExtract=hasilExtract+nol;

            }
            else if (lenghts%8==2)
            {
                String nol="000000";
                Log.d("TAG", "Decodeasdprocessing: "+hasilExtract.length());

                hasilExtract=hasilExtract+nol;

            }
            else if (lenghts%8==1)
            {
                String nol="0000000";
                Log.d("TAG", "Decodeasdprocessing: "+hasilExtract.length());

                hasilExtract=hasilExtract+nol;
            }

            else if (lenghts%8==6)
            {
                String nol="00";
                Log.d("TAG", "Decodeasdprocessing: "+hasilExtract.length());

                hasilExtract=hasilExtract+nol;
            }
            else if (lenghts%8==7)
            {
                String nol="0";
                Log.d("TAG", "Decodeasdprocessing: "+hasilExtract.length());

                hasilExtract=hasilExtract+nol;
            }

        }

        Log.d("TAG", "hskl: : "+hasilExtract);

//                String sb = mod.binertostring(hasilExtract);
        String sb=Util.rapikanBiner(hasilExtract,8," ");
        Log.d("TAG", "Pretty: "+sb);
        String[] parts = sb.split(" ");
        StringBuilder asb = new StringBuilder();

        for (String part : parts) {
            int val = Integer.parseInt(part, 2);
            String c = Character.toString((char) val);
            asb.append(c);
        }

        Log.d("TAG", "tostring: : "+asb.toString());
        String hslk=asb.toString();

        chiper=hslk;
        Log.d("TAG", "chipers: : "+chiper.trim());
        Log.d("TAG", "chiperss: : "+chiper.length());

//                byte[] dataBytes = new byte[1024];
//
//                MessageDigest md = null;
//                try {
//                    md = MessageDigest.getInstance("SHA-256");
//                    dataBytes=AES.static_stringToByteArray(keys);
//                    md.update(dataBytes, 0, dataBytes.length);
//                    Log.d("TAG", "hasil: "+AES.static_stringToByteArray(keys));
//                    Log.d("TAG", "hasilupdat: "+dataBytes);
//                    Log.d("TAG", "hasilmd: "+md.digest());
//                } catch (NoSuchAlgorithmException e) {
//                    e.printStackTrace();
//                }
//
//                byte[] mdbytes = md.digest();
//                AES.setKey(mdbytes);


//            aes AES= new aes();

//            byte[] dataBytes = new byte[1024];

//            MessageDigest md = null;
//            try {
//                md = MessageDigest.getInstance("SHA-256");
//                dataBytes=AES.static_stringToByteArray(keys);
//                md.update(dataBytes, 0, dataBytes.length);
//                Log.d("TAG", "hasil: "+AES.static_stringToByteArray(keys));
//                Log.d("TAG", "hasilupdat: "+dataBytes);
//                Log.d("TAG", "hasilmd: "+md.digest());
//            } catch (NoSuchAlgorithmException e) {
//                e.printStackTrace();
//            }
//            byte[] mdbytes = null;
//            mdbytes=md.digest();
////        System.out.println("Base64 hash is = " + Base64.getEncoder().encodeToString(mdbytes)) ;
//            Log.d("TAG", "hasiasdl: "+ Base64.getEncoder().encodeToString(mdbytes));
//            Log.d("TAG", "hadsffsiasdl: "+Base64.getEncoder().encodeToString(dataBytes));
//            System.out.println(Arrays.toString(mdbytes));
////            System.out.println(Arrays.toString(digest2));
//
////            byte[] mdbytes = md.digest();
////            byte [] key    = aes.Util.hex2byte(keys);
//
//            AES.setKey(mdbytes);

        byte[] data;

        aes AES= new aes();

            try {
                MessageDigest digest = MessageDigest.getInstance(SHA);
                digest.update(keydec.getBytes());
//                    data = Util.StringkeByteArray(kuncis);
//                    digest.update(data, 0, data.length);
//            Log.d("TAG", "hasil: "+AES.static_stringToByteArray(textkey));
//            Log.d("TAG", "hasilupdat: "+dataBytes);
//            Log.d("TAG", "hasilmd: "+digest.digest());

                byte[] mdbytes = digest.digest();
                AES.setKey(mdbytes);

                StringBuilder hextoString = new StringBuilder();

                for (byte msgDigest : mdbytes) {
                    String h = Integer.toHexString(0xFF & msgDigest);
                    while (h.length() < 2)
                        h = "0" + h;
                    hextoString.append(h);
                }
                System.out.println("sdad" + hextoString.toString());


                Log.d("TAG", "hasilas: " + mdbytes);
                Log.d("TAG", "hasilas: " + mdbytes.length);
                output = AES.Decrypt(hslk);
                Log.d("TAG", "startEncruptd: " + output);
                pltext = output;
                kunci = hextoString.toString();


            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }


//            MessageDigest baru = null;
//            try {
//
//
//                baru = MessageDigest.getInstance("SHA-256");
//                data=AES.static_stringToByteArray(kuncis);
//                Log.d("TAG", "hasil: "+kuncis);
//
////            dataBytes=keys.getBytes("UTF-8");
//                baru.update(data, 0, data.length);
//                Log.d("TAG", "hasil: "+AES.static_stringToByteArray(kuncis));
//                Log.d("TAG", "hasilupdat: "+data);
//                Log.d("TAG", "hasilmd: "+baru.digest());
//
//
//            } catch (NoSuchAlgorithmException e) {
//                e.printStackTrace();
//            }
//            byte[] mbytes = baru.digest();
////        System.out.println("Base64 hash is = " + Base64.getEncoder().encodeToString(mdbytes)) ;
//            Log.d("TAG", "hasiasdl: "+Base64.getEncoder().encodeToString(mbytes));
//            Log.d("TAG", "sad: "+baru.digest());
////            Log.d("TAG", "hadsffsiasdl: "+Base64.getEncoder().encodeToString(data));
//            System.out.println(Arrays.toString(mbytes));
//            String hashsa=new String(mbytes, StandardCharsets.UTF_8);
//            System.out.println("hash "+hashsa);
////            pesan  = new Scanner(new File(inputfile)).useDelimiter("\\Z").next();
//
//            AES.setKey(mbytes);
//
////                kunci=Arrays.toString(mbytes);
//            hsls=AES.Decrypt(hslk);
//            pltext=hsls;
//            Log.d("TAG", "hsls: "+hsls);

//            output=AES.Encrypt(sb);

//            String hasil;
//            hasil=decrypt(sb,keys);


//                String hasil=AES.Decrypt(sb);
//                Log.d("TAG", "hasialssdfs: : "+output);

//                Log.d("TAG", "hasials: : "+hasil);
//                txtResult.setText(hasil);

        runOnUiThread(new Runnable() {
            public void run() {
                // runs on UI thread
                txtResult.setText(output);

                Toast.makeText(getApplicationContext(), "Decode Finished", Toast.LENGTH_LONG).show();
                //startActivity(new Intent(this, MainActivity.class));
                status = "Decode berhasil";

            }
        });


    }


}