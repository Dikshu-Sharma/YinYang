package com.soulharmony.api;

import com.soulharmony.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
//    @GET("/api/v1/")
//    Call<List<User>> get();

    @POST("/api/v1/save")
    Call<User> save(@Body User user);
}