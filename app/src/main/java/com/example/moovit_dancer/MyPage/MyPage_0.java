package com.example.moovit_dancer.MyPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.moovit_dancer.MainActivity;
import com.example.moovit_dancer.R;
import com.example.moovit_dancer.portfolio.Portfolio;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MyPage_0 extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

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

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        openMainActivity();
                        return true;
                    case R.id.portfolio:
                        openPortfolio();
                        return true;
                    case R.id.mypage:
//                        openMyPage();
                        return true;
                }
                return false;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.mypage);
    }
    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void openPortfolio() {
        Intent intent = new Intent(this, Portfolio.class);
        startActivity(intent);
    }

    private void openMyPage() {
        Intent intent = new Intent(this, MyPage_0.class);
        startActivity(intent);
    }
}