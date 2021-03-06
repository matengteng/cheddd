package com.cheddd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.cheddd.R;
import com.cheddd.adapter.BorrowMoneyAdapter;
import com.cheddd.base.MyBaseActivity;
import com.cheddd.bean.RepaymentPlan;
import com.cheddd.view.TopNavigationBar;

import java.util.List;

/**
 * 还款的界面.1)从小额借款界面挑转 2）还款中的界面跳转
 */

public class BorrowMoneyActivtiy extends MyBaseActivity implements View.OnClickListener {

    private ListView mListView;
    private TopNavigationBar mTnb;
    private ScrollView mScrollView;
    private BorrowMoneyAdapter mAdapter;
    private List<RepaymentPlan> mList;
    //申请续贷，提前还请借款
    private RelativeLayout mRelativeApplyfor, mRelativeAdvance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_money_activtiy);
        initView();
        initData();
        setData();
        setListener();
    }

    private void setData() {
        mListView.setAdapter(mAdapter);
    }

    private void initData() {
      /*  mList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            mList.add(new BorrowMoneyBean());
        }
        mAdapter = new BorrowMoneyAdapter(mList, BorrowMoneyActivtiy.this);*/
        //mAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mData);
    }

    private void setListener() {
        mRelativeApplyfor.setOnClickListener(this);
        mRelativeAdvance.setOnClickListener(this);
        mTnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.lv_borrowmoney_list);
        mTnb = (TopNavigationBar) findViewById(R.id.tnb_borrowmoney);
        mRelativeAdvance = (RelativeLayout) findViewById(R.id.rl_borrowmoney_advance);
        mRelativeApplyfor = (RelativeLayout) findViewById(R.id.rl_borrowmoney_applyfor);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            mListView.dispatchTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.rl_borrowmoney_advance:
                    startActivity(new Intent(this, AdvanceActivtiy.class));
                    finish();
                    break;
                case R.id.rl_borrowmoney_applyfor:
                    startActivity(new Intent(this, ApplyForActivity.class));
                    finish();
                    break;
                default:
                    break;
            }
        }
    }
}
