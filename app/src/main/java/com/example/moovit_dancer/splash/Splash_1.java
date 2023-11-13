package com.example.moovit_dancer.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.moovit_dancer.MainActivity;
import com.example.moovit_dancer.R;

public class Splash_1 extends AppCompatActivity {
    Button gotomain;
    ImageButton tutorintro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash1);

        tutorintro = (ImageButton) findViewById(R.id.tutorintro);
        gotomain = (Button) findViewById(R.id.gotomain);

        tutorintro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                TODO : 이동 대상 인텐트 변경(소개 작성 클래스로)
                Intent i = new Intent(Splash_1.this, Onboarding.class);

                startActivity(i);    //intent 에 명시된 액티비티로 이동

                finish();    //현재 액티비티 종료
            }
        });

        gotomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                TODO : 이동 대상 인텐트 변경(소개 작성 클래스로)
                Intent i = new Intent(Splash_1.this, MainActivity.class);

                startActivity(i);    //intent 에 명시된 액티비티로 이동

                finish();    //현재 액티비티 종료
            }
        });
    }
}