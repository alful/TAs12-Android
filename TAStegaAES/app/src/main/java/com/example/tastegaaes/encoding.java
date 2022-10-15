package com.example.tastegaaes;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class encoding extends AppCompatActivity implements View.OnClickListener {

//    public static final int IMAGE_PICK = 2;
    private static int RESULT_LOAD_IMG = 1;
    private ProgressDialog progress;
    String status = "-";
    String fname = "";
    Double d = 0.0;
    int pixel = 100;
    String AES = "AES";
    String output;
    String hsls;
    DataHelper dbHelper;
    int mods=0;

    long starttume,endtime,duration;
    Double akhir,psnrs;
    String path="",chiper="",kunci="",pltext="";
    String SHA="SHA-256";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encoding);
        dbHelper = new DataHelper(this);

        Button ChooseImg = (Button)findViewById(R.id.ChooseImg);
        ChooseImg.setOnClickListener(this);
        Button EncodeProc= (Button)findViewById(R.id.EncodeProc);
        EncodeProc.setOnClickListener(this);
        Button Resett= (Button)findViewById(R.id.reset);
        Resett.setOnClickListener(this);
        Button OnlyStegan= (Button)findViewById(R.id.EncodeOnly);
        OnlyStegan.setOnClickListener(this);

        EditText txtStatus = (EditText)findViewById(R.id.TextEncode);
//        final TextView lblCount = (TextView)findViewById(R.id.tv_char);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Process Encryption");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ChooseImg: //di klik menuju choose image
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                // Start the Intent
                startActivityForResult(Intent.createChooser(galleryIntent, "Select Image"), RESULT_LOAD_IMG);
                break;

            case R.id.EncodeProc: //di klik menuju encode proses
                try {
                    mods=1;
                    starttume=0;
                    endtime=0;
                    duration=0;
                    akhir=null;
                    psnrs=null;
                    chiper="";
                    kunci="";
                    path="";
                    pltext="";
                    fname="";
                    starttume=System.nanoTime();

                    Encodeprocessing(mods);
                    endtime=System.nanoTime();
                    duration=endtime-starttume;
                    akhir=(double) duration/1000000000;
                    dbHelper.addEncodeAES(path,fname,pltext,kunci,chiper,psnrs,akhir);

//                    dbHelper.addEncodeAES(path,fname,waktu);

//               //     SQLiteDatabase db = dbHelper.getWritableDatabase();
//                 //   db.execSQL("insert into encodngAES(no, waktu_enc, pathfile, namafile) values('" +
//                   //         text1.getText().toString() + "','" +
//                     //       text2.getText().toString() + "','" +
//                       //     text3.getText().toString() + "','" +
//                         //   text4.getText().toString() + "','" +
//                           // text5.getText().toString() + "')");
                } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.EncodeOnly: //di klik menuju encode proses
                mods=2;
                try {
                    Encodeprocessing(mods);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
//                Encodeonly();


                break;

            case R.id.reset: //di klik menuju encode proses
                EditText txtPesan = (EditText) findViewById(R.id.TextEncode);
                EditText txtkey = (EditText) findViewById(R.id.EncodeKey);
                ImageView localImageView = (ImageView) findViewById(R.id.ivImageEncode);

                txtkey.getText().clear();
                txtPesan.getText().clear();
                localImageView.setImageDrawable(null);


                break;
        }

    }

