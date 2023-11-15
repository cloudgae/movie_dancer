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

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    Button openclass, haveclass;
    ImageButton makeclass;
    /*Button mn1, mn2, mn3;*/
    ImageView noclassimg;

    private RecyclerView recyclerView;
    private DanceClassAdapter adapter;

    ImageView classimg;
    TextView classname, mozip, classdate;
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
                        openMyPage();
                        return true;
                }
                return false;
            }
        });

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Class").document("C7");

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // C7 문서의 open 필드의 값이 true인지 확인합니다
                        boolean isOpen = document.getBoolean("open");

                        // 만약 open 필드가 true이면 텍스트뷰를 수정합니다
                        if (isOpen) {

                            String imageUrl = "https://moovitbucket2.s3.ap-northeast-2.amazonaws.com/C7image/C7image"; // AWS S3 버킷의 이미지 URL로 변경
                            Glide.with(MainActivity.this).load(imageUrl).into(classimg);

                            String cname = document.getString("name");
                            String cmozip = document.getString("mozip");
                            String cdate = document.getString("date");
                            // 텍스트뷰를 수정하는 코드를 여기에 추가합니다
                            classname.setText(cname);  // 예시로 텍스트를 수정하는 코드입니다
                            mozip.setText(cmozip);
                            classdate.setText(cdate);


                            noclassimg.setVisibility(View.INVISIBLE);
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
                    noclassimg.setVisibility(View.VISIBLE);
                    // 작업이 실패한 경우에 대한 처리를 추가할 수도 있습니다
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
//        recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        dancerRef.collection("Class")
//                .whereEqualTo("open", true)
//                .get()
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        List<DanceClass> classes = new ArrayList<>();
//                        for (QueryDocumentSnapshot document : task.getResult()) {
//                            int cid = Integer.parseInt(document.getId());
//                            String cname = document.getString("name");
//                            String cmozip = document.getString("mozip");
//                            classes.add(new DanceClass(cid, cname, cmozip));
//                        }
//
//                        // Log를 사용하여 classes 리스트 확인
//                        for (DanceClass danceClass : classes) {
//                            Log.d("DanceClassAdapter", "ClassName: " + danceClass.getClassName() + ", Mozip: " + danceClass.getMozip());
//                        }
//
//                        // DanceClassAdapter에 데이터 갱신 및 RecyclerView에 설정
//                        adapter = new DanceClassAdapter(classes);
//                        recyclerView.setAdapter(adapter);
//                        adapter.notifyDataSetChanged(); // 데이터가 변경되었음을 알림
//                    } else {
//                        Log.e("DanceClassAdapter", "Error getting documents: ", task.getException());
//                    }
//                });


//        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //                db.collection("Class").document();

//        Map<String, Object> data = new HashMap<>();


//        openclass = findViewById(R.id.openclass);
//        haveclass = findViewById(R.id.haveclass);
        makeclass = findViewById(R.id.makeclass);
        /*mn1 = findViewById(R.id.menu1);
        mn2 = findViewById(R.id.menu2);
        mn3 = findViewById(R.id.menu3);*/

//        openclass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
////                FirebaseFirestore db = FirebaseFirestore.getInstance();
////                DocumentReference dbref = db.collection("Class").document();
////                DocumentReference classRef = db.collection("Class").document();
////// Later...
////                classRef.set(data);
//
//                Intent i = new Intent(MainActivity.this, Open_0.class);
//                startActivity(i);    //intent 에 명시된 액티비티로 이동
//                finish();    //현재 액티비티 종료
//
//
//            }
//        });

//        haveclass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(MainActivity.this, HomeClassList.class);
//                startActivity(i);
//                finish();
//            }
//        });

        makeclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Open_0.class);
                startActivity(i);
                finish();
            }
        });


        /*mn1.setOnClickListener(new View.OnClickListener() {
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
        });*/


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