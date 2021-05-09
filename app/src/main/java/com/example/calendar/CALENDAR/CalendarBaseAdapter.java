package com.example.calendar.CALENDAR;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.calendar.R;

class CalendarBaseAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private String[] data;
    private LayoutInflater lInflater;

    public CalendarBaseAdapter(Context context, int layout, String[] items) {
        this.context = context;
        this.layout = layout;
        this.data = items;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView textView;
        if(convertView == null) textView = new TextView(context);
        else textView = (TextView) convertView;

        if(position != 0 && (position + 1) %7 == 0)
            textView.setTextColor(context.getResources().getColor(R.color.Blue));   // 토요일 파란색 변경

        if(position % 7 == 0)
            textView.setTextColor(context.getResources().getColor(R.color.Red));   // 일요일 빨간색 변경

        textView.setText(data[position]);
        textView.setGravity(Gravity.CENTER);
        textView.setHeight(parent.getHeight()/6);
        textView.setBackgroundColor(R.drawable.ic_launcher_background);
        return textView;
    }
}
