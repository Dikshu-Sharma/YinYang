package com.soulharmony;

import static com.soulharmony.model.Constants.DEFAULT_IMAGE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.soulharmony.api.ApiService;
import com.soulharmony.api.RetrofitService;
import com.soulharmony.model.Constants;
import com.soulharmony.model.User;
import com.soulharmony.model.UserFilter;
import com.soulharmony.service.MatchService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    int userIndex = 0;
    int userImageIndex = 0;

    User displayUser;

    String logInUserId;

    List<User> users = new ArrayList<>();

    List<String> currentUserImages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


        Intent intent = getIntent();
        logInUserId = intent.getStringExtra("userId");
        ImageView imageView = findViewById(R.id.photo);
        TextView userNameView = findViewById(R.id.userName);
        TextView userLocationView = findViewById(R.id.userLocation);
        Button logOutButton = findViewById(R.id.logOutButton);
        ImageButton matchButton = findViewById(R.id.matchButtonId1);

        RetrofitService retrofitService = new RetrofitService();
        ApiService apiService = retrofitService.getRetrofit().create(ApiService.class);
        MatchService matchService = new MatchService();

        apiService.getUsersHome(new UserFilter(logInUserId)).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                users = response.body();
                if (users.size() == 0) {
                    users.add(Constants.DUMMY_USER);
                }
                else {
                    displayUser = users.get(0);
                    Map<String, String> imagesUrlWithIndex = (displayUser.getImagesUrlWithIndex() == null) ? new HashMap<>() : displayUser.getImagesUrlWithIndex();
                    currentUserImages = new ArrayList<>(imagesUrlWithIndex.values());
                    if (currentUserImages.size() > 0) {
                        Picasso.get()
                                .load(currentUserImages.get(userImageIndex))
                                .into(imageView);
                    }
                    userNameView.setText(displayUser.getName() + ", " + displayUser.getAge());
                    userLocationView.setText(displayUser.getCity());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("event=UserFetchFailed", "UserId=" + logInUserId);
            }
        });

        logOutButton.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLoggedIn", false);
            editor.putString("userId", null);
            editor.apply();
            Intent intent1 = new Intent(MainActivity.this, SignIn.class);
            startActivity(intent1);
            finish();
        });

        matchButton.setOnClickListener(v -> {
            Intent intent12 = new Intent(MainActivity.this, Match.class);
            intent12.putExtra("userId", logInUserId);
            startActivity(intent12);
            finish();
        });


        findViewById(R.id.rightButton).setOnClickListener(v -> {
            if (userImageIndex < currentUserImages.size() - 1) {
                userImageIndex++;
                Picasso.get()
                        .load(currentUserImages.get(userImageIndex))
                        .into(imageView);
            }
        });

        findViewById(R.id.leftButton).setOnClickListener(v -> {
            if (userImageIndex > 0) {
                userImageIndex--;
                Picasso.get()
                        .load(currentUserImages.get(userImageIndex))
                        .into(imageView);
            }
        });

        findViewById(R.id.likeButton).setOnClickListener(v -> {
            Boolean isMatch = matchService.userLike(logInUserId, displayUser.get_id());
//            if(isMatch){
//                //TODO: SHIFT TO MATCH SCREEN
//            }
            if (userIndex < users.size() - 1) {
                userIndex++;
                displayUser = users.get(userIndex);
                currentUserImages = getCurrentUserImages(displayUser);
                Picasso.get()
                        .load(currentUserImages.get(userImageIndex))
                        .into(imageView);
                userNameView.setText(displayUser.getName() + ", " + displayUser.getAge());
                userLocationView.setText(displayUser.getCity());
            }
        });

        findViewById(R.id.rejectButton).setOnClickListener(v -> {
            matchService.userDislike(logInUserId, displayUser.get_id());
            if (userIndex < users.size() - 1) {
                userIndex++;
                displayUser = users.get(userIndex);
                currentUserImages = getCurrentUserImages(displayUser);
                Picasso.get()
                        .load(currentUserImages.get(userImageIndex))
                        .into(imageView);
                userNameView.setText(displayUser.getName() + ", " + displayUser.getAge());
                userLocationView.setText(displayUser.getCity());
            }
        });
    }

    private List<String> getCurrentUserImages(User currentUser) {
        if(currentUser.getImagesUrlWithIndex().size() != 0){
            return new ArrayList<>(currentUser.getImagesUrlWithIndex().values());
        }
        else{
            List<String> defaultImages = new ArrayList<>();
            defaultImages.add(DEFAULT_IMAGE);
            return defaultImages;
        }
    }
}