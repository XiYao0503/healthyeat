package com.example.tanxueying.healthyeats;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.app.AlertDialog;


import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private List<String> dataList;

    private List<String> foodIdList;

    private List<String> sizeList;
    private ClickItemContentAdapter adapter;
    private String[] pos2index;


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
        setContentView(R.layout.home);

        final ImageButton button_profile = (ImageButton) findViewById(R.id.profile_btn);
        final TextView net_goal = (TextView) findViewById(R.id.goal_num);
        button_profile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        final ImageButton button_add_food = (ImageButton) findViewById(R.id.add_btn);
        button_add_food.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, UserInputActivity.class);
                startActivity(intent);
            }
        });

        final ImageButton button_logout = (ImageButton) findViewById(R.id.log_out2);
        button_logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        // Attach a listener to read the data at our user reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                //get the user object
                User user = dataSnapshot.getValue(User.class);

                //show the current net calories on the top
                net_goal.setText(user.getGoal_cal() + "-" + user.getTotal() + "=" + user.getNet());
                //list all the food records with delete button
                dataList = new ArrayList<>();
                sizeList = new ArrayList<>();
                foodIdList = new ArrayList<>();

                if (dataSnapshot.child("food").getValue() != null) {
                    for (DataSnapshot emp : dataSnapshot.child("food").getChildren()) {
                        Food food = emp.getValue(Food.class);
                        dataList.add(food.getLabel());
                        foodIdList.add(emp.getKey());
                        sizeList.add("Serving size: " + 20);
//                        sizeList.add("Serving size: " + food.getSize());
                    }

                }
                ListView listView = (ListView) findViewById(R.id.scrollView2);
                adapter = new ClickItemContentAdapter(HomeActivity.this, dataList, sizeList);
                listView.setAdapter(adapter);


                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //go to the food information
                        String foodId = foodIdList.get(position);
                        Food food = dataSnapshot.child("food").child(foodId).getValue(Food.class);
//                        Intent intent = new Intent(HomeActivity.this, FoodInfoActivity.class);
//                        intent.putExtra("foodURI", select_food.getLabelUrl());
//                        System.out.println(select_food.getLabel());
//                        System.out.println(select_food.getMeasure());
//                        intent.putExtra("foodLabel", select_food.getLabel());
//                        intent.putExtra("measureURI", select_food.getMeasureUrl());
//                        intent.putExtra("measureLabel", select_food.getMeasure());
//                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("Error:", databaseError.toString());
            }
        });

    }

    @Override
    public void onClick(final View v) {

        final DatabaseReference ref2 = database.getReference().child(uid);
        // Attach a listener to read the data at our posts reference
        ref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                final User user = dataSnapshot.getValue(User.class);
                switch (v.getId()) {
                    case R.id.iv_del:   //delete button
                        final int position = (int) v.getTag(); //get the index if item

                        //dialog to confirm deletion of the item
                        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                        builder.setTitle("Are you sure to remove the " + dataList.get(position) + "?");
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dataList.remove(position);
                                sizeList.remove(position);
                                String foodId = foodIdList.get(position);
                                foodIdList.remove(position);
                                Food rm = dataSnapshot.child("food").child(foodId).getValue(Food.class);

////                                //update the food list
                                dataSnapshot.child("food").child(foodId).getRef().removeValue();
////
//////                                update the net cal
                                user.setTotal(String.valueOf(Float.parseFloat(user.getTotal()) - Float.parseFloat(rm.getTotal_kcal())));
                                user.setNet(String.valueOf(Float.parseFloat(user.getNet()) + Float.parseFloat(rm.getTotal_kcal())));
                                ref.child("total").setValue(user.getTotal());
                                ref.child("net").setValue(user.getNet());

                                adapter.notifyDataSetChanged();

//                                dataList.remove(position);
//                                //update the food list
//                                String rmId = foodIdList.get(position);
//                                Food rmFood = dataSnapshot.child("food").child(rmId).getValue(Food.class);
//                                foodIdList.remove(position);
//                                user.setFood(foodIdList);
//                                dataSnapshot.child(rmId).getRef().removeValue();
////                                update the net cal
//                                user.setTotal("" + (Float.parseFloat(user.getTotal()) - Float.parseFloat(rmFood.getTotal_kcal())));
//                                user.setNet("" + Float.parseFloat(user.getNet()) + Float.parseFloat(rmFood.getTotal_kcal()));

//                                adapter.notifyDataSetChanged();
                            }
                        });
                        builder.show();
                        break;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }

        });
    }

}
