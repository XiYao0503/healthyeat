package com.example.tanxueying.healthyeats;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private EditText new_age_text, new_height_text, new_weight_text, new_exercise_text, new_username_text;


    //Get the login user
    FirebaseUser user = auth.getCurrentUser();
    //Get the UID
    String uid = user.getUid();
    //Get reference of User
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference ref = database.getReference().child(uid);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.profile);

        new_weight_text = (EditText) findViewById(R.id.weight1);
        new_height_text = (EditText) findViewById(R.id.heightinput);
        new_exercise_text = (EditText) findViewById(R.id.daily_exercise);
        new_username_text = (EditText) findViewById(R.id.editUserName);
        new_age_text = (EditText) findViewById(R.id.ageinput);
//    }

        // Attach a listener to read the data at our user reference
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);
                //Display current user info
                new_username_text.setText(user.getUsername());
                new_weight_text.setText(user.getWeight());
                new_height_text.setText(user.getHeight());
                new_exercise_text.setText(user.getExercise());
                new_age_text.setText(user.getAge());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("Error:", databaseError.toString());
            }
        });


        //Change the profile
        final Button button_done = findViewById(R.id.button2);
        button_done.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Get the new data
                final String new_weight = new_weight_text.getText().toString().trim();
                final String new_height = new_height_text.getText().toString().trim();
                final String new_exercise = new_exercise_text.getText().toString().trim();
                final String new_username = new_username_text.getText().toString().trim();
                final String new_age = new_age_text.getText().toString().trim();


                if (!android.text.TextUtils.isDigitsOnly(new_age)) {
                    Toast.makeText(ProfileActivity.this, "Invalid Age Input.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!android.text.TextUtils.isDigitsOnly(new_weight)) {
                    Toast.makeText(ProfileActivity.this, "Invalid Weight Input.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!android.text.TextUtils.isDigitsOnly(new_height)) {
                    Toast.makeText(ProfileActivity.this, "Invalid Height Input.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!android.text.TextUtils.isDigitsOnly(new_exercise)) {
                    Toast.makeText(ProfileActivity.this, "Invalid Exercise Input.", Toast.LENGTH_SHORT).show();
                    return;
                }

                final DatabaseReference ref2 = database.getReference().child(uid);
                // Attach a listener to read the data at our posts reference
                ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);

                        //if no user input, keep the old value by default
                        if (!new_username.isEmpty()) {
                            user.setUsername(new_username);
                        }
                        if (!new_age.isEmpty()) {
                            user.setAge(new_age);
                        }
                        if (!new_weight.isEmpty()) {
                            user.setWeight(new_weight);
                        }
                        if (!new_height.isEmpty()) {
                            user.setHeight(new_height);

                        }
                        if (!new_exercise.isEmpty()) {
                            user.setExercise(new_exercise);
                        }
                        user.setCal();
                        ref.setValue(user);
                        Toast.makeText(ProfileActivity.this, "Change Saved!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });
                Intent intent = new Intent(ProfileActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        final ImageButton button_back = (ImageButton) findViewById(R.id.imageButton2);
        button_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        final ImageButton button_logout = (ImageButton) findViewById(R.id.log_out);
        button_logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
