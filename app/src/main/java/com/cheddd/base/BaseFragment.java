package com.cheddd.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.cheddd.R;

/**
 * Created by Administrator on 2017/4/27 0027.
 */

public class BaseFragment extends Fragment {
    private View loadinView;//加载中的界面
    private View errorView;//错误界面
    private View emptyView;//空界面
    private View successView;//加载成功的界面
    private FrameLayout mFrameLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFrameLayout = new FrameLayout(getActivity());
        initView();
        showPage();
        show();
        return mFrameLayout;
    }

    //根据服务器切换状态
    private void show() {
    }

    //根据不同的状态，显示不同的界面
    private void showPage() {
    }

    //在frameLayout中添加4中不同的界面
    private void initView() {
        if (loadinView == null) {
            loadinView = createLoadingView();
            mFrameLayout.addView(loadinView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        }
        if (errorView == null) {
            errorView = createErrorView();
            mFrameLayout.addView(loadinView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        }
        if (errorView == null) {
            emptyView = creteEmpteyView();
            mFrameLayout.addView(loadinView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        }
    }

    private View creteEmpteyView() {
        return null;
    }

    private View createErrorView() {
        return null;
    }

    private View createLoadingView() {
        View view = View.inflate(getActivity(), R.layout.createloading, null);
        return view;
    }

}
