package com.example.moovit_dancer;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.moovit_dancer.MyPage.MyPage_0;
import com.example.moovit_dancer.portfolio.Portfolio;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HomeClassInfo extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    TextView genretext, cname1, cname2, gradetext, daytext, locationtext, pricetext, cmozip, dday;
    ImageButton backbtn, rewritebtn, viewstudentbtn;
    ImageView cimage;

    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_class_info);

        backbtn = (ImageButton) findViewById(R.id.backbtn);
        genretext = (TextView) findViewById(R.id.genretext);
//        cname1 = (TextView) findViewById(R.id.cname1);
        cname2 = (TextView) findViewById(R.id.cname2);
        gradetext = (TextView) findViewById(R.id.gradetext);
        daytext = (TextView) findViewById(R.id.daytext);
        locationtext = (TextView) findViewById(R.id.locationtext);
//        pricetext = (TextView) findViewById(R.id.pricetext);
//        cmozip = (TextView) findViewById(R.id.cmozip);
        cimage = (ImageView) findViewById(R.id.cimage);
        pager = (ViewPager) findViewById(R.id.pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        rewritebtn = (ImageButton) findViewById(R.id.rewritebtn);
        viewstudentbtn = (ImageButton) findViewById(R.id.viewstudentbtn);
        dday = findViewById(R.id.dday);

        pager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(pager);
        pager.setAdapter(new HomeClassInfo.PageAdapter(getSupportFragmentManager(), this));

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

        Drawable drawable = getResources().getDrawable(R.drawable.genreicon);
        // dp 값을 px로 변환
        int widthInDp = 35;
        int heightInDp = 35;
        float scale = getResources().getDisplayMetrics().density;
        int widthInPx = (int) (widthInDp * scale + 0.5f);
        int heightInPx = (int) (heightInDp * scale + 0.5f);
        drawable.setBounds(0, 0, widthInPx, heightInPx);
        genretext.setCompoundDrawables(null, drawable, null, null);

        Drawable drawable1 = getResources().getDrawable(R.drawable.dayicon1207);
        // dp 값을 px로 변환
        int widthInDp1 = 35;
        int heightInDp1 = 35;
        float scale1 = getResources().getDisplayMetrics().density;
        int widthInPx1 = (int) (widthInDp1 * scale1 + 0.5f);
        int heightInPx1 = (int) (heightInDp1 * scale1 + 0.5f);
        drawable1.setBounds(0, 0, widthInPx1, heightInPx1);
        daytext.setCompoundDrawables(null, drawable1, null, null);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Class").document("C7");

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
//                    cname1.setText(document.getString("name"));
                    cname2.setText(document.getString("name"));
                    gradetext.setText("난이도 " + document.getString("difficulty"));
                    if (document.getString("frequency") == "1") {
                        daytext.setText("원데이 클래스");
                    } else if (document.getString("frequency") == "n") {
                        daytext.setText("다회차 클래스");
                    }
                    locationtext.setText(document.getString("location"));

                    String imageUrl = "https://moovitbucket2.s3.ap-northeast-2.amazonaws.com/C7image/C7image";
                    Glide.with(HomeClassInfo.this).load(imageUrl).diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true).into(cimage);

                    // 'date' 필드 값 가져오기
                    String dateString = document.getString("date");

                    // dateString을 Date 객체로 변환
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    Date eventDate = null;
                    try {
                        eventDate = dateFormat.parse(dateString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // 오늘 날짜 가져오기
                    Date today = Calendar.getInstance().getTime();

                    // 디데이 계산
                    int dDay = calculateDDay(today, eventDate);

                    dday.setText("D-" + dDay);
                }
            }
        });

        //뒤로가기버튼
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeClassInfo.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        viewstudentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeClassInfo.this, HomeClassDetail.class);
                startActivity(i);
                finish();
            }
        });
    }
    private int calculateDDay(Date today, Date eventDate) {
        // 디데이 계산 (오늘 날짜와 이벤트 날짜의 차이를 일수로 계산)
        long diffInMillies = eventDate.getTime() - today.getTime();
        long diffInDays = diffInMillies / (24 * 60 * 60 * 1000);
        return (int) (diffInDays + 1); // 디데이는 오늘 포함
    }

    static class PageAdapter extends FragmentStatePagerAdapter {
        PageAdapter(FragmentManager fm, Context context) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new HomeClassInfo_tab1();
            } else if (position == 1) {
                return new HomeClassInfo_tab2();
            } else {
                return new HomeClassInfo_tab3();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }


        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "상세정보";
            } else if (position == 1) {
                return "후기";
            } else {
                return "Q&A";
            }
        }
    }


    private void loadImageFromS3(String imageName) {
        // 디바이스 독립적인 픽셀 (dp)를 픽셀로 변환
        int widthInPixels = (int) (360 * getResources().getDisplayMetrics().density);
        int heightInPixels = (int) (200 * getResources().getDisplayMetrics().density);

        Glide.with(HomeClassInfo.this)
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
