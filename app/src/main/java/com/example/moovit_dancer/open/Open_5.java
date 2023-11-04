package com.example.moovit_dancer.open;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;

import com.example.moovit_dancer.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Open_5 extends AppCompatActivity {

    ImageButton backkey, nextkey;
    CalendarView calendarView;
    public String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open5);

        backkey = (ImageButton) findViewById(R.id.backkey);
        nextkey = (ImageButton) findViewById(R.id.nextkey);
        calendarView = (CalendarView) findViewById(R.id.calendarView);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> data = new HashMap<>();
        //1028
        // 현재 액티비티에서 문서 ID를 받음
        String documentId = getIntent().getStringExtra("documentId");
        DocumentReference docRef = db.collection("Class").document(documentId);
//        DocumentReference classRef = db.collection("Class").document("C");

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                selectedDate = i + "-" + (i1 + 1) + "-" + i2;
            }
        });

        backkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Open_5.this, Open_4.class);
                startActivity(i);    //intent 에 명시된 액티비티로 이동
                finish();    //현재 액티비티 종료
            }
        });

        nextkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Open_5.this, Open_6.class);
                i.putExtra("documentId", documentId);
                i.putExtra("selectedDate", selectedDate);
                startActivity(i);    //intent 에 명시된 액티비티로 이동
            }
        });
    }
}