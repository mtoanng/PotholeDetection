package com.example.myapplication.activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.myapplication.LoginScreen;
import com.example.myapplication.R;
import com.example.myapplication.Toan.BottomNavigationActivity;
import com.example.myapplication.database.PotholeData;
import com.example.myapplication.database.PotholeDbHelper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class PotholeActivity extends BottomNavigationActivity implements SensorEventListener, View.OnClickListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Button startButton, stopButton, plotButton;
    private TextView statusTextView;
    private boolean isDetecting = false;
    private int userId;

    // FusedLocationProviderClient for continuous location updates
    private FusedLocationProviderClient fusedLocationClient;
    private Location currentLocation;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pothole_detector);

        // Temporary user ID for demonstration
        userId = 1;

        // Initialize sensor manager for accelerometer
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Initialize buttons and status text view
        startButton = findViewById(R.id.start_button);
        stopButton = findViewById(R.id.stop_button);
        plotButton = findViewById(R.id.plot_button);
        statusTextView = findViewById(R.id.status);

        // Set up listeners for buttons
        startButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
        plotButton.setOnClickListener(v -> {
            // Navigate to Map Screen to plot pothole on Map
//            Intent intent = new Intent(PotholeActivity.this, MapViewScreen.class);
//            startActivity(intent);
        });

        // Initially disable stop button
        stopButton.setEnabled(false);

        // Check permissions for location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

            // Setup location request for continuous updates
            locationRequest = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(2000)  // Request location every 2 seconds
                    .setFastestInterval(1000);  // Get updates every second, if available

            // Initialize location callback to handle location updates
            locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult != null && !locationResult.getLocations().isEmpty()) {
                        currentLocation = locationResult.getLastLocation();
                    }
                }
            };
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Start continuous location updates
    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }
    }

    // Stop location updates
    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.start_button) {
            startDetection();
        } else if (v.getId() == R.id.stop_button) {
            stopDetection();
        }
    }

    private void startDetection() {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        isDetecting = true;
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
        statusTextView.setText("Recording...");
        startLocationUpdates();  // Start location updates when detection starts
    }

    private void stopDetection() {
        sensorManager.unregisterListener(this);
        isDetecting = false;
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        statusTextView.setText("Not Recording");
        stopLocationUpdates();  // Stop location updates when detection stops
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (isDetecting && currentLocation != null) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            if (isPotholeDetected(x, y, z)) {
                double latitude = currentLocation.getLatitude();
                double longitude = currentLocation.getLongitude();
                long timestamp = System.currentTimeMillis();

                PotholeData potholeData = new PotholeData(userId, timestamp, x, y, z, latitude, longitude);
                uploadPotholeData(potholeData);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used
    }

    private boolean isPotholeDetected(float x, float y, float z) {
        // Adjust these thresholds based on your testing and calibration
        float threshold = 10.0f; // Adjust this threshold as needed

        // Calculate the magnitude of the acceleration
        float magnitude = (float) Math.sqrt(x * x + y * y + z * z);

        // Check if the magnitude exceeds the threshold
        return magnitude > threshold;
    }

    private void uploadPotholeData(PotholeData potholeData) {
        PotholeDbHelper dbHelper = new PotholeDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PotholeDbHelper.COLUMN_TIMESTAMP, potholeData.getTimestamp());
        values.put(PotholeDbHelper.COLUMN_X_ACCELERATION, potholeData.getXAcceleration());
        values.put(PotholeDbHelper.COLUMN_Y_ACCELERATION, potholeData.getYAcceleration());
        values.put(PotholeDbHelper.COLUMN_Z_ACCELERATION, potholeData.getZAcceleration());
        values.put(PotholeDbHelper.COLUMN_LATITUDE, potholeData.getLatitude());
        values.put(PotholeDbHelper.COLUMN_LONGITUDE, potholeData.getLongitude());

        long newRowId = db.insert(PotholeDbHelper.TABLE_NAME, null, values);
        db.close();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();  // Stop location updates when the activity is paused
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates();  // Start location updates when the activity is resumed
        }
    }
}
