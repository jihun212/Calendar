package com.example.calendar;

import android.content.Context;
import android.graphics.Color;
import android.text.format.Time;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.util.Calendar;

//BaseAdapter를 상속하여 새로운 어댑터 정의
public class CalendarAdapter_junbeom extends BaseAdapter {
    public static final String TAG = "MonthAdapter";
    Context mContext;

    private MonthItem[] items;
    private int countColumn = 7;

    int mStartDay;
    int startDay;
    int curYear;
    int curMonth;
    int curDay;
    int curWeek;

    int firstDay;
    int lastDay;

    Calendar mCalendar;
    boolean recreateItems = false;


    public CalendarAdapter_junbeom(Context context) {
        super();
        mContext = context;
        init();
    }

    public CalendarAdapter_junbeom(Context context, AttributeSet attributeSet) {
        super();
        mContext = context;
        init();
    }

    //초기화 메소드 설정
    //1개월의 일별 데이터를 담고 있을 수 있는 MonthItem의 배열 객체 생성
    //원하는 날짜 지정
    private void init() {
        items = new MonthItem[7 * 6];
        mCalendar = Calendar.getInstance();
        mCalendar.set(2021,3,1);    //////원하는 날짜 지정///////

        recalculate();
        resetDayNumbers();
    }

    //이전 월로 이동 시 일별 데이터 새로 계산
    public void setPreviousMonth() {
        mCalendar.add(Calendar.MONTH, -1);
        recalculate();
        resetDayNumbers();
    }
    
    //다음 월로 이동 시 일별 데이터 새로 계산
    public void setNextMonth() {
        mCalendar.add(Calendar.MONTH, 1);
        recalculate();
        resetDayNumbers();
    }

    //지정한 월의 일별 데이터를 새로 계산하는 메소드 정의
    private void resetDayNumbers() {
        for (int i = 0; i < 42; i++) {
            int dayNumber = (i+1) - firstDay;
            if (dayNumber < 1 || dayNumber > lastDay) {
                dayNumber = 0;
            }
            items[i] = new MonthItem(dayNumber);
        }
    }

    public void recalculate() {
        mCalendar.set(Calendar.DAY_OF_MONTH,1);

        int dayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK);

        firstDay = getFirstDay(dayOfWeek);

        mStartDay = mCalendar.getFirstDayOfWeek();
        curYear = mCalendar.get(Calendar.YEAR);
        curMonth = mCalendar.get(Calendar.MONTH);
        lastDay = getMonthLastDay(curYear, curMonth);

        int diff = mStartDay - Calendar.SUNDAY -1;
        startDay = getFirstDayOfWeek();
    }

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

    public int getCurDay() {
        return curDay;
    }

    public int getCurWeek() {
        return curWeek;
    }

    public int getNumColumns() {
        return 7;
    }

    @Override
    public int getCount() {
        return 7 * 6;
    }

    @Override
    public Object getItem(int position) {
        return items[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MonthItemView itemView;
        if (convertView == null) {
            itemView = new MonthItemView(mContext);
        } else {
            itemView = (MonthItemView) convertView;
        }

        GridView.LayoutParams params = new GridView.LayoutParams(
                GridView.LayoutParams.MATCH_PARENT,120);

        int rowIndex = position / countColumn;
        int columnIndex = position % countColumn;

        itemView.setItem(items[position]);
        itemView.setLayoutParams(params);
        itemView.setPadding(1,1,1,1);

        itemView.setGravity(Gravity.CENTER);

        if (columnIndex == 0) {
            itemView.setTextColor(Color.RED);   //일요일 빨간색
        } else if (columnIndex == 6) {
            itemView.setTextColor(Color.BLUE);  //토요일 파란색
        } else {
            itemView.setTextColor(Color.BLACK); //나머지 검정색
        }
        return itemView;
    }

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

    private int getMonthLastDay(int year, int month) {
        switch (month) {
            case 0:
            case 2:
            case 4:
            case 6:
            case 7:
            case 9:
            case 11:
                return (31);    //한 달이 31일인 달 계산

            case 3:
            case 5:
            case 8:
            case 10:
                return (30);    //한 달이 30일인 달 계산

            default:
                if( ( (year%4 == 0) && (year%100 != 0) ) || (year%400 == 0) ) { //윤년 계산
                    return (29);
                } else {
                    return (28);
                }
        }
    }
}

