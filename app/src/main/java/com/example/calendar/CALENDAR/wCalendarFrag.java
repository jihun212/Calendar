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
import java.util.Calendar;

public class wCalendarFrag extends Fragment {

    private int year;
    private int month;
    private int week;
    static String weekDATA;
    String[] items;
    String[] wItems;

    private static final String ARG_PARAM3 = "param3";
    Calendar today;
    ArrayList<String> list3 = new ArrayList<>();  //주간 24*7표 공백 저장


    wCalendarFrag(int y, int m, int w) {
        year = y;
        month = m;
        week = w;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View calView = inflater.inflate(R.layout.fragment_w_calendar, container, false);

        CalendarData cal = new CalendarData();          // 달력 데이터 객체 생성
        cal.setCalendar(year, month);                   // 주어진 년,월 데이터를 토대로 객체에 달력 데이터 주입

        items = cal.StringConverter();                  // 생성된 달력 데이터를 문자열 배열에 삽입
        wItems = itemsForWeekCal(week);                 // 문자열 배열에서 week주차의 배열만 추출

        GridView gView = calView.findViewById(R.id.wCalendarGridView);  // calView 에 들어갈 그리드뷰 생성
        GridView gView_week = calView.findViewById(R.id.wCalendarGridView_week);

        getWeekCalendar();

        //어댑터 준비 (배열 객체 이용, simple_list_item_1 리소스 사용
        ArrayAdapter<String> adapt_grid_week
                = new ArrayAdapter<String>(
                getActivity(),
                R.layout.list_item,
                list3);


        CalendarBaseAdapter adapt = new CalendarBaseAdapter(getActivity(), android.R.layout.simple_list_item_1, wItems);  // base 어댑터 생성

        Week_GridAdapter g2;

        //가로모드일 때
        if (getActivity().getWindowManager().getDefaultDisplay().getRotation()
                == Surface.ROTATION_90 || getActivity().getWindowManager().getDefaultDisplay().getRotation() == Surface.ROTATION_270) {
            //어댑터 준비 (배열 객체 이용, simple_list_item_1 리소스 사용
            g2 = new Week_GridAdapter(getActivity(), R.layout.list_item, list3, 130);
        } else {  //세로모드일 때
            g2 = new Week_GridAdapter(getActivity(), R.layout.list_item, list3, 250);
        }

        gView.setAdapter(adapt);    // 그리드뷰에 어댑터 적용
        gView_week.setAdapter(g2);

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
            list3.add("");
        }
    }
}