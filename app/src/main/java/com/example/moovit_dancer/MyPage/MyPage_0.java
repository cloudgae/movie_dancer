package com.example.moovit_dancer.MyPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.moovit_dancer.MainActivity;
import com.example.moovit_dancer.R;
import com.example.moovit_dancer.portfolio.Portfolio;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyPage_0 extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    TextView user_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        Button arw = (Button) findViewById(R.id.goback);
        ImageButton btn1 = (ImageButton) findViewById(R.id.classlist);
        ImageButton btn2 = (ImageButton) findViewById(R.id.likelist);
        ImageButton btn3 = (ImageButton) findViewById(R.id.mytype_info);
        user_profile = findViewById(R.id.user_profile);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef2 = db.collection("Dancer").document("profile1");
        docRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document2 = task.getResult();
                    user_profile.setText(document2.getString("dancername"));
                }
//
            }
        });

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