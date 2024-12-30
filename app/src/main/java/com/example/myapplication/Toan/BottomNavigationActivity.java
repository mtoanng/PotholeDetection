package com.example.myapplication.Toan;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.activity.PotholeActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set up the navigation listener
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_dashboard) {
                // Navigate to Dashboard Activity
                Intent homeIntent = new Intent(this, Dashboard.class);
                startActivity(homeIntent);
                return true;
            } else if (item.getItemId() == R.id.nav_detect) {
                // Navigate to Pothole Activity
                Intent detectIntent = new Intent(this, PotholeActivity.class);
                startActivity(detectIntent);
                return true;
            } else if (item.getItemId() == R.id.nav_settings) {
                // Navigate to Settings Activity
                Intent settingsIntent = new Intent(this, Setting.class);
                startActivity(settingsIntent);
                return true;
            } else {
                return false;
            }
        });
    }
}
