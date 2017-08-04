package com.cheddd.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cheddd.R;
import com.cheddd.application.MyApplications;
import com.cheddd.base.MyBaseActivity;
import com.cheddd.bean.MineRecord;
import com.cheddd.config.NetConfig;
import com.cheddd.utils.LoginTokenUtils;
import com.cheddd.utils.OkhttpUtils;
import com.cheddd.view.TopNavigationBar;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * 借款详情
 */

public class LendDetailsActivity extends MyBaseActivity {

    private TopNavigationBar mTnb;
    private static String TAG = LendDetailsActivity.class.getSimpleName();
    private ImageView mImageView;
    private TextView mTextViewMark, mTextViewOrder, mTextViewMoney, mTextViewNumber;
    private Button mButtonLoanAud, mButtonAuditing, mButtonpayAud;
    private TextView mTvStartTime, mTvAuditingTime, mTvFinshTime;
    private TextView mTextViewLoanAud, mLoanAudAuditing, mTextViewPayAud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lend_details);
        initView();
        initData();
        setListener();
    }

    private void initData() {
        MineRecord record = new MineRecord();
        record.setOrderNo(MyApplications.getOrderNo());
        record.setToken(MyApplications.getToken());
        record.setClientType("2");
        Gson gson = new Gson();
        String json = gson.toJson(record);
        final FormBody formBody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.INDEX_PETTLYLOAN_ORDER, formBody, new OkhttpUtils.HttpCallBack() {

            private String substring;

            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                  //  Log.d(TAG, "完成" + result);
                    try {
                        JSONObject object = new JSONObject(result);
                        JSONObject entity = object.getJSONObject("entity");
                        String bindBank = entity.getString("bindBank");
                        double contractAmt = entity.getDouble("contractAmt");
                        String bindBankCardNo = entity.getString("bindBankCardNo");
                        int loanAud = entity.getInt("loanAud");
                        int payAud = entity.getInt("payAud");
                        int auditing = entity.getInt("auditing");
                        String loanAudDate = entity.getString("loanAudDate");
                        String auditingDate = entity.getString("auditingDate");
                        String payAudDate = entity.getString("payAudDate");

                        if (bindBankCardNo.length() > 4) {
                            substring = bindBankCardNo.substring(bindBankCardNo.length() - 4);
                        }
                        String orderNo = entity.getString("orderNo");
                        mTextViewOrder.setText(orderNo);
                        mTextViewMoney.setText(contractAmt / 100 + "元");
                        mTextViewMark.setText(bindBank);
                        mTextViewNumber.setText("(" + substring + ")");
                        mTvStartTime.setText(loanAudDate);
                        mTvAuditingTime.setText(auditingDate);
                        mTvFinshTime.setText(payAudDate);
                        if (mTextViewMark.getText().toString().equals("交通银行 ")) {
                            mImageView.setImageResource(R.mipmap.bank_com);
                        } else if (mTextViewMark.getText().toString().equals("招商银行")) {
                            mImageView.setImageResource(R.mipmap.bank_cmbc);
                        } else if (mTextViewMark.getText().toString().equals("民生银行")) {
                            mImageView.setImageResource(R.mipmap.bank_cmsb);
                        } else if (mTextViewMark.getText().toString().equals("农业银行")) {
                            mImageView.setImageResource(R.mipmap.bank_abc);
                        } else if (mTextViewMark.getText().toString().equals("上海银行")) {
                            mImageView.setImageResource(R.mipmap.bank_sh);
                        } else if (mTextViewMark.getText().toString().equals("广发银行")) {
                            mImageView.setImageResource(R.mipmap.bank_cgb);
                        } else if (mTextViewMark.getText().toString().equals("中国银行")) {
                            mImageView.setImageResource(R.mipmap.bank_boc);
                        } else if (mTextViewMark.getText().toString().equals("建设银行")) {
                            mImageView.setImageResource(R.mipmap.bank_ccb);
                        } else if (mTextViewMark.getText().toString().equals("兴业银行")) {
                            mImageView.setImageResource(R.mipmap.bank_cib);
                        } else if (mTextViewMark.getText().toString().equals("中信银行")) {
                            mImageView.setImageResource(R.mipmap.bank_citic);
                        } else if (mTextViewMark.getText().toString().equals("华夏银行")) {
                            mImageView.setImageResource(R.mipmap.bank_hxb);
                        } else if (mTextViewMark.getText().toString().equals("平安银行")) {
                            mImageView.setImageResource(R.mipmap.bank_pa);
                        } else if (mTextViewMark.getText().toString().equals("工商银行")) {
                            mImageView.setImageResource(R.mipmap.bank_icbc);
                        } else if (mTextViewMark.getText().toString().equals("光大银行 ")) {
                            mImageView.setImageResource(R.mipmap.bank_ceb);
                        } else if (mTextViewMark.getText().toString().equals("邮储银行")) {
                            mImageView.setImageResource(R.mipmap.bank_yz);
                        } else if (mTextViewMark.getText().toString().equals("浦发银行")) {
                            mImageView.setImageResource(R.mipmap.bank_spdp);
                        } else {
                            mImageView.setImageResource(R.mipmap.ic_launcher);
                        }
                        //借款申请
                        if (0 == loanAud) {
                            mButtonLoanAud.setEnabled(true);
                            mTextViewLoanAud.setEnabled(true);
                        } else {
                            mButtonLoanAud.setEnabled(false);
                            mTextViewLoanAud.setEnabled(false);
                        }
                        //借款审核
                        if (0 == auditing) {
                            mButtonAuditing.setEnabled(true);
                            mTextViewLoanAud.setEnabled(true);
                            mTvAuditingTime.setEnabled(true);
                        } else {
                            mButtonAuditing.setEnabled(false);
                            mTextViewLoanAud.setEnabled(false);
                            mTvAuditingTime.setEnabled(false);
                        }
                        //借款成功
                        if (0 == payAud) {
                            mButtonpayAud.setEnabled(true);
                            mTextViewPayAud.setEnabled(true);
                        } else {
                            mButtonpayAud.setEnabled(false);
                            mTextViewPayAud.setEnabled(false);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
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
        mTnb = (TopNavigationBar) findViewById(R.id.tnb_lendDetails);
        mTextViewMark = (TextView) findViewById(R.id.tv_lenddetails_bank);
        mTextViewMoney = (TextView) findViewById(R.id.tv_lenddetails);
        mTextViewNumber = (TextView) findViewById(R.id.tv_lenddetails_number);
        mTextViewOrder = (TextView) findViewById(R.id.tv_lendDetails_order);
        mImageView = (ImageView) findViewById(R.id.iv_lenddetails_bank);
        mButtonLoanAud = (Button) findViewById(R.id.bt_lendDetails_redtop);
        mButtonAuditing = (Button) findViewById(R.id.bt_lendDetails_finance);
        mButtonpayAud = (Button) findViewById(R.id.bt_lendDetails_finsh);
        mTvAuditingTime = (TextView) findViewById(R.id.tv_lendDetails_AuditingTime);
        mTvStartTime = (TextView) findViewById(R.id.tv_lendDetails_startTime);
        mTvFinshTime = (TextView) findViewById(R.id.tv_lendDetails_finshTime);
        mTextViewPayAud = (TextView) findViewById(R.id.tv_lendDetails_applyfor1);
        mTextViewLoanAud = (TextView) findViewById(R.id.tv_lendDetails_finance);
        mTextViewPayAud = (TextView) findViewById(R.id.tv_lendDetails_finsh);
    }

}
