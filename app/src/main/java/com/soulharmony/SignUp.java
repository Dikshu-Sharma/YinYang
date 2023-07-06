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
import com.soulharmony.util.EmailValidator;
import com.soulharmony.util.PasswordUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {
    String[] genderOptions = {"Gender", "Male", "Female", "Other"};
    Integer genderIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();

        RetrofitService retrofitService = new RetrofitService();
        ApiService apiService = retrofitService.getRetrofit().create(ApiService.class);

        setSpinnerForGender();
        spinnerListener();
        Button signUpNextButton = findViewById(R.id.signupButtonId1);
        signUpNextButton.setOnClickListener(v -> {
            EditText nameEditText = findViewById(R.id.nameId);
            EditText phoneEditText = findViewById(R.id.phoneNumberId);
            EditText emailEditText = findViewById(R.id.emailId);
            EditText ageEditText = findViewById(R.id.ageId);
            EditText cityEditText = findViewById(R.id.cityId);
            EditText passwordEditText = findViewById(R.id.passwordId1);
            EditText confirmPasswordEditText = findViewById(R.id.confirmPasswordId1);

            String name = nameEditText.getText().toString();
            String phone = phoneEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String city = cityEditText.getText().toString();
            String ageString = ageEditText.getText().toString();
            if(validateUserData(name, phone, password, confirmPassword, email, city, ageString, genderIndex)){
                Integer age = Integer.parseInt(ageString);
                String userId = UUID.randomUUID().toString();
                List<String> usersToExclude = new ArrayList<>();
                usersToExclude.add(userId);
                String encryptedPassword = PasswordUtils.hashPassword(password);
                User user = new User(userId, name, email, phone,
                        encryptedPassword, genderOptions[genderIndex], age, 0.0, 0.0, city, new HashMap<>(), usersToExclude);
                apiService.save(user).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Toast.makeText(SignUp.this, "User Saved", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SignUp.this, PhotosUpload.class);
                        intent.putExtra("loginUserId", userId);
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

    private Boolean validateUserData(String name, String phone, String password, String confirmPassword,
                                     String email, String city, String ageString, Integer genderIndex) {
        // TODO : add validation
        if(name.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || email.isEmpty() ||
        city.isEmpty() || ageString.isEmpty()){
            Toast.makeText(SignUp.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(phone.length() != 10){
            Toast.makeText(SignUp.this, "Phone Number Length Isn't 10", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(password.length() < 4){
            Toast.makeText(SignUp.this, "Password Length Should Be At Least 4", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!password.equals(confirmPassword)){
            Toast.makeText(SignUp.this, "Password Not Matching", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!EmailValidator.isValidEmail(email)){
            Toast.makeText(SignUp.this, "Invalid Email", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(genderIndex == 0){
            Toast.makeText(SignUp.this, "Please Select Gender", Toast.LENGTH_SHORT).show();
        }
        else{
            try {
                int age = Integer.parseInt(ageString);
                if(age < 16 || age > 100){
                    Toast.makeText(SignUp.this, "Age Should Be Between 16 - 100", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } catch (Exception ignored){
                Toast.makeText(SignUp.this, "Age Should Be Number", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private void spinnerListener() {
        Spinner spinner = findViewById(R.id.spinnerGender);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                parent.getItemAtPosition(position).toString();
                genderIndex = position;
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