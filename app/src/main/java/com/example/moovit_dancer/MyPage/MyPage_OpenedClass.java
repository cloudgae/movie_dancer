package com.example.moovit_dancer.MyPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.moovit_dancer.HomeClassDetail;
import com.example.moovit_dancer.R;

import org.w3c.dom.Text;

public class MyPage_OpenedClass extends AppCompatActivity {

    Button classinfo;
    Button arw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page_opened_class);

        classinfo = findViewById(R.id.classinfo);

        classinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyPage_OpenedClass.this, HomeClassDetail.class);
                startActivity(i);
                finish();
            }
        });

        Button arw = (Button) findViewById(R.id.goback);

        arw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyPage_OpenedClass.this, MyPage_0.class);
                startActivity(i);
                finish();
            }
        });
    }
}