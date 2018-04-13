package com.example.tanxueying.healthyeats;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText username, email, inputPassword, confrimPassword, age;
    private Spinner gender;
    private ImageButton button_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        gender = (Spinner) findViewById(R.id.spinner3);
        age = (EditText) findViewById(R.id.birthday);
        inputPassword = (EditText) findViewById(R.id.password);
        confrimPassword = (EditText) findViewById(R.id.confirm);


        button_back = (ImageButton) findViewById(R.id.imageButton2);
        button_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        final Button button_done = findViewById(R.id.next);
        button_done.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //check input whether invalid
                if (TextUtils.isEmpty(username.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "Please Enter Username", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(email.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "Please Enter Email Address", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(inputPassword.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "Please Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(confrimPassword.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "Please Confrim Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (inputPassword.getText().toString().trim().length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, please enter minimum 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                if ( !inputPassword.getText().toString().trim().equals(confrimPassword.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "Password is not the same as the confirm password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(gender.getSelectedItem().toString().trim()) || gender.getSelectedItem().toString().trim().equals("Gender")) {

                    Toast.makeText(getApplicationContext(), "Please Select A Gender", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(age.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "Please Enter Your Age", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!android.text.TextUtils.isDigitsOnly(age.getText().toString().trim())) {
                    Toast.makeText(RegisterActivity.this, "Invalid Age Input.", Toast.LENGTH_SHORT).show();
                    return;
                }


                Intent intent = new Intent(RegisterActivity.this, Register2Activity.class);

                // transfer the data to the next page
                intent.putExtra("username", username.getText().toString().trim());
                intent.putExtra("email", email.getText().toString().trim());
                intent.putExtra("password", inputPassword.getText().toString().trim());
                intent.putExtra("gender", gender.getSelectedItem().toString().trim());
                intent.putExtra("age", age.getText().toString().trim());

                startActivity(intent);
            }
        });
    }
}
