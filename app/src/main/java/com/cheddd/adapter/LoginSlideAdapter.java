package com.cheddd.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/5/22 0022.
 * 我的登录适配器,
 */

public class LoginSlideAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmenet;

    public LoginSlideAdapter(FragmentManager fm, List<Fragment> fragment) {
        super(fm);
        mFragmenet = fragment;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmenet.get(position);
    }

    @Override
    public int getCount() {
        return mFragmenet == null ? 0 : mFragmenet.size();

    }
}
