package com.example.myapplication.database;


public class PotholeData {
    private int userID;
    private long timestamp;
    private float xAcceleration;
    private float yAcceleration;
    private float zAcceleration;
    private double latitude;
    private double longitude;

    public PotholeData(int userID,long timestamp, float xAcceleration, float yAcceleration, float zAcceleration, double latitude, double longitude) {
        this.userID = userID;
        this.timestamp = timestamp;
        this.xAcceleration = xAcceleration;
        this.yAcceleration = yAcceleration;
        this.zAcceleration = zAcceleration;
        this.latitude = latitude;
        this.longitude = longitude;
    }
//    public int getUserId() {
//        return userId;
//    }

    public long getTimestamp() {
        return timestamp;
    }

    public float getXAcceleration() {
        return xAcceleration;
    }

    public float getYAcceleration() {
        return yAcceleration;
    }

    public float getZAcceleration() {
        return zAcceleration;
    }
    public double getLatitude(){
        return latitude;
    }
    public double getLongitude(){
        return longitude;
    }
    // Getters and setters for the data fields
}