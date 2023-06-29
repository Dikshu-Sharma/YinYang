package com.soulharmony;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class PhotosUpload extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos_upload);

        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");
        Log.i("event=photosUploading", "userId=" + userId);
    }
}