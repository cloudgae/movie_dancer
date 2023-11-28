package com.example.moovit_dancer.portfolio;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.moovit_dancer.R;
import com.example.moovit_dancer.open.Open_2;

public class Portfolio_upload extends AppCompatActivity {
    private ImageView videoThumbnail;
    EditText edtxt_hash;
    ImageButton addhash;
    Button goback;
    LinearLayout ovalContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio_upload);

        edtxt_hash = findViewById(R.id.edtxt_hash);
        addhash = findViewById(R.id.addhash);
        goback = findViewById(R.id.goback);
        ovalContainer = findViewById(R.id.ovalContainer);

        // 이전 화면(Portfolio.java)에서 썸네일을 받아옴
        Bitmap thumbnail = getIntent().getParcelableExtra("thumbnail");

        if (thumbnail != null) {
            ImageView videoThumbnail = findViewById(R.id.video_thumbnail);
            videoThumbnail.setImageBitmap(thumbnail);
        }

        addhash.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                String text = edtxt_hash.getText().toString();
                if (!text.isEmpty()) {
                    // TextView 및 X 버튼을 포함하는 레이아웃 생성
                    //TODO : 해시태그 추가/삭제 시 파이어스토어에도 연결 필요
                    LinearLayout container = new LinearLayout(Portfolio_upload.this);
                    container.setOrientation(LinearLayout.HORIZONTAL);

                    TextView textView = new TextView(Portfolio_upload.this);
                    textView.setText("# " + text + "  x");
                    textView.setTextColor(Color.parseColor("#6FD5EB"));
                    textView.setTextSize(14);

                    GradientDrawable shape = new GradientDrawable();
                    shape.setShape(GradientDrawable.RECTANGLE);
                    shape.setCornerRadius(40);
                    shape.setColor(Color.parseColor("#6FD5EB"));
                    shape.setAlpha(80);

                    textView.setBackground(shape);
                    textView.setPadding(16, 8, 16, 8);


                    // TextView와 'X' 모양을 컨테이너 레이아웃에 추가
                    container.addView(textView);
//                    container.addView(deleteButton);

                    // 컨테이너 레이아웃을 원 모양 텍스트뷰를 포함한 레이아웃에 추가
                    ovalContainer.addView(container);

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    layoutParams.setMargins(0, 8, 10, 0);
                    container.setLayoutParams(layoutParams); // 컨테이너에 레이아웃 파라미터 설정

                    edtxt_hash.setText(""); // 텍스트 입력 필드 지우기

                    // 'X' 모양을 클릭하여 텍스트뷰 제거
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ovalContainer.removeView(container);
                        }
                    });

                    // 현재 추가된 텍스트뷰들이 가로로 배치되도록 LinearLayout의 방향을 가로로 변경
                    ovalContainer.setOrientation(LinearLayout.HORIZONTAL);
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
    }

}
