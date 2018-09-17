package com.example.ekadarmaputra.jakmall_androidproject.Retrofit.Api;

import com.example.ekadarmaputra.jakmall_androidproject.Model.JSONResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("random/{numJokes}")
    Call<JSONResponse> getJokesJSON(@Path("numJokes") int numJokes);
}
