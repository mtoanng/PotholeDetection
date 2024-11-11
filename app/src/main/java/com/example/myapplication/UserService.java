package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

    public interface UserService {
        @POST("user/login")
        Call<LoginRespond> loginRespon(@Body LoginRequest loginRequest);

        @POST("user/register")
        Call<RegisterRespond> registerRespon(@Body RegisterRequest registerRequest);
    }

