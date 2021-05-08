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

        TextView tv;
        if(convertView == null) tv = new TextView(context);
        else tv = (TextView) convertView;


        if(position != 0 && (position + 1) %7 == 0)
            tv.setTextColor(context.getResources().getColor(R.color.skyblue));

        if(position % 7 == 0)
            tv.setTextColor(context.getResources().getColor(R.color.red));          // 토요일, 일요일 여부 파악 후 텍스트 색상 변경

        tv.setText(data[position]);
        tv.setGravity(Gravity.CENTER);
        tv.setHeight(parent.getHeight()/6);
        tv.setBackgroundColor(context.getResources().getColor(R.color.white));
        return tv;
    }
}
