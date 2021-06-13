package com.example.calendar;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class ScheduleActivity extends AppCompatActivity implements OnMapReadyCallback {

    int year;
    int month;
    int date;

    final int REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION = 0;
    private FusedLocationProviderClient mFusedLocationClient;
    Location mLastLocation;
    Button search_btn;
    EditText address;
    EditText schedule_title;
    TimePicker schedule_start;
    TimePicker schedule_end;
    EditText place;
    EditText memo;

    private DBHelper mDbHelper;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
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
        search_btn = (Button)findViewById(R.id.search);
        place = findViewById(R.id.place);

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
                EditText idTextView=findViewById(R.id.sql_id);
                String _id = idTextView.getText().toString();
                AlertDialog.Builder dlg = new AlertDialog.Builder(ScheduleActivity.this);
                dlg.setMessage("정말 삭제하시겠습니까?");
                dlg.setPositiveButton("예",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ScheduleActivity.this,"삭제", Toast.LENGTH_SHORT).show();
                        mDbHelper.deleteUserBySQL(_id);
                        viewAllToTextView();
                    }
                });
                dlg.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ScheduleActivity.this,"실행 취소", Toast.LENGTH_LONG).show();
                        viewAllToTextView();
                    }
                });
                dlg.show();
            }

        });
        getLastLocation();
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

        Cursor cursor = mDbHelper.getAllUsersBySQL();

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

    private void getLastLocation() {
        // 1. 위치 접근에 필요한 권한 검사 및 요청
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    ScheduleActivity.this,            // MainActivity 액티비티의 객체 인스턴스를 나타냄
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},        // 요청할 권한 목록을 설정한 String 배열
                    REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION    // 사용자 정의 int 상수. 권한 요청 결과를 받을 때
            );
            return;
        }

        // 2. Task<Location> 객체 반환
        Task task = mFusedLocationClient.getLastLocation();

        // 3. Task가 성공적으로 완료 후 호출되는 OnSuccessListener 등록
        task.addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(ScheduleActivity.this::onMapReady);
                // 4. 마지막으로 알려진 위치(location 객체)를 얻음.
                if (location != null) {
                    mLastLocation = location;
                } else
                    Toast.makeText(getApplicationContext(),
                            "No location detected",
                            Toast.LENGTH_SHORT)
                            .show();
            }
        });
    }
    public void onMapReady(GoogleMap googleMap) {
        if(mLastLocation ==null)
            return;

        search_btn.setOnClickListener(new Button.OnClickListener(){
            Address ad;

            @Override
            public void onClick(View v) {

                try {
                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.KOREA);
                    String str=place.getText().toString();
                    List<Address> place = geocoder.getFromLocationName(str,1);
                    if (place.size() >0) {
                        ad = (Address) place.get(0);
                    }
                } catch (IOException e) {
                    Log.e(getClass().toString(),"Failed in using Geocoder.", e);
                    return;
                }
                Double latitude = ad.getLatitude();
                Double longitude = ad.getLongitude();

                LatLng point = new LatLng(latitude,longitude);
                MarkerOptions mOptions = new MarkerOptions();
                mOptions.title("search result");
                googleMap.addMarker(
                        new MarkerOptions().
                                position(point).
                                title(".."));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point,15));

            }

        });

    }

}
