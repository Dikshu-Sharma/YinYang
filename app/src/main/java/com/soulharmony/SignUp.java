package com.soulharmony;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.soulharmony.api.ApiService;
import com.soulharmony.api.RetrofitService;
import com.soulharmony.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {
    String[] genderOptions = {"Gender", "Male", "Female", "Other"};

    String gender = null;

    RetrofitService retrofitService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        retrofitService = new RetrofitService();
        RetrofitService retrofitService = new RetrofitService();
        ApiService apiService = retrofitService.getRetrofit().create(ApiService.class);

        setSpinnerForGender();

        Button button = findViewById(R.id.signupButtonId1);

        button.setOnClickListener(v -> {
            EditText nameEditText = findViewById(R.id.nameId);
            EditText phoneEditText = findViewById(R.id.phoneNumberId);
            EditText emailEditText = findViewById(R.id.emailId);
            EditText ageEditText = findViewById(R.id.ageId);
            EditText cityEditText = findViewById(R.id.cityId);

            String name = nameEditText.getText().toString();
            String phone = phoneEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String city = cityEditText.getText().toString();
            String ageString = ageEditText.getText().toString();
            if(validateUserData(name, phone, email, city, ageString)){
                Integer age = null;
                try {
                        age = Integer.parseInt(ageString);
                } catch (Exception ignored){

                }
                spinnerListener();
                String userId = UUID.randomUUID().toString();
                List<String> usersToExclude = new ArrayList<>();
                usersToExclude.add(userId);
                User user = new User(userId, name, email,
                        phone, gender, age, 0.0, 0.0, city, new HashMap<>(), usersToExclude);
                apiService.save(user).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Toast.makeText(SignUp.this, "User Saved", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SignUp.this, PhotosUpload.class);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(SignUp.this, "Failed", Toast.LENGTH_LONG).show();
                    }
                });
            }



        });

        TextView logInButton = findViewById(R.id.loginButtonId1);
        logInButton.setOnClickListener(v -> {
            Intent intent = new Intent(SignUp.this, SignIn.class);
            startActivity(intent);
            finish();
        });
    }

    private Boolean validateUserData(String name, String phone, String email, String city, String ageString) {
        // TODO : add validation
        return true;
    }

    private void spinnerListener() {
        Spinner spinner = findViewById(R.id.spinnerGender);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = parent.getItemAtPosition(position).toString();
                Toast.makeText(SignUp.this, " Select Gender", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(SignUp.this, "Please Select Gender", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setSpinnerForGender() {
        Spinner spinnerGender = findViewById(R.id.spinnerGender);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genderOptions) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                if (position == 0) {
                    TextView textView = view.findViewById(android.R.id.text1);
                    textView.setText("Gender");
                }
                TextView textView = view.findViewById(android.R.id.text1);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                textView.setTypeface(null, Typeface.BOLD);
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = view.findViewById(android.R.id.text1);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);
    }
}