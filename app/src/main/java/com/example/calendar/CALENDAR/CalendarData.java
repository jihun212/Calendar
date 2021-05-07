package com.example.calendar.CALENDAR;

class CalendarData {
    private int year=0;
    private int month=0;
    private int day=0;

    private int [][] intData = new int[6][7];
    private String [] stringData =new String[42];

    String[] StringConverter(){    // int형 배열을 string형 배열로 변환하는 메소드
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

    void setCalendar(int y, int m) {    // 주어진 년,월 데이터를 토대로 2차원 정수배열을 생성하는 메소드
        this.year = y;
        this.month = m;
        this.day = 0;
        mkCal(year, month);
    }

    private void mkCal(int y, int m) {
        initCal();                              // 배열 초기화
        int tDate = calcDate(y, m);     // 필요한 년월의 1일까지의 총 일수 연산
        int dow = tDate % 7;                    // 시작 요일(dayOfweek) 연산
        int ld = calcFinalDate(y, m);             // 해당 달력의 마지막날 연산
        setCal(dow, ld);                        // 위에 데이터를 토대로 2차원 정수배열에 달력의 데이터를 삽입
    }

    private void setCal(int dow, int lastDay) {     // 2차원 정수 배열에 달력 데이터를 삽입하는 구문
        int d = 1;
        for (int i = 0; i < 6; i++) {
            for (int j = dow; j <7; j++) {          // 제일 첫째주의 시작일 기준을 잡기 위한 for 구문임
                intData[i][j] = d++;
                if (d > lastDay) return;
            }
            if (i == 0)
                dow = 0;
        }
    }

    private void initCal() {     // 정수배열 초기화
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 7; j++)
                intData[i][j] = 0;
    }

    private int calcDate(int year, int month) {        // 0년 1월 1일부터 해당 일까지의 총 일수 연산 메소드
        int[] months = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int tDate = 0;

        tDate += (year - 1) * 365;
        tDate += year / 4 - year / 100 + year / 400;

        if (((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) && (month <= 2))
            tDate--;

        for (int i = 0; i < month - 1; i++)
            tDate += months[i];

        tDate += 1;
        return tDate;
    }

    int calcFinalDate(int year, int month) {                       // 해당 월의 마지막 일을 리턴하는 메소드
        int[] ldom = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int d = 1;

        if (month != 2)
            d = ldom[month - 1];
        else {
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)   // 2월 윤년일 때 실행
                d = ldom[month - 1] + 1;
            else
                d = ldom[month-1];
        }
        return d;
    }
}

