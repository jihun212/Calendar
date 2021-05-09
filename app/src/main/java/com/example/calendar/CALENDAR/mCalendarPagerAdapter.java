package com.example.calendar.CALENDAR;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class mCalendarPagerAdapter extends FragmentStateAdapter {

    public mCalendarPagerAdapter(FragmentActivity fa) {
        super(fa);
    }

    @Override
    public Fragment createFragment(int position) {

        int year = position/12;
        int month=0;

        if((position%12)==0)
            month=1;
        else
            month=(position%12)+1;

        Fragment calFrag = new mCalendarFrag(year,month);
        return calFrag;
    }

    @Override
    public int getItemCount() {
        return 50000;
    }
}