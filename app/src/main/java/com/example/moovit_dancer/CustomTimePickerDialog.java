package com.example.moovit_dancer;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

public class CustomTimePickerDialog extends Dialog {
    private OnTimeSetListener listener;
    private CustomTimePicker customTimePicker;
    // 다이얼로그 내에서 사용될 뷰들
    private TextView textTimePicker;


    public CustomTimePickerDialog(Context context) {
        super(context);
        // 크기 설정: 가로 320dp, 세로 WRAP_CONTENT
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 320, getContext().getResources().getDisplayMetrics());
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
        // 위치 설정: 화면 하단에 다이얼로그 배치
        Window window = getWindow();
        window.setGravity(android.view.Gravity.BOTTOM);

        // 배경 모양 설정: 모서리를 둥글게 만든 모양을 배경으로 설정
        Drawable background = getContext().getResources().getDrawable(R.drawable.custom_dialog_background);
        window.setBackgroundDrawable(background);

        // 다이얼로그에 레이아웃 설정
        setContentView(R.layout.timepickerdialog);

//        // TimePicker 참조 가져오기
//        timePicker = findViewById(R.id.timepicker_alert_two);
        // CustomTimePicker 참조 가져오기
        customTimePicker = findViewById(R.id.timepicker_alert_two);
        textTimePicker = findViewById(R.id.text_timepicker);

        // "선택 완료" 버튼 클릭 리스너 설정
        ImageButton timepickerButton = findViewById(R.id.timepicker_btn);
        timepickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int amPm = customTimePicker.getAmPm();
                int hourOfDay = customTimePicker.getHour();
                int minute = customTimePicker.getMinute();
//                if (listener != null) {
//                    listener.onTimeSet(customTimePicker, amPm, hourOfDay, minute);
//                }
                // 12시간 형식을 24시간 형식으로 변경
                if (amPm == CustomTimePicker.PM) {
                    hourOfDay = (hourOfDay == 12) ? 12 : hourOfDay + 12;
                } else {
                    hourOfDay = (hourOfDay == 12) ? 0 : hourOfDay;
                }

                if (listener != null) {
                    listener.onTimeSet(customTimePicker, amPm, hourOfDay, minute);
                }

                dismiss();
            }
        });



    }
    // 다이얼로그의 텍스트 설정 메서드
    public void setTitleText(String title) {
        if (textTimePicker != null) {
            textTimePicker.setText(title);
        }
    }
    public void setOnTimeSetListener(OnTimeSetListener listener) {
        this.listener = listener;
    }

    public interface OnTimeSetListener {
        void onTimeSet(CustomTimePicker view, int amPm, int hour, int minute);
    }

}

