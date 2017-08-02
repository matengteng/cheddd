package com.cheddd.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cheddd.R;
import com.cheddd.base.MyBaseActivity;
import com.cheddd.view.TopNavigationBar;

public class MoreQuestionActivity extends MyBaseActivity {

    private TopNavigationBar mTnb;
    private WebView mWebViewMotion;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_question);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        initView();
        initView();
        setData();
        setListener();
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
    private void setListener() {
        mTnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mTnb = (TopNavigationBar) findViewById(R.id.tnb_morequestion);
        mWebViewMotion = (WebView) findViewById(R.id.wv_motion_test);

    }
}
