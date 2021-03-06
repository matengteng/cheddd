package com.cheddd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.cheddd.R;
import com.cheddd.application.MyApplications;
import com.cheddd.base.MyBaseActivity;
import com.cheddd.bean.SetpaymentBean;
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
 * 设置支付密码页面
 */

public class SetPaymentActivity extends MyBaseActivity {

    private static String TAG = SetPaymentActivity.class.getSimpleName();
    private TopNavigationBar mTnb;
    //第一次输入密码，第二次输入密码
    private RelativeLayout mRelativeOne, mRelativeTwo;
    private PwdEditText mEditTextOne, mEditTexgtTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_payment);
        initView();
        setListener();
    }

    private void setListener() {
        mEditTexgtTwo.setOnInputFinishListener(new PwdEditText.OnInputFinishListener() {
            @Override
            public void onInputFinish(String password) {
                String onepassword = mEditTextOne.getText().toString().trim();
                String twoPassword = mEditTexgtTwo.getText().toString().trim();
                if (onepassword.equals(twoPassword)) {
                    SetpaymentBean setBeabn = new SetpaymentBean();
                    setBeabn.setClientType("2");
                    setBeabn.setToken(MyApplications.getToken());
                    String phone = SharedPreferencesUtils.getString(SetPaymentActivity.this, "phone", "");
                    setBeabn.setPayPassWord(MD5Utils.encode(phone + mEditTexgtTwo.getText().toString().trim()));
                    Gson gson = new Gson();
                    String json = gson.toJson(setBeabn);
                 //   Log.d(TAG,"设置支付密码"+json);
                    int selectionEnd = mEditTexgtTwo.getSelectionEnd();
                    if (selectionEnd == 6) {
                        FormBody formBody = new FormBody.Builder().add("content", json).build();
                        OkhttpUtils.getInstance(SetPaymentActivity.this).asyncPost(NetConfig.MINE_PAY_PASSWORD, formBody, new OkhttpUtils.HttpCallBack() {
                            @Override
                            public void onError(Request request, IOException e) {

                            }

                            @Override
                            public void onSuccess(Request request, String result) {
                             //   Log.d(TAG, "新增支付密码" + result);
                                if (result != null) {
                                    try {
                                        JSONObject object = new JSONObject(result);
                                        String returnCode = object.getString("returnCode");
                                        String returnMsg = object.getString("returnMsg");
                                        if ("000000".equals(returnCode)) {
                                            ToastUtil.show(SetPaymentActivity.this, returnMsg);
                                            finish();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                    }
                } else {
                    startActivity(new Intent(SetPaymentActivity.this, SetPaymentActivity.class));
                    ToastUtil.show(SetPaymentActivity.this,"两次密码不一致，请重新输入");
                    finish();
                }
            }
        });
        //第一次输入密码
        mEditTextOne.setOnInputFinishListener(new PwdEditText.OnInputFinishListener() {
            @Override
            public void onInputFinish(String password) {
                int selectionEnd = mEditTextOne.getSelectionEnd();
                if (selectionEnd == 6) {
                    mEditTexgtTwo.requestFocus();
                    mRelativeOne.setVisibility(View.INVISIBLE);
                    mRelativeTwo.setVisibility(View.VISIBLE);
                }
            }
        });
        mTnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mTnb = (TopNavigationBar) findViewById(R.id.tnb_payment);
        mEditTextOne = (PwdEditText) findViewById(R.id.et_payment_one);
        mEditTexgtTwo = (PwdEditText) findViewById(R.id.et_payment_two);
        mRelativeOne = (RelativeLayout) findViewById(R.id.rl_payment_one);
        mRelativeTwo = (RelativeLayout) findViewById(R.id.rl_payment_two);
    }

}
