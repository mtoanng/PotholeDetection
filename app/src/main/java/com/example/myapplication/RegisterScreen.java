package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.databinding.ActivityRegisterScreenBinding ;
import com.example.myapplication.DBHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.material.textfield.TextInputLayout;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

public class RegisterScreen extends AppCompatActivity {
    TextView titleSignin, erro;
    TextInputLayout inputLastName, inputName, inputUserName, inputEmail, inputPassWord, inputComformPassWord;
    Button btnReg;
    LoadingDialog loadingDialalog;
//    FirebaseDatabase database;
//    DatabaseReference reference;
    ActivityRegisterScreenBinding  binding;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        DB = new DBHelper(this);

        titleSignin = findViewById(R.id.titleLogin);
        btnReg = findViewById(R.id.btnRegister);
        inputLastName = findViewById(R.id.textInputLayout4);
        inputName = findViewById(R.id.textInputLayout5);
        inputUserName = findViewById(R.id.textInputLayout6);
        inputEmail = findViewById(R.id.textInputLayout8);
        inputPassWord = findViewById(R.id.textInputLayout10);
        inputComformPassWord = findViewById(R.id.textInputLayout11);
        loadingDialalog = new LoadingDialog(this);

        titleSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterScreen.this, LoginScreen.class));
            }
        });

        inputLastName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    inputLastName.setError("Chưa nhập họ");
                    inputLastName.setErrorEnabled(true);
                } else if (charSequence.length() < 1) {
                    inputLastName.setError("Tối thiểu 1 ký tự");
                } else {
                    inputLastName.setError(null);
                    inputLastName.setErrorEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    inputName.setError("Chưa nhập tên");
                    inputName.setErrorEnabled(true);
                } else if (charSequence.length() < 1) {
                    inputName.setError("Tối thiểu 1 ký tự");
                } else {
                    inputName.setError(null);
                    inputName.setErrorEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputUserName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    inputUserName.setError("Chưa nhập username");
                    inputUserName.setErrorEnabled(true);
                } else {
                    inputUserName.setError(null);
                    inputUserName.setErrorEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputEmail.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    inputEmail.setError("Chưa nhập email");
                    inputEmail.setErrorEnabled(true);
                } else {
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
        inputPassWord.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    inputPassWord.setError("Chưa nhập mật khẩu");
                    inputPassWord.setErrorEnabled(true);
                } else if (charSequence.length() < 1) {
                    inputPassWord.setError("Tối thiểu 1 ký tự");
                } else if (charSequence.length() < 3) {
                    inputPassWord.setError("Mật khẩu dài tối thiểu 4 ký thự");
                    inputPassWord.setErrorEnabled(true);
                } else if (charSequence.length() > 16) {
                    inputPassWord.setError("Mật khẩu dài tối đa 16 ký thự");
                    inputPassWord.setErrorEnabled(true);
                } else {
                    inputPassWord.setError(null);
                    inputPassWord.setErrorEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputComformPassWord.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.length() == 0) {
                    inputComformPassWord.setError("Chưa nhập lại mật khẩu");
                    inputComformPassWord.setErrorEnabled(true);
                } else {
                    inputComformPassWord.setError(null);
                    inputComformPassWord.setErrorEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String pass = inputPassWord.getEditText().getText().toString().trim();
                String comfomPass = inputComformPassWord.getEditText().getText().toString().trim();
                if (!comfomPass.equals(pass)) {
                    inputComformPassWord.setError("Nhập lại mật khẩu không chính xác");
                    inputComformPassWord.setErrorEnabled(true);
                }
            }
        });


        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                boolean test = true;
                String lastname = inputLastName.getEditText().getText().toString().trim();
                String name = inputName.getEditText().getText().toString().trim();
                String username = inputUserName.getEditText().getText().toString().trim();
                String email = inputEmail.getEditText().getText().toString().trim();
                String pass = inputPassWord.getEditText().getText().toString().trim();
                String comfomPass = inputComformPassWord.getEditText().getText().toString().trim();

                if (pass.equals(comfomPass)) {
//                        Boolean checkuser = DB.checkusername(email);
//                        if (checkuser == false) {
                            Boolean insert = DB.insertUserData(name, lastname, username, email, pass, comfomPass);
//                            if (insert == true) {
                                Toast.makeText(RegisterScreen.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterScreen.this, LoginScreen.class);
                                startActivity(intent);
//                            } else {
//                                Toast.makeText(RegisterScreen.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
//                            }}
//                            else{
//                                Toast.makeText(RegisterScreen.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
//                                startActivity(intent);
//                            }
                        }
                    }
            }
        );
    }
}
