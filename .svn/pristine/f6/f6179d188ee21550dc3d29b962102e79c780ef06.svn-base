package com.cheddd.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.cheddd.R;

/**
 * Created by Administrator on 2017/5/18 0018.
 */

public class TopNavigationBar extends FrameLayout {
    private ImageView mIvBack;
    private TextView mTvTitle;

    public TopNavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.topnavigationbar, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TopNavigationBar);
        Drawable drawable = typedArray.getDrawable(R.styleable.TopNavigationBar_navigation_back);
        String title = typedArray.getString(R.styleable.TopNavigationBar_navigation_title);
        mIvBack = (ImageView) view.findViewById(R.id.iv_navigation_back);
        mTvTitle = (TextView) view.findViewById(R.id.tv_navigatio_title);
        mTvTitle.setText(title);
    }

    public void setBackVisibility(boolean show) {
        mIvBack.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    public void setOnBackListener(OnClickListener listener) {
        mIvBack.setOnClickListener(listener);
    }
}
