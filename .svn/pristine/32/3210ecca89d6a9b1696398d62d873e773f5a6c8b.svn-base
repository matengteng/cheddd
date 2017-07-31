package com.cheddd.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cheddd.R;
import com.cheddd.adapter.LoanSlideAdapter;
import com.cheddd.base.MyBaseActivity;
import com.cheddd.fragment.MoreLoansFragment;
import com.cheddd.fragment.PayoffFragment;
import com.cheddd.fragment.RefundFragment;
import com.cheddd.view.TopNavigationBar;

import java.util.ArrayList;
import java.util.List;

public class MineLoanActivity extends MyBaseActivity {

    private TopNavigationBar mTnb;
    private ViewPager mViewPageSlide;
    private TabLayout mTabLayoutTitle;
    private List<String> mTitle;
    private List<Fragment> mFragment;
    private LoanSlideAdapter mSlideAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_loan);
        initView();
        initData();
        setData();
        setListener();
    }

    private void setData() {
        mViewPageSlide.setAdapter(mSlideAdapter);
        mTabLayoutTitle.setupWithViewPager(mViewPageSlide);
        mViewPageSlide.setOffscreenPageLimit(2);
    }

    private void initData() {
        mTitle = new ArrayList<>();
        mFragment = new ArrayList<>();
        mTitle.add("还款中");
        mTitle.add("已还清");
        for (int i = 0; i < mTitle.size(); i++) {
            if (i == 0) {
                mFragment.add(new MoreLoansFragment());
            } else {
                mFragment.add(new PayoffFragment());
            }
        }
        mSlideAdapter = new LoanSlideAdapter(getSupportFragmentManager(), mFragment, mTitle);
    }

    private void setListener() {
        mTnb.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mTnb = (TopNavigationBar) findViewById(R.id.tnb_mineloan);
        mTabLayoutTitle = (TabLayout) findViewById(R.id.tl_mineloan_title);
        mViewPageSlide = (ViewPager) findViewById(R.id.vp_mineloan_slide);
    }
}
