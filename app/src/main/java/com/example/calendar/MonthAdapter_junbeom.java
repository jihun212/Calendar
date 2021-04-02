package com.example.calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Calendar;

public class MonthAdapter_junbeom extends BaseAdapter {
    public static final String TAG = "MonthAdapter";
    Context mContext;

    private MonthItem[] items;
    private int countColumn = 7;

    int mStartDay;
    int startDay;
    int curYear;
    int curMonth;

    int firstDay;
    int lastDay;

    Calendar mCalendar;
    boolean recreateItems = false;


    public MonthAdapter_junbeom(Context context) {
        super();
        mContext = context;
        init();
    }

    public MonthAdapter_junbeom(Context context, AttributeSet attributeSet) {
        super();
        mContext = context;
        init();
    }

    private void init() {
        items = new MonthItem[7 * 6];
        mCalendar = Calendar.getInstance();
        recalculate();
        resetDayNumbers();
    }

    public void recalculate() {
        mCalendar.set(Calendar.DAY_OF_MONTH, 1);

        int dayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK);
        firstDay = getFirstDay(dayOfWeek);

        mStartDay = mCalendar.getFirstDayOfWeek();
        curYear = mCalendar.get(Calendar.YEAR);
        curMonth = mCalendar.get(Calendar.MONTH);
        lastDay = getMonthLastDay(curYear, curMonth);

        int diff = mStartDay - Calendar.SUNDAY -1;
        startDay = getFirstDayOfWeek();
    }

    public void setPr




    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
