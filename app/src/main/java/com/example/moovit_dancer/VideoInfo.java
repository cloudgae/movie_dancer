package com.example.moovit_dancer;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class VideoInfo {
    private String videoUrl;
    private String thumbnailUrl;

    public VideoInfo(){
        //기본생성자
    }

    public VideoInfo(String videoUrl, String thumbnailUrl){
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    //Firestore에 VideoInfo를 저장하는 메서드
    public void saveToFirestore(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("videos")
                .add(this)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //Firestore에 추가 성공 시 동작할 코드
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
}
