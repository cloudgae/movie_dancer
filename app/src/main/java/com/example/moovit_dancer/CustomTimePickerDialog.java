package com.example.moovit_dancer;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class CustomTimePickerDialog extends Dialog {
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
    }
}
