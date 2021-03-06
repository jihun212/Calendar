package com.example.calendar.CALENDAR;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class wCalendarPagerAdapter extends FragmentStateAdapter {
    private int year, month;

    public wCalendarPagerAdapter(FragmentActivity fa, int y, int m) {
        super(fa);
        year = y;
        month = m;
    }

    @Override
    public Fragment createFragment(int position) {
        Fragment calFrag = new wCalendarFrag(year,month,position);
        return calFrag;
    }

    @Override
    public int getItemCount() {
        CalendarData cal = new CalendarData();
        cal.setCalendar(year, month);
        return findTotalPage(cal.StringConverter());
    }

    private int findTotalPage(String[] a){
        int i = 1;
        while( i!= 6) {
            if(a[i*7].equals(""))
                return i;
            else i++;
        }
        return i;
    }
}
