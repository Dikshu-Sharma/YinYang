//package com.soulharmony.service;
//
//import android.util.Log;
//import android.widget.Toast;
//
//import com.soulharmony.api.ApiService;
//import com.soulharmony.api.RetrofitService;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class MatchService {
//    RetrofitService retrofitService = new RetrofitService();
//
//    ApiService apiService = retrofitService.getRetrofit().create(ApiService.class);
//    Boolean isMatch = false;
//    public Boolean userLike(String logInUserId, String likedUserId){
//        apiService.userLike(logInUserId, likedUserId).enqueue(new Callback<Boolean>() {
//            @Override
//            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
//                isMatch = response.body() != null && response.body();
//            }
//
//            @Override
//            public void onFailure(Call<Boolean> call, Throwable t) {
//                Log.i("event=dislikeApiFailed", "logInUserId=" + logInUserId + " likedUserId=" + likedUserId);
//            }
//        });
//        if(isMatch){
//            Log.e("event=userMatched", "logInUserId=" + logInUserId + " likedUserId=" + likedUserId);
//        }
//        return isMatch;
//    }
//
//    Boolean isDone = false;
//    public Boolean userDislike(String logInUserId, String dislikedUserId){
//                apiService.userDislike(logInUserId, dislikedUserId).enqueue(new Callback<Boolean>() {
//                    @Override
//                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
//                        isDone = response.body();
//                    }
//
//                    @Override
//                    public void onFailure(Call<Boolean> call, Throwable t) {
//                        Log.e("event=dislikeApiFailed", "logInUserId=" + logInUserId + " dislikedUserId=" + dislikedUserId);
//                    }
//                });
//        return true;
//    }
//}
