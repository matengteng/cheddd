package com.cheddd.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cheddd.R;
import com.cheddd.adapter.GuideSlideAdapter;
import com.cheddd.base.MyBaseActivity;
import com.cheddd.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

import static com.cheddd.R.id.v_guide_line;


public class GuideActivity extends MyBaseActivity {

    private ViewPager mViewPage;
    private Button mButton;
    private GuideSlideAdapter mAdapter;
    private static final int[] mImageView = new int[]{R.mipmap.img_guide_01, R.mipmap.img_guide_02, R.mipmap.img_guide_03};
    private List<ImageView> mData;
    private LinearLayout mLineRound;
    private int mPointWidth;
    private View mView;

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
        setContentView(R.layout.activity_guide);
        initView();
        initData();
        setData();
        setListener();
    }

    private void setListener() {
        mViewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int len = (int) (mPointWidth * positionOffset) + position * mPointWidth;
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mView.getLayoutParams();
                params.leftMargin = len;
                mView.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {
                if (position == mImageView.length - 1) {
                    mButton.setVisibility(View.VISIBLE);
                } else {
                    mButton.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtils.setBoolean(GuideActivity.this, "is_user_guide_show", true);
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void setData() {
        mViewPage.setAdapter(mAdapter);
    }

    private void initData() {
        mData = new ArrayList<>();
        for (int i = 0; i < mImageView.length; i++) {
            ImageView image = new ImageView(this);
            image.setBackgroundResource(mImageView[i]);
            mData.add(image);
        }
        for (int i = 0; i < mImageView.length; i++) {
            ImageView image = new ImageView(this);
            image.setImageResource(R.mipmap.icon01);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i > 0) {
                params.leftMargin = 20;
            }

            image.setLayoutParams(params);
            mLineRound.addView(image);
        }
        mLineRound.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            //当LineLayout执行结束后回调
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                mLineRound.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mPointWidth = mLineRound.getChildAt(1).getLeft() - mLineRound.getChildAt(0).getLeft();
            }
        });
        mAdapter = new GuideSlideAdapter(this, mData);
    }

    private void initView() {
        mViewPage = (ViewPager) findViewById(R.id.vp_guide_slide);
        mButton = (Button) findViewById(R.id.bt_guide_enert);
        mLineRound = (LinearLayout) findViewById(R.id.ll_guide_round);
        mView = findViewById(v_guide_line);
    }
}
