package com.example.myapplication.Toan;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.Toan.BottomNavigationActivity;

public class Setting extends BottomNavigationActivity {

    private TextView userNameTextView, userEmailTextView;
    private Switch notificationSwitch;
    private Spinner sensitivitySpinner;
    private Button saveButton;

    private SharedPreferences sharedPreferences;

    // Sensitivity levels
    private String selectedSensitivity = "Medium"; // Default level

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        // Initialize views
        userNameTextView = findViewById(R.id.user_name);
        userEmailTextView = findViewById(R.id.user_email);

        // Retrieve user information from SharedPreferences
        SharedPreferences userInfoPrefs = getSharedPreferences("UserInfo", MODE_PRIVATE);
        String savedUserName = userInfoPrefs.getString("userName", "Default User");
        String savedEmail = userInfoPrefs.getString("email", "user@example.com");

        // Set the user information on the UI
        userNameTextView.setText(savedUserName);
        userEmailTextView.setText(savedEmail);

        // Initialize SharedPreferences for app settings
        sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE);

        // Initialize UI elements
        TextView accountPage = findViewById(R.id.account_option);
        notificationSwitch = findViewById(R.id.notification_switch);
        sensitivitySpinner = findViewById(R.id.sensitivity_spinner);
        saveButton = findViewById(R.id.save_button);

        // Load saved notification state
        boolean isNotificationEnabled = sharedPreferences.getBoolean("notifications_enabled", true);
        notificationSwitch.setChecked(isNotificationEnabled);

        // Load saved sensitivity level
        selectedSensitivity = sharedPreferences.getString("sensitivity_level", "Medium");

        // Set up spinner for sensitivity levels
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sensitivity_levels, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sensitivitySpinner.setAdapter(adapter);

        // Set the spinner to the saved sensitivity level
        int sensitivityIndex = adapter.getPosition(selectedSensitivity);
        sensitivitySpinner.setSelection(sensitivityIndex);

        // Handle sensitivity selection
        sensitivitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSensitivity = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Default to Medium if nothing is selected
                selectedSensitivity = "Medium";
            }
        });

        // Handle Save button click
        saveButton.setOnClickListener(v -> {
            // Save notification state
            boolean isChecked = notificationSwitch.isChecked();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("notifications_enabled", isChecked);

            // Save sensitivity level
            editor.putString("sensitivity_level", selectedSensitivity);
            editor.apply();  // Apply changes to SharedPreferences

            // Notify user
            Toast.makeText(Setting.this, "Settings Saved", Toast.LENGTH_SHORT).show();

            // Call methods to handle the changes
            if (isChecked) {
                enableNotifications();
            } else {
                disableNotifications();
            }

            // Log sensitivity level for developers
            logSensitivityLevel(selectedSensitivity);
        });
    }

    private void enableNotifications() {
        // Logic to enable notifications
        Toast.makeText(this, "Notifications Enabled", Toast.LENGTH_SHORT).show();
    }

    private void disableNotifications() {
        // Logic to disable notifications
        Toast.makeText(this, "Notifications Disabled", Toast.LENGTH_SHORT).show();
    }

    private void logSensitivityLevel(String sensitivity) {
        // Log sensitivity level for developers (can also send this to a server)
        System.out.println("Pothole Detection Sensitivity Level: " + sensitivity);
        Toast.makeText(this, "Sensitivity Level: " + sensitivity, Toast.LENGTH_SHORT).show();
    }
}
