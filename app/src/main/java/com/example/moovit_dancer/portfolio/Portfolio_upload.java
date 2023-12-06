package com.example.moovit_dancer.portfolio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.moovit_dancer.R;
import com.example.moovit_dancer.open.Open_2;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Portfolio_upload extends AppCompatActivity  implements Portfolio_tab1.PortfolioUploadListener{
    private ImageView videoThumbnail;
    EditText edtxt_hash;
    ImageButton addhash, portfolio_upload;
    Button goback;
    LinearLayout ovalContainer;
    Bitmap thumbnail;
    private Portfolio_tab1 portfolioTab1;
    String newHashtag;
    List<String> existingHashtags;
    Map<String, Object> data = new HashMap<>();
    DocumentReference portfolioRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio_upload);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference portfolioRef = db.collection("Portfolio").document("P1");

//        새로운 문서의 id 가져오기
        // 예제 코드에서는 "Portfolio" 컬렉션의 문서에 "hash" 필드를 사용한다고 가정
        Map<String, Object> data = new HashMap<>();
        List<String> hashtags = new ArrayList<>();
        data.put("hash", hashtags);

        edtxt_hash = findViewById(R.id.edtxt_hash);
        addhash = findViewById(R.id.addhash);
        goback = findViewById(R.id.goback);
        ovalContainer = findViewById(R.id.ovalContainer);
        portfolio_upload = findViewById(R.id.portfolio_upload);

        // 이전 화면(Portfolio.java)에서 썸네일을 받아옴
        Bitmap thumbnail = getIntent().getParcelableExtra("thumbnail");
        ImageView videoThumbnail = findViewById(R.id.video_thumbnail);
        if (thumbnail != null) {
//            ImageView videoThumbnail = findViewById(R.id.video_thumbnail);
            videoThumbnail.setImageBitmap(thumbnail);
        }

        // 처음 화면 생성 시 해시태그 필드 초기화
        portfolioRef.set(data)
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "문서 초기화 성공"))
                .addOnFailureListener(e -> Log.d("Firestore", "문서 초기화 실패: " + e.getMessage()));


        // addhash.setOnClickListener 수정
        addhash.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                String text = edtxt_hash.getText().toString();
                if (!text.isEmpty()) {
                    // UI에 해시태그 추가
                    addHashtagToUI(text, portfolioRef);

                    // Firestore에 업데이트
                    portfolioRef.get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                existingHashtags = (List<String>) document.get("hash");
                                if (existingHashtags == null) {
                                    existingHashtags = new ArrayList<>();
                                }

                                // 추가된 해시태그를 리스트에 추가
                                existingHashtags.add(text);

                                // 파이어스토어에 업데이트
                                portfolioRef.update("hash", existingHashtags)
                                        .addOnSuccessListener(aVoid -> Log.d("Firestore", "문서 업데이트 성공"))
                                        .addOnFailureListener(e -> Log.d("Firestore", "문서 업데이트 실패: " + e.getMessage()));
                            }
                        } else {
                            Log.d("Firestore", "문서를 가져오는 중 오류 발생: ", task.getException());
                        }
                    });

                    edtxt_hash.setText(""); // 텍스트 입력 필드 지우기
                }
            }
        });

        //글자수제한
        final int maxLength = 10; // 최대 글자 수를 10으로 설정하였습니다.
        TextView charCountTextView = findViewById(R.id.charCountTextView);
        // 초기 텍스트 설정
        String initialText = String.format("0 / %d", maxLength);
        SpannableString initialSpannableString = new SpannableString(initialText);
        initialSpannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#787878")), 0, initialText.length(), 0);
        charCountTextView.setText(initialSpannableString);

        edtxt_hash.addTextChangedListener(new TextWatcher() {
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

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Portfolio_upload.this, Portfolio.class);
                startActivity(i);    //intent 에 명시된 액티비티로 이동
            }
        });

        // Portfolio_tab1에 접근
        portfolioTab1 = new Portfolio_tab1();


        portfolio_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 썸네일 비트맵을 Portfolio 액티비티로 전달
                Intent intent = new Intent(Portfolio_upload.this, Portfolio.class);
//                onUploadButtonClick();

                startActivity(intent);

                // DownloadThumbnailTask를 직접 호출하여 썸네일 가져오기
                new Portfolio_tab1.DownloadThumbnailTask().execute("https://moovitbucket2.s3.ap-northeast-2.amazonaws.com/videos/portfolio");

                // 이미지뷰를 visible로 변경
                showImageViewInTab1();
// 인터페이스 메서드 호출
                onPortfolioUploadClicked();
            }
        });
    }


    // Portfolio_tab1의 이미지뷰 가시성 변경 메서드
    public void showImageViewInTab1() {
        if (portfolioTab1 != null) {
            portfolioTab1.showImageView();
        }
    }

    private void addHashtagToUI(String hashtag, DocumentReference portfolioRef) {
        LinearLayout container = new LinearLayout(Portfolio_upload.this);
        container.setOrientation(LinearLayout.HORIZONTAL);

        TextView textView = new TextView(Portfolio_upload.this);
        textView.setText("# " + hashtag + "  x");
        textView.setTextColor(Color.parseColor("#6FD5EB"));
        textView.setTextSize(14);

        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadius(40);
        shape.setColor(Color.parseColor("#6FD5EB"));
        shape.setAlpha(80);

        textView.setBackground(shape);
        textView.setPadding(16, 8, 16, 8);

        container.addView(textView);
        ovalContainer.addView(container);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, 8, 10, 0);
        container.setLayoutParams(layoutParams);

        textView.setOnClickListener(v -> {
            ovalContainer.removeView(container);
            existingHashtags.remove(hashtag);

            // 파이어스토어에서도 삭제
            portfolioRef.update("hash", existingHashtags)
                    .addOnSuccessListener(aVoid -> Log.d("Firestore", "문서 업데이트 성공"))
                    .addOnFailureListener(e -> Log.d("Firestore", "문서 업데이트 실패: " + e.getMessage()));
        });

        ovalContainer.setOrientation(LinearLayout.HORIZONTAL);
    }

    @Override
    public void onPortfolioUploadClicked() {
        // Portfolio_tab1에서 hash_textview의 가시성 업데이트
        portfolioTab1.showImageView();
    }
}
