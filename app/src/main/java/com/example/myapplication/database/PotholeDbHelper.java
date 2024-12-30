package com.example.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PotholeDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "potholeApp.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "potholes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_TIMESTAMP = "timestamp";
    public static final String COLUMN_X_ACCELERATION = "x_acceleration";
    public static final String COLUMN_Y_ACCELERATION = "y_acceleration";
    public static final String COLUMN_Z_ACCELERATION = "z_acceleration";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";


    public PotholeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_POTHOLE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USER_ID + " INTEGER,"
                + COLUMN_TIMESTAMP + " INTEGER,"
                + COLUMN_X_ACCELERATION + " REAL,"
                + COLUMN_Y_ACCELERATION + " REAL,"
                + COLUMN_Z_ACCELERATION + " REAL,"
                + COLUMN_LATITUDE + " REAL,"
                + COLUMN_LONGITUDE + " REAL)";
        db.execSQL(CREATE_POTHOLE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades if needed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}