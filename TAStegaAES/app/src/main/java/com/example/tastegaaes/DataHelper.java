package com.example.tastegaaes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DataHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "TA";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_STD = "EncodeAES";
    private static final String TABLE_STA = "DecodeAES";
    private static final String TABLE_STB = "ENCODEONLY";
    private static final String TABLE_STC = "DECODEONLY";
    private static final String KEY_ID = "id";
    private static final String KEY_PATHFILE = "pathfile";
    private static final String KEY_NAMAFILE = "namafile";
    private static final String KEY_WAKTU = "waktu";
    private static final String KEY_CHIPER = "chiper";
    private static final String KEY_PLAIN = "plain";
    private static final String KEY_KEYS = "kunci";
    private static final String KEY_PSNR = "psnr";

    private static final String CREATE_TABLE_ENCODEAES = "CREATE TABLE "
            + TABLE_STD + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_PATHFILE + " TEXT, "
            + KEY_NAMAFILE + " TEXT, "
            + KEY_PLAIN + " TEXT, "
            + KEY_KEYS + " TEXT, "
            + KEY_CHIPER + " TEXT, "
            + KEY_PSNR + " REAL, "
            + KEY_WAKTU + " REAL );";

    private static final String CREATE_TABLE_DECODEAES = "CREATE TABLE "
            + TABLE_STA + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_PATHFILE + " TEXT, "
            + KEY_NAMAFILE + " TEXT, "
            + KEY_PLAIN + " TEXT, "
            + KEY_KEYS + " TEXT, "
            + KEY_CHIPER + " TEXT, "
            + KEY_WAKTU + " REAL );";

    private static final String CREATE_TABLE_ENCODEONLY = "CREATE TABLE "
            + TABLE_STB + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_PATHFILE + " TEXT, "
            + KEY_NAMAFILE + " TEXT, "
            + KEY_PLAIN + " TEXT, "
            + KEY_PSNR + " REAL, "
            + KEY_WAKTU + " REAL );";

    private static final String CREATE_TABLE_DECODEONLY = "CREATE TABLE "
            + TABLE_STC + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_PATHFILE + " TEXT, "
            + KEY_NAMAFILE + " TEXT, "
            + KEY_PLAIN + " TEXT, "
            + KEY_WAKTU + " REAL );";

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_ENCODEAES);
        sqLiteDatabase.execSQL(CREATE_TABLE_DECODEAES);
        sqLiteDatabase.execSQL(CREATE_TABLE_ENCODEONLY);
        sqLiteDatabase.execSQL(CREATE_TABLE_DECODEONLY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + TABLE_STD + "'");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + TABLE_STA + "'");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + TABLE_STB + "'");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + TABLE_STC + "'");
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + TABLE_STD + "+"+TABLE_STA+"'");
        onCreate(sqLiteDatabase);

    }


    public long addEncodeAES(String path, String nama, String plain, String kunci, String chiper, Double psnr, Double waktu) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PATHFILE, path);
        values.put(KEY_NAMAFILE, nama);
        values.put(KEY_PLAIN, plain);
        values.put(KEY_KEYS, kunci);
        values.put(KEY_CHIPER, chiper);
        values.put(KEY_PSNR, psnr);
        values.put(KEY_WAKTU, waktu);
        long insert = db.insert(TABLE_STD, null, values);

        return insert;
    }

    public ArrayList<EncodeAes> getAllEncodeAes() {
        ArrayList<EncodeAes> userModelArrayList = new ArrayList<EncodeAes>();

        String selectQuery = "SELECT * FROM " + TABLE_STD;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                EncodeAes std = new EncodeAes();
                std.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                std.setPathfile(c.getString(c.getColumnIndex(KEY_PATHFILE)));
                std.setNamafile(c.getString(c.getColumnIndex(KEY_NAMAFILE)));
                std.setPlain(c.getString(c.getColumnIndex(KEY_PLAIN)));
                std.setKeys(c.getString(c.getColumnIndex(KEY_KEYS)));
                std.setChiper(c.getString(c.getColumnIndex(KEY_CHIPER)));
                std.setPsnr(c.getDouble(c.getColumnIndex(KEY_PSNR)));
                std.setWaktu(c.getDouble(c.getColumnIndex(KEY_WAKTU)));
                // adding to Students list
                userModelArrayList.add(std);
            } while (c.moveToNext());
        }
        return userModelArrayList;
    }

    public void deleteEncodeAes(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STD, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public int updateEncodeAes(int id, String path, String nama, String plain, String  kunci, String chiper, Double psnr, Double waktu) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PATHFILE, path);
        values.put(KEY_NAMAFILE, nama);
        values.put(KEY_PLAIN, plain);
        values.put(KEY_KEYS, kunci);
        values.put(KEY_CHIPER, chiper);
        values.put(KEY_PSNR, psnr);
        values.put(KEY_WAKTU, waktu);

        return db.update(TABLE_STD, values, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});

    }
    public long addDecodeAES(String path, String nama, String plain, String kunci, String chiper, Double waktu) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PATHFILE, path);
        values.put(KEY_NAMAFILE, nama);
        values.put(KEY_PLAIN, plain);
        values.put(KEY_KEYS, kunci);
        values.put(KEY_CHIPER, chiper);
        values.put(KEY_WAKTU, waktu);
        long insert = db.insert(TABLE_STA, null, values);

        return insert;
    }

    public ArrayList<DecodeAES> getAllDecodeAES() {
        ArrayList<DecodeAES> userModelArrayList = new ArrayList<DecodeAES>();

        String selectQuery = "SELECT * FROM " + TABLE_STA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                DecodeAES std = new DecodeAES();
                std.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                std.setPathfile(c.getString(c.getColumnIndex(KEY_PATHFILE)));
                std.setNamafile(c.getString(c.getColumnIndex(KEY_NAMAFILE)));
                std.setPlain(c.getString(c.getColumnIndex(KEY_PLAIN)));
                std.setKeys(c.getString(c.getColumnIndex(KEY_KEYS)));
                std.setChiper(c.getString(c.getColumnIndex(KEY_CHIPER)));
                std.setWaktu(c.getDouble(c.getColumnIndex(KEY_WAKTU)));
                // adding to Students list
                userModelArrayList.add(std);
            } while (c.moveToNext());
        }
        return userModelArrayList;
    }

    public void deleteDecodeAes(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STA, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public int updateDecodeAes(int id, String path, String nama, String plain, String  kunci, String chiper, Double waktu) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PATHFILE, path);
        values.put(KEY_NAMAFILE, nama);
        values.put(KEY_PLAIN, plain);
        values.put(KEY_KEYS, kunci);
        values.put(KEY_CHIPER, chiper);
        values.put(KEY_WAKTU, waktu);

        return db.update(TABLE_STA, values, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});

    }


    public long addEncodeOnly(String path, String nama, String plain, Double psnr, Double waktu) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PATHFILE, path);
        values.put(KEY_NAMAFILE, nama);
        values.put(KEY_PLAIN, plain);
        values.put(KEY_PSNR, psnr);
        values.put(KEY_WAKTU, waktu);
        long insert = db.insert(TABLE_STB, null, values);

        return insert;
    }

    public ArrayList<EncodeOnly> getAllEncodeONLY() {
        ArrayList<EncodeOnly> userModelArrayList = new ArrayList<EncodeOnly>();

        String selectQuery = "SELECT * FROM " + TABLE_STB;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                EncodeOnly std = new EncodeOnly();
                std.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                std.setPath(c.getString(c.getColumnIndex(KEY_PATHFILE)));
                std.setNama(c.getString(c.getColumnIndex(KEY_NAMAFILE)));
                std.setPlain(c.getString(c.getColumnIndex(KEY_PLAIN)));
                std.setPsnr(c.getDouble(c.getColumnIndex(KEY_PSNR)));
                std.setWaktu(c.getDouble(c.getColumnIndex(KEY_WAKTU)));
                // adding to Students list
                userModelArrayList.add(std);
            } while (c.moveToNext());
        }
        return userModelArrayList;
    }

    public void deleteEncodeONLY(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STB, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public int updateEncodeONLY(int id, String path, String nama, String plain, Double psnr, Double waktu) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PATHFILE, path);
        values.put(KEY_NAMAFILE, nama);
        values.put(KEY_PLAIN, plain);
        values.put(KEY_PSNR, psnr);
        values.put(KEY_WAKTU, waktu);

        return db.update(TABLE_STB, values, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});

    }


    public long addDecodeOnly(String path, String nama, String plain, Double waktu) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PATHFILE, path);
        values.put(KEY_NAMAFILE, nama);
        values.put(KEY_PLAIN, plain);
        values.put(KEY_WAKTU, waktu);
        long insert = db.insert(TABLE_STC, null, values);

        return insert;
    }

    public ArrayList<DecodeOnly> getAllDecodeONLY() {
        ArrayList<DecodeOnly> userModelArrayList = new ArrayList<DecodeOnly>();

        String selectQuery = "SELECT * FROM " + TABLE_STC;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                DecodeOnly std = new DecodeOnly();
                std.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                std.setPath(c.getString(c.getColumnIndex(KEY_PATHFILE)));
                std.setNama(c.getString(c.getColumnIndex(KEY_NAMAFILE)));
                std.setPlain(c.getString(c.getColumnIndex(KEY_PLAIN)));
                std.setWaktu(c.getDouble(c.getColumnIndex(KEY_WAKTU)));
                // adding to Students list
                userModelArrayList.add(std);
            } while (c.moveToNext());
        }
        return userModelArrayList;
    }

    public void deleteDecodeONLY(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STC, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public int updateDecodeONLY(int id, String path, String nama, String plain, Double waktu) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PATHFILE, path);
        values.put(KEY_NAMAFILE, nama);
        values.put(KEY_PLAIN, plain);
        values.put(KEY_WAKTU, waktu);

        return db.update(TABLE_STC, values, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});

    }

}

