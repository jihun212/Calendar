package com.example.calendar.CALENDAR;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.example.calendar.R;

public class mCalendarFrag extends Fragment {

    private int year;
    private int month;
    String[] items;

    mCalendarFrag(int y, int m) {
        year = y;
        month = m;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View calView = inflater.inflate(R.layout.fragment_m_calendar, container, false);

        CalendarData cal = new CalendarData();          // 달력 데이터 객체 생성
        cal.setCalendar(year, month);                   // 주어진 년,월 데이터를 토대로 객체에 달력 데이터 주입

        items = cal.StringConverter();                  // 생성된 달력 데이터를 문자열 배열에 삽입

        GridView gView = calView.findViewById(R.id.mCalendarGridView);  // calView 에 들어갈 그리드뷰 생성
        CalendarBaseAdapter adapt = new CalendarBaseAdapter(getActivity(),android.R.layout.simple_list_item_1,items);   // base 어댑터 생성

        gView.setAdapter(adapt);// 그리드뷰에 어댑터 적용
        String day_full = year + "년 "+ month  + "월 ";
        gView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(items[position] != "") {
                    Toast.makeText(getActivity(), day_full + items[position] + "일", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return calView;
    }
}

