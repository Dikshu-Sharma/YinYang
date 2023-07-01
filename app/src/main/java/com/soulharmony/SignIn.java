package com.soulharmony;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.soulharmony.api.ApiService;
import com.soulharmony.api.RetrofitService;
import com.soulharmony.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignIn extends AppCompatActivity {

    RetrofitService retrofitService;

    String userId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        retrofitService = new RetrofitService();
        RetrofitService retrofitService = new RetrofitService();
        ApiService apiService = retrofitService.getRetrofit().create(ApiService.class);

        Button signInSubmitButton = findViewById(R.id.logbutton);
        EditText phoneNumber = findViewById(R.id.editTexLogEmail);
        EditText password = findViewById(R.id.editTextLogPassword);
        Button signUpButton = findViewById(R.id.signUpButtonLogInScreen);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        userId = sharedPreferences.getString("userId", null);
        if(isLoggedIn){
            Intent intent = new Intent(SignIn.this, MainActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
            finish();
        }

        signInSubmitButton.setOnClickListener(v -> apiService.getUserByPhone(phoneNumber.toString()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.body().get_id() != null){
                    userId = response.body().get_id();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.putString("userId", userId);
                    editor.apply();
                    Intent intent = new Intent(SignIn.this, MainActivity.class);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(SignIn.this, "Wrong Credentials", Toast.LENGTH_LONG).show();
            }
        }));

        signUpButton.setOnClickListener(v -> {
            Intent intent = new Intent(SignIn.this, SignUp.class);
            startActivity(intent);
            finish();
        });
    }
}