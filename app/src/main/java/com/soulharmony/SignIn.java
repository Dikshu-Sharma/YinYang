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
import com.soulharmony.util.PasswordUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignIn extends AppCompatActivity {

    String userId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().hide();

        RetrofitService retrofitService = new RetrofitService();
        ApiService apiService = retrofitService.getRetrofit().create(ApiService.class);

        Button signInSubmitButton = findViewById(R.id.logbutton);
        EditText phoneNumberEditText = findViewById(R.id.editTexLogEmail);
        EditText passwordEditText = findViewById(R.id.editTextLogPassword);
        Button signUpButton = findViewById(R.id.signUpButtonLogInScreen);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        userId = sharedPreferences.getString("userId", null);
        if (isLoggedIn) {
            Intent intent = new Intent(SignIn.this, MainActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
            finish();
        }

        signInSubmitButton.setOnClickListener(v -> {

            String phone = phoneNumberEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            if(phone.isEmpty() || password.isEmpty()){
                Toast.makeText(SignIn.this, "Please Enter Your Credentials", Toast.LENGTH_SHORT).show();
            }
            else if (phone.length() != 10) {
                Toast.makeText(SignIn.this, "Phone Number Length Isn't 10", Toast.LENGTH_SHORT).show();
            }
            else if(password.length() < 4){
                Toast.makeText(SignIn.this, "Password Length Should Be At Least 4", Toast.LENGTH_SHORT).show();
            }
            else {

                apiService.getUserByPhone(phone).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.body().get_id() != null) {
                            if (!PasswordUtils.verifyPassword(password, response.body().getPassword())) {
                                Toast.makeText(SignIn.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                            }
                            else{
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
                        } else {
                            Toast.makeText(SignIn.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(SignIn.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        signUpButton.setOnClickListener(v -> {
            Intent intent = new Intent(SignIn.this, SignUp.class);
            startActivity(intent);
            finish();
        });
    }
}