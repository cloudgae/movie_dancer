package com.example.moovit_dancer.open;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.moovit_dancer.MainActivity;
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
        // 현재 액티비티에서 문서 ID를 받음
        String documentId = getIntent().getStringExtra("documentId");
        DocumentReference docRef = db.collection("Class").document(documentId);
//        DocumentReference classRef = db.collection("Class").document("C");

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
                // 다이얼로그 변수 선언
                final AlertDialog[] alertDialog = {null};

                AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(Open_8.this);
                LayoutInflater inflater = getLayoutInflater();
                View customLayout = inflater.inflate(R.layout.custom_dialog_layout, null);
                dlgBuilder.setView(customLayout);

                // 버튼 찾기
                Button positiveButton = customLayout.findViewById(R.id.positivebtn);
                Button negativeButton = customLayout.findViewById(R.id.negativebtn);

                // 긍정 버튼 클릭 이벤트 설정
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 여기에 긍정 버튼을 클릭했을 때 수행할 동작을 추가하세요.
                        Intent i2 = new Intent(Open_8.this, MainActivity.class);
                        startActivity(i2);
                        finish();

                        // 다이얼로그를 닫는 코드
                        if (alertDialog[0] != null) {
                            alertDialog[0].dismiss();
                        }
                    }
                });

                // 부정 버튼 클릭 이벤트 설정
                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 여기에 부정 버튼을 클릭했을 때 수행할 동작을 추가하세요.
                        // 예: 다이얼로그를 닫거나 다른 동작 수행

                        // 다이얼로그를 닫는 코드
                        if (alertDialog[0] != null) {
                            alertDialog[0].dismiss();
                        }
                    }
                });

                alertDialog[0] = dlgBuilder.create();
                alertDialog[0].show();
                alertDialog[0].getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            }
        });



    }
}