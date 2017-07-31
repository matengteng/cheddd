package com.cheddd.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cheddd.R;
import com.cheddd.base.MyBaseActivity;
import com.cheddd.view.TopNavigationBar;

/**
 * 申请续贷
 */

public class ApplyForActivity extends MyBaseActivity {

    private TopNavigationBar mtnb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_for);
        initView();
        setListener();
    }

    private void setListener() {
        mtnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mtnb = (TopNavigationBar) findViewById(R.id.tnb_applyfor);
    }
}
