package com.example.moovit_dancer.open;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.moovit_dancer.R;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        WebView webView = findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new BridgeInterface(), "Android");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                //안드로이드-자바스크립트 함수 호출
                webView.loadUrl("javascript:sample2_execDaumPostcode();");
            }
        });
        //최초 웹뷰 로드
        webView.loadUrl("moovit-a1eaf.web.app");
    }
    private class BridgeInterface {
        @JavascriptInterface
        public void processDATA(String data){
            //다음 주소 검색 API 결과값이 브릿지 통로를 ㅗㅌㅇ해 전다ㄹ받는다. from Javascript
            Intent intent = new Intent();
            intent.putExtra("locdata", data);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}