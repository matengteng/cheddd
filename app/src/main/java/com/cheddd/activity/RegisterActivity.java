package com.cheddd.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cheddd.R;
import com.cheddd.application.MyApplications;
import com.cheddd.base.MyBaseActivity;
import com.cheddd.bean.PasswordLogin;
import com.cheddd.bean.PhoneNume;
import com.cheddd.bean.RegisterBean;
import com.cheddd.config.NetConfig;
import com.cheddd.utils.MD5Utils;
import com.cheddd.utils.OkhttpUtils;
import com.cheddd.utils.SharedPreferencesUtils;
import com.cheddd.utils.ToastUtil;
import com.cheddd.view.TopNavigationBar;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 注册的界面
 */
public class RegisterActivity extends MyBaseActivity implements View.OnClickListener, TextWatcher {

    private static String TAG = RegisterActivity.class.getSimpleName();
    private TopNavigationBar mTnb;
    //手机号 验证码 密码 经纪人
    private EditText mEditTextPhone, mEditTextAuthCode, mEditTextPassword, mEditTextBroker;
    //验证码
    private Button mButonRegister;
    //获取验证码
    private TextView mTextViewAuthCode;
    private String jsonString;
    private String returnMsg;
    private OkHttpClient client;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    ToastUtil.show(RegisterActivity.this, returnMsg);
                    finish();
                    break;
                case 4:
                    ToastUtil.show(RegisterActivity.this, returnMsg);
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initData();
        setListener();
    }

    private void initData() {
        client = new OkHttpClient();
    }


    private void setListener() {
        mButonRegister.setOnClickListener(this);
        mEditTextPassword.addTextChangedListener(this);
        mEditTextAuthCode.addTextChangedListener(this);
        mEditTextPhone.addTextChangedListener(this);
        mTextViewAuthCode.setOnClickListener(this);
        mTnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mTnb = (TopNavigationBar) findViewById(R.id.tnb_register);
        mEditTextPhone = (EditText) findViewById(R.id.et_register_phone);
        mEditTextAuthCode = (EditText) findViewById(R.id.et_register_authcode);
        mEditTextPassword = (EditText) findViewById(R.id.et_register_password);
        mEditTextBroker = (EditText) findViewById(R.id.et_register_broker);
        mButonRegister = (Button) findViewById(R.id.bt_register_register);
        mTextViewAuthCode = (TextView) findViewById(R.id.tv_register_authcode);
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.tv_register_authcode:
                    authcode();
                    break;
                case R.id.bt_register_register:
                    registerfinsh();
                    break;
                default:
                    break;
            }
        }
    }

    //注册完成
    private void registerfinsh() {
        String phone = mEditTextPhone.getText().toString().trim();
        if (!phone.matches("1\\d{10}")) {
            ToastUtil.show(this, "手机格式错误");
            return;
        }
        String auth = mEditTextAuthCode.getText().toString().trim();
        if (!auth.matches("([0-9]{4})")) {
            ToastUtil.show(this, "验证码错误");
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
        register.setPassWord(MD5Utils.encode(phone + password));
        Gson gson = new Gson();
        String json = gson.toJson(register);
        Log.d(TAG, "注册" + json);
        FormBody formBody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.REGISTER, formBody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                Log.d(TAG, "注册" + result);
                if (result != null) {
                    try {
                        JSONObject json = new JSONObject(result);
                        jsonString = json.getString("returnCode");
                        returnMsg = json.getString("returnMsg");
                        if ("000000".equals(jsonString)) {
                            loginPassword();
                        } else if ("0028".equals(jsonString)) {
                            ToastUtil.show(RegisterActivity.this, returnMsg);
                            finish();
                        } else if ("0026".equals(jsonString)) {
                            ToastUtil.show(RegisterActivity.this, returnMsg);
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

    //获取验证码
    private void authcode() {
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
            CountDownTimer time = new CountDownTimer(60 * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mTextViewAuthCode.setText(RegisterActivity.this.getString(R.string.phone_captcha_time_remain, millisUntilFinished / 1000));
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
        PhoneNume phoneNume = new PhoneNume();
        phoneNume.setClientType("2");
        phoneNume.setTelNo(mEditTextPhone.getText().toString().trim());
        Gson gson = new Gson();
        String json = gson.toJson(phoneNume);
        FormBody formBody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.REGISTER_AUTHCODE, formBody, new OkhttpUtils.HttpCallBack() {
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
                            ToastUtil.show(RegisterActivity.this, returnMsg);
                        } else if ("0028".equals(returnCode)) {
                            ToastUtil.show(RegisterActivity.this, returnMsg);
                            finish();
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mEditTextPhone.getSelectionEnd() == 11) {
            if (mEditTextPassword.getText().toString().trim().length() > 5) {
                if (mEditTextAuthCode.getSelectionEnd() == 4) {
                    mButonRegister.setEnabled(true);
                } else {
                    mButonRegister.setEnabled(false);
                }
            } else {
                mButonRegister.setEnabled(false);
            }
        } else {
            mButonRegister.setEnabled(false);
        }
    }

    private void loginPassword() {
        String phone = mEditTextPhone.getText().toString().trim();
        if (!phone.matches("1\\d{10}")) {
            ToastUtil.show(this, "手机格式错误");
            return;
        }
        String password = mEditTextPassword.getText().toString().trim();
        if (!password.matches("[a-zA-Z0-9]*")) {
            ToastUtil.show(this, "密码输入有误");
            return;
        }
        PasswordLogin login = new PasswordLogin();
        login.setClientType("2");
        login.setPassWord(MD5Utils.encode(mEditTextPhone.getText().toString().trim() + password));
        login.setTelNo(phone);
        Gson gson = new Gson();
        final String json = gson.toJson(login);
        //Log.d(TAG,"注册"+json);
        FormBody forbody = new FormBody.Builder().add("content", json).build();
        final Request request = new Request.Builder().post(forbody).url(NetConfig.LOGIN_PASSWORD).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Headers headers = response.headers();
                    List<String> cookies = headers.values("Set-Cookie");
                    // String session = cookies.get(0);
                    //Log.d(TAG, "session:" + cookies.toString());
                    SharedPreferencesUtils.setString(RegisterActivity.this, "session", cookies.toString());
                    SharedPreferencesUtils.setPhone(RegisterActivity.this, "phone", mEditTextPhone.getText().toString().trim());
                    Message msg = new Message();
                    String string = response.body().string();
                    if (string != null) {
                        //  Log.d(TAG, NetConfig.PHONE_TREND + "content" + "=" + json);
                        try {
                            JSONObject object = new JSONObject(string);
                            String returnCode = object.getString("returnCode");
                            returnMsg = object.getString("returnMsg");
                            String token = object.getString("token");
                            if ("000000".equals(returnCode)) {
                                MyApplications.setToken(token);
                                msg.what = 1;
                            } else if ("0027".equals(returnCode)) {
                                msg.what = 4;
                            } else {
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    mHandler.sendMessage(msg);

                }
            }
        });
    }
}
