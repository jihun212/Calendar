package com.example.calendar.CALENDAR;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class wCalendarGridAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> list;
    private int mResource;
    int height;

    public wCalendarGridAdapter(Context context, int resource, ArrayList<String>list, int height){
        this.context=context;
        this.mResource=resource;
        this.list=list;
        this.height = height;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (convertView == null) {LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent,false);
        }
        TextView text = (TextView)convertView;
        text.setText(list.get(position));
        text.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP);

        return text;
    }
}

