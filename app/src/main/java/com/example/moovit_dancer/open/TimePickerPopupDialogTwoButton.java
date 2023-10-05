package com.example.moovit_dancer.open;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;

import com.example.moovit_dancer.MainActivity;
import com.example.moovit_dancer.R;

import org.checkerframework.checker.nullness.qual.NonNull;

public class TimePickerPopupDialogTwoButton extends Dialog {

    private Context context;
    private TimePickerPopupDialogClickListener TimePickerPopupDialogClickListener;
    private TimePicker timePicker;
    private TextView tvTitle, tvNegative, tvPositive;
    private String text;
//    private static String TAG = Open_8.TAG;
    private int setHourValue;
    private int setMinuteValue;

    public TimePickerPopupDialogTwoButton(@NonNull Context context,
                                          TimePickerPopupDialogClickListener TimePickerPopupDialogClickListener){
        super(context);
        this.context = context;
        this.TimePickerPopupDialogClickListener = TimePickerPopupDialogClickListener;
    }
    public void setText (String text){
        this.text = text;
    }
    public void setHourValue (int setHourValue){
        this.setHourValue = setHourValue;
    }
    public void setMinuteValue (int setMinuteValue){
        this.setMinuteValue = setMinuteValue;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timepickerdialog);

        timePicker = (TimePicker) findViewById(R.id.timepicker_alert_two);

        timePicker.setIs24HourView(false);
        timePicker.setHour(setHourValue);
        timePicker.setMinute(setMinuteValue);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                setHourValue = i;
                setMinuteValue = i1;
                Log.d(TAG, setHourValue + "시" + setMinuteValue + "분");
            }
        });

        //popup dialog 버튼 이벤트
        tvPositive = findViewById(R.id.time_btn_yes);
        tvPositive.setOnClickListener(view -> {
            this.TimePickerPopupDialogClickListener.onPositiveClick(setHourValue, setMinuteValue);
            dismiss();
        });

        tvNegative = findViewById(R.id.time_btn_no);
        tvNegative.setOnClickListener(view -> {
            this.TimePickerPopupDialogClickListener.onNegativeClick();
            dismiss();
        });
    }
}
