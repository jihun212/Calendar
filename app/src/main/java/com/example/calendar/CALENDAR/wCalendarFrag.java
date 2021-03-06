package com.example.calendar.CALENDAR;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.example.calendar.R;

import java.util.ArrayList;

public class wCalendarFrag extends Fragment {

    private int year;
    private int month;
    private int week;
    static String weekDATA;
    String[] items;
    String[] wItems;

    private static final String ARG_PARAM1 = "param1";

    ArrayList<String> list1 = new ArrayList<>();  
    //ArrayList 저장

    wCalendarFrag(int y, int m, int w) {
        year = y;
        month = m;
        week = w;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View calView = inflater.inflate(R.layout.fragment_w_calendar, container, false);

        CalendarData cal = new CalendarData();          
        // 달력 데이터 객체 준비
        cal.setCalendar(year, month);                   
        // 객체에 달력 데이터 생성

        items = cal.StringConverter();                  
        // 문자열 배열에 삽입
        wItems = itemsForWeekCal(week);                 
        // 문자열 배열에서 week주차의 배열 추출

        GridView gView = calView.findViewById(R.id.wCalendarGridView);              
        // calView 에 들어갈 그리드뷰1 생성
        GridView gView_week = calView.findViewById(R.id.wCalendarGridView_week);    
        // calView 에 들어갈 그리드뷰2 생성

        gView_week.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getActivity(), ((position/7))+" 시"+" position"+((position%7)+1), Toast.LENGTH_SHORT).show();
            }
        });

        getWeekCalendar();

        ArrayAdapter<String> adapt_grid_week = new ArrayAdapter<String>(getActivity(), R.layout.grid_item, list1);
        // ArrayAdapter 준비

        CalendarBaseAdapter adapt = new CalendarBaseAdapter(getActivity(), android.R.layout.simple_list_item_1, wItems);
        // base 어댑터 생성

        wCalendarGridAdapter gadapt;

        if (getActivity().getWindowManager().getDefaultDisplay().getRotation() == Surface.ROTATION_90 || getActivity().getWindowManager().getDefaultDisplay().getRotation() == Surface.ROTATION_270) {
            gadapt = new wCalendarGridAdapter(getActivity(), R.layout.grid_item, list1, 150);
        } else {
            gadapt = new wCalendarGridAdapter(getActivity(), R.layout.grid_item, list1, 250);
        }

        gView.setAdapter(adapt);
        // 그리드뷰1에 어댑터 적용
        gView_week.setAdapter(gadapt);
        // 그리드뷰2에 어댑터 적용

        return calView;
    }

    public String[] itemsForWeekCal(int week) {
        String[] forReturn = new String[7];

        for (int i = 0; i < 7; i++)
            forReturn[i] = items[i + week * 7];

        return forReturn;
    }

    public static void setWeekDATA(int w) {
        weekDATA = Integer.toString((w - 1) * 7);
    }

    private void getWeekCalendar() {
        for (int i = 1; i <= 168; i++) {
            list1.add("");
        }
    }
}