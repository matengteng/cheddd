package com.cheddd.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.cheddd.R;
import com.cheddd.base.MyBaseActivity;
import com.cheddd.view.TopNavigationBar;

public class HetongActivity extends MyBaseActivity implements View.OnClickListener {

    private RelativeLayout mRelativeLayoutFuwu, mRelativeLayoutFuwuJiekuan, mRelativeLayoutWeituo, mRelativeLayoutWeituoFuwuxieyi,
            mRelativeLayoutShouquanshu;

    private TopNavigationBar mTnb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hetong);
        initView();
        setListener();
    }

    private void setListener() {
        mTnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mRelativeLayoutWeituo.setOnClickListener(this);
        mRelativeLayoutFuwu.setOnClickListener(this);
        mRelativeLayoutFuwuJiekuan.setOnClickListener(this);
        mRelativeLayoutShouquanshu.setOnClickListener(this);
        mRelativeLayoutWeituoFuwuxieyi.setOnClickListener(this);


    }

    private void initView() {
        mTnb= (TopNavigationBar) findViewById(R.id.tnb_hetong);
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
                    Intent intent1 = new Intent(HetongActivity.this, MotionActivity.class);
                    intent1.putExtra("url", "http://47.93.163.237:9080/agreement/6.html");
                    startActivity(intent1);
                    break;
                case R.id.rl_hetong_shouquanshu:
                    Intent intent2 = new Intent(HetongActivity.this, MotionActivity.class);
                    intent2.putExtra("url", "http://47.93.163.237:9080/agreement/5.html");
                    startActivity(intent2);
                    break;
                case R.id.rl_hetong_fuwuweituo:
                    Intent intent3 = new Intent(HetongActivity.this, MotionActivity.class);
                    intent3.putExtra("url", "http://47.93.163.237:9080/agreement/3.html");
                    startActivity(intent3);
                    break;
                case R.id.rl_hetong_xieyi:
                    Intent intent4 = new Intent(HetongActivity.this, MotionActivity.class);
                    intent4.putExtra("url", "http://47.93.163.237:9080/agreement/2.html");
                    startActivity(intent4);
                    break;
                default:
                    break;
            }
        }
    }
}
