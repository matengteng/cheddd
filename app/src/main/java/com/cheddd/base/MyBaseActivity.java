package com.cheddd.base;

import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.cheddd.utils.StatusBarUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.LinkedList;
import java.util.List;

public abstract class MyBaseActivity extends AppCompatActivity {
    private List<MyBaseActivity> mActivity = new LinkedList<>();
    private List<MyBaseActivity> copy;
    private TextView status;

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        synchronized (mActivity) {
            mActivity.remove(this);
        }

    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        //设置具体的场景
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_ANALYTICS_OEM);



//
        StatusBarUtils.assistActivity(this);
//        status = new TextView(this);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setStatusBarColor(Color.parseColor("#e83d2e"));
//        }

        synchronized (mActivity) {
            mActivity.add(this);
        }
        init();
    }

    public void killAll() {
        synchronized (mActivity) {
            copy = new LinkedList<>(mActivity);
        }

        for (MyBaseActivity activity : copy) {
            activity.finish();
        }
        MobclickAgent.onKillProcess(this);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    protected void init() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    public int getStatusHeight() {
        int statusBarHeight = -1;
//获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.getCurrentFocus() != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return manager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);

    }


}
