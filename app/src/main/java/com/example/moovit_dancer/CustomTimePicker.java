package com.example.moovit_dancer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

public class CustomTimePicker extends LinearLayout {
    private NumberPicker amPmPicker;
    private NumberPicker hourPicker;
    private NumberPicker minutePicker;
    private int margin = 20; // 원하는 마진 크기 (픽셀)

    public CustomTimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTimePicker(Context context) {
        super(context);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.custom_time_picker, this, true);

        amPmPicker = findViewById(R.id.amPmPicker);
        hourPicker = findViewById(R.id.hourPicker);
        minutePicker = findViewById(R.id.minutePicker);

        // 설정 및 초기화
        amPmPicker.setMinValue(0);
        amPmPicker.setMaxValue(1);
        amPmPicker.setDisplayedValues(new String[]{"오전", "오후"});

        hourPicker.setMinValue(1);
        hourPicker.setMaxValue(12);

        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(59);

        setPadding(50, 0, 50, 0);
    }


    public int getAmPm() {
        return amPmPicker.getValue();
    }

    public int getHour() {
        return hourPicker.getValue();
    }

    public int getMinute() {
        return minutePicker.getValue();
    }

    public void setAmPm(int value) {
        amPmPicker.setValue(value);
    }

    public void setHour(int value) {
        hourPicker.setValue(value);
    }

    public void setMinute(int value) {
        minutePicker.setValue(value);
    }
}
