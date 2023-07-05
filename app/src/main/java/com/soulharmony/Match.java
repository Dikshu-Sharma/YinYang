package com.soulharmony;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.soulharmony.adapter.UserAdapter;
import com.soulharmony.api.ApiService;
import com.soulharmony.api.RetrofitService;
import com.soulharmony.model.Constants;
import com.soulharmony.model.User;
import com.soulharmony.model.UserFilter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Match extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;

    private String logInUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        getSupportActionBar().hide();

        ImageButton homeButton = findViewById(R.id.homeId2);
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(Match.this, MainActivity.class);
            intent.putExtra("userId", logInUserId);
            startActivity(intent);
            finish();
        });

        Intent intent = getIntent();
        String logInUserId = intent.getStringExtra("userId");

        recyclerView = findViewById(R.id.mainUserRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        RetrofitService retrofitService = new RetrofitService();
        ApiService apiService = retrofitService.getRetrofit().create(ApiService.class);

        apiService.getUsersHome(new UserFilter("953972d5-462e-42fa-ba01-31cecb654098")).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.body().size() == 0){
                    List<User> dummyUser = new ArrayList<>();
                    dummyUser.add(Constants.DUMMY_USER);
                    userAdapter = new UserAdapter(dummyUser);
                }else {
                    userAdapter = new UserAdapter(response.body());
                }
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("event=UserFetchFailed", "UserId=" + logInUserId);
            }
        });
    }
}