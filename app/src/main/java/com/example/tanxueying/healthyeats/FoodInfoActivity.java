package com.example.tanxueying.healthyeats;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class FoodInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_info);

        final ImageButton button_done = (ImageButton)findViewById(R.id.imageButton5);
        button_done.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(FoodInfoActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        final ImageButton button_back = (ImageButton)findViewById(R.id.imageButton2);
        button_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(FoodInfoActivity.this, UserInputActivity.class);
                startActivity(intent);
            }
        });
    }
}
