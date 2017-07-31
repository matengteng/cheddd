package com.cheddd.activity;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cheddd.R;
import com.cheddd.adapter.LoginSlideAdapter;
import com.cheddd.base.MyBaseActivity;
import com.cheddd.fragment.LoginPasswordFragment;
import com.cheddd.fragment.LoginPhoneFragment;
import com.cheddd.fragment.MineRecordFragment;
import com.cheddd.fragment.MineRefundFragment;
import com.cheddd.view.TopNavigationBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 交易记录的界面，已登录从我的界面跳转过来
 */

public class RecordActivity extends MyBaseActivity {
    private TopNavigationBar mBar;
    private ViewPager mViewPageSlide;
    private List<Fragment> mDataFragment;
    private LoginSlideAdapter mViewPageAdapter;
    private RadioButton[] mRadioButton;
    private RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        initView();
        initData();
        initTab();
        setData();
        setlistener();
    }

    private void initTab() {
        mRadioButton = new RadioButton[2];
        for (int i = 0; i < mRadioButton.length; i++) {
            mRadioButton[i] = (RadioButton) mRadioGroup.getChildAt(i);
            mRadioButton[i].setChecked(false);
            mRadioButton[i].setHintTextColor(Color.RED);

        }
        mRadioButton[0].setChecked(true);
        mRadioGroup.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        for (int i = 0; i < mRadioButton.length; i++) {
                            if (mRadioButton[i].getId() == checkedId) {
                                mViewPageSlide.setCurrentItem(i);
                            }
                        }
                    }
                });
        mViewPageSlide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < mRadioButton.length; i++) {
                    mRadioButton[i].setChecked(false);
                }
                mRadioButton[position].setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void setlistener() {
        mBar.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void setData() {
        mViewPageAdapter = new LoginSlideAdapter(getSupportFragmentManager(), mDataFragment);
        mViewPageAdapter.notifyDataSetChanged();
        mViewPageSlide.setAdapter(mViewPageAdapter);
    }

    private void initData() {
        mDataFragment = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                mDataFragment.add(new MineRecordFragment());
            } else {
                mDataFragment.add(new MineRefundFragment());
            }

        }
    }

    private void initView() {
        mBar = (TopNavigationBar) findViewById(R.id.tnb_record);
        mViewPageSlide = (ViewPager) findViewById(R.id.vp_record_slide);
        mRadioGroup = (RadioGroup) findViewById(R.id.rg_record_fragment);
    }
}
