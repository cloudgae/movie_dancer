package com.example.moovit_dancer.open;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.moovit_dancer.MainActivity;
import com.example.moovit_dancer.R;
import com.example.moovit_dancer.splash.Splash_0;
import com.example.moovit_dancer.splash.Splash_1;
import com.example.moovit_dancer.splash.Splash_2;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Array;
import java.util.HashMap;
import java.util.Map;

public class Open_0 extends AppCompatActivity {

    String[] items = {"K-POP", "스트릿", "코레오"};
    String[] items2 = {"입문", "중급", "고급"};
    ImageButton backkey, nextkey0;
    RadioGroup Rg_p, Rg_t;
    RadioButton R_p1, R_pn, R_t1, R_tn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open0);

        backkey = findViewById(R.id.backkey);

        Spinner spinner = findViewById(R.id.spinner);
        Spinner spinner2 = findViewById(R.id.spinner2);

        nextkey0 = findViewById(R.id.nextkey0);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        Map<String, Object> data = new HashMap<>();
//        DocumentReference classRef = db.collection("Class").document("C");
        //새로운 문서에 대한 참조 생성
        DocumentReference newClassRef = db.collection("Class").document();
//        새로운 문서의 id 가져오기
        String newClassId = newClassRef.getId();
        Map<String, Object> data = new HashMap<>();

        Rg_p = findViewById(R.id.Rg_p);
        R_p1 = findViewById(R.id.R_p1);
        R_pn = findViewById(R.id.R_pn);
        Rg_t = findViewById(R.id.Rg_t);
        R_t1 = findViewById(R.id.R_t1);
        R_tn = findViewById(R.id.R_tn);

        //드롭다운 메뉴1
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, R.layout.custom_spinner_item, R.id.text_view_id, items
        );
        //드롭다운 메뉴 스타일 여기서 수ㄱ
        adapter.setDropDownViewResource(
                R.layout.custom_spinner_item
        );
        spinner.setAdapter(adapter);
        spinner.setPopupBackgroundResource(R.drawable.custom_spinner_dropdown_item_background);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String item = adapterView.getItemAtPosition(i).toString();
                switch (i) {
                    case 0:
                        data.put("genre", "kpop");
                        newClassRef.set(data);
//                        classRef.update("genre", "kpop");
                        break;
                    case 1:
                        data.put("genre", "street");
                        newClassRef.set(data);
                        break;
                    case 2:
                        data.put("genre", "choreo");
                        newClassRef.set(data);
                         break;
                };
            };

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                data.put("genre", "kpop");
                newClassRef.set(data);
            }
        });

        //드롭다운 메뉴2
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(
                this, R.layout.custom_spinner_item, R.id.text_view_id, items2
        );
        adapter2.setDropDownViewResource(
                R.layout.custom_spinner_item
        );
        spinner2.setAdapter(adapter2);
//        spinner2.setPopupBackgroundResource(R.drawable.custom_spinner_dropdown_item_background);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        data.put("diff", "상");
                        newClassRef.set(data);
//                        classRef.update("genre", "kpop");
                        break;
                    case 1:
                        data.put("diff", "중");
                        newClassRef.set(data);
                        break;
                    case 2:
                        data.put("diff", "하");
                        newClassRef.set(data);
                        break;
                };
            };

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                    data.put("diff", "상");
                newClassRef.set(data);
            }
        });

        //인원수
        Rg_p.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.R_p1){
                    data.put("people", "1");
                    newClassRef.set(data);
                }
                else{
                    data.put("people", "n");
                    newClassRef.set(data);
                }
            }
        });

        //회차
        Rg_t.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.R_t1){
                    data.put("time", "1");
                    newClassRef.set(data);
                }
                else{
                    data.put("time", "n");
                    newClassRef.set(data);
                }
            }
        });

//        뒤로가기 버튼
        backkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new Intent(현재 context, 이동할 activity)
                Intent i = new Intent(Open_0.this, MainActivity.class);
                startActivity(i);    //intent 에 명시된 액티비티로 이동
                finish();    //현재 액티비티 종료
            }
        });

        //다음 버튼
        nextkey0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new Intent(현재 context, 이동할 activity)
                Intent i = new Intent(Open_0.this, Open_1.class);
                i.putExtra("documentId", newClassId);
                startActivity(i);    //intent 에 명시된 액티비티로 이동
//                finish();    //현재 액티비티 종료
            }
        });
    }
}