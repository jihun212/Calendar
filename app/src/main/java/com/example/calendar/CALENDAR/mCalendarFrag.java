package com.example.calendar.CALENDAR;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.example.calendar.DBHelper;
import com.example.calendar.MainActivity;
import com.example.calendar.R;
import com.example.calendar.ScheduleActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class mCalendarFrag extends Fragment {

    private int year;
    private int month;
    String[] items;
    private DBHelper mDbHelper;

    private OnTimePickerSetListener onTimePickerSetListener;

    mCalendarFrag(int y, int m) {
        year = y;
        month = m;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View calView = inflater.inflate(R.layout.fragment_m_calendar, container, false);

        CalendarData cal = new CalendarData();
        // 달력 데이터 객체 생성
        cal.setCalendar(year, month);
        // 객체에 달력 데이터 적용

        items = cal.StringConverter();
        // 데이터를 문자열 배열에 삽입

        GridView gView = calView.findViewById(R.id.mCalendarGridView);
        // calView 에 들어갈 그리드뷰 생성
        CalendarBaseAdapter adapt = new CalendarBaseAdapter(getActivity(),android.R.layout.simple_list_item_1,items);
        // base 어댑터 생성

        gView.setAdapter(adapt);
        // 그리드뷰에 어댑터 적용
        String day_full = year + "년 "+ month  + "월 ";

        gView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(items[position] != "") {
                    Toast.makeText(getActivity(), day_full + items[position] + "일", Toast.LENGTH_SHORT).show();
                    int date = Integer.parseInt(items[position]);
                    onTimePickerSetListener.onTimePickerSet(year,month,date);
                }else{
                    AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                    dlg.setTitle("dialog 확인용"); //제목
                    dlg.setMessage("빈공간입니다 ."); // 메시지
                    dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //토스트 메시지
                            Toast.makeText(getActivity(), "확인을 눌르셨습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dlg.show();
                }

            }
        });


        return calView;
    }
    public interface OnTimePickerSetListener{
        void onTimePickerSet(int year, int month, int date);
    }

    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof OnTimePickerSetListener){
            onTimePickerSetListener = (OnTimePickerSetListener)context;
        }else{
            throw new RuntimeException(context.toString()+" must implement OnTimePickerListener");
        }
    }
    public void onDetach(){
        super.onDetach();
        onTimePickerSetListener = null;
    }


}


