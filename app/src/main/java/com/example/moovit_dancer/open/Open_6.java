package com.example.moovit_dancer.open;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.moovit_dancer.CustomTimePickerDialog;
import com.example.moovit_dancer.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Open_6 extends AppCompatActivity {

    TextView time_start, time_end;
    ImageButton backkey, nextkey;
    TextView date1;
    String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open6);

        time_start = (TextView) findViewById(R.id.time_start);
        time_end = (TextView) findViewById(R.id.time_end);
        backkey = (ImageButton) findViewById(R.id.backkey);
        nextkey = (ImageButton) findViewById(R.id.nextkey);
        date1 = (TextView) findViewById(R.id.date1);
        selectedDate="";
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> data = new HashMap<>();
        //1028
        // 현재 액티비티에서 문서 ID를 받음
        String documentId = getIntent().getStringExtra("documentId");
        DocumentReference docRef = db.collection("Class").document(documentId);
//        DocumentReference classRef = db.collection("Class").document("C");

        selectedDate = getIntent().getStringExtra("selectedDate");

        // 날짜 포맷을 설정
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy년 mm월 dd일");

        try {
            Date date = inputFormat.parse(selectedDate);

            if (date != null) {
                String formattedDate = outputFormat.format(date);
                date1.setText(formattedDate);
            } else {
                // date가 null인 경우에 대한 처리
                // 예를 들어, 오류 메시지를 표시하거나 기본 날짜 값을 설정할 수 있습니다.
            }
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            // 파싱 오류 처리
            // 예를 들어, 오류 메시지를 표시하거나 기본 날짜 값을 설정할 수 있습니다.
        }


        time_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomTimePickerDialog(); // 커스텀 타임피커 다이얼로그를 표시하는 메서드 호출
            }
        });
        time_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showCustomTimePickerDialog(); // 커스텀 타임피커 다이얼로그를 표시하는 메서드 호출

                }
            });
//        time_start.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final Calendar calendarInstance = Calendar.getInstance();
//                int hr = calendarInstance.get(Calendar.HOUR_OF_DAY);
//                int min = calendarInstance.get(Calendar.MINUTE);
//                TimePickerDialog.OnTimeSetListener onTimeListener = new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
//                        if(view.isShown()){
//                            calendarInstance.set(Calendar.HOUR_OF_DAY, i);
//                            calendarInstance.set(Calendar.MINUTE, i1);
//                        }
//                    }
//                };
//
//                TimePickerDialog timePickerDialog = new TimePickerDialog(Open_6.this,
//                        R.style.timepicker_Theme,
//                        onTimeListener, hr, min, false);
//
//                timePickerDialog.setTitle("시간");
//                timePickerDialog.getWindow().setGravity(Gravity.BOTTOM);
//
//                Objects.requireNonNull(timePickerDialog.getWindow())
//                        .setBackgroundDrawableResource(android.R.color.transparent);
//                timePickerDialog.show();
//
//
//            }
//        });
//        time_end.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final Calendar calendarInstance = Calendar.getInstance();
//                int hr = calendarInstance.get(Calendar.HOUR_OF_DAY);
//                int min = calendarInstance.get(Calendar.MINUTE);
//                TimePickerDialog.OnTimeSetListener onTimeListener = new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
//                        if(view.isShown()){
//                            calendarInstance.set(Calendar.HOUR_OF_DAY, i);
//                            calendarInstance.set(Calendar.MINUTE, i1);
//                        }
//                    }
//                };
//
//                TimePickerDialog timePickerDialog = new TimePickerDialog(Open_6.this,
//                        R.style.timepicker_Theme,
//                        onTimeListener, hr, min, false);
//
//                timePickerDialog.setTitle("시간");
//
//                Objects.requireNonNull(timePickerDialog.getWindow())
//                        .setBackgroundDrawableResource(android.R.color.transparent);
//                timePickerDialog.show();
//            }
//        });

        backkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Open_6.this, Open_5.class);
                startActivity(i);    //intent 에 명시된 액티비티로 이동
                finish();    //현재 액티비티 종료
            }
        });

        nextkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Open_6.this, Open_7.class);
                startActivity(i);    //intent 에 명시된 액티비티로 이동
                finish();    //현재 액티비티 종료
            }
        });
    }
    private void showCustomTimePickerDialog() {
        // 커스텀 다이얼로그를 생성하고 표시
        CustomTimePickerDialog timePickerDialog = new CustomTimePickerDialog(this);
        timePickerDialog.show();
    }
}