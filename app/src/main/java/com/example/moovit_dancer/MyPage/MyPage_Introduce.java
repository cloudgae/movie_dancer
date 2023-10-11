package com.example.moovit_dancer.MyPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.moovit_dancer.R;

public class MyPage_Introduce extends AppCompatActivity {

    Button arw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page_introduce);

        Button arw = (Button) findViewById(R.id.goback);

        arw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyPage_Introduce.this, MyPage_0.class);
                startActivity(i);
                finish();
            }
        });
    }
}