package com.example.moovit_dancer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeClassDetail extends AppCompatActivity {

    Button detail;
    Button arw;
    ImageView cimage, studentimg;
    TextView cname, cdate, dday, studentname, studentgrade;
    LinearLayout linear1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_class_detail);

        detail = (Button) findViewById(R.id.seedetail);
        arw = (Button) findViewById(R.id.goback);
        cimage = (ImageView) findViewById(R.id.cimage);
        studentimg = (ImageView) findViewById(R.id.studentimg);
        cname = (TextView) findViewById(R.id.cname);
        cdate = (TextView) findViewById(R.id.cdate);
        dday = (TextView) findViewById(R.id.dday);
        studentname = (TextView) findViewById(R.id.studentname);
        studentgrade = (TextView) findViewById(R.id.studentgrade);
        linear1 = (LinearLayout) findViewById(R.id.linear1);

        linear1.setVisibility(View.INVISIBLE);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Class").document("C7");

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    cname.setText(document.getString("name"));
                    cdate.setText(document.getString("date"));

                    String ismozip = document.getString("mozip");

                    if("1".equals(ismozip)){
                        linear1.setVisibility(View.VISIBLE);
                    }
                    else {
                        linear1.setVisibility(View.INVISIBLE);
                    }


                    // AWS S3에서 이미지를 로드하여 이미지뷰에 설정
                    String imageName = "C7image/C7image"; // S3 버킷 내 이미지 파일의 경로 및 파일명
                    loadImageFromS3(imageName);
                }
            }
        });


       detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeClassDetail.this, HomeClassInfo.class);
                startActivity(i);
                finish();
            }
        });

       //뒤로가기
        arw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeClassDetail.this, HomeClassInfo.class);
                startActivity(i);
                finish();
            }
        });


    }

    private void loadImageFromS3(String imageName) {
        // 디바이스 독립적인 픽셀 (dp)를 픽셀로 변환
        int widthInPixels = (int) (360 * getResources().getDisplayMetrics().density);
        int heightInPixels = (int) (200 * getResources().getDisplayMetrics().density);

        Glide.with(HomeClassDetail.this)
                .load("https://moovitbucket2.s3.amazonaws.com/" + imageName)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        e.printStackTrace(); // 로드 실패 시 오류 메시지 출력
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .centerCrop()
                .override(widthInPixels, heightInPixels)  // 디바이스 독립적인 픽셀로 크기 지정
                .into(cimage);
    }
}