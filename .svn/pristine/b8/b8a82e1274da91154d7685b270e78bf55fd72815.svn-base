package com.cheddd.fragment;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
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
import com.cheddd.activity.ForgetPasswordActivity;
import com.cheddd.activity.RegisterActivity;
import com.cheddd.application.MyApplications;
import com.cheddd.base.BaseFragment;
import com.cheddd.bean.PasswordLogin;
import com.cheddd.bean.PhoneTrend;
import com.cheddd.config.NetConfig;
import com.cheddd.utils.MD5Utils;
import com.cheddd.utils.OkhttpUtils;
import com.cheddd.utils.SharedPreferencesUtils;
import com.cheddd.utils.ToastUtil;
import com.facebook.common.util.SecureHashUtil;
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
 * Created by Administrator on 2017/5/22 0022.
 * 密码登录的界面
 */

public class LoginPasswordFragment extends BaseFragment implements View.OnClickListener, TextWatcher {
    private Context mContext;
    private static String TAG = LoginPasswordFragment.class.getSimpleName();
    //手机号，密码
    private EditText mEditTextPhone, mEditTextPassword;
    //登录 ，注册
    private Button mButtonLogin, mButtonRegister;
    //忘记密码
    private TextView mTextViewForget;
    private OkHttpClient client;

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
                case 2:
                    ToastUtil.show(getActivity(), returnMsg);
                case 3:
                    getActivity().startActivity(new Intent(getActivity(), RegisterActivity.class));
                    getActivity().finish();
                case 4:
                    ToastUtil.show(getActivity(), returnMsg);
                default:
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_login_password, null);
        initView(view);
        initData();
        setListener();
        return view;
    }

    private void setListener() {
        mButtonLogin.setOnClickListener(this);
        mEditTextPassword.addTextChangedListener(this);
        mEditTextPhone.addTextChangedListener(this);
        mTextViewForget.setOnClickListener(this);
        mButtonRegister.setOnClickListener(this);

    }

    private void initData() {
        client = new OkHttpClient();
    }

    private void initView(View view) {
        mEditTextPhone = (EditText) view.findViewById(R.id.et_login_password_phone);
        mEditTextPassword = (EditText) view.findViewById(R.id.et_login_password_password);
        mButtonLogin = (Button) view.findViewById(R.id.bt_login_password);
        mButtonRegister = (Button) view.findViewById(R.id.bt_login_register);
        mTextViewForget = (TextView) view.findViewById(R.id.tv_login_password_forget);

    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.tv_login_password_forget:
                    Intent intent1 = new Intent(getContext(), ForgetPasswordActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.bt_login_register:
                    Intent intent2 = new Intent(getContext(), RegisterActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.bt_login_password:
                    loginPassword();
                    break;

            }
        }
    }

    //密码登录
    private void loginPassword() {
        String phone = mEditTextPhone.getText().toString().trim();
        if (!phone.matches("1\\d{10}")) {
            ToastUtil.show(getActivity(), "手机格式错误");
            return;
        }
        String password = mEditTextPassword.getText().toString().trim();
        if (!password.matches("[a-zA-Z0-9]*")) {
            ToastUtil.show(getActivity(), "密码输入有误");
            return;
        }
        PasswordLogin login = new PasswordLogin();
        login.setClientType("2");
        login.setPassWord(MD5Utils.encode(mEditTextPhone.getText().toString().trim() + password));
        login.setTelNo(phone);
        Gson gson = new Gson();
        final String json = gson.toJson(login);
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
                            } else if ("0026".equals(returnCode)) {
                                msg.what = 2;
                            } else if ("0025".equals(returnCode)) {
                                msg.what = 3;
                            } else if ("0027".equals(returnCode)) {
                                msg.what = 4;
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
                mButtonLogin.setEnabled(true);
            } else {
                mButtonLogin.setEnabled(false);
            }
        } else {
            mButtonLogin.setEnabled(false);
        }
    }
}
