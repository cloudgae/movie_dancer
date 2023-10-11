package com.example.moovit_dancer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.moovit_dancer.MyPage.MyPage_OpenedClass;

public class HomeClassList extends AppCompatActivity {

    Button classinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_class_list);

        classinfo = findViewById(R.id.classinfo);

        classinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeClassList.this, HomeClassDetail.class);
                startActivity(i);
                finish();
            }
        });
    }
}