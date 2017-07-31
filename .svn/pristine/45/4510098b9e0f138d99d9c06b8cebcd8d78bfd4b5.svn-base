package com.cheddd.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cheddd.R;
import com.cheddd.base.MyBaseActivity;

public class MotionActivity extends MyBaseActivity {

    private WebView mWebViewMotion;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        initView();
        setData();
    }

    private void setData() {
        mWebViewMotion.setWebViewClient(new WebViewClient());
        mWebViewMotion.setWebChromeClient(new WebChromeClient());
        mWebViewMotion.loadUrl(url);
        // 获取WebView的基本设置
        WebSettings settings = mWebViewMotion.getSettings();
        // 设置和js交互是否可用
        settings.setJavaScriptEnabled(true);
    }

    private void initView() {
        mWebViewMotion = (WebView) findViewById(R.id.wv_motion_test);
    }
}
