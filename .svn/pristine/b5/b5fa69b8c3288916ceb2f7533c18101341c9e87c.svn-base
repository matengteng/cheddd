package com.cheddd.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.cheddd.R;
import com.cheddd.bean.Carousel;

import java.util.List;

/**
 * Created by Administrator on 2017/4/30 0030.
 * 图片循环播放的adapter
 */

public class HomeHeadViewpage extends PagerAdapter {
    private List<Carousel> mData;
    private Context mContext;

    public HomeHeadViewpage(List<Carousel> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
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
        View view = View.inflate(mContext, R.layout.home_head_adapter, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_head_adapter);
        Carousel carousel = mData.get(position % mData.size());
        imageView.setImageResource(carousel.getIconResId());
        container.addView(view);
        return view;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
