package com.cheddd.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.cheddd.R;
import com.cheddd.base.MyBaseActivity;

public class HetongActivity extends MyBaseActivity implements View.OnClickListener {

    private RelativeLayout mRelativeLayoutFuwu, mRelativeLayoutFuwuJiekuan, mRelativeLayoutWeituo, mRelativeLayoutWeituoFuwuxieyi,
            mRelativeLayoutShouquanshu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hetong);
        initView();
        setListener();
    }

    private void setListener() {
        mRelativeLayoutWeituo.setOnClickListener(this);
        mRelativeLayoutFuwu.setOnClickListener(this);
        mRelativeLayoutFuwuJiekuan.setOnClickListener(this);
        mRelativeLayoutShouquanshu.setOnClickListener(this);


    }

    private void initView() {
        mRelativeLayoutFuwu = (RelativeLayout) findViewById(R.id.rl_hetong_fuwu);
        mRelativeLayoutFuwuJiekuan = (RelativeLayout) findViewById(R.id.rl_hetong_xieyi);
        mRelativeLayoutWeituo = (RelativeLayout) findViewById(R.id.rl_hetong_koukuan);
        mRelativeLayoutWeituoFuwuxieyi = (RelativeLayout) findViewById(R.id.rl_hetong_fuwuweituo);
        mRelativeLayoutShouquanshu = (RelativeLayout) findViewById(R.id.rl_hetong_shouquanshu);
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.rl_hetong_fuwu:
                    break;
                case R.id.rl_hetong_shouquanshu:
                    break;
                case R.id.rl_hetong_koukuan:
                    break;
                case R.id.rl_hetong_xieyi:
                    break;
                default:
                    break;
            }
        }
    }
}
