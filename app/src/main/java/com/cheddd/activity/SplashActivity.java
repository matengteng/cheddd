package com.cheddd.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.cheddd.R;
import com.cheddd.base.MyBaseActivity;
import com.cheddd.utils.SharedPreferencesUtils;


public class SplashActivity extends MyBaseActivity {

    private ImageView mImageView;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            enertGuide();
            //startActivity(new Intent(SplashActivity.this, GuideActivity.class));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_splash);
        initView();
        initData();
    }

    private void initData() {
        setAnimatot(mImageView);
        mHandler.sendEmptyMessageDelayed(1, 2000);
    }

    private void initView() {
        mImageView = (ImageView) findViewById(R.id.iv_splash_enert);
        setAnimatot(mImageView);
    }

    private void setAnimatot(ImageView imageView) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "alpha", 0.0f, 1.0f);
        animator.setDuration(2000);
        animator.start();
    }

    private void enertGuide() {

        boolean userGuide = SharedPreferencesUtils.getBoolean(this, "is_user_guide_show", false);
        if (!userGuide) {
            startActivity(new Intent(SplashActivity.this, GuideActivity.class));
        } else {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }
        finish();
    }
}
