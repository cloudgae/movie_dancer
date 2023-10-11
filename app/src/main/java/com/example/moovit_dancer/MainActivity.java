package com.example.moovit_dancer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.moovit_dancer.MyPage.MyPage_0;
import com.example.moovit_dancer.open.Open_0;
import com.example.moovit_dancer.portfolio.Portfolio;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button openclass,haveclass;
    Button mn1, mn2, mn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //                db.collection("Class").document();

//        Map<String, Object> data = new HashMap<>();


        openclass = findViewById(R.id.openclass);
        haveclass = findViewById(R.id.haveclass);
        mn1 = findViewById(R.id.menu1);
        mn2 = findViewById(R.id.menu2);
        mn3 = findViewById(R.id.menu3);

        openclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                FirebaseFirestore db = FirebaseFirestore.getInstance();
//                DocumentReference dbref = db.collection("Class").document();
//                DocumentReference classRef = db.collection("Class").document();
//// Later...
//                classRef.set(data);

                Intent i = new Intent(MainActivity.this, Open_0.class);
                startActivity(i);	//intent 에 명시된 액티비티로 이동
                finish();	//현재 액티비티 종료


            }
        });

        haveclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, HomeClassList.class);
                startActivity(i);
                finish();
            }
        });
        mn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        mn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Portfolio.class);
                startActivity(i);
                finish();
            }
        });

        mn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, MyPage_0.class);
                startActivity(i);
                finish();
            }
        });
    }

}