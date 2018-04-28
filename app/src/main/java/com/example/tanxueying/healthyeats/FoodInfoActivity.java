package com.example.tanxueying.healthyeats;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.*;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FoodInfoActivity extends AppCompatActivity {
    private static final String TAG = FoodInfoActivity.class.getSimpleName();
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_info);
        final String foodLabel = getIntent().getExtras().getString("foodLabel");
        final String foodURI = getIntent().getExtras().getString("foodURI");
        final String measureURI = getIntent().getExtras().getString("measureURI");
        final String measureLabel = getIntent().getExtras().getString("measureLabel");


        showInfomation(foodURI, measureURI);

//        final String yield_string = getIntent().getExtras().getString("yield");

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



    private void showInfomation(String foodURI, String measureURI) {
        String url = Constant.POST_URL;
        Log.d(TAG, url);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading....");
        dialog.show();

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("yield", 1);
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonIngredient = new JSONObject();

            jsonIngredient.put("quantity", 1);
            jsonIngredient.put("measureURI", measureURI);
            jsonIngredient.put("foodURI", foodURI);
            jsonArray.put(jsonIngredient);

            jsonObject.put("ingredients", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
//        System.out.println(gson.toJson(jsonObject));
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject,  new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                parseJsonData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("can not parse the url!!");
                Toast.makeText(getApplicationContext(), "can not parse the url!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(FoodInfoActivity.this);
        rQueue.add(request);

    }




    private void parseJsonData(JSONObject object) {


        dialog.dismiss();
    }

    public static String readFile(String filename) {
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            result = sb.toString();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }



}
