package com.cheddd.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cheddd.R;
import com.cheddd.base.MyBaseActivity;
import com.cheddd.view.TopNavigationBar;

public class MoreQuestionActivity extends MyBaseActivity {

    private TopNavigationBar mTnb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_question);
        initView();
        setListener();
    }

    private void setListener() {
        mTnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mTnb = (TopNavigationBar) findViewById(R.id.tnb_morequestion);

    }
}
