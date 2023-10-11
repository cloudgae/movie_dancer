package com.example.moovit_dancer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.moovit_dancer.MyPage.MyPage_0;

public class HomeClassInfo extends AppCompatActivity {


    Button arw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_class_info);

        Button arw = (Button) findViewById(R.id.goback);

        arw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeClassInfo.this, HomeClassDetail.class);
                startActivity(i);
                finish();
            }
        });
    }
}