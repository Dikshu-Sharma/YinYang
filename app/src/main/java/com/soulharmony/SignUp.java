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

import com.soulharmony.model.User;

import java.util.ArrayList;
import java.util.UUID;

public class SignUp extends AppCompatActivity {
    String[] genderOptions = {"Gender", "Male", "Female", "Other"};

    String gender = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setSpinnerForGender();

        Button button = findViewById(R.id.signupButtonId1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
//                validateUserData(name, phone, email, city, ageString);

                // TODO : REMOVE LATER ON.
                ageString = "18";

                Integer age = Integer.parseInt(ageString);
                spinnerListener();
                String userId = UUID.randomUUID().toString();
                User user = new User(userId, name, email,
                        phone, gender, age, new ArrayList<>(), 0.0,0.0, city);
                // TODO : SAVE USER DATA TO IN MONGO
                Intent intent = new Intent(SignUp.this, PhotosUpload.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
                finish();
            }
        });
    }

    private void spinnerListener(){
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genderOptions){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                // Set the default name (hint)
                if (position == 0) {
                    TextView textView = view.findViewById(android.R.id.text1);
                    textView.setText("Gender");
                }

                // Customize the text size
                TextView textView = view.findViewById(android.R.id.text1);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                textView.setTypeface(null, Typeface.BOLD);
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);

                // Customize the text size for dropdown items
                TextView textView = view.findViewById(android.R.id.text1);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);
    }
}