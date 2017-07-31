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

public class RefundDetailsActivity extends MyBaseActivity {
    private TopNavigationBar mTnb;
    private static String TAG = RefundDetailsActivity.class.getSimpleName();
    private ImageView mImageView;
    private TextView mTextViewMark, mTextViewOrder, mTextViewMoney, mTextViewNumber;
    private Button mButtonLoanAud, mButtonAuditing, mButtonpayAud;
    private TextView mTvStartTime, mTvAuditingTime, mTvFinshTime;
    private TextView mTextViewLoanAud, mLoanAudAuditing, mTextViewPayAud;
    private String transactionId;
    private String orderNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund_details);
        Intent intent = getIntent();
        transactionId = intent.getStringExtra("transactionId");
        orderNo = intent.getStringExtra("orderNo");
        initView();
        initData();
        setListener();
    }

    private void initData() {
        MineRecord record = new MineRecord();
        record.setTransactionId(transactionId);
        record.setOrderNo(orderNo);
        record.setClientType("2");
        record.setToken(MyApplications.getToken());
        Gson gson = new Gson();
        String json = gson.toJson(record);
        Log.d(TAG, "还款详情" + json);
        final FormBody formBody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.INDEX_PETTLYLOAN_DETALIS, formBody, new OkhttpUtils.HttpCallBack() {

            private String substring;

            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                    Log.d(TAG, "完成" + result);
                    try {
                        //{"token":null,"returnCode":"000000","returnMsg":"操作成功","
                        // entity":{"bindBankCardNo":"11111111111111111","submitRepayStatus":1,
                        // "RepayTime":"2017年07月20日 00:00:00","transactionId":"63ce42b4b9104fb8a22eb3c83d1ee98f","
                        // orderNo":"EM000015E1","bankHandleTime":"2017年07月20日 00:00:00","actualRepayAmt":40559,
                        // "RepayStatus":0,"bindBankTag":"SPDB",
                        // "bindBank":"浦发银行","submitRepayTime":"","bankHandleStatus":0},"rows":[],"flag":"true"}
                        JSONObject object = new JSONObject(result);
                        String returnCode = object.getString("returnCode");
                        if ("000000".equals(returnCode)) {
                            JSONObject entity = object.getJSONObject("entity");
                            String bindBankCardNo = entity.getString("bindBankCardNo");
                            String transactionId = entity.getString("transactionId");
                            String orderNo = entity.getString("orderNo");
                            double actualRepayAmt = entity.getDouble("actualRepayAmt");
                            String bindBank = entity.getString("bindBank");
                            String repayTime = entity.getString("RepayTime");
                            String bankHandleTime = entity.getString("bankHandleTime");
                            String submitRepayTime = entity.getString("submitRepayTime");
                            int submitRepayStatus = entity.getInt("submitRepayStatus");
                            int repayStatus = entity.getInt("RepayStatus");
                            int bankHandleStatus = entity.getInt("bankHandleStatus");
                            if (bindBankCardNo.length() > 4) {
                                substring = bindBankCardNo.substring(bindBankCardNo.length() - 4);
                            }
                            /*
                            * map.put("bindBank", "");// 绑定的银行
		map.put("bindBankCardNo", "");// 绑定的银行卡号
		map.put("bindBankTag", "");// 绑定的银行卡标记
		map.put("orderNo", "");// 订单编号
		map.put("transactionId", 0);// 交易编号
		map.put("actualRepayAmt", 0);// 还款金额

		map.put("submitRepayStatus", 1);// 提交还款状态 0 已通过 1 未通过
		map.put("submitRepayTime", "");// 提交还款状态时间
		map.put("bankHandleStatus", 1);// 银行处理状态 0 已通过 1 未通过
		map.put("bankHandleTime", "");// 银行处理状态时间
		map.put("RepayStatus", 1);// 还款成功 0 已通过 1 未通过
		map.put("RepayTime", "");// 还款成功时间*/
                            mTextViewOrder.setText(orderNo);
                            mTextViewMoney.setText(actualRepayAmt / 100 + "元");
                            mTextViewMark.setText(bindBank);
                            mTextViewNumber.setText("(" + substring + ")");
                            mTvStartTime.setText(submitRepayTime);
                            mTvAuditingTime.setText(bankHandleTime);
                            mTvFinshTime.setText(repayTime);
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
                            if (0 == submitRepayStatus) {
                                mButtonLoanAud.setEnabled(true);
                                mTextViewLoanAud.setEnabled(true);
                            } else {
                                mButtonLoanAud.setEnabled(false);
                                mTextViewLoanAud.setEnabled(false);
                            }
                            //借款审核
                            if (0 == bankHandleStatus) {
                                mButtonAuditing.setEnabled(true);
                                mTextViewLoanAud.setEnabled(true);
                            } else {
                                mButtonAuditing.setEnabled(false);
                                mTextViewLoanAud.setEnabled(false);
                            }
                            //借款成功
                            if (0 == repayStatus) {
                                mButtonpayAud.setEnabled(true);
                                mTextViewPayAud.setEnabled(true);
                            } else {
                                mButtonpayAud.setEnabled(false);
                                mTextViewPayAud.setEnabled(false);
                            }
                        } else {
                            return;
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
