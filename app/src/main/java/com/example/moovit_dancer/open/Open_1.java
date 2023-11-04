package com.example.moovit_dancer.open;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.moovit_dancer.MainActivity;
import android.Manifest;
import com.example.moovit_dancer.R;
import com.example.moovit_dancer.VideoInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Open_1 extends AppCompatActivity {

    String title;
    ImageView addvid, addpic;
    EditText edtxt_title;
    ImageButton backkey, nextkey;

    //1028
    private static final int REQUEST_VIDEO_PICK = 1;
    private ImageView thumbnailImageView;
    private Uri selectedVideoUri;

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == REQUEST_VIDEO_PICK && requestCode == RESULT_OK){
//            if(data != null){
//                selectedVideoUri = data.getData();
//
//                //영상의 썸네일 생성 및 표시
//                Bitmap thumbnail = ThumbnailUtils.createVideoThumbnail(selectedVideoUri.getPath(),
//                        MediaStore.Video.Thumbnails.MICRO_KIND);
//                thumbnailImageView.setImageBitmap(thumbnail);
//            }
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open1);

        backkey = (ImageButton) findViewById(R.id.backkey);
        nextkey = (ImageButton) findViewById(R.id.nextkey);
        edtxt_title = (EditText) findViewById(R.id.edtxt_title);
        addvid = (ImageView) findViewById(R.id.addvid);
        addpic = (ImageView) findViewById(R.id.addpic);

//        //1028
//        thumbnailImageView = findViewById(R.id.thumbnailImageView);
//
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> data = new HashMap<>();

        //1028
        // 현재 액티비티에서 문서 ID를 받음
        String documentId = getIntent().getStringExtra("documentId");

        DocumentReference docRef = db.collection("Class").document(documentId);

//        // AWS S3 버킷 및 자격 증명 설정
//        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
//                context, "AWS_IDENTITY_POOL_ID", Regions.YOUR_REGION);


//
//        addvid.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent pickVideoIntent = new Intent(Intent.ACTION_PICK,
//                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(pickVideoIntent, REQUEST_VIDEO_PICK);
//            }
//        });
//
//        // onActivityResult에서 선택한 영상 처리
//        if(selectedVideoUri != null){
//            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
//            StorageReference videoRef = storageRef.child("videos/" + UUID.randomUUID() + ".mp4");
//
//            videoRef.putFile(selectedVideoUri)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            //업로드가 성공한 경우
//                            // 업로드된 영상의 다운로드 URL 얻기
//                            videoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                @Override
//                                public void onSuccess(Uri uri) {
//                                    String videoUrl = uri.toString();
//
//                                    // 썸네일 생성 및 Firebase Firestore에 정보 저장
//                                    // 아래에서 설명합니다.
//                                    saveVideoInfoToFirestore(videoUrl);
//                                }
//                            });
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.d("fail", "영상 업로드 실패");
//                        }
//                    });
//        }
//
//        //

        backkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Open_1.this, Open_0.class);
                startActivity(i);    //intent 에 명시된 액티비티로 이동
                finish();    //현재 액티비티 종료
            }
        });

        nextkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //edittext에 입력한 제목 정보 파이어스토어에 추가
                title = edtxt_title.getText().toString();
                data.put("name", title);
                docRef.update(data);

                Intent i = new Intent(Open_1.this, Open_2.class);
                i.putExtra("documentId", documentId);
                startActivity(i);    //intent 에 명시된 액티비티로 이동
            }
        });

    }
    private void saveVideoInfoToFirestore(String videoUrl) {
        // 썸네일 생성 및 Firebase Firestore에 정보 저장
        // 아래 예시에서는 썸네일 URL을 빈 문자열로 저장하도록 합니다.
        VideoInfo videoInfo = new VideoInfo(videoUrl, "");

        // Firebase Firestore에 VideoInfo 저장
        videoInfo.saveToFirestore();
    }
}