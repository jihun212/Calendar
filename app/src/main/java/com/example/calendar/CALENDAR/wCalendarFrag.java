package com.example.calendar.CALENDAR;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.example.calendar.R;
//import com.example.calendar.scheduleActivity;

public class wCalendarFrag extends Fragment {

    //public static final int REQUEST_CODE_MENU = 101;
    private int year;
    private int month;
    private int week;
    static String weekDATA;
    String[] items;
    String[] wItems;

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
        CalendarBaseAdapter adapt = new CalendarBaseAdapter(getActivity(),android.R.layout.simple_list_item_1,wItems);  // base 어댑터 생성

        gView.setAdapter(adapt);    // 그리드뷰에 어댑터 적용

//        gView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View v,
//                                    int position, long id) {
//                if(wItems[position].equals("")){}    //터치한 부분이 빈칸일 경우 아무 행동도 하지 않게 함
//                else {
//                    int wd = (weekDATA == null ? 0 : Integer.parseInt(weekDATA));
//
//                    wd += Integer.parseInt(wItems[position]);
//
//                    String mon_STR = ( month < 10 ? "0" + month : "" + month);
//                    String dat_STR = ( wd < 10 ? "0" + wd : "" + wd);
//
//                    String Key = year + mon_STR + dat_STR;
//                    Intent intent = new Intent(getActivity(), scheduleActivity.class);
//                    intent.putExtra("KeyDATA",Key);
//                    startActivityForResult(intent, REQUEST_CODE_MENU);
//                }
//            }
//        });

        return calView;
    }


    public String[] itemsForWeekCal(int week) {
        String[] forReturn = new String[7];
        for(int i = 0; i < 7; i++)
            forReturn[i] = items[i + week*7];
        return forReturn;
    }

    public static void setWeekDATA(int w) {
        weekDATA = Integer.toString((w-1)*7);
    }
}