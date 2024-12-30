//package com.example.myapplication;
//
//import androidx.appcompat.app.AppCompatActivity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//import com.google.android.gms.auth.api.signin.GoogleSignIn;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInClient;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import android.database.Cursor;
//
//public class UserActivity extends AppCompatActivity {
//    GoogleSignInOptions gso;
//    GoogleSignInClient gsc;
//    TextView userName,emailUser;
//    Button signOutBtn;
//    DBHelper DB;
////    DatabaseReference reference;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.user);
//
//
//        userName = findViewById(R.id.userName);
//        emailUser = findViewById(R.id.email);
//        signOutBtn = findViewById(R.id.signout);
//
//
//        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
//        gsc = GoogleSignIn.getClient(this,gso);
//
//        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
//        if(acct!=null){
//            String personName = acct.getDisplayName();
//            String personEmail = acct.getEmail();
//            userName.setText(personName);
//            emailUser.setText(personEmail);
//        }
//
//        DB = new DBHelper(this);
//
//        String emailOrUsername = getIntent().getStringExtra("userID");
//
//        if (emailOrUsername != null) {
//            Cursor cursor = DB.getData(emailUser, userName);
//
//            if (cursor != null && cursor.moveToFirst()) {
//                // Hiển thị thông tin người dùng
//                String userName1 = cursor.getString(cursor.getColumnIndexOrThrow("username"));
//                String emailUser1 = cursor.getString(cursor.getColumnIndexOrThrow("email"));
//
//                userName.setText(userName1);
//                emailUser.setText(emailUser1);
//            }
//
//            signOutBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    signOut();
//                }
//            });
//
//
//            void signOut () {
//                gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(Task<Void> task) {
//                        finish();
//                        startActivity(new Intent(UserActivity.this, MainActivity.class));
//                    }
//                });
//            }
//}

package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class UserActivity extends AppCompatActivity {
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    TextView userName, emailUser;
    Button signOutBtn;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);

        userName = findViewById(R.id.userName);
        emailUser = findViewById(R.id.email);
        signOutBtn = findViewById(R.id.signout);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            userName.setText(personName);
            emailUser.setText(personEmail);

            SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("userName", personName);
            editor.putString("email", personEmail);
            editor.apply();
        }

        DB = new DBHelper(this);

        String emailOrUsername = getIntent().getStringExtra("userID");

        if (emailOrUsername != null) {
            Cursor cursor = DB.getData1(emailOrUsername, emailOrUsername);

            if (cursor != null && cursor.moveToFirst()) {
                String userName1 = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String emailUser1 = cursor.getString(cursor.getColumnIndexOrThrow("userID"));

                userName.setText(userName1);
                emailUser.setText(emailUser1);

                SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("userName", userName1);
                editor.putString("email", emailUser1);
                editor.apply();
            } else {
                Toast.makeText(this, "No user data found", Toast.LENGTH_SHORT).show();
            }
        }


        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }

    private void signOut() {
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                finish();
                startActivity(new Intent(UserActivity.this, MainActivity.class));
            }
        });
    }
}
