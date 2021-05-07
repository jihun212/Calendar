package com.example.calendar.CALENDAR;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class wCalendarPagerAdapter extends FragmentStatePagerAdapter {
    private int year, month;

    public wCalendarPagerAdapter(FragmentManager fm, int y, int m) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        year = y;
        month = m;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment calFrag = new wCalendarFrag(year,month,position);
        return calFrag;
    }

    @Override
    public int getCount() {
        CalendarData cal = new CalendarData();          // 달력 데이터 객체 생성
        cal.setCalendar(year, month);                   // 주어진 년,월 데이터를 토대로 객체에 달력 데이터 주입
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
