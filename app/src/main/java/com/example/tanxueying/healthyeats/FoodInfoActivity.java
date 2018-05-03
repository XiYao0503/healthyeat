package com.example.tanxueying.healthyeats;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FoodInfoActivity extends AppCompatActivity {
    private static final String TAG = FoodInfoActivity.class.getSimpleName();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    DecimalFormat decimalFormat = new DecimalFormat(".00");
    //Get the login user
    FirebaseUser user = auth.getCurrentUser();
    //Get the UID
    String uid = user.getUid();
    //Get reference of User
    FirebaseDatabase database = FirebaseDatabase.getInstance();


    ProgressDialog dialog;
    private TextView unit;
    private EditText serving_size;
    private EditText num_of_serving;
    private TextView total_kcal;
    private TextView food_name;
    private float yield = 1.0f;
    private float quantity = 1.0f;
    private String foodLabel;
    private String foodURI;
    private String measureURI;
    private String measureLabel;
    private String title;
    private int position;
    private String isFromHome;

    private float unit_kcal = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_info);

        food_name = (TextView) findViewById(R.id.food_title);
        total_kcal = (TextView) findViewById(R.id.total_value);


        position = getIntent().getExtras().getInt("position");

        foodLabel = getIntent().getExtras().getString("foodLabel");
        foodURI = getIntent().getExtras().getString("foodURI");
        measureURI = getIntent().getExtras().getString("measureURI");
        measureLabel = getIntent().getExtras().getString("measureLabel");
        if (getIntent().getExtras().getString("quantity") != null)
            quantity = Float.valueOf(getIntent().getExtras().getString("quantity"));
        isFromHome = getIntent().getExtras().getString("isFromHome", "False");

        unit = (TextView) findViewById(R.id.unit);
        unit.setText(measureLabel);
        food_name.setText(foodLabel);

        serving_size = (EditText) findViewById(R.id.size_input);
        serving_size.setText(decimalFormat.format(quantity));
        num_of_serving = (EditText) findViewById(R.id.number_input);
        showInfomation(foodURI, measureURI);

        serving_size.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence == null || charSequence.length() == 0) {
                    quantity =0.0f;
                } else {
                    quantity = Float.parseFloat(charSequence.toString());

                }
                total_kcal.setText(decimalFormat.format(unit_kcal * yield * quantity) + "KCAL");

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        num_of_serving.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence == null || charSequence.length() == 0) {
                    yield =0.0f;
                } else {
                    yield = Float.parseFloat(charSequence.toString());

                }
                total_kcal.setText(decimalFormat.format(unit_kcal * yield * quantity) + "KCAL");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


//        final String yield_string = getIntent().getExtras().getString("yield");

        final ImageButton button_done = (ImageButton) findViewById(R.id.imageButton5);
        button_done.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!isFromHome.equals("False")) {
                    Intent intent = new Intent(FoodInfoActivity.this, HomeActivity.class);
                    saveFoodInfo();
                    startActivity(intent);
                } else {
                    saveFoodInfo();
                    finish();
                }

            }
        });

        final ImageButton button_back = (ImageButton) findViewById(R.id.imageButton2);
        button_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isFromHome.equals("False")) {

                    finish();
                } else {
                    Intent intent = new Intent(FoodInfoActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            }
        });


    }

    private void saveFoodInfo() {
        if (serving_size.getText().toString().trim().isEmpty() || num_of_serving.getText().toString().trim().isEmpty()) {
            return;
        }
        final Food food = new Food(foodLabel, foodURI, measureLabel, measureURI);
        quantity = Float.parseFloat(serving_size.getText().toString().trim());
        yield = Float.parseFloat(num_of_serving.getText().toString().trim());

        food.setQuantity(decimalFormat.format(quantity));
        food.setYield(decimalFormat.format(yield));
        food.setKcal(decimalFormat.format(quantity * unit_kcal));
        food.setTotal_kcal(decimalFormat.format(quantity * yield * unit_kcal));
        final DatabaseReference ref = database.getReference().child(uid);
        // Attach a listener to read the data at our posts reference
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                //get the current date
                String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
                System.out.println("current date:" + date);
                //update the food record
                String foodId = ref.child("food").child(date).push().getKey();
                Log.e("foodid", foodId);
                ref.child("food").child(date).child(foodId).setValue(food);
                user.addFood(food);
                ref.child("total").setValue(user.getTotal());
                ref.child("net").setValue(user.getNet());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
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
//        System.out.println(gson.toJson(jsonObject));
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {

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
            if (ingredientObjects.has("ENERC_KCAL")) {
                unit_kcal = ((Double) ingredientObjects.getJSONObject("ENERC_KCAL").get("quantity")).floatValue();
                total_kcal.setText(decimalFormat.format(unit_kcal * yield * quantity) + "KCAL");
            }

            System.out.println(unit_kcal);
            System.out.println("=========================");
            while (keys.hasNext()) {
                String key = keys.next();
                JSONObject value = ingredientObjects.getJSONObject(key);
                ingredientsList.add(value);
            }

            String[] ingredients = new String[ingredientsList.size()];
            for (int i = 0; i < ingredientsList.size(); ++i) {
                JSONObject cur = ingredientsList.get(i);
                ingredients[i] = cur.get("label").toString() + ": " + decimalFormat.format(((Double) cur.get("quantity")).floatValue()) + " " + cur.get("unit").toString();
//                Log.d("bbbbbbbbb", cur.get("label").toString());

                ListView ingredientList = (ListView) findViewById(R.id.ingredient);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_listview, R.id.textView, ingredients);
                ingredientList.setAdapter(arrayAdapter);

            }

            // for pieChart
            List<String> xValues = new ArrayList<>();
            List<Float> yValues = new ArrayList<>();
            float total = 0;
            float sum = 0;
            for (JSONObject cur : ingredientsList) {
                if (cur.get("unit").toString().equals("g")) {
                    total += ((Double) cur.get("quantity")).floatValue() * 1000.0f;
                } else if (cur.get("unit").toString().equals("mg")) {
                    total += ((Double) cur.get("quantity")).floatValue();
                }
            }

            for (JSONObject cur : ingredientsList) {
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
            yValues.add((1.0f - sum / total) * 100.0f);
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

        // set a title in the piechart with food name

    }

}
