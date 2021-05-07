package com.example.calendar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.calendar.CALENDAR.mCalendarPagerAdapter;
import com.example.calendar.CALENDAR.wCalendarPagerAdapter;
import com.example.calendar.CALENDAR.wCalendarFrag;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    ViewPager mCalViewPager, wCalViewPager;                            // 월간달력과 주간달력 뷰페이저 생성
    FragmentStatePagerAdapter adapter;                                       // 뷰페이저와 페이저어댑터 생성
    Calendar javaCalendar;                                              // 실행 당일의 날짜를 따오기 위한 자바캘린더
    int curPosition, mode, is1 = 0, is2 = 0, weekDATA;
    ActionBar ymBar;                                                    // 앱 최상단 앱바 생성

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);                         // activity_main

        mCalViewPager = findViewById(R.id.vpPager);
        wCalViewPager = findViewById(R.id.vpPager2);                    // 뷰페이저 장착

        ymBar=getSupportActionBar();
        ymBar.setTitle("초기 앱바 타이틀");                                // 앱바 생성

        javaCalendar = Calendar.getInstance();                          // 캘린더 객체 생성

        if(is1 == 0) {                  // 초기 실행 시, 달력 기본 포지션을 오늘 날짜에 의거하여 설정함. 1회성
            curPosition = javaCalendar.get(Calendar.YEAR) * 12 + javaCalendar.get(Calendar.MONTH);
            setMonthlyCalendar(curPosition);
            is1++;
        }
    }

    //액션버튼 메뉴 액션바에 집어 넣기
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //액션버튼을 클릭했을때의 동작.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.month_view) {
            if(mode == 0) {
                return true;
            }
            Toast.makeText(this, "월간 달력", Toast.LENGTH_SHORT).show();
            setMonthlyCalendar(curPosition);
            mode = 0;
            return true;
        }

        if (id == R.id.week_view) {
            if(mode == 1){
                return true;
            }
            Toast.makeText(this, "주간 달력", Toast.LENGTH_SHORT).show();
            setWeeklyCalendar(curPosition);
            mode = 1;
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setMonthlyCalendar(int pos) {            // 월간달력 표시하는 메소드. 입력받은 포지션에 따라 달력 표시
        mCalViewPager.setVisibility(View.VISIBLE);
        wCalViewPager.setVisibility(View.GONE);
        // 주간달력 뷰는 GONE 상태로, 월간은 VISIBLE 상태로 전환

        int y = pos/12;
        int m = getMonthfromPos(pos);

        ymBar.setTitle(y +"년 "+ m +"월 ");
        //ymBar.setSubtitle("월간 달력");                   // 입력받은 포지션 값을 해석하고 앱바 타이틀 조정

        if(is2 != 0)                                    // 중요! 뷰페이저 생성은 1회성으로 한정함.
            mCalViewPager.setCurrentItem(pos);
        else{
            is2++;
            adapter = new mCalendarPagerAdapter(getSupportFragmentManager());
            mCalViewPager.setAdapter(adapter);
            //fpAdapter = new mCalendarPagerAdapter(getSupportFragmentManager());      // 페이저어댑터 정의
            //mCalViewPager.setAdapter(fpAdapter);                                     // 뷰페이저에 어댑터 장착
            mCalViewPager.setCurrentItem(pos);                                       // 앱 실행시 제일 처음 보여주는 페이지 설정
            mCalViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // 사용자가 화면을 스와이프할때,
                @Override
                public void onPageSelected(int position) {              // 스와이프 후 달력이 표현하는 월에 따라 앱바 변경
                    int year = position/12;
                    int month = getMonthfromPos(position);

                    ymBar.setTitle(year+"년 "+month+"월 ");
                    curPosition = position;
                }

                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
                @Override
                public void onPageScrollStateChanged(int state) { }
            });
        }
    }

    public void setWeeklyCalendar(int pos) {             // 주간 달력을 표시하는 메소드

        mCalViewPager.setVisibility(View.GONE);
        wCalViewPager.setVisibility(View.VISIBLE);
        // 월간 달력하고 반대로 설정

        int y = pos/12;
        int m = getMonthfromPos(pos);

        ymBar.setTitle(y +"년 "+ m +"월 ");
        //ymBar.setSubtitle("1주차");                       // 주간 달력에도 년,월 값은 필요로 함

        adapter = new wCalendarPagerAdapter(getSupportFragmentManager(),y,m);
        wCalViewPager.setAdapter(adapter);

        //FragmentStateAdapter adapter2 = new wCalendarPagerAdapter(this,y,m);
        //wCalViewPager.setAdapter(adapter2);

        //fpAdapter = new wCalendarPagerAdapter(getSupportFragmentManager(),y,m);         // 페이저어댑터 정의
        //wCalViewPager.setAdapter(fpAdapter);                                            // 뷰페이저에 어댑터 장착

        wCalViewPager.setCurrentItem(0);                                                // 앱 실행시 제일 처음 보여주는 페이지 설정

        wCalViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // 사용자가 화면을 스와이프할때,
            @Override
            public void onPageSelected(int position) {
                weekDATA = position;
                ymBar.setSubtitle(weekDATA+1 + "주차");
                wCalendarFrag.setWeekDATA(weekDATA);
            }
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
            @Override
            public void onPageScrollStateChanged(int state) { }
        });
    }

    public int getMonthfromPos(int pos){        // 개발 편의를 위한 짧은 메소드. 포지션 값에서 월 값을 추출함
        if((pos%12)==0) return 1;
        else return (pos%12)+1;
    }
}


