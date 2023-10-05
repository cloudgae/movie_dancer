package com.example.moovit_dancer.open;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.moovit_dancer.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Open_8 extends AppCompatActivity {

    ImageButton backkey, nextkey;
    EditText edtxt_fee;
    Integer fee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open8);

        backkey = (ImageButton) findViewById(R.id.backkey);
        nextkey = (ImageButton) findViewById(R.id.nextkey);
        edtxt_fee = (EditText) findViewById(R.id.edtxt_fee);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> data = new HashMap<>();
        DocumentReference classRef = db.collection("Class").document("C");

        backkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Open_8.this, Open_7.class);
                startActivity(i);    //intent 에 명시된 액티비티로 이동
                finish();    //현재 액티비티 종료
            }
        });

        nextkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fee = Integer.parseInt(edtxt_fee.getText().toString());
                data.put("fee", fee);
                classRef.update(data);

                AlertDialog.Builder dlg = new AlertDialog.Builder(Open_8.this); dlg.setTitle("클래스 개설 요청 완료!");
                dlg.setMessage("클래스가 개설 요청 완료되었어요. 내 클래스의\n" +
                        "승인 현황은 마이페이지 내 개설 클래스 목록\n" +
                        "에서 확인 가능해요."); dlg.setIcon(R.mipmap.ic_launcher);
                dlg.setPositiveButton("바로가기", null);
                dlg.setNegativeButton("닫기", null);
                dlg.show();
            }
        });
    }
}