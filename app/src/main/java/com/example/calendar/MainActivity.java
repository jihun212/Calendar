package com.example.calendar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.calendar.CALENDAR.mCalendarPagerAdapter;
import com.example.calendar.CALENDAR.wCalendarPagerAdapter;
import com.example.calendar.CALENDAR.wCalendarFrag;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    ViewPager2 mCalViewPager, wCalViewPager;                            // 월간달력과 주간달력 뷰페이저 생성
    FragmentStateAdapter adapter;                                       // 뷰페이저와 페이저어댑터 생성
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
        ymBar.setTitle("초기 앱바 타이틀");                               // 앱바 생성

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
        //mCalViewPager.setVisibility(View.VISIBLE);
        //wCalViewPager.setVisibility(View.GONE);
        // 주간달력 뷰는 GONE 상태로, 월간은 VISIBLE 상태로 전환

        int y = pos/12;
        int m = getMonthfromPos(pos);

        ymBar.setTitle(y +"년 "+ m +"월 ");
        ymBar.setSubtitle("월간 달력");                   // 입력받은 포지션 값을 해석하고 앱바 타이틀 조정

        if(is2 != 0)                                    // 중요! 뷰페이저 생성은 1회성으로 한정함.
            mCalViewPager.setCurrentItem(pos);
        else{
            is2++;
            adapter = new mCalendarPagerAdapter(this);// 페이저어댑터 정의
            mCalViewPager.setAdapter(adapter);// 뷰페이저에 어댑터 장착

            //mCalViewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);

            mCalViewPager.setCurrentItem(pos);                                       // 앱 실행시 제일 처음 보여주는 페이지 설정
            mCalViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() { // 사용자가 화면을 스와이프할때,
                @Override
                public void onPageSelected(int position) {              // 스와이프 후 달력이 표현하는 월에 따라 앱바 변경
                    int year = position/12;
                    int month = getMonthfromPos(position);

                    ymBar.setTitle(year+"년 "+month+"월 ");
                    curPosition = position;
                }
//                @Override
//                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
//                @Override
//                public void onPageScrollStateChanged(int state) { }
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
        ymBar.setSubtitle("1주차");                       // 주간 달력에도 년,월 값은 필요로 함

        adapter = new wCalendarPagerAdapter(this,y,m);// 페이저어댑터 정의
        wCalViewPager.setAdapter(adapter);// 뷰페이저에 어댑터 장착
        wCalViewPager.setCurrentItem(0);                                                // 앱 실행시 제일 처음 보여주는 페이지 설정
        wCalViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() { // 사용자가 화면을 스와이프할때,
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

