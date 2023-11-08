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
import android.graphics.Region;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferNetworkLossHandler;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.moovit_dancer.R;
import com.google.android.material.tabs.TabLayout;

import java.io.File;

public class Portfolio extends AppCompatActivity {
    ImageButton addbtn;
    private static final int PICK_VIDEO_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);


        ViewPager pager = findViewById(R.id.pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        addbtn = (ImageButton) findViewById(R.id.addbtn);


        pager.setOffscreenPageLimit(2); //현재 페이지의 양쪽에 보유해야하는 페이지 수를 설정 (상황에 맞게 사용하시면 됩니다.)
        tabLayout.setupWithViewPager(pager); //텝레이아웃과 뷰페이저를 연결
        pager.setAdapter(new Portfolio.PageAdapter(getSupportFragmentManager(),this)); //뷰페이저 어뎁터 설정 연결


        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();

//                // AWS S3 클라이언트 생성
//                BasicAWSCredentials credentials = new BasicAWSCredentials("AKIA3VFT4KKFVVSDK4HD", "v7s+547KQNGDPmXh2tp7fGnc1XZABgboQqVyJ1Sx");
//                AmazonS3 s3Client = new AmazonS3Client(credentials, Region.getRegion(Regions.AP_NORTHEAST_2));
//
//                // 선택한 동영상 파일
//                File videoFile = new File("선택한 동영상 파일 경로");
//
//                // 업로드할 S3 버킷 및 객체 키
//                String bucketName = "S3_버킷_이름";
//                String objectKey = "동영상_객체_키";
//
//                // 동영상 업로드
//                s3Client.putObject(new PutObjectRequest(bucketName, objectKey, videoFile));

            }
        });



    }



    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("video/*"); // 동영상 파일만 선택 가능하도록 설정
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "동영상 선택"), PICK_VIDEO_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_VIDEO_REQUEST && resultCode == RESULT_OK) {
            if (data != null) {
                Uri videoUri = data.getData();
                // 선택한 동영상을 업로드하거나 처리하는 코드를 작성하세요.
            }
        }
    }

    static class PageAdapter extends FragmentStatePagerAdapter { //뷰 페이저 어뎁터


        PageAdapter(FragmentManager fm, Context context) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        }


        @NonNull
        @Override
        public Fragment getItem(int position) {
            if (position == 0) { //프래그먼트 사용 포지션 설정 0 이 첫탭
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
            if (position == 0) { //텝 레이아웃의 타이틀 설정
                return "포트폴리오";
            } else {
                return "진행중인 클래스";
            }
        }
    }

//    public void uploadWithTransferUtilty(String fileName, File file) {
//
//        AWSCredentials awsCredentials = new BasicAWSCredentials("AKIA3VFT4KKFUQGSJP6U", "4OIv39jOl1GwrpDeUCToEVhsTvxNFLAcAe0e9eg1");	// IAM 생성하며 받은 것 입력
//        AmazonS3Client s3Client = AmazonS3Client.builder()
//                .credentialsProvider(new AWSStaticCredentialsProvider(credentials))
//                .region(Region.of("AP_NORTHEAST_2"))
//                .build();
//
//        TransferUtility transferUtility = TransferUtility.builder().s3Client(s3Client).context(getActivity().getApplicationContext()).build();
//        TransferNetworkLossHandler.getInstance(getActivity().getApplicationContext());
//
//        TransferObserver uploadObserver = transferUtility.upload("talent-house-app/photo", fileName, file);	// (bucket api, file이름, file객체)
//
//        uploadObserver.setTransferListener(new TransferListener() {
//            @Override
//            public void onStateChanged(int id, TransferState state) {
//                if (state == TransferState.COMPLETED) {
//                    // Handle a completed upload
//                }
//            }
//            @Override
//            public void onProgressChanged(int id, long current, long total) {
//                int done = (int) (((double) current / total) * 100.0);
//                Log.d("MYTAG", "UPLOAD - - ID: $id, percent done = $done");
//            }
//            @Override
//            public void onError(int id, Exception ex) {
//                Log.d("MYTAG", "UPLOAD ERROR - - ID: $id - - EX:" + ex.toString());
//            }
//        });
}