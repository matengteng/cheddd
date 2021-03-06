package com.cheddd.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cheddd.bean.SlideBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/20 0020.
 * 首页轮播的适配器
 */

public class IndexHeaderAdapter extends PagerAdapter {
    private List<ImageView> mImageView;


    public IndexHeaderAdapter(List<ImageView> mImageView) {

        this.mImageView = mImageView;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (mImageView.size() != 0) {
            View view = mImageView.get(position % mImageView.size());
            container.addView(view);
            return view;
        }
        return null;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mImageView.get(position % mImageView.size()));
    }
}
