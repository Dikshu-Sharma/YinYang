package com.soulharmony;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.soulharmony.api.ApiService;
import com.soulharmony.api.RetrofitService;
import com.soulharmony.model.User;
import com.soulharmony.model.UserFilter;
import com.squareup.picasso.Picasso;
//import com.soulharmony.service.S3Helper;

import org.checkerframework.checker.units.qual.A;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    int userIndex = 0;
    int userImageIndex = 0;

    User currentUser;

    String userId;

    RetrofitService retrofitService;

    List<User> users = new ArrayList<>();

    List<String> currentUserImages = new ArrayList<>();

    String DEFAULT_IMAGE = "https://firebasestorage.googleapis.com/v0/b/yinyang-9f595.appspot.com/o/userImages%2F66eec5ae-7de1-48b5-9463-8d761ad27ca3*_*1?alt=media&token=a12b216e-3c78-41a6-a9c1-05f1eadfd37f";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        ImageView imageView = findViewById(R.id.photo);
        TextView userNameView = findViewById(R.id.userName);
        TextView userLocationView = findViewById(R.id.userLocation);
        Button logOutButton = findViewById(R.id.logOutButton);

        retrofitService = new RetrofitService();
        RetrofitService retrofitService = new RetrofitService();
        ApiService apiService = retrofitService.getRetrofit().create(ApiService.class);

        apiService.getUsersHome(new UserFilter(userId)).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                users = response.body();
                if (users.size() == 0) {
                    users.add(new User(null, null, null, null, null, null, null, null, null, null, new HashMap<>(), new ArrayList<>()));
                    Toast.makeText(MainActivity.this, "No Users Found", Toast.LENGTH_LONG).show();
                }
                currentUser = users.get(0);
                Map<String, String> imagesUrlWithIndex = (currentUser.getImagesUrlWithIndex() == null)?new HashMap<>():currentUser.getImagesUrlWithIndex();
                currentUserImages = new ArrayList<>(imagesUrlWithIndex.values());
                if(currentUserImages.size()>0) {
                    Picasso.get()
                            .load(currentUserImages.get(userImageIndex))
                            .into(imageView);
                }
                userNameView.setText(currentUser.getName() + ", " + currentUser.getAge());
                userLocationView.setText(currentUser.getCity());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Users Fetch Failed", Toast.LENGTH_LONG).show();
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
            if (userIndex < users.size() - 1) {
                userIndex++;
                currentUser = users.get(userIndex);
                Picasso.get()
                        .load(currentUserImages.get(0))
                        .into(imageView);
                userNameView.setText(currentUser.getName() + ", " + currentUser.getAge());
                userLocationView.setText(currentUser.getCity());
            }
        });

        findViewById(R.id.rejectButton).setOnClickListener(v -> {
            if (userIndex > 0) {
                userIndex--;
                userImageIndex = 0;
                currentUser = users.get(userIndex);
                Picasso.get()
                        .load(currentUserImages.get(0))
                        .into(imageView);
                userNameView.setText(currentUser.getName() + ", " + currentUser.getAge());
                userLocationView.setText(currentUser.getCity());
            }
        });
    }
}