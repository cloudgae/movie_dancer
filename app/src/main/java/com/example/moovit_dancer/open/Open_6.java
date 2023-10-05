package com.example.moovit_dancer.open;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.moovit_dancer.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Open_6 extends AppCompatActivity {

    Button time_start, time_end;
    ImageButton backkey, nextkey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open6);

        time_start = (Button) findViewById(R.id.time_start);
        time_end = (Button) findViewById(R.id.time_end);
        backkey = (ImageButton) findViewById(R.id.backkey);
        nextkey = (ImageButton) findViewById(R.id.nextkey);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> data = new HashMap<>();
        DocumentReference classRef = db.collection("Class").document("C");



        time_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendarInstance = Calendar.getInstance();
                int hr = calendarInstance.get(Calendar.HOUR_OF_DAY);
                int min = calendarInstance.get(Calendar.MINUTE);
                TimePickerDialog.OnTimeSetListener onTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        if(view.isShown()){
                            calendarInstance.set(Calendar.HOUR_OF_DAY, i);
                            calendarInstance.set(Calendar.MINUTE, i1);
                        }
                    }
                };

                TimePickerDialog timePickerDialog = new TimePickerDialog(Open_6.this,
                        R.style.timepicker_Theme,
                        onTimeListener, hr, min, false);

                timePickerDialog.setTitle("시간");

                Objects.requireNonNull(timePickerDialog.getWindow())
                        .setBackgroundDrawableResource(android.R.color.transparent);
                timePickerDialog.show();


            }
        });
        time_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendarInstance = Calendar.getInstance();
                int hr = calendarInstance.get(Calendar.HOUR_OF_DAY);
                int min = calendarInstance.get(Calendar.MINUTE);
                TimePickerDialog.OnTimeSetListener onTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        if(view.isShown()){
                            calendarInstance.set(Calendar.HOUR_OF_DAY, i);
                            calendarInstance.set(Calendar.MINUTE, i1);
                        }
                    }
                };

                TimePickerDialog timePickerDialog = new TimePickerDialog(Open_6.this,
                        R.style.timepicker_Theme,
                        onTimeListener, hr, min, false);

                timePickerDialog.setTitle("시간");

                Objects.requireNonNull(timePickerDialog.getWindow())
                        .setBackgroundDrawableResource(android.R.color.transparent);
                timePickerDialog.show();
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

                Intent i = new Intent(Open_6.this, Open_7.class);
                startActivity(i);    //intent 에 명시된 액티비티로 이동
                finish();    //현재 액티비티 종료
            }
        });
    }
}