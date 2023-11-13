package com.example.moovit_dancer.open;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.moovit_dancer.CustomTimePicker;
import com.example.moovit_dancer.CustomTimePickerDialog;
import com.example.moovit_dancer.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
                showCustomTimePickerDialog(true); // 시작 시간 다이얼로그 표시
            }
        });

        time_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomTimePickerDialog(false); // 종료 시간 다이얼로그 표시
            }
        });

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
//
//                Intent i = new Intent(Open_6.this, Open_7.class);
//                startActivity(i);    //intent 에 명시된 액티비티로 이동
//                finish();    //현재 액티비티 종료
                Intent i = new Intent(Open_6.this, Open_7.class);
                i.putExtra("documentId", documentId);
                i.putExtra("selectedDate", selectedDate);
                startActivity(i);    //intent 에 명시된 액티비티로 이동
            }
        });
    }
    private void showCustomTimePickerDialog(boolean isStartTime) {
        // isStartTime을 통해 시작 시간인지 종료 시간인지 확인
        String dialogTitle = isStartTime ? "시작시간 선택" : "종료시간 선택";

        // 커스텀 다이얼로그를 생성하고 표시
        CustomTimePickerDialog timePickerDialog = new CustomTimePickerDialog(this);

        // 생성된 다이얼로그에 제목 설정
        timePickerDialog.setTitleText(dialogTitle);

        timePickerDialog.setOnTimeSetListener(new CustomTimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(CustomTimePicker view, int amPm, int hour, int minute) {
                // 선택된 시간을 처리하는 로직을 여기에 추가
                String amPmText = (amPm == CustomTimePicker.AM) ? "오전" : "오후";
//                String selectedTime = String.format("%s %02d:%02d", amPmText, hour, minute);
                int hour12 = (hour > 12) ? hour - 12 : hour;
                if (hour12 == 0) {
                    hour12 = 12;
                }
                // 텍스트뷰에 설정
                if (isStartTime) {
                    time_start.setText(String.format("%s %02d:%02d", amPmText, hour12, minute));
                    saveTimeToFirestore(hour, minute, true);
                } else {
                    time_end.setText(String.format("%s %02d:%02d", amPmText, hour12, minute));
                    saveTimeToFirestore(hour, minute, false);
                    // 이후에 필요한 작업 수행
                }
            }
        });

        timePickerDialog.show();
    }


    private void saveTimeToFirestore(int hour, int minute, boolean isStartTime) {
        // Firestore에 선택된 시간을 저장하는 코드 작성
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String documentId = getIntent().getStringExtra("documentId");
        DocumentReference docRef = db.collection("Class").document(documentId);

        String field = isStartTime ? "starttime" : "endtime";

        // 시간 값을 24시간 형태로 포맷팅
        String formattedTime = String.format("%02d:%02d", hour, minute);

        Map<String, Object> data = new HashMap<>();
        data.put(field, formattedTime);

        docRef.update(data);
    }


}