package com.example.tanxueying.healthyeats.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tanxueying.healthyeats.Food;
import com.example.tanxueying.healthyeats.R;

import java.util.List;

/**
 * Created by yaoxi on 2018/4/29.
 */

public class myAdapter extends ArrayAdapter {

    private int resourceId;


    public myAdapter(@NonNull Context context, int textViewResourceId, @NonNull List objects) {
        super(context, textViewResourceId, objects);
        this.resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Food food = (Food) getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.foodName = (TextView) view.findViewById (R.id.food_name);
            viewHolder.measure = (TextView) view.findViewById(R.id.measure);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.foodName.setText(food.getLabel());
        viewHolder.measure.setText(food.getMeasure());
        return view;
    }

    class ViewHolder {
        TextView foodName;
        EditText yield;
        EditText quantity;
        TextView measure;
    }
}
