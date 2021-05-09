package com.example.calendar.CALENDAR;

class CalendarData {
    private int year=0;
    private int month=0;
    private int day=0;

    private int [][] intData = new int[6][7];
    private String [] stringData =new String[42];

    String[] StringConverter(){
        // int형 배열을 string형 배열로 변환
        int count=0;

        for(int i=0;i<6;i++)
            for(int j=0;j<7;j++){
                if(intData[i][j]==0)
                    stringData[count]= "";
                else
                    stringData[count] = "" + intData[i][j];
                count++;
            }
        return stringData;
    }

    void setCalendar(int y, int m) {
        // 주어진 년,월 데이터를 토대로 2차원 정수배열을 생성하는 메소드
        this.year = y;
        this.month = m;
        this.day = 0;
        mkCal(year, month);
    }

    private void mkCal(int y, int m) {
        initCal();
        // 배열 초기화
        int totalDate = calcDate(y, m);
        // 필요한 년월의 1일까지의 총 일수 연산
        int dow = totalDate % 7;
        // 시작 요일(dayOfweek) 연산
        int ld = calcFinalDate(y, m);
        // 해당 달력의 마지막날 연산
        setCal(dow, ld);
        // 위에 데이터를 토대로 2차원 정수배열에 달력의 데이터를 삽입
    }

    private void setCal(int dow, int lastDay) {
        // 2차원 정수 배열에 달력 데이터를 삽입하는 구문
        int d = 1;
        for (int i = 0; i < 6; i++) {
            for (int j = dow; j <7; j++) {
                // 제일 첫째주의 시작일 기준을 잡기 위한 for 구문
                intData[i][j] = d++;
                if (d > lastDay) return;
            }
            if (i == 0)
                dow = 0;
        }
    }

    private void initCal() {
        // 정수배열 초기화
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 7; j++)
                intData[i][j] = 0;
    }

    private int calcDate(int year, int month) {
        // 해당 일까지 총 일수 계산
        int[] months = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int totalDate = 0;

        totalDate += (year - 1) * 365;
        totalDate += year / 4 - year / 100 + year / 400;

        if (((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) && (month <= 2))
            totalDate--;

        for (int i = 0; i < month - 1; i++)
            totalDate += months[i];

        totalDate += 1;
        return totalDate;
    }

    int calcFinalDate(int year, int month) {
        // 해당 월의 마지막 일수 계산
        int[] lastday = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int d = 1;

        if (month != 2)
            d = lastday[month - 1];
        else {
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
                // 윤년 계산
                d = lastday[month - 1] + 1;
            else
                d = lastday[month-1];
        }
        return d;
    }
}