//    private void Encodeonly() {
//        EditText txtPesan = (EditText) findViewById(R.id.TextEncode);
//        EditText txtkey = (EditText) findViewById(R.id.EncodeKey);
//        String keys=txtkey.getText().toString();
//        Log.d("TAG", "KEysada: "+keys);
//
//        String pesan = txtPesan.getText().toString();
//
//
//        if (pesan.replaceAll(" ", "") == "") {
//            Toast.makeText(getApplicationContext(), "Please write a message", Toast.LENGTH_LONG).show();
//            status = "Please write a message";
//            //text cuma spasi
//            return;
//        }
//
//        if (pesan == "") {
//            Toast.makeText(getApplicationContext(), "Please write a message", Toast.LENGTH_LONG).show();
//            status = "Please write a message";
//            return;
//        }
////        if (keys == "") {
////            Toast.makeText(getApplicationContext(), "Please write a key", Toast.LENGTH_LONG).show();
////            status = "Please write a key";
////            return;
////        }
////        if (keys.replaceAll(" ", "") == "") {
////            Toast.makeText(getApplicationContext(), "Please write a message", Toast.LENGTH_LONG).show();
////            status = "Please write a message";
////            //text cuma spasi
////            return;
////        }
//
//
//
//        ImageView localImageView = (ImageView) findViewById(R.id.ivImageEncode);
//        if (localImageView.getDrawable() == null) {
//            Toast.makeText(getApplicationContext(), "Please attach an image", Toast.LENGTH_LONG).show();
//            status = "Please attach an image";
//            //img kosong
//            return;
//        }
//
//        Bitmap localBitmap = ((BitmapDrawable) localImageView.getDrawable()).getBitmap();
//        Bitmap Cover_Image = localBitmap.copy(Bitmap.Config.ARGB_8888,true);
//
//        Module mod = new Module();
//        String hasil = mod.stringtobiner(output).concat("00000000000000000000"); // ubah pesan ke binary dan di tambahakn enol di belakgannya
//        int pjg_hasil = output.length();
//        int total_lsb = mod.sumlsb(Cover_Image);
//
//
//        if (Cover_Image == null) {
//
//            Toast.makeText(getApplicationContext(), "Please attach an image", Toast.LENGTH_LONG).show();
//            status = "Please attach an image";
//            return;
//        }
//
//        if (total_lsb < pjg_hasil) {
//            Toast.makeText(getApplicationContext(), "Please select another image", Toast.LENGTH_LONG).show();
//            status = "Please select another image";
//            return;
//        }
//
//        else{
//            Bitmap Stego_Image = insertMessage(hasil); // masuk ke metthod insert message
//            Log.d("TAG", "insertsdhdsa: "+insertMessage(hasil));
//            SaveImage(Stego_Image);
//            d = mod.hitungPSNR(Cover_Image, Stego_Image); //menghitung PSNR
//            psnrs=d;
//            Toast.makeText(getApplicationContext(), "Image Saved :  " + fname+" PSNR : " +d, Toast.LENGTH_LONG).show();
//            //startActivity(new Intent(this, MainActivity.class));
//            status = "encoding berhasil";
//
//        }
//
//
//    }


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
        AlertDialog dialog = new AlertDialog.Builder(encoding.this)
                .setTitle("Hasil Proses Encoding")
                .setMessage("\n Status \t : " + status + " \n PSNR \t :  "+ d +"\n\n Stego Image Name : \n"+fname+"\n")
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

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ImageView imageView = (ImageView) findViewById(R.id.ivImageEncode);
                imageView.setImageBitmap(bitmap);
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
        EditText txtPesan = (EditText) findViewById(R.id.TextEncode);
        EditText txtkey = (EditText) findViewById(R.id.EncodeKey);
        String keys=txtkey.getText().toString();
        Log.d("TAG", "KEysada: "+keys);
            aes AES= new aes();

        String pesan = txtPesan.getText().toString();

        byte[] dataBytes = new byte[1024];

        if (pesan.replaceAll(" ", "") == "") {
            Toast.makeText(getApplicationContext(), "Please write a message", Toast.LENGTH_LONG).show();
            status = "Please write a message";
            //text cuma spasi
            return;
        }

        if (pesan == "") {
            Toast.makeText(getApplicationContext(), "Please write a message", Toast.LENGTH_LONG).show();
            status = "Please write a message";
            return;
        }
        if (keys == "") {
            Toast.makeText(getApplicationContext(), "Please write a key", Toast.LENGTH_LONG).show();
            status = "Please write a key";
            return;
        }
        if (keys.replaceAll(" ", "") == "") {
            Toast.makeText(getApplicationContext(), "Please write a message", Toast.LENGTH_LONG).show();
            status = "Please write a message";
            //text cuma spasi
            return;
        }

        if (mods!=2) {


            try {
                MessageDigest digest = MessageDigest.getInstance(SHA);
//            digest.update(keys.getBytes());// baris 330 -331 sama artinya dengan baris ini
                dataBytes = AES.static_stringToByteArray(keys);
                digest.update(dataBytes, 0, dataBytes.length);
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
                output = AES.Encrypt(pesan);
                kunci = hextoString.toString();

                pltext = pesan;
                chiper = output;

                Log.d("TAG", "startEncruptd: " + output);


            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        else
        {
            output=pesan;
        }




//
//        MessageDigest md = null;
//        try {
//            aes AES= new aes();
//
//
//            md = MessageDigest.getInstance("SHA-256");
//            dataBytes=AES.static_stringToByteArray(keys);
////            dataBytes=keys.getBytes("UTF-8");
//            md.update(dataBytes, 0, dataBytes.length);
//            Log.d("TAG", "hasil: "+AES.static_stringToByteArray(keys));
//            Log.d("TAG", "hasilupdat: "+dataBytes);
//            Log.d("TAG", "hasilmd: "+md.digest());
//            byte[] mdbytes = md.digest();
////        System.out.println("Base64 hash is = " + Base64.getEncoder().encodeToString(mdbytes)) ;
//            Log.d("TAG", "hasiasdl: "+Base64.getEncoder().encodeToString(mdbytes));
//            Log.d("TAG", "sad: "+md.digest());
//            Log.d("TAG", "hadsffsiasdl: "+Base64.getEncoder().encodeToString(dataBytes));
//            System.out.println(Arrays.toString(mdbytes));
////            pesan  = new Scanner(new File(inputfile)).useDelimiter("\\Z").next();
//
//
//            int pkeys= mdbytes.length;
//            Log.d("TAG", "hslasds: "+pkeys);
//            AES.setKey(mdbytes);
//            hsls=AES.Encrypt(pesan);
//            chiper=hsls;
//            kunci=Arrays.toString(mdbytes);
//            pltext=pesan;
//            output=hsls;
//            Log.d("TAG", "hsls: "+hsls);
//
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }


//        hsls = android.util.Base64.encodeToString(hsls, android.util.Base64.DEFAULT );

        aes aes2=new aes();



        try {
//            output=encrypt(pesan,keys);

        } catch (Exception e) {
            e.printStackTrace();
        }
//        String de= AES.Decrypt(output);
        Log.d("TAG", "hasil: "+output);
//        Log.d("TAG", "hasil: "+de);

        MessageDigest digest=null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        digest.reset();
        try {
            Log.i("Eamorr",digest.digest(keys.getBytes("UTF-8")).toString());
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

//        Log.d("TAG", "hasiasdasdasl: "+de);

//
//        if (pesan.replaceAll(" ", "") == "") {
//            Toast.makeText(getApplicationContext(), "Please write a message", Toast.LENGTH_LONG).show();
//            status = "Please write a message";
//            //text cuma spasi
//            return;
//        }

        ImageView localImageView = (ImageView) findViewById(R.id.ivImageEncode);
        if (localImageView.getDrawable() == null) {
            Toast.makeText(getApplicationContext(), "Please attach an image", Toast.LENGTH_LONG).show();
            status = "Please attach an image";
            //img kosong
            return;
        }

        Bitmap localBitmap = ((BitmapDrawable) localImageView.getDrawable()).getBitmap();
        Bitmap Cover_Image = localBitmap.copy(Bitmap.Config.ARGB_8888,true);

        Module mod = new Module();
        String hasil = mod.stringtobiner(output).concat("00000000000000000000"); // ubah pesan ke binary dan di tambahakn enol di belakgannya
//        String absd= mod.stringtobiner(output);
        Log.d("TAG", "Encodeprocessing: "+hasil);
//        Log.d("TAG", "Encodeprocessinasdg: "+absd);
        int pjg_hasil = output.length();
        int total_lsb = mod.sumlsb(Cover_Image);
//        Log.d("TAG", "pjg hsl: "+pjg_hasil);
//        Log.d("TAG", "totallsb: "+total_lsb);


        if (Cover_Image == null) {

            Toast.makeText(getApplicationContext(), "Please attach an image", Toast.LENGTH_LONG).show();
            status = "Please attach an image";
            return;
        }

        if (total_lsb < pjg_hasil) {
            Toast.makeText(getApplicationContext(), "Please select another image", Toast.LENGTH_LONG).show();
            status = "Please select another image";
            return;
        }

        else{
            Bitmap Stego_Image = insertMessage(hasil); // masuk ke metthod insert message
            Log.d("TAG", "insertsdhdsa: "+insertMessage(hasil));
            SaveImage(Stego_Image,mods);
            d = mod.hitungPSNR(Cover_Image, Stego_Image); //menghitung PSNR
            psnrs=d;
            Toast.makeText(getApplicationContext(), "Image Saved :  " + fname+" PSNR : " +d, Toast.LENGTH_LONG).show();
            //startActivity(new Intent(this, MainActivity.class));
            status = "encoding berhasil";

        }

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
//        String pth= "/storage/emulated/0";
//        File roots=new File(pth+"/SteganoAES");
//        roots.mkdir();
        File localFile1;

        if (modsa != 2)
        {
            localFile1 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Image-Stegano-AES");
        }
        else
            localFile1 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Image-Stega-Only");

        localFile1.mkdirs();
        String ph=localFile1.getPath();
        path=ph;
        Log.d("TAG", "SaveImage: "+ph);
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        fname = "Data-" + n + ".jpg";
        File localFile2 = new File(localFile1, fname);
        scanMedia(localFile2);

        if (localFile2.exists()) {
            localFile2.delete();
        }

        try {
            FileOutputStream out = new FileOutputStream(localFile2);
            paramBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap insertMessage(String encryptedMessage) {

        //simpan pesan ke pixel lsb
        ImageView ivLoadImg = (ImageView) findViewById(R.id.ivImageEncode);
        Bitmap bi2 = ((BitmapDrawable)ivLoadImg.getDrawable()).getBitmap();
        //mengcopy bitmap dengan spesifikasi setiap pixel disimpan dalam 4 bytes
        Bitmap bi1 = bi2.copy(Bitmap.Config.ARGB_8888,true);


        int a,b;

        if (bi1.getHeight()<pixel)
        {a = bi1.getHeight();}
        else {a = pixel;}

        if (bi1.getWidth()<pixel){b = bi1.getWidth();}
        else {b = pixel;}

        Module mod = new Module();
        int charIndex = 0;
        String r3, g3, b3;
        int pjgpesan = encryptedMessage.length();

        for (int i = 0; i < a; i++) {
            // pass through each row
            for (int j = 0; j < b; j++) {
                // holds the pixel that is currently being processed
                int pixel = bi1.getPixel(j, i);
                // Mengubag semua nilai pixel Lsb menjadi 0
                int A = (pixel >> 24) & 0xff;
                int R = (pixel >> 16) & 0xff;
                int G = (pixel >> 8) & 0xff;
                int B = (pixel) & 0xff;
                String r1 = Integer.toBinaryString(R);
                String g1 = Integer.toBinaryString(G);
                String b1 = Integer.toBinaryString(B);

                String rr = mod.binertoeightbiner(r1);//menjadi binari dengan pjg 8
                String r2 = rr.substring(0, 6); // mengambil bilai sebanyak 7 dari 8
                String gg = mod.binertoeightbiner(g1);
                String g2 = gg.substring(0, 6);
                String bb = mod.binertoeightbiner(b1);
                String b2 = bb.substring(0, 6);

                //red
                if (charIndex < pjgpesan) {
                    String PesanR = encryptedMessage.substring(charIndex, charIndex + 2); // index 0, 1 alias indeks ke - 0;
                    Log.d("TAG", "insertMessagePesanR: "+PesanR);
                    if ( Integer.valueOf(PesanR) == 01) {
                        r3 = r2.concat("01"); //mengganti bit paling belakang menjadi 1
                    }
                    else if( Integer.valueOf(PesanR) == 11) {
                        r3 = r2.concat("11"); //mengganti bit paling belakang menjadi 1
                    }
                    else if( Integer.valueOf(PesanR) == 10) {
                        r3 = r2.concat("10"); //mengganti bit paling belakang menjadi 1
                    }
                    else{
                        r3 = r2.concat("00"); //mengganti bit paling belaka menjadi 0
                    }
                    R = mod.binertointeger(r3); // nilai pixel R baru
                    charIndex++; // char index di tambah sebanyak 1
                    charIndex++;
                }

                //green
                if (charIndex<pjgpesan) {
                    String PesanG = encryptedMessage.substring(charIndex, charIndex + 2); // lnjut dari index atasnya
                    Log.d("TAG", "insertMessagePesanG: "+PesanG);
                    if ( Integer.valueOf(PesanG) == 01) {
                        g3 = g2.concat("01");
                    }
                    else if( Integer.valueOf(PesanG) == 11) {
                        g3 = g2.concat("11"); //mengganti bit paling belakang menjadi 1
                    }
                    else if( Integer.valueOf(PesanG) == 10) {
                        g3 = g2.concat("10"); //mengganti bit paling belakang menjadi 1
                    }
                    else{
                        g3 = g2.concat("00");
                    }
                    G = mod.binertointeger(g3);
                    charIndex++; //char index di tambah sebanyak 1
                    charIndex++;
                }

                //blue
                if (charIndex<pjgpesan){
                    String PesanB = encryptedMessage.substring(charIndex, charIndex + 2); // lnjut dari index atasnya
                    Log.d("TAG", "insertMessagePesanB: "+PesanB);
                    if ( Integer.valueOf(PesanB) == 01) {
                        b3 = b2.concat("01");
                    }
                    else if( Integer.valueOf(PesanB) == 11) {
                        b3 = b2.concat("11"); //mengganti bit paling belakang menjadi 1
                    }
                    else if( Integer.valueOf(PesanB) == 10) {
                        b3 = b2.concat("10"); //mengganti bit paling belakang menjadi 1
                    }
                    else{
                        b3 = b2.concat("00");
                    }
                    B = mod.binertointeger(b3);
                    charIndex++; //char index di tambah sebanyak 1
                    charIndex++;
                }

                if (charIndex>=pjgpesan){
                    return bi1;
                }

                int rgba = (A<<24)|(R<<16)|(G<<8)|(B); //gabungkan 3  komponen warna
                bi1.setPixel(j, i, rgba); //settting pixel baru

            }
        }
        return bi1;
    }

    private void scanMedia(File paramFile) {
        sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(paramFile)));
    }

    private String encrypt (String Data, String password) throws Exception{
        SecretKey key = generateKey(password);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE,key);
        byte[]  encVal = c.doFinal(Data.getBytes());
        String encryptedValue = android.util.Base64.encodeToString(encVal, android.util.Base64.DEFAULT );
        return encryptedValue;

    }

    private SecretKeySpec generateKey(String password) throws Exception {
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = password.getBytes("UTF-8");
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        return  secretKeySpec;
    }


}