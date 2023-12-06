package com.example.moovit_dancer.portfolio;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.example.moovit_dancer.R;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Portfolio_tab1 extends Fragment {
    private static ImageView video_thumbnail, grad;
    private static TextView hash_textview;
    private static MaterialCardView playerCardView3;
    private static FrameLayout framelayout;
    private static List<String> hashtags = new ArrayList<>();
    private static boolean isInitialLoad = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_portfolio_tab1, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        video_thumbnail = view.findViewById(R.id.video_thumbnail);
        grad = view.findViewById(R.id.grad);
        hash_textview = view.findViewById(R.id.hash_textview);
        playerCardView3 = view.findViewById(R.id.playerCardView3);
        framelayout = view.findViewById(R.id.framelayout);
//        framelayout.setVisibility(View.INVISIBLE);
        if (isInitialLoad) {
            // 초기에 해시태그 텍스트뷰를 비어있는 상태로 설정
            hash_textview.setText("");

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference portfolioRef = db.collection("Portfolio").document("P1");

            // "P1" 문서의 데이터 가져오기
            portfolioRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // "hash" 필드에 저장된 문자열 배열 가져오기
                        List<String> loadedHashtags = (List<String>) document.get("hash");
                        if (loadedHashtags != null && !loadedHashtags.isEmpty()) {
                            hashtags.clear();
                            hashtags.addAll(loadedHashtags);
                            updateHashTextView();
                        }
                    }
                } else {
                    // 가져오기 실패 시에 대한 처리
                }
            });

            isInitialLoad = false;
        }

//        // 동영상 URL 설정
//        String videoUrl = "https://moovitbucket2.s3.ap-northeast-2.amazonaws.com/videos/portfolio";
//
//        // 동영상에서 썸네일을 비동기적으로 추출하여 이미지뷰에 설정
//        new DownloadThumbnailTask().execute(videoUrl);

    }

    public static class DownloadThumbnailTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... videoUrls) {
            try {
                if (videoUrls.length > 0) {
                    String videoUrl = videoUrls[0];
                    // URL을 통해 동영상 썸네일을 추출
                    return extractThumbnailFromVideoUrl(videoUrl);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                // 추출된 썸네일을 ImageView에 설정
                video_thumbnail.setImageBitmap(bitmap);
                grad.setImageResource(R.drawable.portfolio_gr);
            } else {
                // 썸네일을 가져오지 못한 경우에 대한 처리 (예: 기본 이미지 설정)
                video_thumbnail.setImageResource(R.drawable.thumbnail);
            }
        }

        private Bitmap extractThumbnailFromVideoUrl(String videoUrl) throws IOException {
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(videoUrl);

            // 특정 시간(마이크로초)에서 이미지 추출 (여기서는 10초)
            long timeInMicroseconds = 10000000;
            return retriever.getFrameAtTime(timeInMicroseconds, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
        }
    }

    // 이미지뷰 가시성 설정 메서드
    public void showImageView() {
        if (video_thumbnail != null) {
            video_thumbnail.setVisibility(View.VISIBLE);
//            grad.setVisibility(View.VISIBLE);
//            hash_textview.setVisibility(View.VISIBLE);
        }
//        if (playerCardView3 != null) {
//            playerCardView3.setVisibility(View.VISIBLE);
//
//        }
    }
    private static void updateHashTextView() {
        if (hash_textview != null) {
            StringBuilder hashText = new StringBuilder();
            for (String hashtag : hashtags) {
                hashText.append("# ").append(hashtag).append(" ");
            }
            hash_textview.setText(hashText.toString());
        }
    }

    // 이 메서드를 호출하여 해시태그 추가
    private void addHashtag(String hashtag) {
        // 초기 로드 시에만 비어 있는 상태로 설정
        if (isInitialLoad) {
            hashtags.clear();
            isInitialLoad = false;
        }

        hashtags.add(hashtag);
        updateHashTextView();
    }
}