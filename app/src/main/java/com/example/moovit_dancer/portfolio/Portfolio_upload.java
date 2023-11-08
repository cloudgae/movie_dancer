package com.example.moovit_dancer.portfolio;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.moovit_dancer.R;

public class Portfolio_upload extends AppCompatActivity {
    private ImageView videoThumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio_upload);

        // 이전 화면(Portfolio.java)에서 썸네일을 받아옴
        Bitmap thumbnail = getIntent().getParcelableExtra("thumbnail");

        if (thumbnail != null) {
            ImageView videoThumbnail = findViewById(R.id.video_thumbnail);
            videoThumbnail.setImageBitmap(thumbnail);
        }
    }

}
