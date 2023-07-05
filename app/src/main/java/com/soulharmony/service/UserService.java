//package com.soulharmony.service;
//
//import android.util.Log;
//import android.widget.Toast;
//
//import com.soulharmony.MainActivity;
//import com.soulharmony.api.ApiService;
//import com.soulharmony.api.RetrofitService;
//import com.soulharmony.model.Constants;
//import com.soulharmony.model.User;
//import com.soulharmony.model.UserFilter;
//import com.squareup.picasso.Picasso;
//
//import org.checkerframework.checker.units.qual.A;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class UserService {
//
//    RetrofitService retrofitService = new RetrofitService();
//    ApiService apiService = retrofitService.getRetrofit().create(ApiService.class);
//
//    public List<User> fetchUsers(String logInUserId) {
//
//        List<User> users = new ArrayList<>();
//        users.add(Constants.DUMMY_USER);
//
//        apiService.getUsersHome(new UserFilter(logInUserId)).enqueue(new Callback<List<User>>() {
//            @Override
//            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
//                if (response.body().size() != 0) {
//                    users.clear();
//                    users.addAll(response.body());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<User>> call, Throwable t) {
//                Log.e("event=UserFetchFailed", "UserId=" + logInUserId);
//            }
//        });
//        return users;
//    }
//}
