package com.example.moovit_dancer.splash;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.moovit_dancer.MainActivity;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.example.moovit_dancer.R;

import java.util.HashMap;
import java.util.Map;

public class Onboarding extends AppCompatActivity {

    Button arw;
    LinearLayout careerContainer;
    Button addCareerButton;
    ImageButton finishBtn;
    int careerCount = 1; // 초기 career 상자 개수

    FirebaseFirestore firestore;
    CollectionReference dancerCollection;
    DocumentReference profile1Document;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        Button arw = (Button) findViewById(R.id.goback);
        careerContainer = findViewById(R.id.career_container);
        addCareerButton = findViewById(R.id.add_career);
        finishBtn = findViewById(R.id.finish_btn); // finish 버튼 추가


        // Firebase Firestore 초기화
        firestore = FirebaseFirestore.getInstance();

        // Firestore 컬렉션 및 문서 참조 설정
        dancerCollection = firestore.collection("Dancer");
        profile1Document = dancerCollection.document("profile1");



        arw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Onboarding.this, Splash_1.class);
                startActivity(i);
                finish();
            }
        });

        addCareerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // '+ 추가하기' 버튼을 클릭하면 새로운 career 상자를 생성
                addNewCareerBox();
            }
        });

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // '작성 완료' 버튼을 클릭하면 'edtxt_intro'의 내용을 Firestore에 저장
                saveIntroduceToFirestore();
                // 나머지 career 내용도 Firestore에 저장
                saveAllCareersToFirestore();
                Intent i = new Intent(Onboarding.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void saveIntroduceToFirestore() {
        // 'edtxt_intro'의 내용을 Firestore에 저장
        String introduce = ((EditText) findViewById(R.id.edtxt_intro)).getText().toString();
        Map<String, Object> data = new HashMap<>();
        data.put("introduce", introduce);

        profile1Document.update(data)
                .addOnSuccessListener(aVoid -> {
                    // 성공적으로 저장되었을 때 수행할 작업
                })
                .addOnFailureListener(e -> {
                    // 저장 중 오류 발생 시 수행할 작업
                });
    }
    private void addNewCareerBox() {
//        careerCount++;

        // 새로운 career 상자 생성
        EditText newCareer = new EditText(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                200, // 높이를 200으로 설정
                1.0f
        );
        params.setMargins(0, 0, 0, dpToPx(10)); // marginBottom: 10dp
        newCareer.setLayoutParams(params);
        newCareer.setHint("탭하여 내용을 입력해주세요.");
        newCareer.setHintTextColor(Color.parseColor("#BBBBBB")); // 힌트 텍스트 색상
        newCareer.setTextSize(14);
        newCareer.setGravity(Gravity.CENTER_VERTICAL); // 수직 정렬
        newCareer.setPadding(dpToPx(10), 0, dpToPx(10), 0);
        newCareer.setBackgroundResource(R.drawable.et_board);
        newCareer.setTextColor(Color.parseColor("#BBBBBB")); // 텍스트 색상
        newCareer.setMaxLines(1);

        // 새로운 career 상자에 이미지 아이콘 추가
        int drawableId = getResources().getIdentifier("no" + (careerCount + 1), "drawable", getPackageName());
        newCareer.setCompoundDrawablesWithIntrinsicBounds(drawableId, 0, 0, 0);
        newCareer.setCompoundDrawablePadding(dpToPx(10));
        newCareer.setPadding(dpToPx(10), 0, dpToPx(10), 0);

        // 새로운 career 상자를 LinearLayout에 추가
        careerContainer.addView(newCareer, careerContainer.getChildCount() - 1);
        careerCount++;
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    private void saveCareerToFirestore(String careerData, int careerCount) {
        // Firestore에 데이터 저장
        // 해당 career 데이터를 "career1", "career2", "career3" 필드에 차곡차곡 입력
        // careerCount를 사용하여 필드 이름을 결정
        String careerFieldName = "career" + careerCount;
        Map<String, Object> data = new HashMap<>();
        data.put(careerFieldName, careerData);

        profile1Document.update(data)
                .addOnSuccessListener(aVoid -> {
                    // 성공적으로 저장되었을 때 수행할 작업
                })
                .addOnFailureListener(e -> {
                    // 저장 중 오류 발생 시 수행할 작업
                });
    }

    private void saveAllCareersToFirestore() {
        // 모든 career 데이터를 Firestore에 저장
        for (int i = 1; i <= careerCount; i++) {
            View view = careerContainer.getChildAt(i - 1);
            if (view instanceof EditText) {
                EditText careerEditText = (EditText) view;
                saveCareerToFirestore(careerEditText.getText().toString(), i);
            }
        }
    }

}