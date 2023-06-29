package com.soulharmony;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.soulharmony.model.User;
//import com.soulharmony.service.S3Helper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    int userIndex = 0;
    int userImageIndex = 0;

    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<User> users = new ArrayList<>();
        List<String> user1Images = new ArrayList<>();
        user1Images.add(R.drawable.sample + "");
        user1Images.add(R.drawable.sample2 + "");
        user1Images.add(R.drawable.sample + "");
//        File downloadedFile = S3Helper.downloadImage("sample.jpeg");
//        Bitmap bitmap = BitmapFactory.decodeFile(downloadedFile.getAbsolutePath());
        ImageView imageView = findViewById(R.id.photo);
//        imageView.setImageBitmap(bitmap);

        List<String> user2Images = new ArrayList<>();
        user2Images.add(R.drawable.sample2 + "");
        user2Images.add(R.drawable.sample2 + "");
        user2Images.add(R.drawable.sample + "");
        User user1 = new User("n1","user1", "em","ph", "male", 23, user1Images, 23.0, 23.0, "London");
        User user2 = new User("n2","user2", "gm","ph", "male", 23, user2Images, 23.0, 23.0, "Bengaluru");
        users.add(user1);
        users.add(user2);

        currentUser = users.get(0);

        List<String> currentUserImages = currentUser.getImages();

        imageView.setImageResource(Integer.parseInt(currentUserImages.get(userImageIndex)));
        TextView userName = findViewById(R.id.userName);
        userName.setText(currentUser.getName());
        TextView userLocation = findViewById(R.id.userLocation);
        userLocation.setText(currentUser.getCity());


        findViewById(R.id.rightButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userImageIndex < currentUserImages.size()-1){
                    userImageIndex++;
                    imageView.setImageResource(Integer.parseInt(currentUserImages.get(userImageIndex)));
                }
            }
        });

        findViewById(R.id.leftButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userImageIndex > 0){
                    userImageIndex--;
                    imageView.setImageResource(Integer.parseInt(currentUserImages.get(userImageIndex)));
                }
            }
        });

        findViewById(R.id.likeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userIndex < users.size()-1){
                    userIndex++;
                    userImageIndex = 0;
                    currentUser = users.get(userIndex);
                    imageView.setImageResource(Integer.parseInt(currentUser.getImages().get(userImageIndex)));
                    TextView userName = findViewById(R.id.userName);
                    userName.setText(currentUser.getName());
                    TextView userLocation = findViewById(R.id.userLocation);
                    userLocation.setText(currentUser.getCity());
                }
            }
        });

        findViewById(R.id.rejectButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userIndex > 0){
                    userIndex--;
                    userImageIndex = 0;
                    currentUser = users.get(userIndex);
                    imageView.setImageResource(Integer.parseInt(currentUser.getImages().get(userImageIndex)));
                    TextView userName = findViewById(R.id.userName);
                    userName.setText(currentUser.getName());
                    TextView userLocation = findViewById(R.id.userLocation);
                    userLocation.setText(currentUser.getCity());
                }
            }
        });



    }
}