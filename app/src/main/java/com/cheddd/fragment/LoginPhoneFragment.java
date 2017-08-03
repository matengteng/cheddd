package com.cheddd.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cheddd.R;
import com.cheddd.activity.RegisterActivity;
import com.cheddd.application.MyApplications;
import com.cheddd.base.BaseFragment;
import com.cheddd.bean.PhoneNume;
import com.cheddd.bean.PhoneTrend;
import com.cheddd.config.NetConfig;
import com.cheddd.utils.OkhttpUtils;
import com.cheddd.utils.SharedPreferencesUtils;
import com.cheddd.utils.ToastUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;


import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Callback;

/**
 * Created by Administrator on 2017/5/22 0022.
 * 手机登录的界面
 */

public class LoginPhoneFragment extends BaseFragment implements TextWatcher, View.OnClickListener {
    private static String TAG = LoginPhoneFragment.class.getSimpleName();
    private Context mContext;
    private TextView mTextViewAuth;
    private EditText mEditTextPhone, mEditTextAuth;
    private Button mButtonFinsh;
    private OkHttpClient client;
    private Call call;

    private String returnMsg;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    ToastUtil.show(getActivity(), returnMsg);
                    getActivity().finish();
                    break;
                case 3:
                    ToastUtil.show(getActivity(), returnMsg);
                    break;
                case 2:
                    getActivity().startActivity(new Intent(getActivity(), RegisterActivity.class));
                    getActivity().finish();
                    break;
                default:
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_login_phone, null);
        mContext = getActivity();
        initView(view);
        initData();
        setListener();
        return view;
    }

    private void initData() {
        client = new OkHttpClient();
    }

    private void setListener() {
        mButtonFinsh.setOnClickListener(this);
        mEditTextPhone.addTextChangedListener(this);
        mEditTextAuth.addTextChangedListener(this);
        mTextViewAuth.setOnClickListener(this);
    }

    private void initView(View view) {
        mButtonFinsh = (Button) view.findViewById(R.id.bt_login_login);
        mEditTextAuth = (EditText) view.findViewById(R.id.et_login_phone_authcode);
        mEditTextPhone = (EditText) view.findViewById(R.id.et_login_trend_phone);
        mTextViewAuth = (TextView) view.findViewById(R.id.tv_login_authcode);
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
            if (mEditTextAuth.getSelectionEnd() == 4) {
                mButtonFinsh.setEnabled(true);
            } else {
                mButtonFinsh.setEnabled(false);
            }
        } else {
            mButtonFinsh.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login_authcode:
                authcode();
                break;
            case R.id.bt_login_login:
                loginFinish();
                break;
        }
    }

    //登录的界面
    private void loginFinish() {
        String phone = mEditTextPhone.getText().toString().trim();
        if (!phone.matches("1\\d{10}")) {
            ToastUtil.show(getActivity(), "手机格式错误");
            return;
        }
        String auth = mEditTextAuth.getText().toString().trim();
        if (!auth.matches("([0-9]{4})")) {
            ToastUtil.show(getActivity(), "验证码错误");
            return;
        }
        loginNet();
    }

    //访问登录的接口
    private void loginNet() {
        PhoneTrend trend = new PhoneTrend();
        trend.setClientType("2");
        trend.setTelNo(mEditTextPhone.getText().toString().trim());
        trend.setSms(mEditTextAuth.getText().toString().trim());
        Gson gson = new Gson();
        final String json = gson.toJson(trend);
        FormBody forbody = new FormBody.Builder().add("content", json).build();
        final Request request = new Request.Builder().post(forbody).url(NetConfig.PHONE_TREND).build();
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
                    SharedPreferencesUtils.setString(mContext, "session", cookies.toString());
                    SharedPreferencesUtils.setPhone(mContext, "phone", mEditTextPhone.getText().toString().trim());
                    Message msg = new Message();
                    String string = response.body().string();
                    if (string != null) {
                        Log.d(TAG, "登录的接口" + string);
                        Log.d(TAG, NetConfig.PHONE_TREND + "content" + "=" + json);
                        try {
                            JSONObject object = new JSONObject(string);
                            String returnCode = object.getString("returnCode");
                            returnMsg = object.getString("returnMsg");
                            String token = object.getString("token");
                            if ("000000".equals(returnCode)) {
                                MyApplications.setToken(token);
                                msg.what = 1;
                            } else if ("0025".equals(returnCode)) {
                                msg.what = 2;
                            } else if ("0026".equals(returnCode)) {
                                msg.what = 3;
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

    //获取验证码的界面
    private void authcode() {
        String phone = mEditTextPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.show(getActivity(), "手机号码不能为空");
            return;
        }
        if (!phone.matches("1\\d{10}")) {
            ToastUtil.show(getActivity(), "手机格式错误");
            return;
        }
        if ("获取验证码".equals(mTextViewAuth.getText().toString().trim())) {
            CountDownTimer time = new CountDownTimer(60 * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mTextViewAuth.setText(mContext.getString(R.string.phone_captcha_time_remain, millisUntilFinished / 1000));
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
        PhoneNume phonenumeber = new PhoneNume();
        phonenumeber.setTelNo(mEditTextPhone.getText().toString().trim());
        phonenumeber.setClientType("2");
        Gson gson = new Gson();
        String json = gson.toJson(phonenumeber);
        Log.d(TAG, "authcode: " + json);

        FormBody formbody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(getActivity()).asyncPost(NetConfig.lOGIN_AUTHCODE, formbody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                Log.d(TAG, "接口数据" + result);
                if (request != null) {

                    try {
                        JSONObject json = new JSONObject(result);
                        Log.d(TAG, "json");
                        String returnCode = json.getString("returnCode");
                        String returnMsg = json.getString("returnMsg");
                        if ("000000".equals(returnCode)) {
                            ToastUtil.show(getActivity(), returnMsg);
                        }
                        if ("0025".equals(returnCode)) {
                            ToastUtil.show(getActivity(), returnMsg);
                            startActivity(new Intent(getActivity(), RegisterActivity.class));
                        }
                        if ("0026".equals(returnCode)) {
                            ToastUtil.show(getActivity(), returnMsg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
