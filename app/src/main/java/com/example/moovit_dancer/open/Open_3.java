package com.example.moovit_dancer.open;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

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
    }
}