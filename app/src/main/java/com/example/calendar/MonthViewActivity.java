package com.example.calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class MonthViewActivity extends AppCompatActivity {

    private int year,month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = getIntent();
        year = intent.getIntExtra("year",-1);
        month = intent.getIntExtra("month",-1);

        if (year == -1 || month == -1){
            year = Calendar.getInstance().get(Calendar.YEAR);
            month = Calendar.getInstance().get(Calendar.MONTH);
        }

        //Calendar cal = Calendar.getInstance();
        //cal.set(2021,10,1);
        //int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);


        TextView yearMonthTV = findViewById(R.id.year_month); // year_month 가져오기
        //yearMonthTV.setText(year + "년" + month + "월");
        yearMonthTV.setText(year + "년" + (month+1) + "월");


        Button prevBtn = findViewById(R.id.previous);
        prevBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MonthViewActivity.class);
                if(month < 1) {
                    intent.putExtra("year", year -1);
                    intent.putExtra("month", 11);
                }else{
                    intent.putExtra("year", year);
                    intent.putExtra("month", month-1);
                }

                startActivity(intent);
                finish();
            }
        });


        Button nextBtn = findViewById(R.id.next);
        nextBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MonthViewActivity.class);
                if(month == 11) {
                    intent.putExtra("year", year + 1);
                    intent.putExtra("month", 0);
                }else{
                    intent.putExtra("year", year);
                    intent.putExtra("month", month+1);
                }

                startActivity(intent);
                finish();
            }
        });
    }
}