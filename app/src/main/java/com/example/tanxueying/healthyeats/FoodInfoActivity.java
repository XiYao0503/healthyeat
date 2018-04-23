package com.example.tanxueying.healthyeats;

import android.app.ProgressDialog;
import android.content.Intent;
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
import org.json.JSONObject;

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

        Food_nutrition food_nutriton = new Food_nutrition(1, new Ingredient(foodURI, measureURI));
        showInfomation(food_nutriton);

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



    private void showInfomation( Food_nutrition food_nutrition) {
        String url = Constant.POST_URL;
        Log.d(TAG, url);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading....");
        dialog.show();
        Gson gson = new Gson();
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject = new JSONObject(gson.toJson(food_nutrition));
        } catch (JSONException e) {
            Log.d(TAG, "convert object to json error");
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject,  new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                parseJsonData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "can not parse the url!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(FoodInfoActivity.this);
        rQueue.add(request);

    }

    private void parseJsonData(JSONObject object) {
        try {
            float calories = (float)object.get("calories");
            int totalWeight = object.getInt("totalWeight");
            Enum[] dietLabels = object;
            final List<JSONObject> al = new ArrayList<>();
            List<String> list = new ArrayList<>();

            Set<String> set = new HashSet<>();
            for(int i = 0; i < foodArray.length(); ++i) {
                JSONObject cur = foodArray.getJSONObject(i);
                String s = cur.getJSONObject("food").get("label").toString();
                if (set.contains(s)) {
                    continue;
                }
                al.add(cur);
                set.add(s);
                list.add(s);
            }
            System.out.print(list.size());
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
            foodList.setAdapter(adapter);
            foodList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(UserInputActivity.this, FoodInfoActivity.class);
                    // transfer the data to the next page
                    try {
                        JSONObject food = al.get(i).getJSONObject("food");
                        JSONObject measure = al.get(i).getJSONArray("measures").getJSONObject(0);
                        intent.putExtra("foodURI", food.get("uri").toString());
                        intent.putExtra("foodLabel", food.get("label").toString());
                        intent.putExtra("measureURI", measure.get("uri").toString());
                        intent.putExtra("measureLabel", measure.get("label").toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "transfer data error!", Toast.LENGTH_SHORT).show();
                    }

                    startActivity(intent);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dialog.dismiss();
    }



}
