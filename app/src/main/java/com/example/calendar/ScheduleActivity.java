package com.example.calendar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.nio.Buffer;


public class ScheduleActivity extends AppCompatActivity {

    int year;
    int month;
    int date;


    EditText schedule_title;
    TimePicker schedule_start;
    TimePicker schedule_end;
    EditText place;
    EditText memo;

    private DBHelper mDbHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);

        schedule_title=findViewById(R.id.schedule_title);

        schedule_start=findViewById(R.id.schedule_start);
        int start_hour= TimePickerSplit(schedule_start)[0];
        int start_min= TimePickerSplit(schedule_start)[1];

        schedule_end=findViewById(R.id.schedule_end);
        int end_hour= TimePickerSplit(schedule_end)[0];
        int end_min= TimePickerSplit(schedule_end)[1];

        place=findViewById(R.id.place);
        memo=findViewById(R.id.memo);

        mDbHelper = new DBHelper(this);

        Intent intent = getIntent();
        int year = intent.getIntExtra("year",0);
        int month =intent.getIntExtra("month",0);
        int date =intent.getIntExtra("date",0);

        schedule_title.setText(year+"년"+month+"월"+date+"일");

        Button save_btn = (Button)findViewById(R.id.save);
        Button cancel_btn = (Button)findViewById(R.id.cancel);
        Button delete_btn = (Button)findViewById(R.id.delete);


        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ScheduleActivity.this,"저장", Toast.LENGTH_SHORT).show();
                mDbHelper.insertUserBySQL(
                        schedule_title.getText().toString(),
                        start_hour,
                        start_min,
                        end_hour,
                        end_min,
                        place.getText().toString(),
                        memo.getText().toString()
                );
                viewAllToTextView();
            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ScheduleActivity.this,"삭제", Toast.LENGTH_SHORT).show();
                EditText idTextView=findViewById(R.id.sql_id);
                String _id = idTextView.getText().toString();
                mDbHelper.deleteUserBySQL(_id);
                viewAllToTextView();
            }

        });
    }

    int [] TimePickerSplit(TimePicker timepicker){
        int hour, min;
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){
            hour = timepicker.getHour();
            min= timepicker.getMinute();
        }else{
            hour = timepicker.getCurrentHour();
            min=timepicker.getCurrentMinute();
        }
        return new int[] {hour,min};
    }


    //SQLite 관련 함수
    //저장 삭제 확인용으로 지워도 무방
    private void viewAllToTextView() {
        TextView result = (TextView)findViewById(R.id.result);

        Cursor cursor = mDbHelper.getAllMemosBySQL();

        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            buffer.append(cursor.getInt(0)+" \t");
            buffer.append(cursor.getString(1)+" \t");
            buffer.append(cursor.getString(2)+" \t");
            buffer.append(cursor.getString(3)+" \t");
            buffer.append(cursor.getString(4)+" \t");
            buffer.append(cursor.getString(5)+" \t");
            buffer.append(cursor.getString(6)+" \t");
            buffer.append(cursor.getString(7)+"\n");
        }
        result.setText(buffer);
    }
}
