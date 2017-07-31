package com.cheddd.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cheddd.R;
import com.cheddd.application.MyApplications;
import com.cheddd.base.MyBaseActivity;
import com.cheddd.bean.AmendPayBean;
import com.cheddd.bean.PhoneTrend;
import com.cheddd.config.NetConfig;
import com.cheddd.utils.LoginTokenUtils;
import com.cheddd.utils.OkhttpUtils;
import com.cheddd.utils.ToastUtil;
import com.cheddd.view.TopNavigationBar;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * 找回支付密码，从修改支付密码（忘记原支付密码）跳转过来
 */

public class ForgetPaymentActivty extends MyBaseActivity implements View.OnClickListener, TextWatcher {

    private static String TAG = ForgetPasswordActivity.class.getSimpleName();
    private TopNavigationBar mTnb;
    private EditText mEditTextPhone, mEditTextAuth;
    private Button mButtonnext;
    private TextView mTextViewAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_payment_activty);
        initView();
        initData();
        setListener();
    }

    private void initData() {

    }

    private void setListener() {
        mButtonnext.setOnClickListener(this);
        mTextViewAuth.setOnClickListener(this);
        mEditTextAuth.addTextChangedListener(this);
        mEditTextPhone.addTextChangedListener(this);
        mTnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mTnb = (TopNavigationBar) findViewById(R.id.tnb_forgetpayment);
        mButtonnext = (Button) findViewById(R.id.bt_forgetpayment_next);
        mEditTextPhone = (EditText) findViewById(R.id.et_forgetpayment_phone);
        mEditTextAuth = (EditText) findViewById(R.id.et_forgetpayment_authcode);
        mTextViewAuth = (TextView) findViewById(R.id.tv_forgetpayment_authcode);
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.bt_forgetpayment_next:
                    //找回支付密码
                    nextPayment();
                    break;
                case R.id.tv_forgetpayment_authcode:
                    authCode();
                    break;
            }
        }
    }

    //点击提交
    private void nextPayment() {
        PhoneTrend phoneTrend = new PhoneTrend();
        phoneTrend.setClientType("2");
        phoneTrend.setSms(mEditTextAuth.getText().toString().trim());
        phoneTrend.setTelNo(mEditTextPhone.getText().toString().trim());
        phoneTrend.setToken(MyApplications.getToken());
        Gson gson = new Gson();
        String json = gson.toJson(phoneTrend);
        FormBody formBody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.PHONE_TREND, formBody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                    try {
                        JSONObject object = new JSONObject(result);
                        String returnCode = object.getString("returnCode");
                        String returnMsg = object.getString("returnMsg");
                        if ("000000".equals(returnCode)) {
                            startActivity(new Intent(ForgetPaymentActivty.this, RetrievePaymentActivity.class));
                            finish();
                        } else if ("0017".equals(returnCode)) {
                            startActivity(new Intent(ForgetPaymentActivty.this, LoginActivity.class));
                        } else if ("0026".equals(returnCode)) {
                            ToastUtil.show(ForgetPaymentActivty.this, returnMsg);
                            return;
                        } else {
                            startActivity(new Intent(ForgetPaymentActivty.this, LoginActivity.class));
                            ToastUtil.show(ForgetPaymentActivty.this, "请重新登录");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void authCode() {
        String phone = mEditTextPhone.getText().toString().trim();
        if (!phone.matches("1\\d{10}")) {
            ToastUtil.show(this, "手机格式错误");
            return;
        }
        if ("获取验证码".equals(mTextViewAuth.getText().toString().trim())) {
            CountDownTimer time = new CountDownTimer(30 * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mTextViewAuth.setText(ForgetPaymentActivty.this.getString(R.string.phone_captcha_time_remain, millisUntilFinished / 1000));
                }

                @Override
                public void onFinish() {
                    mTextViewAuth.setEnabled(true);
                    mTextViewAuth.setText("获取验证码");
                }
            };
            time.start();
            mTextViewAuth.setEnabled(false);
        }
        //调用提交的接口
        AmendPayBean payBean = new AmendPayBean();
        payBean.setTelNo(mEditTextPhone.getText().toString().trim());
        payBean.setToken(MyApplications.getToken());
        payBean.setClientType("2");
        Gson gson = new Gson();
        String json = gson.toJson(payBean);
        FormBody formbody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(ForgetPaymentActivty.this).asyncPost(NetConfig.MINE_PAY_FORGETPAYMENT, formbody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                    Log.d(TAG, "获取设置支付密码的验证码" + result);
                    try {
                        JSONObject object = new JSONObject(result);
                        String returnCode = object.getString("returnCode");
                        String returnMsg = object.getString("returnMsg");
                        if ("000000".equals(returnCode)) {
                            ToastUtil.show(ForgetPaymentActivty.this, returnMsg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    //EditText的点击事件
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    //EditText的点击事件
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    //EditText的点击事件
    @Override
    public void afterTextChanged(Editable s) {
        if (mEditTextPhone.getText().toString().length() == 11) {
            if (mEditTextAuth.getText().toString().length() == 4) {
                mButtonnext.setEnabled(true);
            } else {
                mButtonnext.setEnabled(false);
            }
        } else {
            mButtonnext.setEnabled(false);
        }

    }
}
