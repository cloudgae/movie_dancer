package com.example.moovit_dancer.open;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.moovit_dancer.MainActivity;
import com.example.moovit_dancer.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




public class Open_2 extends AppCompatActivity {

    EditText edtxt_hash, edtxt_location, edtxt_location2;
    ImageButton addhash;
    ImageButton backkey, nextkey, loc_add;
    String hash, location, location2;
    LinearLayout ovalContainer;

    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open2);

        edtxt_hash = (EditText) findViewById(R.id.edtxt_hash);
        addhash = (ImageButton) findViewById(R.id.addhash);
        backkey = (ImageButton) findViewById(R.id.backkey);
        nextkey = (ImageButton) findViewById(R.id.nextkey);
        edtxt_location = (EditText) findViewById(R.id.edtxt_location);
        ovalContainer = findViewById(R.id.ovalContainer);

        loc_add = (ImageButton) findViewById(R.id.loc_add);
        edtxt_location2 = (EditText) findViewById(R.id.edtxt_location2);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> data = new HashMap<>();
        //1028
        // 현재 액티비티에서 문서 ID를 받음
        String documentId = getIntent().getStringExtra("documentId");
        DocumentReference docRef = db.collection("Class").document(documentId);
//        DocumentReference classRef = db.collection("Class").document("C");

        //1028
        addhash.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                String text = edtxt_hash.getText().toString();
                if (!text.isEmpty()) {
                    // TextView 및 X 버튼을 포함하는 레이아웃 생성
                    //TODO : 해시태그 추가/삭제 시 파이어스토어에도 연결 필요
                    LinearLayout container = new LinearLayout(Open_2.this);
                    container.setOrientation(LinearLayout.HORIZONTAL);

                    TextView textView = new TextView(Open_2.this);
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

                    // 'X' 모양 추가
//                    TextView deleteButton = new TextView(Open_2.this);
//                    deleteButton.setText("x");
//                    deleteButton.setTextColor(Color.parseColor("#E87FEA"));
//                    deleteButton.setPadding(8, 8, 8, 8);
//                    deleteButton.setTextSize(14);


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
        //
        backkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Open_2.this, Open_1.class);
                startActivity(i);    //intent 에 명시된 액티비티로 이동
                finish();    //현재 액티비티 종료
            }
        });

        nextkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //edittext에 입력한 제목 정보 파이어스토어에 추가
                hash = edtxt_hash.getText().toString();
                location = edtxt_location.getText().toString();
                location2 = edtxt_location2.getText().toString();

//                data.put("hash", hash);
                data.put("location", location);
                data.put("location2", location2);
                docRef.update(data);

                Intent i = new Intent(Open_2.this, Open_3.class);
                i.putExtra("documentId", documentId);
                startActivity(i);    //intent 에 명시된 액티비티로 이동
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

        //주소검색
        edtxt_location.setFocusable(false);

        loc_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //주소 검색 웹뷰 화면으로 이동
                Intent intent = new Intent(Open_2.this, SearchActivity.class);
                getSearchResult.launch(intent);
            }
        });
        edtxt_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //주소 검색 웹뷰 화면으로 이동
                Intent intent = new Intent(Open_2.this, SearchActivity.class);
                getSearchResult.launch(intent);
            }
        });

    }

    private final ActivityResultLauncher<Intent> getSearchResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                //Search Activity로부터의 결과값이 이곳으로 전달됨
                if (result.getResultCode() == RESULT_OK){
                    if (result.getData() != null){
                        String locdata = result.getData().getStringExtra("locdata");
                        edtxt_location.setText(locdata);
                    }
                }
            }
    );


}