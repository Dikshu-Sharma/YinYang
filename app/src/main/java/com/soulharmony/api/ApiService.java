package com.soulharmony.api;

import com.soulharmony.model.ImagesRequest;
import com.soulharmony.model.User;
import com.soulharmony.model.UserFilter;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST("/v1/home")
    Call<List<User>> getUsersHome(@Body UserFilter user);

    @GET("/v1/phone/{phone}")
    Call<User> getUserByPhone(@Path("phone") String phone);
    @POST("/v1/user")
    Call<User> save(@Body User user);

    @POST("/v1/images")
    Call<Boolean> saveImagesUrl(@Body ImagesRequest imagesRequest);

    @PATCH("/v1/like")
    Call<Boolean> userLike(@Query("logInUserId") String logInUserId, @Query("likedUserId") String likedUserId);

    @PATCH("/v1/dislike")
    Call<Boolean> userDislike(@Query("logInUserId") String logInUserId, @Query("dislikedUserId") String dislikedUserId);

    @GET("/v1/match")
    Call<List<User>> matches(@Query("logInUserId") String logInUserId);
}