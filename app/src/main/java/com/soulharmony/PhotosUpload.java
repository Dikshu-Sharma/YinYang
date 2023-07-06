package com.soulharmony;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.soulharmony.api.ApiService;
import com.soulharmony.api.RetrofitService;
import com.soulharmony.model.ImagesRequest;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotosUpload extends AppCompatActivity {
    Map<String, String> imagesUrlWithNumber = new HashMap<>();
    FirebaseStorage firebaseStorage;
    Uri imageUri;

    String userId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos_upload);
        getSupportActionBar().hide();


        Intent intent = getIntent();
        userId = intent.getStringExtra("loginUserId");
        Log.i("event=photosUploading", "userId=" + userId);

        firebaseStorage = FirebaseStorage.getInstance();
        ImageView imageView = findViewById(R.id.imageView);
        ImageView imageView1 = findViewById(R.id.imageView1);
        ImageView imageView2 = findViewById(R.id.imageView2);
        ImageView imageView3 = findViewById(R.id.imageView3);
        ImageView imageView4 = findViewById(R.id.imageView4);
        imageView.setOnClickListener(v -> {
            openGallery(0);
        });
        imageView1.setOnClickListener(v -> {
            openGallery(1);
        });
        imageView2.setOnClickListener(v -> {
            openGallery(2);
        });
        imageView3.setOnClickListener(v -> {
            openGallery(3);
        });
        imageView4.setOnClickListener(v -> {
            openGallery(4);
        });

        // TODO : SAVE IMAGES URL MAP INTO MONGO INSIDE USER
        Button submitButton = findViewById(R.id.nextButtonId);
        submitButton.setOnClickListener(v -> {
            RetrofitService retrofitService = new RetrofitService();
            ApiService apiService = retrofitService.getRetrofit().create(ApiService.class);
            apiService.saveImagesUrl(new ImagesRequest(userId, imagesUrlWithNumber)).enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.body() != null && response.body()) {
                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("isLoggedIn", true);
                        editor.putString("loginUserId", userId);
                        editor.apply();
//                        Toast.makeText(PhotosUpload.this, "Images Uploaded", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
//                    Toast.makeText(PhotosUpload.this, "Images Upload Failed", Toast.LENGTH_LONG).show();
                }
            });
            Intent intent1 = new Intent(PhotosUpload.this, MainActivity.class);
            intent1.putExtra("loginUserId", userId);
            startActivity(intent1);
            finish();
        });
    }

    private void openGallery(Integer requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            ImageView imageView = findViewById(R.id.imageView);
            imageView.setImageURI(imageUri);
            uploadImageToFireBase(imageUri, requestCode);
        } else if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            ImageView imageView = findViewById(R.id.imageView1);
            imageView.setImageURI(imageUri);
            uploadImageToFireBase(imageUri, requestCode);
        } else if (requestCode == 2 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            ImageView imageView = findViewById(R.id.imageView2);
            imageView.setImageURI(imageUri);
            uploadImageToFireBase(imageUri, requestCode);
        } else if (requestCode == 3 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            ImageView imageView = findViewById(R.id.imageView3);
            imageView.setImageURI(imageUri);
            uploadImageToFireBase(imageUri, requestCode);
        } else if (requestCode == 4 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            ImageView imageView = findViewById(R.id.imageView4);
            imageView.setImageURI(imageUri);
            uploadImageToFireBase(imageUri, requestCode);
        }
    }


    private void uploadImageToFireBase(Uri uri, Integer suffix) {
        StorageReference storageReference = firebaseStorage.getReference().child("userImages").child(userId + "*_*" + suffix.toString());
        storageReference.putFile(uri).addOnCompleteListener(task13 -> {
            if (task13.isSuccessful()) {
                storageReference.getDownloadUrl().addOnSuccessListener(downloadUrl -> {
                    Toast.makeText(PhotosUpload.this, "uploaded", Toast.LENGTH_SHORT).show();
                    String imageUrl = downloadUrl.toString();
                    imagesUrlWithNumber.put(suffix.toString(), imageUrl);
                });
            }
        });
    }


}