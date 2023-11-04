package com.example.moovit_dancer.open;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.moovit_dancer.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Open_4 extends AppCompatActivity {

    EditText edtxt_curri, edtxt_caution;
    ImageButton backkey, nextkey;
    String curri, caution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open4);

        edtxt_curri = (EditText) findViewById(R.id.edtxt_curri);
        edtxt_caution = (EditText) findViewById(R.id.edtxt_caution);
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
                Intent i = new Intent(Open_4.this, Open_3.class);
                startActivity(i);    //intent 에 명시된 액티비티로 이동
                finish();    //현재 액티비티 종료
            }
        });

        nextkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //edittext에 입력한 제목 정보 파이어스토어에 추가
                curri = edtxt_curri.getText().toString();
                data.put("curri", curri);
                caution = edtxt_caution.getText().toString();
                data.put("caution", caution);
                docRef.update(data);

                Intent i = new Intent(Open_4.this, Open_5.class);
                i.putExtra("documentId", documentId);
                startActivity(i);    //intent 에 명시된 액티비티로 이동
            }
        });
    }
}