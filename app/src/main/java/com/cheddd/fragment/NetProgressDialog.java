package com.cheddd.fragment;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.cheddd.R;

/**
 * Created by mateng on 2017/7/14.
 */

public class NetProgressDialog extends DialogFragment {


    private static NetProgressDialog instance;

    public static NetProgressDialog getInstance() {
        if (instance == null)
            instance = new NetProgressDialog();
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(R.mipmap.net_loadding);
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "rotation", 0, 360);
        animator.setDuration(1000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(-1);
        animator.start();
        return imageView;
    }

}
