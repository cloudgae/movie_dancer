package com.example.moovit_dancer.open;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.moovit_dancer.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Open_2 extends AppCompatActivity {

    EditText edtxt_hash;
    Button addhash;
    ImageButton backkey, nextkey;
    String hash;
    LinearLayout ovalContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open2);

        edtxt_hash = (EditText) findViewById(R.id.edtxt_hash);
        addhash = (Button) findViewById(R.id.addhash);
        backkey = (ImageButton) findViewById(R.id.backkey);
        nextkey = (ImageButton) findViewById(R.id.nextkey);

        ovalContainer = findViewById(R.id.ovalContainer);

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
                    LinearLayout container = new LinearLayout(Open_2.this);
                    container.setOrientation(LinearLayout.HORIZONTAL);

                    TextView textView = new TextView(Open_2.this);
                    textView.setText("# " + text + "  x");
                    textView.setTextColor(Color.parseColor("#E87FEA"));
                    textView.setTextSize(14);

                    GradientDrawable shape = new GradientDrawable();
                    shape.setShape(GradientDrawable.RECTANGLE);
                    shape.setCornerRadius(40);
                    shape.setColor(Color.parseColor("#E87FEA"));
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
                data.put("hash", hash);
                docRef.update(data);

                Intent i = new Intent(Open_2.this, Open_3.class);
                i.putExtra("documentId", documentId);
                startActivity(i);    //intent 에 명시된 액티비티로 이동
            }
        });
    }
}