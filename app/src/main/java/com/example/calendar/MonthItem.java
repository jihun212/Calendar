package com.example.calendar;

//일자 정보를 담기 위한 클래스 정의
public class MonthItem {
    private int dayValue;

    public MonthItem(int day) {
        dayValue = day;
    }

    public int getDay() {
        return dayValue;
    }

    public void setDay(int day) {
        this.dayValue = day;
    }



}