package com.example.moovit_dancer.MyPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.moovit_dancer.MainActivity;
import com.example.moovit_dancer.R;

public class MyPage_0 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        Button arw = (Button) findViewById(R.id.goback);
        Button btn1 = (Button) findViewById(R.id.classlist);
        Button btn2 = (Button) findViewById(R.id.likelist);
        Button btn3 = (Button) findViewById(R.id.mytype_info);

        arw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyPage_0.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyPage_0.this, MyPage_OpenedClass.class);
                startActivity(i);
                finish();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyPage_0.this, MyPage_UploadList.class);
                startActivity(i);
                finish();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyPage_0.this, MyPage_Introduce.class);
                startActivity(i);
                finish();
            }
        });
    }
}