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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
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
import java.util.Iterator;
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

        final TextView food_name = (TextView) findViewById(R.id.title);
        food_name.setText(foodLabel);


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
//        foodURI = "http://www.edamam.com/ontologies/edamam.owl#Food_11529";
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
        try {
            // for listView
            List<JSONObject> ingredientsList = new ArrayList<>();
            JSONObject ingredientObjects = object.getJSONObject("totalNutrients");
            Iterator<String> keys = ingredientObjects.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                JSONObject value = ingredientObjects.getJSONObject(key);
                ingredientsList.add(value);
            }

            String[] ingredients = new String[ingredientsList.size()];
            for(int i = 0; i < ingredientsList.size(); ++i) {
                JSONObject cur = ingredientsList.get(i);
                ingredients[i] = cur.get("label").toString() + ": " + cur.get("quantity").toString() + " " + cur.get("unit").toString();
//                Log.d("bbbbbbbbb", cur.get("label").toString());

                ListView ingredientList = (ListView)findViewById(R.id.ingredient);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_listview, R.id.textView, ingredients);
                ingredientList.setAdapter(arrayAdapter);
            }

            // for pieChart
            List<String> xValues = new ArrayList<>();
            List<Float> yValues = new ArrayList<>();
            float total = 0;
            float sum = 0;
            for(JSONObject cur : ingredientsList) {
                if (cur.get("unit").toString().equals("g")) {
                    total += ((Double) cur.get("quantity")).floatValue() * 1000.0f;
                } else if (cur.get("unit").toString().equals("mg")) {
                    total += ((Double) cur.get("quantity")).floatValue();
                }
            }

            for(JSONObject cur : ingredientsList) {
                float quantity = 0.0f;
                if (cur.get("unit").toString().equals("g")) {
                    quantity = ((Double) cur.get("quantity")).floatValue() * 1000.0f;
                } else if (cur.get("unit").toString().equals("mg")) {
                    quantity = ((Double) cur.get("quantity")).floatValue();
                }
                if (quantity > total / 20) {
                    sum += quantity;
                    xValues.add(cur.get("label").toString());
                    yValues.add(quantity * 100.0f / total);
                }
            }

            xValues.add("Other");
            yValues.add(1.0f - sum / total);
            pieChart(xValues, yValues);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dialog.dismiss();
    }

    public void pieChart(List<String> xValues, List<Float> yValues) {
        String[] labels = xValues.toArray(new String[xValues.size()]);
        Float[] quantities = yValues.toArray(new Float[yValues.size()]);

        List<PieEntry> pieEntries = new ArrayList<>();
        for (int i = 0; i < quantities.length; i++) {
            pieEntries.add(new PieEntry(quantities[i], labels[i]));
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "Ingredient Percentage");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());

        PieChart pieChart = (PieChart) findViewById(R.id.piechart);
        pieChart.setData(data);
        pieChart.invalidate();

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
