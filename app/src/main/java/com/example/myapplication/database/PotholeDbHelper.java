package com.example.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PotholeDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "pothole_detector.db";
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
    public boolean insertPothole(int userId, long timestamp, float xAcceleration, float yAcceleration,
                                 float zAcceleration, double latitude, double longitude) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_TIMESTAMP, timestamp);
        values.put(COLUMN_X_ACCELERATION, xAcceleration);
        values.put(COLUMN_Y_ACCELERATION, yAcceleration);
        values.put(COLUMN_Z_ACCELERATION, zAcceleration);
        values.put(COLUMN_LATITUDE, latitude);
        values.put(COLUMN_LONGITUDE, longitude);

        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result != -1; // Returns true if the insertion was successful
    }
         * Retrieve all pothole records from the database.
     */
    public List<String> getAllPotholes() {
        List<String> potholeList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                int userId = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID));
                long timestamp = cursor.getLong(cursor.getColumnIndex(COLUMN_TIMESTAMP));
                float xAcceleration = cursor.getFloat(cursor.getColumnIndex(COLUMN_X_ACCELERATION));
                float yAcceleration = cursor.getFloat(cursor.getColumnIndex(COLUMN_Y_ACCELERATION));
                float zAcceleration = cursor.getFloat(cursor.getColumnIndex(COLUMN_Z_ACCELERATION));
                double latitude = cursor.getDouble(cursor.getColumnIndex(COLUMN_LATITUDE));
                double longitude = cursor.getDouble(cursor.getColumnIndex(COLUMN_LONGITUDE));

                potholeList.add("ID: " + id + ", User ID: " + userId + ", Timestamp: " + timestamp
                        + ", Location: (" + latitude + ", " + longitude + ")");
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return potholeList;
    }
    /**
     * Delete a specific pothole by its ID.
     */
    public boolean deletePotholeById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected > 0;
    }
    /**
     * Delete all records from the database.
     */
    public void clearDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.close();
    }

}