package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginScreen extends AppCompatActivity {
    TextInputLayout inputEmail, inputPassword;
    Button btnLogin, btnGoogle;
    TextView singup,erro;
    LoadingDialog loadingDialalog;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        btnLogin = findViewById(R.id.btnLogin);
        inputEmail = findViewById(R.id.textInputLayout3);
        inputPassword = findViewById(R.id.textInputLayout2);
        singup = findViewById(R.id.titleRegister);
        btnGoogle = findViewById(R.id.btnGoogle);
        LoadingDialog loadingDialalog = new LoadingDialog(LoginScreen.this);

        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginScreen.this, RegisterScreen.class));
            }
        });

        btnGoogle = findViewById(R.id.btnGoogle);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputEmail.getEditText().getText().toString().trim();
                String pass = inputPassword.getEditText().getText().toString().trim();

                if (!validateUsername() | !validatePassword()) {
                } else {
                    checkUser();
                }
            }
        });
    }
    public Boolean validateUsername() {
        String val = inputEmail.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            inputEmail.setError("Chưa nhập địa chỉ email");
            return false;
        } else {
            inputEmail.setError(null);
            return true;
        }
    }
    public Boolean validatePassword(){
        String val = inputPassword.getEditText().getText().toString();
        if (val.isEmpty()) {
            inputPassword.setError("Chưa nhập mật khẩu");
            return false;
        } else {
            inputPassword.setError(null);
            return true;
        }
    }

    public void checkUser(){
        String userEmail = inputEmail.getEditText().getText().toString().trim();
        String userPassword = inputPassword.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean isUserFound = false;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String emailFromDB = snapshot.child("email").getValue(String.class);
                    String passwordFromDB = snapshot.child("pass").getValue(String.class);

                    if (emailFromDB != null && emailFromDB.equals(userEmail)) {
                        isUserFound = true;
                        if (passwordFromDB != null && passwordFromDB.equals(userPassword)) {

                            String nameFromDB = snapshot.child("name").getValue(String.class);
                            String usernameFromDB = snapshot.child("username").getValue(String.class);

                            Toast.makeText(LoginScreen.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(LoginScreen.this, UserActivity.class);
                            intent.putExtra("name", nameFromDB);
                            intent.putExtra("username", usernameFromDB);
                            intent.putExtra("email", emailFromDB);
                            startActivity(intent);
                            finish();
                        } else {
                            // Mật khẩu sai
                            Toast.makeText(LoginScreen.this, "Sai mật khẩu!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                }

                if (!isUserFound) {
                    // Tài khoản không tồn tại
                    Toast.makeText(LoginScreen.this, "Email không tồn tại!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Lỗi kết nối cơ sở dữ liệu
                Toast.makeText(LoginScreen.this, "Lỗi kết nối với cơ sở dữ liệu!", Toast.LENGTH_SHORT).show();
            }
        });
        }

        void signIn(){
            Intent signInIntent = gsc.getSignInIntent();
            startActivityForResult(signInIntent,1000);
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                navigateToSecondActivity();
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }

    }
    void navigateToSecondActivity(){
        finish();
        Intent intent = new Intent(LoginScreen.this, UserActivity.class);
        startActivity(intent);
    }
}
