package com.example.tastegaaes;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class decoding extends AppCompatActivity implements View.OnClickListener {

    private static int RESULT_LOAD_IMG2 = 1;
    String status = "-";
    int pixel = 150;
    String hsls;

    String AES = "AES";
    String namesa="";
    ProgressDialog progressDialog;
    DataHelper dbHelper;
    String SHA="SHA-256";
    String output="";
    int modsa=0;


    long starttume,endtime,duration;
    Double akhir,psnrs;
    String path="",chiper="",kunci="",pltext="",fname="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decoding);

        Button bChooseImage2 = (Button)findViewById(R.id.ChooseImage2);
        bChooseImage2.setOnClickListener(this);
        Button bDecodeProcess= (Button)findViewById(R.id.DecodeProcess);
        bDecodeProcess.setOnClickListener(this);
        Button bDecodeOnly= (Button)findViewById(R.id.DecodeSteganoOnly);
        bDecodeOnly.setOnClickListener(this);
        Button Resett= (Button)findViewById(R.id.dreset);
        Resett.setOnClickListener(this);

        dbHelper = new DataHelper(this);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Decode Process");

    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        switch (view.getId()    ) {
            case R.id.ChooseImage2:
                Intent galleryIntent2 = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent2.setType("image/*");
                // Start the Intent
                startActivityForResult(Intent.createChooser(galleryIntent2, "Select Image"), RESULT_LOAD_IMG2);
                break;
            case R.id.DecodeProcess:
                modsa=1;
                EditText kuns = (EditText) findViewById(R.id.DecodeKey);
                String kuncsas=kuns.getText().toString();


                if (((ImageView)findViewById(R.id.ivImageDecode)).getDrawable() == null) {
                    Toast.makeText(getApplicationContext(), "Please attach an stegano image", Toast.LENGTH_LONG).show();
                    status = "Please attach an stegano image";
                    return;
                }
                if (kuncsas.matches(""))
                {
                    Toast.makeText(getApplicationContext(), "Please write a key", Toast.LENGTH_LONG).show();
                    status = "Please write a key";
                    return;
                }
                else {


                    MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
                    myAsyncTasks.execute();
                }
//                try {
//                    MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
//                    myAsyncTasks.execute();
//
//                    Decodeprocessing();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                EditText txtResult = (EditText) findViewById(R.id.TextDecode);
//                txtResult.setText("");

                break;

            case R.id.DecodeSteganoOnly:
                modsa=2;
                MyAsyncTasks myAsyncTasksa = new MyAsyncTasks();
                myAsyncTasksa.execute();

                break;

            case R.id.dreset:
                EditText txtPesan = (EditText) findViewById(R.id.TextDecode);
                EditText txtkey = (EditText) findViewById(R.id.DecodeKey);
                ImageView localImageView = (ImageView) findViewById(R.id.ivImageDecode);

                txtkey.getText().clear();
                txtPesan.getText().clear();
                localImageView.setImageDrawable(null);


                break;
        }

    }

    private String extractMessage(Bitmap bi) {
        int a,b;

//        Log.d("TAG", "extractMessage: "+bi.getHeight());
//        if (bi.getHeight()<pixel){a = bi.getHeight();}
//        else {a = pixel;}
//
//        if (bi.getWidth()<pixel){b = bi.getWidth();}
//        else {b = pixel;}
        a=bi.getHeight();
        b=bi.getWidth();
        String extractedText = "";
        Module mod = new Module();
//        Log.d("TAG", "extractMessagea: "+a);
//        Log.d("TAG", "extractMessageb: "+b);


        for (int i = 0; i < a; i++) {
            // pass through each row
            for (int j = 0; j < b; j++) {
                // holds the pixel that is currently being processed
                int pixel = bi.getPixel(j, i);
//                Log.d("TAG", "extractMessagePixel: "+pixel);

                int R1 = (pixel >> 16) & 0xff;
                int G1 = (pixel >> 8) & 0xff;
                int B1 = (pixel) & 0xff;
//                Log.d("TAG", "extractMessageR1: "+R1);
//                Log.d("TAG", "extractMessageG1: "+G1);
//                Log.d("TAG", "extractMessageB1: "+B1);

                String r1 = Integer.toBinaryString(R1);
//                Log.d("TAG", "extractMessager1: "+r1);

                String g1 = Integer.toBinaryString(G1);
//                Log.d("TAG", "extractMessageg1: "+g1);

                String b1 = Integer.toBinaryString(B1);
//                Log.d("TAG", "extractMessageb1: "+b1);

//                String rr = mod.binertoeightbiner(r1);
//                String R = rr.substring(6, 8);
//
//                String gg = mod.binertoeightbiner(g1);
//                String G = gg.substring(6, 8);
//
//                String bb = mod.binertoeightbiner(b1);
//                String B = bb.substring(6, 8);


                String rr = mod.binertoeightbiner(r1);
                String R = rr.substring(7, 8);

                String gg = mod.binertoeightbiner(g1);
                String G = gg.substring(7, 8);

                String bb = mod.binertoeightbiner(b1);
                String B = bb.substring(7, 8);
//                Log.d("TAG", "extractMessageR: "+R);
//                Log.d("TAG", "extractMessageG: "+G);
//                Log.d("TAG", "extractMessageB: "+B);
                extractedText += R+G+B;
//                Log.d("TAG", "i: : "+i);
//                Log.d("TAG", "j: : "+j);
                Log.d("TAG", "extractedText: : "+extractedText);
                if (extractedText.contains("000000000000000000")){
                    break;
                }



            }
        }

        return extractedText;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void Decodeprocessing(int mods) throws Exception {

        EditText txtResult = (EditText) findViewById(R.id.TextDecode);
        EditText kuns = (EditText) findViewById(R.id.DecodeKey);
        String kuncis=kuns.getText().toString();

        if (((ImageView)findViewById(R.id.ivImageDecode)).getDrawable() == null) {
            Toast.makeText(getApplicationContext(), "Please attach an stegano image", Toast.LENGTH_LONG).show();
            status = "Please attach an stegano image";
            return;
        }



        else {

//            aes AES=new aes();

            ImageView ivImageResult = (ImageView) findViewById(R.id.ivImageDecode);
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
            String sb=prettyBinary(hasilExtract,8," ");
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
            Log.d("TAG", "KEysaasdda: "+kuncis);

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

            if (mods !=2) {
                try {
                    MessageDigest digest = MessageDigest.getInstance(SHA);
                    digest.update(kuncis.getBytes());
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
            }
            else {
                output = hslk;
                pltext = output;

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMG2 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            path="";
            fname="";

            Uri uri = data.getData();
            namesa=uri.getLastPathSegment();
            path=uri.getLastPathSegment();
            if (uri.getScheme().equals("content")) {
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                try {
                    if (cursor != null && cursor.moveToFirst()) {
                        fname = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } finally {
                    cursor.close();
                }
            }
            if (fname == null) {
                fname = uri.getPath();
                int cut = fname.lastIndexOf('/');
                if (cut != -1) {
                    fname = fname.substring(cut + 1);
                }
            }


            Log.d("TAG", "onActivityResult: "+namesa);

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                ImageView imageView = (ImageView) findViewById(R.id.ivImageDecode);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.decode, menu); // untuk tampilan atas pojok kanan
        return true;
    }

    @Override


    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar Item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        AlertDialog dialog = new AlertDialog.Builder(decoding.this)
                .setTitle("Hasil Proses Decoding")
                .setMessage("\n Status \t : " + status + "\n")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        if (id == android.R.id.home) {
            this.finish();
            return true;
        }
        dialog.show();
        return super.onOptionsItemSelected(item);
    }
    private String decrypt(String outputString, String password) throws Exception{
        SecretKey key = generateKey(password);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedValue = android.util.Base64.decode(outputString, android.util.Base64.DEFAULT);
        byte[] decValue = c.doFinal(decodedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
    }
    private SecretKeySpec generateKey(String password) throws Exception {
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = password.getBytes("UTF-8");
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        return  secretKeySpec;
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


    private class MyAsyncTasks extends AsyncTask<Void, Void, Void>{
        ProgressDialog pdLoading = new ProgressDialog(decoding.this);

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(decoding.this);
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                starttume=0;
                endtime=0;
                duration=0;
                akhir=null;
                chiper="";
                kunci="";
                pltext="";
                starttume=System.nanoTime();
                Decodeprocessing(modsa);



            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {

            endtime=System.nanoTime();
            duration=endtime-starttume;
            akhir=(double) duration/1000000000;
            dbHelper.addDecodeAES(path,fname,pltext,kunci,chiper,akhir);
            if (modsa==2)
                dbHelper.addDecodeOnly(path,fname,pltext,akhir);
            progressDialog.dismiss();

        }

    }
}