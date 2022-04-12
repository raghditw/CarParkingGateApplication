package com.example.assignment2_it_it342;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper( Context context) {
        super(context, "Useerdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table ParkingDetails(etx_PN TEXT primary key, etx_color TEXT, etx_make TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists ParkingDetails");
    }

    public boolean insertParkingData(String PN, String color, String make){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("etx_PN", PN);
        contentValues.put("etx_color", color);
        contentValues.put("etx_make", make);
        long result = DB.insert("ParkingDetails", null, contentValues);
        if (result== -1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean deleteParkingData(String PN) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from ParkingDetails where etx_PN = ?", new String[]{PN});
        if (cursor.getCount() > 0) {
            long result = DB.delete("ParkingDetails", "etx_PN=?", new String[]{PN});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else{
            return false;
         }
    }

    public Cursor getdata() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from ParkingDetails", null);
        return cursor;
    }

    public int getProfilesCount() {
        String countQuery = "SELECT  * FROM  ParkingDetails";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
}

