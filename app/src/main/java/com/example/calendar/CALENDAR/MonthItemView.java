package com.example.calendar.CALENDAR;

import android.content.Context;
import android.graphics.Color;

import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class MonthItemView extends AppCompatTextView {

    public MonthItemView(Context context) {
        super(context);
        init();
    }

    public MonthItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setBackgroundColor(Color.WHITE);
    }

    public void setItem(MonthItem item) {
        int day = item.getDay();
        if (day != 0) {
            setText(String.valueOf(day));
        } else {
            setText("");
        }
    }
}
