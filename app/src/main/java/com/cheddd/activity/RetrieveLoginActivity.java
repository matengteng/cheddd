package com.cheddd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cheddd.R;
import com.cheddd.application.MyApplications;
import com.cheddd.base.MyBaseActivity;
import com.cheddd.bean.RetrieveLoginBean;
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

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * 修改登录密码,从账户安全（登录密码）跳转过来
 */
public class RetrieveLoginActivity extends MyBaseActivity implements View.OnClickListener, TextWatcher {

    private static final String TAG = RetrievePaymentActivity.class.getSimpleName();
    private TopNavigationBar mTnb;
    private TextView mTextViewForget;
    //原来的密码，
    private EditText mEditTextPassword;
    //新登录密码，确认的登录密码
    private EditText mEditTextformer, mEditTextAffirm;
    private Button mButtonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_login);
        initView();
        setListener();
    }

    private void setListener() {
        mButtonNext.setOnClickListener(this);
        mTextViewForget.setOnClickListener(this);
        mTnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mEditTextPassword.addTextChangedListener(this);
        mEditTextAffirm.addTextChangedListener(this);
        mEditTextformer.addTextChangedListener(this);
    }

    private void initView() {
        mTnb = (TopNavigationBar) findViewById(R.id.tnb_retrieveLogin);
        mTextViewForget = (TextView) findViewById(R.id.tv_retrieveLogin_next);
        mEditTextPassword = (EditText) findViewById(R.id.et_retrieveLogin_password);
        mButtonNext = (Button) findViewById(R.id.bt_retrieveLogin_next);
        mEditTextformer = (EditText) findViewById(R.id.et_retrieveLoginNext_former);
        mEditTextAffirm = (EditText) findViewById(R.id.et_retrieveLoginNext_afffirmformer);
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.tv_retrieveLogin_next:
                    startActivity(new Intent(this, ForgetPasswordActivity.class));
                    finish();
                    break;
                case R.id.bt_retrieveLogin_next:
                  /*  startActivity(new Intent(this, RetrieveLoginNextActivity.class));
                    finish();*/
                    nextFinsh();
                    break;
                default:
                    break;
            }
        }
    }

    //点击提交
    private void nextFinsh() {
        if (!mEditTextformer.getText().toString().equals(mEditTextAffirm.getText().toString())) {
            ToastUtil.show(this, "两次密码不一致");
            return;
        }
        RetrieveLoginBean bean = new RetrieveLoginBean();
        bean.setClientType("2");
        bean.setToken(MyApplications.getToken());
        String phone = SharedPreferencesUtils.getString(RetrieveLoginActivity.this, "phone", "");
        bean.setOldPassWord(MD5Utils.encode(phone + mEditTextPassword.getText().toString().trim()));
        bean.setPassWord(MD5Utils.encode(phone + mEditTextformer.getText().toString().trim()));
        Gson gson = new Gson();
        String json = gson.toJson(bean);
       // Log.d(TAG,"修改密码"+json);
        FormBody formbody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.ACCOUNT_PASSWORD, formbody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
               // Log.d(TAG, "登录密码修改" + result);
                if (result != null) {
                    try {
                        JSONObject object = new JSONObject(result);
                        String returnCode = object.getString("returnCode");
                        String returnMsg = object.getString("returnMsg");
                        if ("000000".equals(returnCode)) {
                            ToastUtil.show(RetrieveLoginActivity.this, returnMsg);
                            finish();
                        } else if ("0043".equals(returnCode)) {
                            ToastUtil.show(RetrieveLoginActivity.this, returnMsg);
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
        if (mEditTextPassword.getText().toString().length() > 3) {
            if (mEditTextAffirm.getText().toString().length() > 3) {
                if (mEditTextformer.getText().toString().trim().length() > 3) {
                    mButtonNext.setEnabled(true);
                } else {
                    mButtonNext.setEnabled(false);
                }
            } else {
                mButtonNext.setEnabled(false);
            }
        } else {
            mButtonNext.setEnabled(false);
        }
    }
}
