package com.example.calendar.CALENDAR;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class mCalendarPagerAdapter extends FragmentStatePagerAdapter {

    public mCalendarPagerAdapter(FragmentManager fm) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @Override
    public Fragment getItem(int position) {

        int year = position/12;
        int month=0;

        if((position%12)==0)
            month=1;
        else
            month=(position%12)+1;

        Fragment calFrag=new mCalendarFrag(year,month);
        return calFrag;
    }

    @Override
    public int getCount() {
        return 50000;
    }
}