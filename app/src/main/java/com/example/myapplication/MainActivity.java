package com.example.myapplication;

//import android.os.Bundle;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//public class MainActivity extends AppCompatActivity {
//
//    LoginRespond loginRespon;
//    MeowBottomNavigation bottomNavigation;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//    }
//}

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnLanguageSelection;
    private Button btnSignIn;
    private Button btnSignUp;
    private Button btnContinueWithGoogle;
    private Button btnForgotPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize buttons
        btnLanguageSelection = findViewById(R.id.btn_language_selection);
        btnSignIn = findViewById(R.id.btn_sign_in);
        btnSignUp = findViewById(R.id.btn_sign_up);
//        btnContinueWithGoogle = findViewById(R.id.btn_continue_with_google);
//        btnForgotPassword = findViewById(R.id.btn_forgot_password);

        // Set up button click listeners
        btnLanguageSelection.setOnClickListener(v -> {
            // Handle language selection (Example: show a dialog or a new activity)
            Toast.makeText(this, "Language selection clicked", Toast.LENGTH_SHORT).show();
        });

        btnSignIn.setOnClickListener(v -> {
            // Handle sign in (Example: start SignInActivity)
            Intent intent = new Intent(MainActivity.this, LoginScreen.class);
            startActivity(intent);
        });

        btnSignUp.setOnClickListener(v -> {
            // Handle sign up (Example: start SignUpActivity)
            Intent intent = new Intent(MainActivity.this, RegisterScreen.class);
            startActivity(intent);
        });

//        btnContinueWithGoogle.setOnClickListener(v -> {
//           Intent intent = new Intent(MainActivity.this, CheckMail.class);
//           startActivity(intent);
//        });
//
//        btnForgotPassword.setOnClickListener(v -> {
//            // Handle forgot password (Example: start ForgotPasswordActivity)
//            Toast.makeText(this, "Forgot password clicked", Toast.LENGTH_SHORT).show();
//        });
    }
}
