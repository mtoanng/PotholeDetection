package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginScreen extends AppCompatActivity {
    TextInputLayout inputEmail, inputPassword;
    Button btnLogin;
    TextView singup,erro;
    LoadingDialog loadingDialalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        btnLogin = findViewById(R.id.btnLogin);
        inputEmail = findViewById(R.id.textInputLayout3);
        inputPassword = findViewById(R.id.textInputLayout2);
        singup = findViewById(R.id.titleRegister);
        LoadingDialog loadingDialalog = new LoadingDialog(LoginScreen.this);

        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginScreen.this, RegisterScreen.class));
            }
        });


        inputEmail.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()==0){
                    inputEmail.setError("Chưa nhập địa chỉ email");
                    inputEmail.setErrorEnabled(true);
                }
                else{
                    inputEmail.setError(null);
                    inputEmail.setErrorEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()==0){
                    inputPassword.setError("Chưa nhập mật khẩu");
                    inputPassword.setErrorEnabled(true);
                }
                else{
                    inputPassword.setError(null);
                    inputPassword.setErrorEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputEmail.getEditText().getText().toString().trim();
                String pass = inputPassword.getEditText().getText().toString().trim();
                boolean test =true;
                if (email.isEmpty()) {
                    inputEmail.setErrorEnabled(true);
                    inputEmail.setError("Chưa nhập địa chỉ email");
                    test=false;
                }
                if (pass.isEmpty()) {
                    inputPassword.setErrorEnabled(true);
                    inputPassword.setError("Chưa nhập mật khẩu");
                    test=false;
                }
                if (test==true){
                    loadingDialalog.ShowDialog("Đang tải...");
                    LoginRequest loginRequest = new LoginRequest();
                    loginRequest.setEmail(email);
                    loginRequest.setPassword(pass);
                    loginUser(loginRequest);

                }
            }
        });
    }
    public void loginUser(LoginRequest loginRequest){
        Call<LoginRespond> loginResponCall = ApiClient.getService().loginRespon(loginRequest);
        loginResponCall.enqueue(new Callback<LoginRespond>() {
            @Override
            public void onResponse(Call<LoginRespond> call, Response<LoginRespond> response) {
                if (response.isSuccessful()){
                    LoginRespond loginRespon=response.body();
                    startActivity(new Intent(LoginScreen.this,MainActivity.class).putExtra("data",loginRespon));
                    finish();

                }else{
                    String message =  "Tài khoản đã tồn tại";
                    erro = findViewById(R.id.erroSignin);
                    erro.setText(message);
                    loadingDialalog.HideDialog();
                }
            }

            @Override
            public void onFailure(Call<LoginRespond> call, Throwable t) {

                String message= t.getLocalizedMessage();
                Toast.makeText(LoginScreen.this,message,Toast.LENGTH_LONG).show();
            }
        });
    }
}
