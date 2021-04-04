package com.example.calendar;

import android.content.Context;
import android.graphics.Color;
import android.text.format.Time;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.util.Calendar;

//BaseAdapter를 상속하여 새로운 어댑터 정의
public class CalendarAdapter extends BaseAdapter {
    Context mContext;

    private MonthItem[] items;  // MonthItem.java 참조

    int mStartDay;
    int startDay;

    int curYear;    // 현재년도
    int curMonth;   // 현재달

    int firstDay;
    int lastDay;

    Calendar mCalendar;

    public CalendarAdapter(Context context) {
        super();
        mContext = context;
        init();
    }

    // 초기화 메소드 설정
    // 1개월의 일별 데이터를 담고 있을 수 있는 MonthItem의 배열 객체 생성
    // 원하는 날짜 지정
    // 현재 달은 4월 이므로 3월입력
    // recalculate(), resetDayNumbers() 사용
    private void init() {
        items = new MonthItem[7 * 6];
        mCalendar = Calendar.getInstance();
        mCalendar.set(2021,3,1);    //////원하는 날짜 지정///////

        recalculate();
        resetDayNumbers();
    }

    // 이전 월로 이동 시 일별 데이터 새로 계산
    // 이전 월로 이동 시 MONTH -1
    // recalculate(), resetDayNumbers() 사용
    public void setPreviousMonth() {
        mCalendar.add(Calendar.MONTH, -1);
        recalculate();
        resetDayNumbers();
    }
    
    // 다음 월로 이동 시 일별 데이터 새로 계산
    // 다음 월로 이동 시 MONTH +1
    // recalculate(), resetDayNumbers() 사용
    public void setNextMonth() {
        mCalendar.add(Calendar.MONTH, 1);
        recalculate();
        resetDayNumbers();
    }

    // 지정한 월의 일별 데이터를 새로 계산하는 메소드 정의
    // 해당하는 달의 날짜 수를 계산하는 알고리즘
    private void resetDayNumbers() {
        for (int i = 0; i < 42; i++) {
            // dayNumber 계산
            int dayNumber = (i+1) - firstDay;   // getFirstDay 참조
            if (dayNumber < 1 || dayNumber > lastDay) { // getMonthLastDay 참조
                dayNumber = 0;
            }
            // data item 저장
            items[i] = new MonthItem(dayNumber);
        }
    }

    // 새로운 달 재계산 메소드
    public void recalculate() {
        mCalendar.set(Calendar.DAY_OF_MONTH,1);
        int dayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK);

        firstDay = getFirstDay(dayOfWeek);
        mStartDay = mCalendar.getFirstDayOfWeek();

        curYear = mCalendar.get(Calendar.YEAR);
        curMonth = mCalendar.get(Calendar.MONTH);

        lastDay = getMonthLastDay(curYear, curMonth);
        startDay = getFirstDayOfWeek();
    }

    // 첫번째 요일 메소드
    private int getFirstDay(int dayOfWeek) {
        int result = 0;
        if (dayOfWeek == Calendar.SUNDAY) {
            result = 0;
        } else if (dayOfWeek == Calendar.MONDAY) {
            result = 1;
        } else if (dayOfWeek == Calendar.TUESDAY) {
            result = 2;
        } else if (dayOfWeek == Calendar.WEDNESDAY) {
            result = 3;
        } else if (dayOfWeek == Calendar.THURSDAY) {
            result = 4;
        } else if (dayOfWeek == Calendar.FRIDAY) {
            result = 5;
        } else if (dayOfWeek == Calendar.SATURDAY) {
            result = 6;
        }
        return result;
    }

    public int getCurYear() {
        return curYear;
    }

    public int getCurMonth() {
        return curMonth;
    }

    // getCount
    // 배열 사이즈 정의
    // 7*6=42개의 데이터 존재
    @Override
    public int getCount() {
        return 7 * 6;
    }

    // 아이템의 position(위치) 에 대한 값 정의
    // 위치에 담을 데이터 리턴
    @Override
    public Object getItem(int position) {
        return items[position];
    }

    // 각 공간에 대한 위치 정보 정의
    // 아이템 위치 확인하여 position 리턴
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 뷰의 모양 정의 및 리턴
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MonthItemView itemView;

        // ConvertView는 안드로이드가 사용자의 편의성을 위해 제공하는 객체
        // 이전에 안드로이드에 불러온 뷰에 대한 정보를 저장하는 객체
        // 전에 불러왔던 뷰에 대한 정보를 재사용할때 사용하는 객체
        if (convertView == null) {
            itemView = new MonthItemView(mContext);
        } else {
            itemView = (MonthItemView) convertView;
        }

        // 그리드 구조에 접근
        GridView.LayoutParams params = new GridView.LayoutParams(
                GridView.LayoutParams.MATCH_PARENT, 120);
        
        int countColumn = 7;    // 1주일 = 7일
        int columnIndex = position % countColumn;
        itemView.setItem(items[position]);  // 날짜 정보
        itemView.setLayoutParams(params);   // 그리드 뷰 칸의 각 속성 크기 조정
        itemView.setPadding(1,1,1,1);   //양쪽 Padding 각 1씩
        itemView.setGravity(Gravity.CENTER);    //글자 위치 CENTER

        if (columnIndex == 0) {
            itemView.setTextColor(Color.RED);   //일요일 빨간색 설정
        } else if (columnIndex == 6) {
            itemView.setTextColor(Color.BLUE);  //토요일 파란색 설정
        } else {
            itemView.setTextColor(Color.BLACK); //나머지 검정색 설정
        }
        return itemView;
    }

    // 첫번째 주의 첫번째 요일 구하기 메소드
    public static int getFirstDayOfWeek() {
        int startDay = Calendar.getInstance().getFirstDayOfWeek();
        if (startDay == Calendar.SATURDAY) {
            return Time.SATURDAY;
        } else if (startDay == Calendar.MONDAY) {
            return Time.MONDAY;
        } else {
            return Time.SUNDAY;
        }
    }

    // 한 달이 31일, 30일, 혹은 2월 달의 윤년을 계산하기 위한 메소드
    private int getMonthLastDay(int year, int month) {
        switch (month) {
            case 0:
            case 2:
            case 4:
            case 6:
            case 7:
            case 9:
            case 11:
                return (31);    //한 달 31일 리턴

            case 3:
            case 5:
            case 8:
            case 10:
                return (30);    //한 달 30일 리턴

            default:
                if( ( (year%4 == 0) && (year%100 != 0) ) || (year%400 == 0) ) { //윤년 계산
                    return (29);    //한 달 29일 리턴
                } else {
                    return (28);    //한 달 28일 리턴
                }
        }
    }
}

