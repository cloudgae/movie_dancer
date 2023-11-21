package com.example.moovit_dancer.open;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.moovit_dancer.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Open_3 extends AppCompatActivity {

    EditText edtxt_intro, edtxt_intro2;
    ImageButton backkey, nextkey;
    String intro, intro2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open3);

        edtxt_intro = (EditText) findViewById(R.id.edtxt_intro);
        edtxt_intro2 = (EditText) findViewById(R.id.edtxt_intro2);
        backkey = (ImageButton) findViewById(R.id.backkey);
        nextkey = (ImageButton) findViewById(R.id.nextkey);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> data = new HashMap<>();
        //1028
        // 현재 액티비티에서 문서 ID를 받음
        String documentId = getIntent().getStringExtra("documentId");
        DocumentReference docRef = db.collection("Class").document(documentId);
//        DocumentReference classRef = db.collection("Class").document("C");

        backkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Open_3.this, Open_2.class);
                startActivity(i);    //intent 에 명시된 액티비티로 이동
                finish();    //현재 액티비티 종료
            }
        });

        nextkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //edittext에 입력한 제목 정보 파이어스토어에 추가
                intro = edtxt_intro.getText().toString();
                data.put("intro", intro);
                intro2 = edtxt_intro2.getText().toString();
                data.put("intro2", intro2);
                docRef.update(data);

                Intent i = new Intent(Open_3.this, Open_4.class);
                i.putExtra("documentId", documentId);
                startActivity(i);    //intent 에 명시된 액티비티로 이동
            }
        });

        //글자수제한
        final int maxLength = 200; // 최대 글자 수를 10으로 설정하였습니다.
        TextView charCountTextView = findViewById(R.id.charCountTextView);
        TextView charCountTextView2 = findViewById(R.id.charCountTextView2);
        // 초기 텍스트 설정
        String initialText = String.format("0 / %d", maxLength);
        SpannableString initialSpannableString = new SpannableString(initialText);
        initialSpannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#787878")), 0, initialText.length(), 0);
        charCountTextView.setText(initialSpannableString);
        charCountTextView2.setText(initialSpannableString);

        edtxt_intro.addTextChangedListener(new TextWatcher() {
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

        edtxt_intro2.addTextChangedListener(new TextWatcher() {
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

                charCountTextView2.setText(spannableString);
            }
        });
    }
}