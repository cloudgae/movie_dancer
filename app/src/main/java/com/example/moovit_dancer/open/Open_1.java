package com.example.moovit_dancer.open;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.moovit_dancer.MainActivity;

import android.Manifest;

import com.example.moovit_dancer.R;
import com.example.moovit_dancer.VideoInfo;
import com.example.moovit_dancer.portfolio.Portfolio;
import com.example.moovit_dancer.portfolio.Portfolio_upload;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private ImageView picImageView;
    private File videoFile; // 동영상 파일 변수 추가

    private static final int REQUEST_IMAGE_PICK = 2;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_VIDEO_PICK && resultCode == RESULT_OK && data != null) {
            Uri videoUri = data.getData();
            String videoPath = getRealPathFromURI(videoUri);
            if (videoPath != null) {
                videoFile = new File(videoPath);

                // 비동기 작업으로 S3 업로드 실행
                new UploadVideoTask_1().execute(videoFile);

                // 썸네일 생성
                Bitmap thumbnail = createRoundedThumbnail(videoFile);

                // 썸네일을 Portfolio_upload.java로 전달
                //썸네일을 이미지뷰에 설정
                thumbnailImageView.setImageBitmap(thumbnail);
//                Intent intent = new Intent(this, Portfolio_upload.class);
//                intent.putExtra("thumbnail", thumbnail);
//                startActivity(intent);
            } else {
                // 오류 처리: videoPath가 null일 경우
                Log.e("dd", "동영상 파일 경로가 null입니다. 선택된 동영상이 올바른지 확인하세요. Uri: " + videoUri);
            }
        }

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            String imagePath = getRealPathFromURI(imageUri);

            if (imagePath != null) {
                File imageFile = new File(imagePath);

                // 비동기 작업으로 S3 업로드 실행
                new UploadImageTask().execute(imageFile);

                // 이미지 업로드 후 이미지 뷰에 설정
                Bitmap croppedAndResizedBitmap = cropAndResizeBitmap(imageFile);
                picImageView.setImageBitmap(croppedAndResizedBitmap);
            } else {
                // 오류 처리: imagePath가 null일 경우
                Log.e("openImageGallery", "실패");
            }
        }

    }


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
        thumbnailImageView = findViewById(R.id.thumbnailImageView);
