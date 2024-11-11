package com.example.myapplication;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.Interceptor;

public class ApiClient {
    public static Retrofit getRetrofit() {

        // Create the HttpLoggingInterceptor
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);  // Correct usage of setLevel

        // Build OkHttpClient with the interceptor
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)  // No need to cast to Interceptor
                .build();

        // Create Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://randomuser.me/api/")
                .client(okHttpClient)  // Use the OkHttpClient
                .build();

        return retrofit;
    }

    // Method to get the UserService instance
    public static UserService getService() {
        return getRetrofit().create(UserService.class);
    }
}
