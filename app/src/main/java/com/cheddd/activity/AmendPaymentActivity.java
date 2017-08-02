package com.cheddd.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheddd.R;
import com.cheddd.application.MyApplications;
import com.cheddd.base.MyBaseActivity;
import com.cheddd.bean.AmendPayBean;
import com.cheddd.config.NetConfig;
import com.cheddd.utils.MD5Utils;
import com.cheddd.utils.OkhttpUtils;
import com.cheddd.utils.SharedPreferencesUtils;
import com.cheddd.utils.ToastUtil;
import com.cheddd.view.PwdEditText;
import com.cheddd.view.TopNavigationBar;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * 修改支付密码的界面
 */

public class AmendPaymentActivity extends MyBaseActivity implements View.OnClickListener {



    private static String TAG = AmendPaymentActivity.class.getSimpleName();
    private TopNavigationBar mTnb;
    //第一次输入密码，第二次输入密码
    private RelativeLayout mRelativeOne, mRelativeTwo, mRelativeFormer;
    private PwdEditText mEditTextOne, mEditTexgtTwo, mEditTexgtFormer;
    private TextView mTextViewForget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amend_payment);
        initView();
        setListener();
    }

    private void setListener() {
        mTextViewForget.setOnClickListener(this);
        mTnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mEditTexgtFormer.setOnInputFinishListener(new PwdEditText.OnInputFinishListener() {
            @Override
            public void onInputFinish(String password) {
                int selectionEnd = mEditTexgtFormer.getSelectionEnd();
                if (selectionEnd == 6) {
                    mEditTextOne.requestFocus();
                    mRelativeFormer.setVisibility(View.INVISIBLE);
                    mRelativeOne.setVisibility(View.VISIBLE);
                }
            }
        });
        mEditTextOne.setOnInputFinishListener(new PwdEditText.OnInputFinishListener() {
            @Override
            public void onInputFinish(String password) {
                int selectionEnd = mEditTexgtFormer.getSelectionEnd();
                if (selectionEnd == 6) {
                    mEditTexgtTwo.requestFocus();
                    mRelativeOne.setVisibility(View.INVISIBLE);
                    mRelativeTwo.setVisibility(View.VISIBLE);
                }
            }
        });
        mEditTexgtTwo.setOnInputFinishListener(new PwdEditText.OnInputFinishListener() {
            @Override
            public void onInputFinish(String password) {
                int selectionEnd = mEditTexgtFormer.getSelectionEnd();
                String formerPassword = mEditTexgtFormer.getText().toString().trim();
                String onePassword = mEditTextOne.getText().toString().trim();
                String twoPassword = mEditTexgtTwo.getText().toString().trim();
                if (onePassword.equals(twoPassword)) {
                    AmendPayBean paybean = new AmendPayBean();
                    paybean.setToken(MyApplications.getToken());
                    paybean.setClientType("2");
                    String phone = SharedPreferencesUtils.getString(AmendPaymentActivity.this, "phone", "");
                    paybean.setPayPassWord(MD5Utils.encode(phone + mEditTexgtTwo.getText().toString().trim()));
                    paybean.setOldPayPassWord(MD5Utils.encode(phone + mEditTexgtFormer.getText().toString().trim()));
                    paybean.setTelNo(phone);
                    Gson gson = new Gson();
                    String json = gson.toJson(paybean);
                    Log.d(TAG,"修改支付密码"+json);
                    if (selectionEnd == 6) {
                        FormBody formBody = new FormBody.Builder().add("content", json).build();
                        OkhttpUtils.getInstance(AmendPaymentActivity.this).asyncPost(NetConfig.MINE_PAY_AMENDPAYMEMT, formBody, new OkhttpUtils.HttpCallBack() {
                            @Override
                            public void onError(Request request, IOException e) {

                            }

                            @Override
                            public void onSuccess(Request request, String result) {
                                if (result != null) {
                                    Log.d(TAG, "变更支付密码" + result);
                                    //{"token":null,"returnCode":"0030",
                                    // "returnMsg":"此用户支付密码已设置","entity":null,"rows":[],"flag":"false"}
                                    try {
                                        JSONObject object = new JSONObject(result);
                                        String returnCode = object.getString("returnCode");
                                        String returnMsg = object.getString("returnMsg");
                                        if ("000000".equals(returnCode)) {
                                            ToastUtil.show(AmendPaymentActivity.this, returnMsg);
                                            finish();
                                        } else if ("0039".equals(returnCode)) {
                                            startActivity(new Intent(AmendPaymentActivity.this, AmendPaymentActivity.class));
                                            ToastUtil.show(AmendPaymentActivity.this, returnMsg);
                                            finish();
                                        } else {
                                            startActivity(new Intent(AmendPaymentActivity.this, LoginActivity.class));
                                            finish();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                            }
                        });
                        startActivity(new Intent(AmendPaymentActivity.this, SafetyActivity.class));
                        finish();
                    }
                }
            }
        });
    }

    private void initView() {
        mTnb = (TopNavigationBar) findViewById(R.id.tnb_amendpayment);
        mTextViewForget = (TextView) findViewById(R.id.tv_amendPayment_forget);
        mRelativeOne = (RelativeLayout) findViewById(R.id.rl_amendpayment_one);
        mRelativeTwo = (RelativeLayout) findViewById(R.id.rl_amendpayment_two);
        mRelativeFormer = (RelativeLayout) findViewById(R.id.rl_amendpayment_former);
        mEditTextOne = (PwdEditText) findViewById(R.id.et_amendpayment_one);
        mEditTexgtTwo = (PwdEditText) findViewById(R.id.et_amendpayment_two);
        mEditTexgtFormer = (PwdEditText) findViewById(R.id.et_amendpayment_former);
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.tv_amendPayment_forget:
                    startActivity(new Intent(this, ForgetPaymentActivty.class));
                    finish();
                    break;
            }
        }
    }
}