//
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> data = new HashMap<>();

        //1028
        // 현재 액티비티에서 문서 ID를 받음
        String documentId = getIntent().getStringExtra("documentId");

        DocumentReference docRef = db.collection("Class").document(documentId);


        addvid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        picImageView = findViewById(R.id.picImageView);

        addpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageGallery();
            }
        });
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


        //글자수제한
        final int maxLength = 20; // 최대 글자 수를 10으로 설정하였습니다.
        TextView charCountTextView = findViewById(R.id.charCountTextView);
        // 초기 텍스트 설정
        String initialText = String.format("0 / %d", maxLength);
        SpannableString initialSpannableString = new SpannableString(initialText);
        initialSpannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#787878")), 0, initialText.length(), 0);
        charCountTextView.setText(initialSpannableString);

        edtxt_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
                // 텍스트 변경 전에 호출되는 메서드
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // 텍스트가 변경될 때 호출되는 메서드
            }

            @Override
            public void afterTextChanged(Editable editable) {
                int currentLength = editable.length();
                int remainingChars = maxLength - currentLength;

                // 작성한 글자 수와 최대 글자 수를 각각 다른 색으로 표시
                String charCountText = String.format("%d / %d", currentLength, maxLength);
                SpannableString spannableString = new SpannableString(charCountText);

                // 작성한 글자 수에 대한 색상
                int textColor = (currentLength <= maxLength) ? Color.parseColor("#787878") : Color.parseColor("#FF0000");
                ForegroundColorSpan greenSpan = new ForegroundColorSpan(textColor);
                spannableString.setSpan(greenSpan, 0, String.valueOf(currentLength).length(), 0);

                // 최대 글자 수에 대한 색상
                ForegroundColorSpan graySpan = new ForegroundColorSpan(Color.parseColor("#BBBBBB")); // 예: 회색
                spannableString.setSpan(graySpan, String.valueOf(currentLength).length() + 3, charCountText.length(), 0);

                charCountTextView.setText(spannableString);
            }
        });

    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "동영상 선택"), REQUEST_VIDEO_PICK);
    }
    private void openImageGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }
    private Bitmap createRoundedThumbnail(File videoFile) {
        if (videoFile != null && videoFile.exists()) {
            try{
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(videoFile.getAbsolutePath());

            // 동영상의 썸네일을 가져옵니다. 시간(ms)을 지정할 수 있습니다.
            // 예: 10000 ms (10초)
            Bitmap originalThumbnail = retriever.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);

            // 일부분만 크롭하여 원하는 크기의 이미지 생성
            int desiredSize = dpToPx(80); // 80dp를 픽셀로 변환
            Bitmap croppedThumbnail = cropBitmap(originalThumbnail, desiredSize);

            // 둥근 모서리 적용
            Bitmap roundedThumbnail = getRoundedCornerBitmap(croppedThumbnail, dpToPx(5)); // 10dp를 픽셀로 변환하여 반지름 설정

            return roundedThumbnail;
            } catch (Exception e) {
                Log.e("createRoundedThumbnail", "Error creating thumbnail: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            Log.e("createRoundedThumbnail", "Video file is null or does not exist.");
        }
        return null;
    }
    // 둥근 모서리 적용
    private Bitmap getRoundedCornerBitmap(Bitmap bitmap, int radius) {
        Bitmap roundedBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(roundedBitmap);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE); // 둥근 부분의 배경색을 설정할 수 있습니다.

        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);

        // 사각형에서 둥근 부분을 제외하고 투명하게 처리
        canvas.drawRoundRect(rectF, radius, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        // 둥근 부분에 원본 비트맵 적용
        canvas.drawBitmap(bitmap, null, rect, paint);

        return roundedBitmap;
    }
    // 비트맵 일부분 크롭
    private Bitmap cropBitmap(Bitmap original, int desiredSize) {
        if (original == null) {
            return null;
        }

        int originalWidth = original.getWidth();
        int originalHeight = original.getHeight();

        // 크롭할 부분의 좌표 계산 (가운데 기준)
        int left = (originalWidth - desiredSize) / 2;
        int top = (originalHeight - desiredSize) / 2;

        // 비트맵 일부분 크롭
        return Bitmap.createBitmap(original, left, top, desiredSize, desiredSize);
    }
    // dp를 px로 변환
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
    // AsyncTask를 사용하여 백그라운드에서 S3 업로드 처리
    private class UploadVideoTask_1 extends AsyncTask<File, Void, Void> {
        @Override
        protected Void doInBackground(File... files) {
            if (files.length > 0) {
                File videoFile = files[0];

                // AWS S3 클라이언트 생성
                BasicAWSCredentials credentials = new BasicAWSCredentials("AKIA3VFT4KKFUQGSJP6U", "4OIv39jOl1GwrpDeUCToEVhsTvxNFLAcAe0e9eg1");
                AmazonS3 s3Client = new AmazonS3Client(credentials, Region.getRegion(Regions.AP_NORTHEAST_2));

                // 업로드할 S3 버킷 이름 및 객체 키 설정
                String bucketName = "moovitbucket2";
//                String objectKey = generateObjectKey(videoFile.getAbsolutePath());
                String objectKey = generateObjectKey(videoFile.getAbsolutePath(), "videos");

                // 동영상 업로드
//                s3Client.putObject(new PutObjectRequest(bucketName, objectKey, videoFile));
                // 동영상 업로드
//                s3Client.putObject(new PutObjectRequest(bucketName, "videos/" + generateObjectKey(videoFile.getAbsolutePath()), videoFile));
//                s3Client.putObject(new PutObjectRequest(bucketName, generateObjectKey(videoFile.getAbsolutePath(), "videos"), videoFile));
                s3Client.putObject(new PutObjectRequest(bucketName, generateObjectKey(videoFile.getAbsolutePath(), "videos"), videoFile));

            }
            return null;
        }
    }

    private class UploadImageTask extends AsyncTask<File, Void, Void> {
        @Override
        protected Void doInBackground(File... files) {
            if (files.length > 0) {
                File imageFile = files[0];

                // AWS S3 클라이언트 생성
                BasicAWSCredentials credentials = new BasicAWSCredentials("AKIA3VFT4KKFUQGSJP6U", "4OIv39jOl1GwrpDeUCToEVhsTvxNFLAcAe0e9eg1");
                AmazonS3 s3Client = new AmazonS3Client(credentials, Region.getRegion(Regions.AP_NORTHEAST_2));

                // 업로드할 S3 버킷 이름 및 객체 키 설정
                String bucketName = "moovitbucket2";
                // 이미지 업로드
                s3Client.putObject(new PutObjectRequest(bucketName, generateObjectKey(imageFile.getAbsolutePath(), "images"), imageFile));
//                s3Client.putObject(new PutObjectRequest(bucketName, "images/" + generateObjectKey(imageFile.getAbsolutePath()), imageFile));
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
    private String generateObjectKey(String filePath, String fileType) {
        if (filePath != null) {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            // 이미지 파일인 경우
            if (fileType.equals("images")) {
//                return "C7image/" + timeStamp + "_" + new File(filePath).getName();
                return "C7image/" + "C7image";
            }
            // 동영상 파일인 경우
            else if (fileType.equals("videos")) {
//                return "C7video/" + timeStamp + "_" + new File(filePath).getName();
                return "C7video/" + "C7video";
            }

            // 기본적으로는 fileType을 그대로 사용
            return fileType + "/" + timeStamp + "_" + new File(filePath).getName();
        } else {
            // filePath가 null인 경우 예외 처리
            Log.e("generateObjectKey", "filePath is null");
            return "";
        }
    }


    private Bitmap cropAndResizeBitmap(File imageFile) {
        Bitmap originalBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());

        // 이미지 일부분 크롭
        Bitmap croppedBitmap = cropBitmap(originalBitmap);

        // 크기 조절
        int desiredSize = dpToPx(80);
        Bitmap resizedBitmap = resizeBitmap(croppedBitmap, desiredSize);

        // 둥근 모서리 적용
        Bitmap roundedBitmap = getRoundedCornerBitmap(resizedBitmap, dpToPx(5)); // 5dp를 픽셀로 변환하여 반지름 설정

        return roundedBitmap;
    }
    private Bitmap cropBitmap(Bitmap originalBitmap) {
        // 이미지 일부분 크롭
        // 예: 이미지 중앙의 부분을 크롭
        int width = originalBitmap.getWidth();
        int height = originalBitmap.getHeight();

        int size = Math.min(width, height); // 가로, 세로 중 작은 값 선택
        int left = (width - size) / 2;
        int top = (height - size) / 2;
        int right = left + size;
        int bottom = top + size;

        // 크롭
        Bitmap croppedBitmap = Bitmap.createBitmap(originalBitmap, left, top, right - left, bottom - top);

        return croppedBitmap;
    }

    private Bitmap resizeBitmap(Bitmap originalBitmap, int desiredSize) {
        // 크기 조절
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, desiredSize, desiredSize, true);

        return resizedBitmap;
    }

}