package com.example.moovit_dancer.open;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moovit_dancer.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Open_7 extends AppCompatActivity {

    ImageButton backkey, nextkey;
    TextView startdate, enddate, minstudent, maxstudent;
    private Calendar startCalendar;
    private Calendar endCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open7);

        backkey = (ImageButton) findViewById(R.id.backkey);
        nextkey = (ImageButton) findViewById(R.id.nextkey);

        startdate = (TextView) findViewById(R.id.startdate);
        enddate = (TextView) findViewById(R.id.enddate);
        minstudent = (TextView) findViewById(R.id.minstudent);
        maxstudent = (TextView) findViewById(R.id.maxstudent);

        startCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> data = new HashMap<>();
        // 현재 액티비티에서 문서 ID를 받음
        String documentId = getIntent().getStringExtra("documentId");
        DocumentReference docRef = db.collection("Class").document(documentId);
//        DocumentReference classRef = db.collection("Class").document("C");


        backkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Open_7.this, Open_6.class);
                startActivity(i);    //intent 에 명시된 액티비티로 이동
                finish();    //현재 액티비티 종료
            }
        });

        nextkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent i = new Intent(Open_7.this, Open_8.class);
//                startActivity(i);    //intent 에 명시된 액티비티로 이동
//                finish();    //현재 액티비티 종료
                Intent i = new Intent(Open_7.this, Open_8.class);
                i.putExtra("documentId", documentId);
                startActivity(i);    //intent 에 명시된 액티비티로 이동
            }
        });

        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(true);
            }
        });
        enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(false);
            }
        });
        minstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNumberPickerDialog(true);
            }
        });

        maxstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNumberPickerDialog(false);
            }
        });
    }
    private void showDatePickerDialog(final boolean isStartDate) {
        int year, month, day;
        Calendar minDateCalendar = Calendar.getInstance(); // 최소 선택 날짜를 오늘로 설정

        if (isStartDate) {
            year = startCalendar.get(Calendar.YEAR);
            month = startCalendar.get(Calendar.MONTH);
            day = startCalendar.get(Calendar.DAY_OF_MONTH);
        } else {
            year = endCalendar.get(Calendar.YEAR);
            month = endCalendar.get(Calendar.MONTH);
            day = endCalendar.get(Calendar.DAY_OF_MONTH);
        }

        // 변수를 final로 선언하여 오류 해결
        final DatePickerDialog[] datePickerDialog = {null};

        datePickerDialog[0] = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, month, dayOfMonth, 0, 0, 0);

                // 날짜 포맷을 설정
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String formattedDate = dateFormat.format(selectedDate.getTime());

                // 선택된 날짜를 텍스트뷰에 설정
                if (isStartDate) {
                    startdate.setText(formattedDate);
                    saveDateToFirestore("startdate", selectedDate.getTime());
                    // startDate를 선택하면 endDate의 선택 가능 날짜를 설정
                    endCalendar.set(Calendar.YEAR, year);
                    endCalendar.set(Calendar.MONTH, month);
                    endCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    // endDate 선택 시 startDate 이후의 날짜만 선택 가능하도록 설정
                    datePickerDialog[0].getDatePicker().setMinDate(selectedDate.getTimeInMillis());
                } else {
                    // endDate를 startDate 이후로 설정
                    if (selectedDate.after(startCalendar) || selectedDate.equals(startCalendar)) {
                        enddate.setText(formattedDate);
                        saveDateToFirestore("enddate", selectedDate.getTime());
                    } else {
                        // 선택된 endDate가 startDate보다 이전이면 처리 (예: 에러 메시지 표시)
                        Toast.makeText(Open_7.this, "endDate는 startDate 이후로 설정하세요.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, year, month, day);

        // startDate와 endDate 모두 오늘 이전의 날짜를 선택할 수 없도록 설정
        datePickerDialog[0].getDatePicker().setMinDate(minDateCalendar.getTimeInMillis());

        // 날짜 선택 제한 (현재 날짜 이전으로 선택 불가능)
        datePickerDialog[0].getDatePicker().setMinDate(minDateCalendar.getTimeInMillis());


        // 다이얼로그 표시
        datePickerDialog[0].show();
    }

    private void showNumberPickerDialog(final boolean isMinStudent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.YourNumberPickerTheme));
//        builder.setTitle("학생 수 선택");

//        // NumberPicker 설정
//        final NumberPicker numberPicker = new NumberPicker(new ContextThemeWrapper(this, R.style.YourAlertDialogTheme));
//        numberPicker.setMinValue(1);
//        numberPicker.setMaxValue(50);
        // NumberPicker를 감싸는 커스텀 레이아웃 설정
        View customLayout = getLayoutInflater().inflate(R.layout.custom_number_picker, null);
        NumberPicker numberPicker = customLayout.findViewById(R.id.numberpicker);

        // NumberPicker 설정
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(50);

        // 현재 값 설정
        try {
            if (isMinStudent) {
                numberPicker.setValue(Integer.parseInt(minstudent.getText().toString()));
            } else {
                numberPicker.setValue(Integer.parseInt(maxstudent.getText().toString()));
            }
        } catch (NumberFormatException e) {
            // 텍스트가 유효한 정수가 아닌 경우 처리
            // 오류 메시지를 표시하거나 기본값을 설정하거나 원하는 방식으로 처리 가능
            e.printStackTrace(); // 디버깅을 위해 예외 로그를 출력
            numberPicker.setValue(1); // 기본값 설정 (이 경우 1)
        }

//        builder.setView(numberPicker);
        builder.setView(customLayout);

        // 확인 버튼 클릭 시 이벤트 처리
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int selectedValue = numberPicker.getValue();

                // 선택된 값을 텍스트뷰에 설정
                if (isMinStudent) {
                    minstudent.setText(String.valueOf(selectedValue));
                    saveToFirestore("minstudent", selectedValue);
                } else {
                    maxstudent.setText(String.valueOf(selectedValue));
                    saveToFirestore("maxstudent", selectedValue);
                }
            }
        });

        // 취소 버튼 클릭 시 이벤트 처리
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 취소 버튼 클릭 시 아무 동작 없음
            }
        });

        builder.show();
    }



    private void saveDateToFirestore(String field, Date selectedDate) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String documentId = getIntent().getStringExtra("documentId");
        DocumentReference docRef = db.collection("Class").document(documentId);

        // 날짜 값을 Firestore에 저장
        Map<String, Object> data = new HashMap<>();
        data.put(field, selectedDate);

        docRef.update(data);
    }
    private void saveToFirestore(String field, int selectedValue) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String documentId = getIntent().getStringExtra("documentId");
        DocumentReference docRef = db.collection("Class").document(documentId);

        // 값을 Firestore에 저장
        Map<String, Object> data = new HashMap<>();
        data.put(field, selectedValue);

        docRef.update(data);
    }
}