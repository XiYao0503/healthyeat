package com.example.tanxueying.healthyeats;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ClickItemContentAdapter extends BaseAdapter {
    private final View.OnClickListener listener;
    private final List<String> dataList;

    public ClickItemContentAdapter(View.OnClickListener listener, List<String> dataList) {
        this.listener = listener;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_click_item_content, parent,false);
            holder.iv_del = (ImageView) convertView.findViewById(R.id.iv_del);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_title.setText(dataList.get(position));

        //add listener to items
        holder.iv_del.setOnClickListener(listener);
        //send the position of the item
        holder.iv_del.setTag(position);

        return convertView;
    }

    class ViewHolder {
        TextView  tv_title;
        ImageView iv_del;
    }
}

