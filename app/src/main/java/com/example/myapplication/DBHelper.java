package com.example.myapplication;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context ) {
        super(context,"potholeApp",null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table UserDetails(name TEXT,lastname TEXT, username TEXT, userID TEXT primary key,password PASSWORD, comfomPassword comfomPassword)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists UserDetails");
    }

    public Boolean insertUserData(String name, String lastname, String username, String email,String password, String comfomPassword){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("lastname",lastname);
        contentValues.put("username",username);
        contentValues.put("userID",email);
        contentValues.put("password",password);
        contentValues.put("comfomPassword",comfomPassword);
        long result= DB.insert("UserDetails",null,contentValues);
        if (result == -1){
            return false;
        }else {
            return true;
        }
    }
    public Cursor getData1(String email, String username) {
        SQLiteDatabase DB = this.getWritableDatabase();
        String query = "SELECT userID, username FROM UserDetails WHERE userID = ? OR username = ?";
        Log.d("Debug", "Executing query: " + query + " with values: " + email + ", " + username);
        return DB.rawQuery(query, new String[]{email, username});
    }

    public Cursor getData2(String email, String password) {
        SQLiteDatabase DB = this.getWritableDatabase();
        String query = "SELECT userID, password FROM UserDetails WHERE userID = ? OR password = ?";
        return DB.rawQuery(query, new String[]{email, password});
    }

}
