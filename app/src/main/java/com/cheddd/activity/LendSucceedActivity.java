package com.cheddd.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheddd.R;
import com.cheddd.application.MyApplications;
import com.cheddd.base.MyBaseActivity;
import com.cheddd.bean.IndexLoanBean;
import com.cheddd.bean.IndexLoanDetalis;
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

/*
借款成功
*/
public class LendSucceedActivity extends MyBaseActivity implements View.OnClickListener {

    private static String TAG = LendSucceedActivity.class.getSimpleName();
    private TopNavigationBar mTnb;
    private Button mButtonFinsh, mButtonKnow;
    private Dialog mDialog;
    private RelativeLayout mRelativeKnow;
    private TextView mTextViewMark, mTextViewNumber, mTextViewMoney;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lend_succeed);
        initView();
        initData();
        setListener();
    }

    private void initData() {
        String json = LoginTokenUtils.getJson();
        FormBody formBody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.INDEX_PETTLYLOAN_ORDER, formBody, new OkhttpUtils.HttpCallBack() {

            private String substring;

            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                   // Log.d(TAG, "完成" + result);
                    try {
                        JSONObject object = new JSONObject(result);
                        JSONObject entity = object.getJSONObject("entity");
                        String bindBank = entity.getString("bindBank");
                        double contractAmt = entity.getDouble("contractAmt");
                        String bindBankCardNo = entity.getString("bindBankCardNo");
                        if (bindBankCardNo.length() > 4) {
                            substring = bindBankCardNo.substring(bindBankCardNo.length() - 4);
                        }
                        mTextViewMoney.setText(contractAmt/100+"元");
                        mTextViewMark.setText(bindBank);
                        mTextViewNumber.setText("(" + substring + ")");
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
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void setListener() {
        mButtonKnow.setOnClickListener(this);
        mButtonKnow.setOnClickListener(this);
        mButtonFinsh.setOnClickListener(this);
        mTnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mTnb = (TopNavigationBar) findViewById(R.id.tnb_lendSucceed);
        mButtonFinsh = (Button) findViewById(R.id.bt_lendSuccess_finsh);
        mButtonKnow = (Button) findViewById(R.id.bt_petty_money);
        mTextViewMoney = (TextView) findViewById(R.id.tv_lendSucceed_money);
        mTextViewMark = (TextView) findViewById(R.id.tv_lendSucceed_mark);
        mTextViewNumber = (TextView) findViewById(R.id.tv_lendSucceed_number);
        mImageView = (ImageView) findViewById(R.id.iv_lendSucceed_mark);
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.bt_lendSuccess_finsh:
                    startActivity(new Intent(this, LendDetailsActivity.class));
                    finish();
                    break;
                case R.id.bt_petty_money:
                    know();
                    break;
                case R.id.rl_dialog_close:
                    mDialog.dismiss();
                    break;
                case R.id.bt_petty_problem:
                    Intent intent = new Intent(LendSucceedActivity.this, MoreQuestionActivity.class);
                    intent.putExtra("url", "http://47.93.163.237:9080/agreement/4.html");
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    }

    private void know() {
        mDialog = new AlertDialog.Builder(this).create();
        View view = LayoutInflater.from(this).inflate(R.layout.dialong_lend_konw, null);
        mDialog.setCanceledOnTouchOutside(false);
        mRelativeKnow = (RelativeLayout) view.findViewById(R.id.rl_dialog_close);
        mRelativeKnow.setOnClickListener(this);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();
        mDialog.getWindow().setContentView((LinearLayout) view);
    }

}
