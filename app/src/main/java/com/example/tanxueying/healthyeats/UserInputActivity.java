package com.example.tanxueying.healthyeats;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class UserInputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinput);

        final ImageButton button_back = (ImageButton)findViewById(R.id.back_btn);
        button_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(UserInputActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        final ImageButton button_done = (ImageButton)findViewById(R.id.done);
        button_done.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(UserInputActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        final TextView view1 = (TextView) findViewById(R.id.textView6);
        view1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(UserInputActivity.this, FoodInfoActivity.class);
                startActivity(intent);
            }
        });

        final TextView view2 = (TextView) findViewById(R.id.textView7);
        view2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(UserInputActivity.this, FoodInfoActivity.class);
                startActivity(intent);
            }
        });
    }
}
