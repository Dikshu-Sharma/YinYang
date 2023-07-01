package com.soulharmony.api;

import com.soulharmony.model.ImagesRequest;
import com.soulharmony.model.User;
import com.soulharmony.model.UserFilter;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @POST("/v1/home")
    Call<List<User>> getUsersHome(@Body UserFilter user);

    @GET("/v1/login/{phone}")
    Call<User> getUserByPhone(@Path("phone") String phone);
    @POST("/v1/user")
    Call<User> save(@Body User user);

    @POST("/v1/images")
    Call<Boolean> saveImagesUrl(@Body ImagesRequest imagesRequest);
}