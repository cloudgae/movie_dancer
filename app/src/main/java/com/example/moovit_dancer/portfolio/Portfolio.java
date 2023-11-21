package com.example.moovit_dancer.portfolio;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.os.Environment;
import android.widget.ImageView;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.moovit_dancer.MainActivity;
import com.example.moovit_dancer.MyPage.MyPage_0;
import com.example.moovit_dancer.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Portfolio extends AppCompatActivity {
    ImageButton addbtn;
    private static final int PICK_VIDEO_REQUEST = 1;
    private File videoFile; // 동영상 파일 변수 추가
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);

        ViewPager pager = findViewById(R.id.pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        addbtn = findViewById(R.id.addbtn);

        pager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(pager);
        pager.setAdapter(new PageAdapter(getSupportFragmentManager(), this));

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
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
//                        openPortfolio();
                        return true;
                    case R.id.mypage:
                        openMyPage();
                        return true;
                }
                return false;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.portfolio);
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
    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "동영상 선택"), PICK_VIDEO_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_VIDEO_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri videoUri = data.getData();
            String videoPath = getRealPathFromURI(videoUri);
            if (videoPath != null) {
                videoFile = new File(videoPath);

                // 비동기 작업으로 S3 업로드 실행
                new UploadVideoTask().execute(videoFile);

                // 썸네일 생성
                Bitmap thumbnail = createThumbnail(videoFile);

                // 썸네일을 Portfolio_upload.java로 전달
                Intent intent = new Intent(this, Portfolio_upload.class);
                intent.putExtra("thumbnail", thumbnail);
                startActivity(intent);
            } else {
                // 오류 처리: videoPath가 null일 경우
                Log.e("dd", "실패");
            }
        }
    }


    // (이전 코드 부분 생략)

    private class LoadThumbnailTask extends AsyncTask<File, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(File... files) {
            if (files.length > 0) {
                File videoFile = files[0];
                Bitmap thumbnail = createThumbnail(videoFile);
                return thumbnail;
            }
            return null;
        }


        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                ImageView videoThumbnail = findViewById(R.id.video_thumbnail);
                if (videoThumbnail != null) {
                    // 썸네일 비트맵을 ImageView에 설정
                    videoThumbnail.setImageBitmap(bitmap);
                }
            }
        }


    }


    private Bitmap createThumbnail(File videoFile) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(videoFile.getAbsolutePath());

        // 동영상의 썸네일을 가져옵니다. 시간(ms)을 지정할 수 있습니다.
        // 예: 10000 ms (10초)
        Bitmap thumbnail = retriever.getFrameAtTime(10000000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);

        // 썸네일을 조정할 수 있습니다. (크기, 비율 등)
        // 예: 썸네일 크기 조정 (썸네일 크기를 맞춰야 할 경우)
        int desiredWidth = 320; // 원하는 너비
        int desiredHeight = 173; // 원하는 높이
        thumbnail = Bitmap.createScaledBitmap(thumbnail, desiredWidth, desiredHeight, false);

        return thumbnail;
    }


    // AsyncTask를 사용하여 백그라운드에서 S3 업로드 처리
    private class UploadVideoTask extends AsyncTask<File, Void, Void> {
        @Override
        protected Void doInBackground(File... files) {
            if (files.length > 0) {
                File videoFile = files[0];

                // AWS S3 클라이언트 생성
                BasicAWSCredentials credentials = new BasicAWSCredentials("AKIA3VFT4KKFUQGSJP6U", "4OIv39jOl1GwrpDeUCToEVhsTvxNFLAcAe0e9eg1");
                AmazonS3 s3Client = new AmazonS3Client(credentials, Region.getRegion(Regions.AP_NORTHEAST_2));

                // 업로드할 S3 버킷 이름 및 객체 키 설정
                String bucketName = "moovitbucket2";
                String objectKey = generateObjectKey(videoFile.getAbsolutePath());

                // 동영상 업로드
                s3Client.putObject(new PutObjectRequest(bucketName, objectKey, videoFile));
            }
            return null;
        }
    }

    // URI를 실제 파일 경로로 변환
    private String getRealPathFromURI(Uri contentUri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, projection, null, null, null);
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
        cursor.moveToFirst();
        String filePath = cursor.getString(columnIndex);
        cursor.close();
        return filePath;
    }

    // 고유한 객체 키 생성
    private String generateObjectKey(String videoPath) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return "videos/" + timeStamp + "_" + videoFile.getName();
    }

    static class PageAdapter extends FragmentStatePagerAdapter {
        PageAdapter(FragmentManager fm, Context context) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new Portfolio_tab1();
            } else {
                return new Portfolio_tab2();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "포트폴리오";
            } else {
                return "진행중인 클래스";
            }
        }
    }

}
