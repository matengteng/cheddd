package com.cheddd.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.cheddd.R;
import com.cheddd.base.MyBaseActivity;
import com.cheddd.fragment.IndexFragment;
import com.cheddd.fragment.InfoFragment;
import com.cheddd.fragment.MineFragment;

public class MainActivity extends MyBaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private RadioGroup mRg;
    private FragmentManager mManager;
    private Fragment mCurrentfragment;
    private boolean isExit = false;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_main);
        initView();
        initData();
        //setData();
        setListener();
    }

    private void setData() {
        Intent intent = getIntent();
        int i = intent.getIntExtra("PhoneApproveActivity", -1);
        if (i == 1) {
            Fragment fragment = new InfoFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fl_main_radiofroup, fragment);
            transaction.commit();
        }
    }


    //设置监听
    private void setListener() {
        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Class<? extends Fragment> clazz = null;
                switch (checkedId) {
                    case R.id.rb_main_home:
                        clazz = IndexFragment.class;
                        break;
                    case R.id.rb_main_manage:
                        clazz = InfoFragment.class;
                        break;
                    case R.id.rb_main_mine:
                        clazz = MineFragment.class;
                        break;
                }
                showAndHide(R.id.fl_main_radiofroup, clazz);
            }
        });
    }

    //初始化数据
    private void initData() {
        mManager = getSupportFragmentManager();
        showAndHide(R.id.fl_main_radiofroup, IndexFragment.class);

    }

    //加载控件
    private void initView() {
        mRg = (RadioGroup) findViewById(R.id.rg_main_fragment);
    }

    private void showAndHide(int contentId, Class<? extends Fragment> clazz) {
        if (mCurrentfragment != null && mCurrentfragment.getClass().getSimpleName().equals(clazz.getSimpleName())) {
            return;
        }
        FragmentTransaction transaction = mManager.beginTransaction();
        Fragment fragment = null;
        try {
            Fragment fragmentByTag = mManager.findFragmentByTag(clazz.getSimpleName());
            if (fragmentByTag != null) {
                transaction.show(fragmentByTag);
                transaction.hide(mCurrentfragment);
                fragmentByTag = mCurrentfragment;
            } else {
                fragment = clazz.newInstance();
                transaction.replace(contentId, fragment, clazz.getSimpleName());
                if (mCurrentfragment != null) {
                    transaction.hide(mCurrentfragment);
                }
                mCurrentfragment = fragment;
            }
            transaction.commit();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setExit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void setExit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(MainActivity.this, "请再按一次退出程序", Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            showAndHide(R.id.fl_main_radiofroup, InfoFragment.class);
        }

    }*/
}
