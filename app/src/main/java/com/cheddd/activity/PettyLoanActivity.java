package com.cheddd.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheddd.R;
import com.cheddd.application.MyApplications;
import com.cheddd.base.MyBaseActivity;
import com.cheddd.config.NetConfig;
import com.cheddd.utils.LoginTokenUtils;
import com.cheddd.utils.OkhttpUtils;
import com.cheddd.utils.ToastUtil;
import com.cheddd.view.CircleProgressBar;
import com.cheddd.view.CircleProgressView;
import com.cheddd.view.TopNavigationBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Request;

/*
小额贷款的界面
*/
public class PettyLoanActivity extends MyBaseActivity implements View.OnClickListener {
    private static String TAG = PettyLoanActivity.class.getSimpleName();
    //我的借款,提前还请借款,dialong关闭按钮
    private RelativeLayout mRelativeBorrowMoney, mRelativeAdvance, mRelativeClose;
    private Dialog mDialog;
    //借钱
    private TextView mTextViewLendMoney, mTextViewData, mTextViewMoney, mTextViewSmall;
    private int mProgress;
    private CircleProgressView mProgressBar;

    private TopNavigationBar mTnb;
    private Button mButtonProblem, mButtonKnow;
   /* private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mProgress < 100) {
                mProgress++;
                 // mProgressBar.setProgress(mProgress);
                mHandler.sendEmptyMessageDelayed(1, 100);
            }
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petty_loan);
        initView();
        initData();
        setListener();
    }



    private void initData() {
        final String json = LoginTokenUtils.getJson();
        FormBody formbody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.INDEX_PETTYLOAN_INFO, formbody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                    Log.d(TAG, "获取可借额度和总额度，还款试算" + result);
                    Log.d(TAG, "onSuccess:" + NetConfig.INDEX_PETTYLOAN_INFO + "content" + "=" + json);
                    try {
                        JSONObject object = new JSONObject(result);
                        String returnCode = object.getString("returnCode");
                        String returnMsg = object.getString("returnMsg");
                        if ("000000".equals(returnCode)) {
                            JSONObject entity = object.getJSONObject("entity");
                            int loanLimit = entity.getInt("loanLimit");
                            int smallLoanSum = entity.getInt("smallLoanSum");
                            String orderNo = entity.getString("orderNo");
                            MyApplications.setOrderNo(orderNo);
                            String newRepaymentDate = entity.getString("newRepaymentDate");
                            double newRepayment = entity.getDouble("newRepayment");
                            mProgressBar.setValue((int) (1.0 * loanLimit / smallLoanSum * 360));
                            mTextViewData.setText(newRepaymentDate + "应还");
                            mTextViewMoney.setText("￥" + newRepayment / 100 + "");
                            mTextViewSmall.setText(loanLimit / 100 + "");
                        }else  if("0017".equals(returnCode)){
                            startActivity(new Intent(PettyLoanActivity.this,LoginActivity.class));
                        }else if("0021".equals(returnCode)){
                            mRelativeAdvance.setVisibility(View.GONE);
                            mRelativeBorrowMoney.setVisibility(View.GONE);
                            mTextViewLendMoney.setEnabled(false);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }


    private void setListener() {
        mRelativeAdvance.setOnClickListener(this);
        mTextViewLendMoney.setOnClickListener(this);
        mRelativeBorrowMoney.setOnClickListener(this);
        //mProgressBar.setOnClickListener(this);
        mTnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mTextViewSmall = (TextView) findViewById(R.id.tv_pettyloan_small);
        mTextViewMoney = (TextView) findViewById(R.id.tv_petty_money);
        mTextViewData = (TextView) findViewById(R.id.tv_petty_month);
        mRelativeAdvance = (RelativeLayout) findViewById(R.id.rl_petty_advance);
        mProgressBar = (CircleProgressView) findViewById(R.id.cpb_petty_round);
        mTextViewLendMoney = (TextView) findViewById(R.id.tv_petty_lendmoney);
        mRelativeBorrowMoney = (RelativeLayout) findViewById(R.id.rl_petty_borrowMoney);
        mTnb = (TopNavigationBar) findViewById(R.id.tnb_petty);
    }

    public void onClick(View view) {
        if (view != null) {
            switch (view.getId()) {
               /* case R.id.cpb_petty_round:
                    mProgress = 0;
                    mHandler.sendEmptyMessageDelayed(1, 100);
                    break;*/
                //借钱
                case R.id.tv_petty_lendmoney:
                    // startActivity(new Intent(this, LendMoneyActivity.class));
                    lendMoney();
                    break;
                //我的借款
                case R.id.rl_petty_borrowMoney:
                    startActivity(new Intent(this, LoanActivity.class));
                    break;
                //提前还请借款
                case R.id.rl_petty_advance:
                    startActivity(new Intent(this, AdvanceActivtiy.class));
                    finish();
                    break;
                case R.id.bt_petty_money:
                    know();
                    break;
                case R.id.bt_petty_problem:
                    break;
                case R.id.rl_dialog_close:
                    mDialog.dismiss();
                    break;
                default:
                    break;
            }
        }
    }

    //借钱
    private void lendMoney() {
        final String json = LoginTokenUtils.getJson();
        FormBody formbody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.INDEX_PETTLYLOAN_ORDER, formbody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                    Log.d(TAG, "判断借钱的状态" + result);
                    try {
                        JSONObject object = new JSONObject(result);
                        String returnCode = object.getString("returnCode");
                        String returnMsg = object.getString("returnMsg");
                        if ("000000".equals(returnCode)) {
                            JSONObject entity = object.getJSONObject("entity");
                            int canLoanYN = entity.getInt("canLoanYN");
                            if (canLoanYN == 0) {
                                startActivity(new Intent(PettyLoanActivity.this, LendMoneyActivity.class));
                            } else {
                                startActivity(new Intent(PettyLoanActivity.this, LendDetailsActivity.class));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    //借款须知
    private void know() {
        mDialog = new AlertDialog.Builder(this).create();
        View view = LayoutInflater.from(this).inflate(R.layout.dialong_lend_konw, null);
        mDialog.setCanceledOnTouchOutside(false);
        mRelativeClose = (RelativeLayout) view.findViewById(R.id.rl_dialog_close);
        mRelativeClose.setOnClickListener(this);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();
        mDialog.getWindow().setContentView((LinearLayout) view);
    }
}