//
//public class MonthCalendarAdapter extends FragmentStateAdapter {
//    private static int NUM_ITEMS=100;
//
//    private MonthItem[] items;  // MonthItem.java 참조
//    private int countColumn = 7;    // 1주일
//
//    int mStartDay;
//    int startDay;
//    int curYear;    // 현재년도
//    int curMonth;   // 현재달
//
//
//    Context mContext;
//    int firstDay;
//    int lastDay;
//
//    Calendar mCalendar;
//
//    // 초기화 메소드 설정
//    // 1개월의 일별 데이터를 담고 있을 수 있는 MonthItem의 배열 객체 생성
//    // 원하는 날짜 지정
//    // 현재 달은 4월 이므로 3월입력
//    // recalculate(), resetDayNumbers() 사용
//    private void init() {
//        items = new MonthItem[7 * 6];
//        mCalendar = Calendar.getInstance();
//        mCalendar.set(2021,3,1);    //////원하는 날짜 지정///////
//
//        recalculate();
//        resetDayNumbers();
//    }
//
//    // 이전 월로 이동 시 일별 데이터 새로 계산
//    // 이전 월로 이동 시 MONTH -1
//    // recalculate(), resetDayNumbers() 사용
//    public void setPreviousMonth() {
//        mCalendar.add(Calendar.MONTH, -1);
//        recalculate();
//        resetDayNumbers();
//    }
//
//    // 다음 월로 이동 시 일별 데이터 새로 계산
//    // 다음 월로 이동 시 MONTH +1
//    // recalculate(), resetDayNumbers() 사용
//    public void setNextMonth() {
//        mCalendar.add(Calendar.MONTH, 1);
//        recalculate();
//        resetDayNumbers();
//    }
//
//    // 지정한 월의 일별 데이터를 새로 계산하는 메소드 정의
//    // 해당하는 달의 날짜 수를 계산하는 알고리즘
//    private void resetDayNumbers() {
//        for (int i = 0; i < 42; i++) {
//            // dayNumber 계산
//            int dayNumber = (i+1) - firstDay;   // getFirstDay 참조
//            if (dayNumber < 1 || dayNumber > lastDay) { // getMonthLastDay 참조
//                dayNumber = 0;
//            }
//            // data item 저장
//            items[i] = new MonthItem(dayNumber);
//        }
//    }
//
//    // 새로운 달 재계산 메소드
//    public void recalculate() {
//        mCalendar.set(Calendar.DAY_OF_MONTH,1);
//        int dayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK);
//
//        firstDay = getFirstDay(dayOfWeek);
//        mStartDay = mCalendar.getFirstDayOfWeek();
//
//        curYear = mCalendar.get(Calendar.YEAR);
//        curMonth = mCalendar.get(Calendar.MONTH);
//
//        lastDay = getMonthLastDay(curYear, curMonth);
//        startDay = getFirstDayOfWeek();
//    }
//
//
//    // 뷰의 모양 정의 및 리턴
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//
//        MonthItemView itemView;
//
//        // ConvertView는 안드로이드가 사용자의 편의성을 위해 제공하는 객체
//        // 이전에 안드로이드에 불러온 뷰에 대한 정보를 저장하는 객체
//        // 전에 불러왔던 뷰에 대한 정보를 재사용할때 사용하는 객체
//        if (convertView == null) {
//            itemView = new MonthItemView(mContext);
//        } else {
//            itemView = (MonthItemView) convertView;
//        }
//
//        // 그리드 구조에 접근
//        GridView.LayoutParams params = new GridView.LayoutParams(
//                GridView.LayoutParams.MATCH_PARENT, 120);
//
//        int columnIndex = position % countColumn;
//        itemView.setItem(items[position]);  // 날짜 정보
//        itemView.setLayoutParams(params);   // 그리드 뷰 칸의 각 속성 크기 조정
//        itemView.setPadding(1,1,1,1);   //양쪽 Padding 각 1씩
//        itemView.setGravity(Gravity.CENTER);    //글자 위치 CENTER
//
//        if (columnIndex == 0) {
//            itemView.setTextColor(Color.RED);   //일요일 빨간색 설정
//        } else if (columnIndex == 6) {
//            itemView.setTextColor(Color.BLUE);  //토요일 파란색 설정
//        } else {
//            itemView.setTextColor(Color.BLACK); //나머지 검정색 설정
//        }
//        return itemView;
//    }
//
//    // 첫번째 요일 메소드
//    private int getFirstDay(int dayOfWeek) {
//        int result = 0;
//        if (dayOfWeek == Calendar.SUNDAY) {
//            result = 0;
//        } else if (dayOfWeek == Calendar.MONDAY) {
//            result = 1;
//        } else if (dayOfWeek == Calendar.TUESDAY) {
//            result = 2;
//        } else if (dayOfWeek == Calendar.WEDNESDAY) {
//            result = 3;
//        } else if (dayOfWeek == Calendar.THURSDAY) {
//            result = 4;
//        } else if (dayOfWeek == Calendar.FRIDAY) {
//            result = 5;
//        } else if (dayOfWeek == Calendar.SATURDAY) {
//            result = 6;
//        }
//        return result;
//    }
//
//    // 첫번째 주의 첫번째 요일 구하기 메소드
//    public static int getFirstDayOfWeek() {
//        int startDay = Calendar.getInstance().getFirstDayOfWeek();
//        if (startDay == Calendar.SATURDAY) {
//            return Time.SATURDAY;
//        } else if (startDay == Calendar.MONDAY) {
//            return Time.MONDAY;
//        } else {
//            return Time.SUNDAY;
//        }
//    }
//
//    // 한 달이 31일, 30일, 혹은 2월 달의 윤년을 계산하기 위한 메소드
//    private int getMonthLastDay(int year, int month) {
//        switch (month) {
//            case 0:
//            case 2:
//            case 4:
//            case 6:
//            case 7:
//            case 9:
//            case 11:
//                return (31);    //한 달 31일 리턴
//
//            case 3:
//            case 5:
//            case 8:
//            case 10:
//                return (30);    //한 달 30일 리턴
//
//            default:
//                if( ( (year%4 == 0) && (year%100 != 0) ) || (year%400 == 0) ) { //윤년 계산
//                    return (29);    //한 달 29일 리턴
//                } else {
//                    return (28);    //한 달 28일 리턴
//                }
//        }
//    }
//
//    public int getCurYear() {
//        return curYear;
//    }
//
//    public int getCurMonth() {
//        return curMonth;
//    }
//
//    // getCount
//    // 배열 사이즈 정의
//    // 7*6=42개의 데이터 존재
//    @Override
//    public int getCount() {
//        return 7 * 6;
//    }
//
//    // 아이템의 position(위치) 에 대한 값 정의
//    // 위치에 담을 데이터 리턴
//    @Override
//    public Object getItem(int position) {
//        return items[position];
//    }
//
//    // 각 공간에 대한 위치 정보 정의
//    // 아이템 위치 확인하여 position 리턴
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//
//
//
//
//
//
//
//    public MonthCalendarAdapter(@NonNull Fragment fragment) {
//        super(fragment);
//    }
//
//    @NonNull
//    @Override
//    public Fragment createFragment(int position) {
//        int year = position;
//        int month = position+1;
//
//        return MonthCalenderFragment.newInstance(year,month);
//
//    }
//
//
//
//    @Override
//    public int getItemCount() {
//        return NUM_ITEMS;
//    }
//}