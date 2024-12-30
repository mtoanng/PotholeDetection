package com.example.myapplication.Toan;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DashboardDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "potholeApp.db";
    private static final int DATABASE_VERSION = 1;

    public DashboardDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables here if needed
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades
    }

    public int getTotalPotholes(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM potholes WHERE user_id = ?",
                new String[]{userId}
        );
        if (cursor.moveToFirst()) {
            int totalPotholes = cursor.getInt(0);
            cursor.close();
            return totalPotholes;
        }
        cursor.close();
        return 0; // Default if no records are found
    }
    public double getTotalDistance(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT total_distance FROM UserDetails WHERE userID = ?",
                new String[]{userId}
        );
        if (cursor.moveToFirst()) {
            double totalDistance = cursor.getDouble(0);
            cursor.close();
            return totalDistance;
        }
        cursor.close();
        return 0.0; // Default if no records are found
    }
    public double getPotholeFrequency(String userId) {
        int totalPotholes = getTotalPotholes(userId);
        double totalDistance = getTotalDistance(userId);

        if (totalDistance > 0) {
            return totalPotholes / totalDistance;
        }
        return 0.0; // Avoid division by zero
    }
}

