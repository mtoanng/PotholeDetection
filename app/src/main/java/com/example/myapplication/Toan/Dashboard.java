    package com.example.myapplication.Toan;

    import android.content.SharedPreferences;
    import android.database.Cursor;
    import android.os.Bundle;
    import android.widget.TextView;

    import com.example.myapplication.R;

    public class Dashboard extends BottomNavigationActivity{
        private TextView userNameTextView, userEmailTextView;
        private TextView totalPotholeTextView, totalDistanceTextView, potholeFrequencyTextView, potholeSeverityTextView;
        private DashboardDBHelper dashboardDBHelper;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dashboard);

            //Top section
            userNameTextView = findViewById(R.id.user_name);
            userEmailTextView = findViewById(R.id.user_email);

            SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
            String savedUserName = sharedPreferences.getString("userName", "Default User");
            String savedEmail = sharedPreferences.getString("email", "user@example.com");

            userNameTextView.setText(savedUserName);
            userEmailTextView.setText(savedEmail);



            //Metrics
            totalPotholeTextView = findViewById(R.id.total_pothole);
            totalDistanceTextView = findViewById(R.id.total_distance);
            potholeFrequencyTextView = findViewById(R.id.pothole_frequency);
            potholeSeverityTextView = findViewById(R.id.pothole_severity);

            dashboardDBHelper = new DashboardDBHelper(this);



            displayUserMetrics(savedEmail);

        }

        private void displayUserMetrics(String userId) {
            // Fetch total potholes, total distance, and pothole frequency from the database
            int totalPotholes = dashboardDBHelper.getTotalPotholes(userId);
            double totalDistance = dashboardDBHelper.getTotalDistance(userId);
            double potholeFrequency = dashboardDBHelper.getPotholeFrequency(userId);

            // Display the values in the TextViews
            totalPotholeTextView.setText("Total Potholes: " + totalPotholes);
            totalDistanceTextView.setText("Total Distance: " + totalDistance + " km");
            potholeFrequencyTextView.setText("Pothole Frequency: " + potholeFrequency);
        }
    }
