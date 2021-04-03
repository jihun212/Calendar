package com.example.calendar;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MonthViewActivity_junbeom extends AppCompatActivity {

    GridView gv_calendar;
    CalendarAdapter_junbeom monthViewAdapter;
    TextView year_month;
    int curYear;
    int curMonth;
    int curDay;
    int curWeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_junbeom);





        /*
        Intent intent = getIntent();
        year = intent.getIntExtra("year", -1);
        month = intent.getIntExtra("month", -1);

        if (year == -1 || month == -1) {
            year = Calendar.getInstance().get(Calendar.YEAR);
            month = Calendar.getInstance().get(Calendar.MONTH);
        }

        Calendar cal = Calendar.getInstance();
        cal.set(2021,3,1);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

         */

        //월별 캘린더뷰 객체 참조
        gv_calendar = findViewById(R.id.gv_calendar);
        monthViewAdapter = new CalendarAdapter_junbeom(this);
        gv_calendar.setAdapter(monthViewAdapter);

        //리스너 설정
        gv_calendar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MonthItem curItem = (MonthItem) monthViewAdapter.getItem(position);
                int day = curItem.getDay();
            }
        });


        //년, 월 텍스트 가져오기
        year_month = findViewById(R.id.year_month);
        setMonthText();

        //이전 월로 넘어가는 이벤트 처리
        Button prevBtn = findViewById(R.id.previous);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthViewAdapter.setPreviousMonth();
                monthViewAdapter.notifyDataSetChanged();

                setMonthText();

                /*
                Intent intent = new Intent(getApplicationContext(),
                        MainActivity_junbeom.class);
                intent.putExtra("year", year);
                intent.putExtra("month", month-1);

                startActivity(intent);
                finish();

                 */
            }
        });

        //다음 월로 넘어가는 이벤트 처리
        Button nextBtn = findViewById(R.id.next);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthViewAdapter.setNextMonth();
                monthViewAdapter.notifyDataSetChanged();

                setMonthText();
            }
        });

        //토스트메세지
        gv_calendar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                curYear = monthViewAdapter.getCurYear();
                curMonth = monthViewAdapter.getCurMonth();
                curDay = monthViewAdapter.getCurDay();
                curWeek = monthViewAdapter.getCurWeek();
                final int month = curMonth;
                final int year = curYear;
                final int day = curDay;
                final int week = curWeek;
                //String day_full = year + "년 "+ (month+1)  + "월 " + day + "일 " + week + "요일";
                String day_full = year + "년 "+ (month+1)  + "월 " + day + "일 " + week + "요일";

                Toast myToast = Toast.makeText(MonthViewActivity_junbeom.this,day_full, Toast.LENGTH_LONG);
                myToast.show();
            }
        });
    }

    private void setMonthText() {
        curYear = monthViewAdapter.getCurYear();
        curMonth = monthViewAdapter.getCurMonth();

        year_month.setText(curYear + "년" + (curMonth+1) + "월");
    }
}

