package com.soulharmony;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.soulharmony.adapter.UserAdapter;
import com.soulharmony.api.ApiService;
import com.soulharmony.api.RetrofitService;
import com.soulharmony.model.Constants;
import com.soulharmony.model.User;

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

        Intent intent = getIntent();
        logInUserId = intent.getStringExtra("userId");

//        ImageButton homeButton = findViewById(R.id.homeButtonId4);
//        homeButton.setOnClickListener(v -> {
//            Intent intentMain = new Intent(Match.this, MainActivity.class);
//            intentMain.putExtra("userId", logInUserId);
//            startActivity(intentMain);
//            finish();
//        });

        recyclerView = findViewById(R.id.mainUserRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        RetrofitService retrofitService = new RetrofitService();
        ApiService apiService = retrofitService.getRetrofit().create(ApiService.class);

        apiService.matches(logInUserId).enqueue(new Callback<List<User>>() {
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