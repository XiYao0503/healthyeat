package com.example.tanxueying.healthyeats;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class Register2Activity extends AppCompatActivity {
    private FirebaseAuth auth;

    private EditText height_text, weight_text, exercise_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.register2);


        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        height_text = (EditText)findViewById(R.id.heightinput);
        weight_text = (EditText)findViewById(R.id.weightinput);
        exercise_text = (EditText)findViewById(R.id.exerciseinput);

        //Get the data from the prev page
        final String username = getIntent().getExtras().getString("username");
        final String email = getIntent().getExtras().getString("email");
        final String password = getIntent().getExtras().getString("password");
        final String gender = getIntent().getExtras().getString("gender");
        final String age = getIntent().getExtras().getString("age");




        final ImageButton button_back = (ImageButton) findViewById(R.id.back_btn);
        button_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Register2Activity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        final Button button_done = findViewById(R.id.register);
        button_done.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Get the data from the curr page
                final String height = height_text.getText().toString().trim();
                final String weight = weight_text.getText().toString().trim();
                final String exercise = exercise_text.getText().toString().trim();

                if (!android.text.TextUtils.isDigitsOnly(weight)) {
                    Toast.makeText(Register2Activity.this, "Invalid Weight Input.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!android.text.TextUtils.isDigitsOnly(height)) {
                    Toast.makeText(Register2Activity.this, "Invalid Height Input.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!android.text.TextUtils.isDigitsOnly(exercise)) {
                    Toast.makeText(Register2Activity.this, "Invalid Exercise Input.", Toast.LENGTH_SHORT).show();
                    return;
                }

                //create user using email and psw
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Register2Activity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
//

                                if (!task.isSuccessful()) {
                                    Toast.makeText(Register2Activity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {

                                    //get the UID as the key of user table
                                    FirebaseUser user = task.getResult().getUser();
                                    final String uid = user.getUid();
                                    //store other info into database
                                    final User newUser = new User(username, gender, age, height, weight, exercise);
                                    //calculate the goal cal
                                    newUser.setCal();
                                    //save to database
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    final DatabaseReference ref = database.getReference();

                                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            ref.child(uid).setValue(newUser);
                                            Toast.makeText(Register2Activity.this, "Register Successfully.", Toast.LENGTH_SHORT).show();
                                        }
                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }

                                    });

                                    //go to login
                                    startActivity(new Intent(Register2Activity.this, LoginActivity.class));
                                    finish();
                                }
                            }
                        });

            }
        });
    }
}
