package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.TimeUnit;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import okhttp3.*;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes;


import java.io.IOException;

public class GoogleLogin extends AppCompatActivity {

    private static final int RC_SIGN_IN = 100;
    private GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = "GoogleLogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called");

        setContentView(R.layout.activity_login_screen);

        // Configure Google Sign-In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id)) // Web Client ID
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Trigger Sign-In
        findViewById(R.id.btnGoogle).setOnClickListener(v -> signIn());
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult() called");

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "Google sign-in successful. Account: " + account.getEmail());
                // Khi đăng nhập thành công, gọi sendTokenToServer()
                sendTokenToServer(account.getIdToken());
            } catch (ApiException e) {
                Log.e(TAG, "Google sign-in failed. Status code: " + e.getStatusCode(), e);
            }
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                Log.d(TAG, "Sign-in successful: Email = " + account.getEmail());
                String idToken = account.getIdToken();
                Log.d(TAG, "ID Token: " + idToken);

                // Send token to server
                sendTokenToServer(idToken);
            }
        } catch (ApiException e) {
            int statusCode = e.getStatusCode();
            Log.e(TAG, "signInResult:failed code=" + statusCode, e);

            // Xử lý theo mã lỗi
//            handleGoogleSignInError(statusCode);
        }
    }

    private void sendTokenToServer(String idToken) {
        OkHttpClient client = new OkHttpClient();

        // Replace with your API endpoint
        String url = "https://spacez.com:8080/api/auth/google";

        // Create JSON object
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("idToken", idToken);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create request body
        RequestBody body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonObject.toString()
        );

        // Build the request
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        // Make the API call
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Log and show an error toast if the API call fails
                Log.e(TAG, "API call failed", e);
                runOnUiThread(() -> {
                    Toast.makeText(GoogleLogin.this, "API call failed. Please check your connection.", Toast.LENGTH_SHORT).show();
                });
            }

//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    Log.d(TAG, "Server response: " + response.body().string());
//                    runOnUiThread(() -> {
//                        Log.d(TAG, "Navigating to CheckMail activity...");
//                        Intent intent = new Intent(GoogleLogin.this, RegisterScreen.class);
//                        startActivity(intent);
//                        finish();
//                    });
//                } else {
//                    Log.e(TAG, "Server error: " + response.code());
//                    runOnUiThread(() -> {
//                        Toast.makeText(GoogleLogin.this, "Server error: " + response.message(), Toast.LENGTH_SHORT).show();
//                    });
//                }
//            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // Log response code and body for debugging
                Log.d(TAG, "Response code: " + response.code());
                Log.d(TAG, "Response body: " + response.body().string());

                if (response.isSuccessful()) {
                    runOnUiThread(() -> {
                        Log.d(TAG, "Navigating to RegisterScreen activity...");
                        Intent intent = new Intent(GoogleLogin.this, RegisterScreen.class);
                        startActivity(intent);
//                      finish();  // Make sure this is not causing the problem
                    });
                    } else {
                        runOnUiThread(() -> {
                        Toast.makeText(GoogleLogin.this, "Server error: " + response.message(), Toast.LENGTH_SHORT).show();
                    });
                }
            }
    });
    };
}
