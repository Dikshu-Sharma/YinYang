package com.soulharmony;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.soulharmony.adapter.UserAdapter;
import com.soulharmony.api.ApiService;
import com.soulharmony.api.RetrofitService;
import com.soulharmony.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String loginUserId;

//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent(MatchActivity.this, MainActivity.class);
//        intent.putExtra("loginUserId", loginUserId);
//        startActivity(intent);
//        finish();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        loginUserId = intent.getStringExtra("loginUserId");

//        ImageButton homeButtonMatchScreen = findViewById(R.id.homeMatchId1);
//        homeButtonMatchScreen.setOnClickListener(v -> {
//            Intent homeScreenIntent = new Intent(MatchActivity.this, MainActivity.class);
//            homeScreenIntent.putExtra("loginUserId", logInUserId);
//            startActivity(homeScreenIntent);
//            finish();
//        });

        ImageButton logoutButtonMatchScreen = findViewById(R.id.logoutMatchId1);
        logoutButtonMatchScreen.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLoggedIn", false);
            editor.putString("loginUserId", null);
            editor.apply();
            Intent intent1 = new Intent(MatchActivity.this, SignIn.class);
            startActivity(intent1);
            finish();
        });

        recyclerView = findViewById(R.id.mainUserRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        RetrofitService retrofitService = new RetrofitService();
        ApiService apiService = retrofitService.getRetrofit().create(ApiService.class);

        apiService.matches(loginUserId).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.body().size() == 0) {
                    Toast.makeText(MatchActivity.this, "Please come back later", Toast.LENGTH_SHORT).show();
                } else {
                    UserAdapter userAdapter = new UserAdapter(MatchActivity.this, response.body(), loginUserId);
                    recyclerView.setAdapter(userAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("event=UserFetchFailed", "UserId=" + loginUserId);
            }
        });
    }
}