//
//public class MonthViewActivity extends AppCompatActivity {
//
//    GridView gv_calendar;   //그리드뷰
//    CalendarBaseAdapter monthViewAdapter;   //캘린더어댑터
//    TextView year_month;    //텍스트뷰
//    int curYear;    //현재년도
//    int curMonth;   //현재달
//
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.main_menu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.month_view:
//
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.main_container, new MonthViewFragment());
//                fragmentTransaction.commit();
//                Toast.makeText(getApplicationContext(), "month_view", Toast.LENGTH_SHORT).show();
//                return true;
//
//
//            case R.id.week_view:
//
//                fragmentManager = getSupportFragmentManager();
//                fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.main_container, new WeekViewFragment());
//                fragmentTransaction.commit();
//                Toast.makeText(getApplicationContext(), "week_view", Toast.LENGTH_SHORT).show();
//                return true;
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main_junbeom);
//
////        교수님이 알려주신 Intent 코드
////        Intent intent = getIntent();
////        year = intent.getIntExtra("year", -1);
////        month = intent.getIntExtra("month", -1);
////
////        if (year == -1 || month == -1) {
////            year = Calendar.getInstance().get(Calendar.YEAR);
////            month = Calendar.getInstance().get(Calendar.MONTH);
////        }
////
////        Calendar cal = Calendar.getInstance();
////        cal.set(2021,3,1);
////        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
//
//
//        //gv_calendar 객체 참조
//        gv_calendar = findViewById(R.id.gv_calendar);
//        monthViewAdapter = new CalendarBaseAdapter(this);
//        gv_calendar.setAdapter(monthViewAdapter);
//
//        // gv_calendar에 클릭리스너 설정
//        gv_calendar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                MonthItem curItem = (MonthItem) monthViewAdapter.getItem(position);
//                int day = curItem.getDay();
//
//                // 토스트메세지 설정
//                // monthViewAdapter 에서 getCurYear(), getCurMonth() 받아오기
//                // final의 기능 : 지역변수를 상수화 시켜준다
//                // 해당 월에 맞는 토스트 메시지를 보여줘야 하기때문에
//                // String day_full 에서 (month+1)을 해준다
//                // 날짜가 표시되지 않는 공간에 대해서는 토스트메세지를 출력하지 않는다.
//                curYear = monthViewAdapter.getCurYear();
//                curMonth = monthViewAdapter.getCurMonth();
//                final int month = curMonth;
//                final int year = curYear;
//                String day_full = year + "년 "+ (month+1)  + "월 " + day + "일 ";
//                if (day != 0) {
//                    Toast myToast = Toast.makeText(MonthViewActivity.this, day_full, Toast.LENGTH_LONG);
//                    myToast.show();
//                }
//            }
//        });
//
//
//        // 년, 월 텍스트 가져오기
//        year_month = findViewById(R.id.year_month);
//        year_month.setSelected(true);
//        setMonthText();
//
//        // 이전 월로 넘어가는 버튼이벤트 처리
//        // setPreviousMonth(), notifyDataSetChanged(), setMonthText() 사용
//        Button prevBtn = findViewById(R.id.previous);
//        prevBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                monthViewAdapter.setPreviousMonth();
//                monthViewAdapter.notifyDataSetChanged();
//
//                setMonthText();
//
////                교수님이 알려주신 Intent코드
////                Intent intent = new Intent(getApplicationContext(),
////                        MainActivity_junbeom.class);
////                intent.putExtra("year", year);
////                intent.putExtra("month", month-1);
////
////                startActivity(intent);
////                finish();
//            }
//        });
//
//        // 다음 월로 넘어가는 버튼이벤트 처리
//        // setNextMonth(), notifyDataSetChanged(), setMonthText() 사용
//        Button nextBtn = findViewById(R.id.next);
//        nextBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                monthViewAdapter.setNextMonth();
//                monthViewAdapter.notifyDataSetChanged();
//
//                setMonthText();
//            }
//        });
//    }
//
//    // year_month에 현재년도 + "년" + (현재달+1) + "월" 입력
//    private void setMonthText() {
//        curYear = monthViewAdapter.getCurYear();
//        curMonth = monthViewAdapter.getCurMonth();
//
//        year_month.setText(curYear + "년" + (curMonth+1) + "월");
//    }
//}
//
