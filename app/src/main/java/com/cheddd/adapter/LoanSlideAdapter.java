package com.cheddd.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/6/5 0005.
 * 我的借款滑动的适配器
 */

public class LoanSlideAdapter extends FragmentPagerAdapter {
    private List<Fragment> mfrFragments;
    private List<String> mTitles;

    public LoanSlideAdapter(FragmentManager fm, List<Fragment> mfrFragments, List<String> mTitles) {
        super(fm);
        this.mfrFragments = mfrFragments;
        this.mTitles = mTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return mfrFragments.get(position);
    }

    @Override
    public int getCount() {
        return mfrFragments == null ? 0 : mfrFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
