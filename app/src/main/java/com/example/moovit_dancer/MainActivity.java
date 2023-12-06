package com.example.moovit_dancer;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.moovit_dancer.MyPage.MyPage_0;
import com.example.moovit_dancer.open.Open_0;
import com.example.moovit_dancer.portfolio.Portfolio;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    Button openclass, haveclass;
    ImageButton makeclass;
    /*Button mn1, mn2, mn3;*/
    ImageView noclassimg;
    boolean isOpen = false;
    private RecyclerView recyclerView;
    private DanceClassAdapter adapter;

    ImageView classimg;
    TextView classname, mozip, classdate, dancer_name;
    LinearLayout openlistlayout;
    Button classinfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        noclassimg = (ImageView) findViewById(R.id.noclassimg);
        classimg = (ImageView) findViewById(R.id.classimg);
        classname = (TextView) findViewById(R.id.classname);
        mozip = (TextView) findViewById(R.id.mozip);
        classdate = (TextView) findViewById(R.id.classdate);
        openlistlayout = (LinearLayout) findViewById(R.id.openlistlayout);
        classinfo = (Button) findViewById(R.id.classinfo);
        dancer_name = (TextView) findViewById(R.id.dancer_name);

        // 초기에 숨겨놓기
        openlistlayout.setVisibility(View.INVISIBLE);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
//                        openMainActivity();
                        return true;
                    case R.id.portfolio:
                        openPortfolio();
                        return true;
                    case R.id.mypage:
                        openMyPage();
                        return true;
                }
                return false;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.home);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Class").document("C7");

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        // C7 문서의 open 필드의 값이 true인지 확인합니다
                        Boolean openField = document.getBoolean("open");

                        // 만약 open 필드가 true이면 텍스트뷰를 수정합니다
                        if (openField != null && openField) {
                            openlistlayout.setVisibility(View.VISIBLE);
                            noclassimg.setVisibility(View.INVISIBLE);

                            String imageUrl = "https://moovitbucket2.s3.ap-northeast-2.amazonaws.com/C7image/C7image";
                            Glide.with(MainActivity.this).load(imageUrl).diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true).into(classimg);

                            String cname = document.getString("name");
                            String cmozip = document.getString("mozip");
                            String cdate = document.getString("date");
                            String cst = document.getString("starttime");
                            String cet = document.getString("endtime");

                            // 텍스트뷰를 수정하는 코드를 여기에 추가합니다
                            classname.setText(cname);
                            mozip.setText(Objects.requireNonNull(cmozip) + "명");
                            classdate.setText(cdate + "  " + cst + " ~ " + cet);

                        } else {
                            openlistlayout.setVisibility(View.INVISIBLE);
                            noclassimg.setVisibility(View.VISIBLE);
                            // open 필드가 false인 경우에 대한 처리를 추가할 수도 있습니다
                        }
                    } else {
                        noclassimg.setVisibility(View.VISIBLE);
                        openlistlayout.setVisibility(View.INVISIBLE);
                        // 문서가 존재하지 않는 경우에 대한 처리를 추가할 수도 있습니다
                    }
                } else {
                    openlistlayout.setVisibility(View.INVISIBLE);
                    noclassimg.setVisibility(View.VISIBLE);
                    // 작업이 실패한 경우에 대한 처리를 추가할 수도 있습니다
                    Log.e(TAG, "Error getting documents: ", task.getException());
                }
            }
        });

        classinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, HomeClassInfo.class);
                startActivity(i);
                finish();
            }
        });
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//                        db.collection("Class").document();

        DocumentReference docRef2 = db.collection("Dancer").document("profile1");
        docRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document2 = task.getResult();
                    dancer_name.setText(document2.getString("dancername") +  " 댄서님,\n안녕하세요.");
                }
//
            }
        });

        makeclass = findViewById(R.id.makeclass);


        makeclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Open_0.class);
                startActivity(i);
                finish();
            }
        });


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