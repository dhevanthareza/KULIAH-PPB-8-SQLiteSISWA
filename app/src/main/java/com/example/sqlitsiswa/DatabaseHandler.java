package com.example.sqlitsiswa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION= 3;
    private static final String DATABASE_NAME= "Sekolah";
    private static final String TABLE_SISWA= "Siswa";
    private static final String KEY_NIS= "nis";
    private static final String KEY_NAMA= "nama";

    public DatabaseHandler(Context context) {
        // TODOAuto-generated constructor stub
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODOAuto-generated method stub
        String query_table_siswa = "CREATE TABLE " + TABLE_SISWA +
                "(" +
                KEY_NIS + " TEXT PRIMARY KEY," + // Define a primary key
                KEY_NAMA + " TEXT" +
                ")";
        Log.d("Baca Siswa: ", "Membaca Semua Data Siswa..");
        Log.d("Query table: ", query_table_siswa);
        db.execSQL(query_table_siswa);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SISWA);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SISWA);
            onCreate(db);
        }
    }

    // MenambahSiswaBaru
    public void addSiswa(Siswa siswa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NIS, siswa.getNis());
        values.put(KEY_NAMA, siswa.getNama());
        // Inserting Row
        db.insert(TABLE_SISWA, null, values);
        db.close(); //
    }
    // MembacaSiswa
    public Siswa getSiswa(String nis) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SISWA, new String[] {
                        KEY_NIS, KEY_NAMA}, KEY_NIS+ "=?",
                new String[] {nis}, null, null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        Siswa siswa = new Siswa(cursor.getString(0),
                cursor.getString(1));
        return siswa;
    }
    // MembacaSemuaSiswa
    public List<Siswa> getSemuaSiswa() {
        List<Siswa> siswaList = new ArrayList<Siswa>();
        String query_select_siswa = "SELECT * FROM "+
                TABLE_SISWA;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query_select_siswa,
                null);
        if(cursor.moveToFirst()) {
            do{
                Siswa siswa = new Siswa(cursor.getString(0),
                        cursor.getString(1));
                siswaList.add(siswa);
            } while(cursor.moveToNext());
        }
        return siswaList;
    }
    public void deleteSiswa(Siswa siswa) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SISWA, KEY_NIS+ "='"+
                    siswa.getNis()+"'",null);
        db.close();
        System.out.println("Data terhapus "+siswa.getNis());
    }
    public void deleteRow(String xnis) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SISWA, KEY_NIS+ "='"+
                xnis+"'",null);
        db.close();
        System.out.println("Data terhapus "+xnis);
    }
    public void updateMethod(String nis, String nama){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update "+TABLE_SISWA+" set nama='"+nama+
                "' where nis='"+nis+"'");
        db.close();
        System.out.println("Data sudah di update "+nis);
    }
}
