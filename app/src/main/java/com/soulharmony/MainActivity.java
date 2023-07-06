package com.soulharmony;

import static com.soulharmony.model.Constants.DEFAULT_IMAGE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.soulharmony.api.ApiService;
import com.soulharmony.api.RetrofitService;
import com.soulharmony.model.User;
import com.soulharmony.model.UserFilter;
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

    String loginUserId;

    List<User> users = new ArrayList<>();

    List<String> currentUserImages = new ArrayList<>();

    Boolean isMatch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        loginUserId = intent.getStringExtra("loginUserId");
        ImageView imageView = findViewById(R.id.photo);
        TextView userNameView = findViewById(R.id.userName);
        TextView userLocationView = findViewById(R.id.userLocation);
        ImageButton logOutButton = findViewById(R.id.logButtonId3);
        ImageButton matchButton = findViewById(R.id.matchButtonId1);

        RetrofitService retrofitService = new RetrofitService();
        ApiService apiService = retrofitService.getRetrofit().create(ApiService.class);

        apiService.getUsersHome(new UserFilter(loginUserId)).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                users = response.body() == null ? new ArrayList<>() : response.body();
                if (users.size() == 0) {
                    Intent intentMatchScreen = new Intent(MainActivity.this, MatchActivity.class);
                    intentMatchScreen.putExtra("loginUserId", loginUserId);
                    startActivity(intentMatchScreen);
                    finish();
                } else {
                    displayUser = users.get(0);
                    Map<String, String> imagesUrlWithIndex = (displayUser.getImagesUrlWithIndex() == null) ?
                            new HashMap<>() : displayUser.getImagesUrlWithIndex();
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
                Toast.makeText(MainActivity.this, "API Failed", Toast.LENGTH_SHORT).show();
                Log.e("event=UserFetchFailed", "UserId=" + loginUserId);
            }
        });

        logOutButton.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLoggedIn", false);
            editor.putString("loginUserId", null);
            editor.apply();
            Intent intent1 = new Intent(MainActivity.this, SignIn.class);
            startActivity(intent1);
            finish();
        });

        matchButton.setOnClickListener(v -> {
            Intent intent12 = new Intent(MainActivity.this, MatchActivity.class);
            intent12.putExtra("loginUserId", loginUserId);
            startActivity(intent12);
            finish();
        });

        Button rightImageButton = findViewById(R.id.rightButton);
        rightImageButton.setOnClickListener(v -> {
            if (userImageIndex < currentUserImages.size() - 1) {
                userImageIndex++;
                Picasso.get()
                        .load(currentUserImages.get(userImageIndex))
                        .into(imageView);
            }
        });
        Button leftImageButton = findViewById(R.id.leftButton);
        leftImageButton.setOnClickListener(v -> {
            if (userImageIndex > 0) {
                userImageIndex--;
                Picasso.get()
                        .load(currentUserImages.get(userImageIndex))
                        .into(imageView);
            }
        });

        ImageButton likeButton = findViewById(R.id.likeButtonId1);
        likeButton.setOnClickListener(v -> {
            if(displayUser.get_id() != null) {
                apiService.userLike(loginUserId, displayUser.get_id()).enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        isMatch = response.body() != null && response.body();
                        if(isMatch){
                            Log.e("event=userMatched", "logInUserId=" + loginUserId + " likedUserId=" + displayUser.get_id());
                            Intent intentMatchScreen = new Intent(MainActivity.this, MatchActivity.class);
                            intentMatchScreen.putExtra("loginUserId", loginUserId);
                            startActivity(intentMatchScreen);
                            finish();
                        }
                        else if(userIndex < users.size() - 1) {
                            userIndex++;
                            displayUser = users.get(userIndex);
                            currentUserImages = getCurrentUserImages(displayUser);
                            userImageIndex=0;
                            Picasso.get()
                                    .load(currentUserImages.get(userImageIndex))
                                    .into(imageView);
                            userNameView.setText(displayUser.getName() + ", " + displayUser.getAge());
                            userLocationView.setText(displayUser.getCity());
                        }
                        else{
                            Intent intentMatchScreen = new Intent(MainActivity.this, MatchActivity.class);
                            intentMatchScreen.putExtra("loginUserId", loginUserId);
                            startActivity(intentMatchScreen);
                            finish();
                        }
                    }
                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Log.i("event=dislikeApiFailed", "logInUserId=" + loginUserId + " likedUserId=" + displayUser.get_id());
                    }
                });
            }
        });

        ImageButton rejectButton = findViewById(R.id.rejectButtonId1);
        rejectButton.setOnClickListener(v -> {
            apiService.userDislike(loginUserId, displayUser.get_id()).enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    Boolean isDone = response.body();
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Log.e("event=dislikeApiFailed", "logInUserId=" + loginUserId + " dislikedUserId=" + displayUser.get_id());
                }
            });
            if (userIndex < users.size() - 1) {
                userIndex++;
                displayUser = users.get(userIndex);
                currentUserImages = getCurrentUserImages(displayUser);
                userImageIndex = 0;
                Picasso.get()
                        .load(currentUserImages.get(userImageIndex))
                        .into(imageView);
                userNameView.setText(displayUser.getName() + ", " + displayUser.getAge());
                userLocationView.setText(displayUser.getCity());
            }
            else{
                Intent intentMatchScreen = new Intent(MainActivity.this, MatchActivity.class);
                intentMatchScreen.putExtra("loginUserId", loginUserId);
                startActivity(intent);
                finish();
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