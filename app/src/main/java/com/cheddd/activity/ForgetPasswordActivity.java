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
import android.widget.Toast;

import com.cheddd.R;
import com.cheddd.base.MyBaseActivity;
import com.cheddd.bean.PhoneNume;
import com.cheddd.bean.RegisterBean;
import com.cheddd.config.NetConfig;
import com.cheddd.utils.MD5Utils;
import com.cheddd.utils.OkhttpUtils;
import com.cheddd.utils.ToastUtil;
import com.cheddd.view.TopNavigationBar;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Request;

/*
* 忘记密码
*/
public class ForgetPasswordActivity extends MyBaseActivity implements TextWatcher, View.OnClickListener {
    private static String TAG = ForgetPasswordActivity.class.getSimpleName();
    private TopNavigationBar mTnb;
    //手机号码，验证码，新密码
    private EditText mEditTextPhone, mEditTextAuthcode, mEditTextPassword;
    private Button mButtonAffirm;
    private TextView mTextViewAuthCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        initView();
        setListener();
    }

    private void setListener() {
        mButtonAffirm.setOnClickListener(this);
        mTextViewAuthCode.setOnClickListener(this);
        mEditTextPhone.addTextChangedListener(this);
        mEditTextPassword.addTextChangedListener(this);
        mEditTextAuthcode.addTextChangedListener(this);
        mTnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mTnb = (TopNavigationBar) findViewById(R.id.tnb_forget);
        mEditTextPhone = (EditText) findViewById(R.id.et_forget_phone);
        mEditTextPassword = (EditText) findViewById(R.id.et_forget_password);
        mEditTextAuthcode = (EditText) findViewById(R.id.et_forget_authcode);
        mButtonAffirm = (Button) findViewById(R.id.bt_forget_login);
        mTextViewAuthCode = (TextView) findViewById(R.id.tv_forget_authcode);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mEditTextPhone.getSelectionEnd() == 11) {
            if (mEditTextAuthcode.getSelectionEnd() == 4) {
                if (mEditTextPassword.getText().toString().trim().length() > 5) {
                    mButtonAffirm.setEnabled(true);
                } else {
                    mButtonAffirm.setEnabled(false);
                }
            } else {
                mButtonAffirm.setEnabled(false);
            }
        } else {
            mButtonAffirm.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.tv_forget_authcode:
                    authForget();
                    break;
                case R.id.bt_forget_login:
                    forgetLogin();
                    break;
            }
        }
    }

    //确认的点击事件
    private void forgetLogin() {
        String phone = mEditTextPhone.getText().toString().trim();
        if (!phone.matches("1\\d{10}")) {
            ToastUtil.show(this, "手机格式错误");
            return;
        }
        String auth = mEditTextAuthcode.getText().toString().trim();
        if (!auth.matches("([0-9]{4})")) {
            ToastUtil.show(this, "验证码输入有误");
            return;
        }
        String password = mEditTextPassword.getText().toString().trim();
        if (!password.matches("[a-zA-Z0-9]*")) {
            ToastUtil.show(this, "密码输入有误");
            return;
        }
        RegisterBean register = new RegisterBean();
        register.setClientType("2");
        register.setTelNo(phone);
        register.setSms(auth);
        register.setPassWord(MD5Utils.encode(mEditTextPhone.getText().toString().trim() + mEditTextPassword.getText().toString().trim()));
        Gson gson = new Gson();
        String json = gson.toJson(register);
        Log.d(TAG, json);
        FormBody formBody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.FORGETPASSWORD, formBody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                Log.d(TAG, "忘记密码" + result);
                if (result != null) {
                    try {
                        JSONObject json = new JSONObject(result);
                        String returnCode = json.getString("returnCode");
                        String returnMsg = json.getString("returnMsg");
                        if ("000000".equals(returnCode)) {
                            Toast.makeText(ForgetPasswordActivity.this, returnMsg, Toast.LENGTH_SHORT).show();
                            finish();
                        } else if ("0025".equals(returnCode)) {
                            startActivity(new Intent(ForgetPasswordActivity.this, RegisterActivity.class));
                            ToastUtil.show(ForgetPasswordActivity.this, returnMsg);
                            finish();
                        } else if ("0026".equals(returnCode)) {
                            ToastUtil.show(ForgetPasswordActivity.this, returnMsg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    //获取验证码
    private void authForget() {
        String phone = mEditTextPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.show(this, "手机号码不能为空");
            return;
        }
        if (!phone.matches("1\\d{10}")) {
            ToastUtil.show(this, "手机格式错误");
            return;
        }
        if ("获取验证码".equals(mTextViewAuthCode.getText().toString().trim())) {
            CountDownTimer time = new CountDownTimer(30 * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mTextViewAuthCode.setText(ForgetPasswordActivity.this.getString(R.string.phone_captcha_time_remain, millisUntilFinished / 1000));
                }

                @Override
                public void onFinish() {
                    mTextViewAuthCode.setEnabled(true);
                    mTextViewAuthCode.setText("获取验证码");
                }
            };
            time.start();
            mTextViewAuthCode.setEnabled(false);
        }
        //转换字符串
        PhoneNume phoneNume = new PhoneNume();
        phoneNume.setClientType("2");
        phoneNume.setTelNo(mEditTextPhone.getText().toString().trim());
        Gson gson = new Gson();
        String json = gson.toJson(phoneNume);
        FormBody formBody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.FORGETPASSWORD_AUTHCODE, formBody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                Log.d(TAG, "获取验证码" + result);
                if (result != null) {
                    try {
                        JSONObject object = new JSONObject(result);
                        String returnCode = object.getString("returnCode");
                        String returnMsg = object.getString("returnMsg");

                        if ("000000".equals(returnCode)) {
                            ToastUtil.show(ForgetPasswordActivity.this, returnMsg);
                        }
                        if ("0025".equals(returnCode)) {
                            ToastUtil.show(ForgetPasswordActivity.this, returnMsg + "请先注册");
                            startActivity(new Intent(ForgetPasswordActivity.this, RegisterActivity.class));
                            finish();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}

