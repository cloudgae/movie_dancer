package com.example.moovit_dancer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeClassDetail extends AppCompatActivity {

    Button detail;
    Button arw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_class_detail);

        Button detail = (Button) findViewById(R.id.seedetail);
        Button arw = (Button) findViewById(R.id.goback);

       detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeClassDetail.this, HomeClassInfo.class);
                startActivity(i);
                finish();
            }
        });

        arw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeClassDetail.this, HomeClassList.class);
                startActivity(i);
                finish();
            }
        });


    }
}