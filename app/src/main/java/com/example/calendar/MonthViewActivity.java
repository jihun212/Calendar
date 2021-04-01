package com.example.calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class MonthViewActivity extends AppCompatActivity implements View.OnClickListener {

    private Button previous;
    private Button next;
    private Calendar calendar;

    private int year, month;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;

        previous = findViewById(R.id.previous);
        previous.setOnClickListener(this);

        next = findViewById(R.id.next);
        next.setOnClickListener(this);
    }
    private void setGridCellAdapterToDate(int month, int year) {

    }
    @Override
    public void onClick(View v) {
        if (v == previous) {
            if (month <= 1) {
                month = 12;
                year--;
            } else {
                month--;
            }
            setGridCellAdapterToDate(month, year);
        }
        if (v == next) {
            if (month > 11) {
                month = 1;
                year++;
            } else {
                month++;
            }
            setGridCellAdapterToDate(month, year);
        }
    }






        Intent intent = getIntent();
        year = intent.getIntExtra("year",-1);
        month = intent.getIntExtra("month",-1);

        if (year == -1 || month == -1){
            year = Calendar.getInstance().get(Calendar.YEAR);
            month = Calendar.getInstance().get(Calendar.MONTH);
        }

        Calendar cal = Calendar.getInstance();
        cal.set(2021,3,1);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);


        TextView yearMonthTV = findViewById(R.id.year_month); // year_month 가져오기
        yearMonthTV.setText(year + "년" + month + "월");


        previous = findViewById(R.id.previous);
        previous.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MonthViewActivity.class);
                intent.putExtra("year", year);
                intent.putExtra("month",month-1);

                startActivity(intent);
                finish();
            }
        });


        next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MonthViewActivity.class);
                intent.putExtra("year", year);
                intent.putExtra("month",month+1);

                startActivity(intent);
                finish();
            }
        }



    });
    }
}