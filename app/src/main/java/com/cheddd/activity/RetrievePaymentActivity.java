package com.cheddd.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
 * 找回支付密码，从FotgetPaymentActivity的界面跳转过来
 */

public class RetrievePaymentActivity extends MyBaseActivity {

    private static String TAG = RetrievePaymentActivity.class.getSimpleName();
    private TopNavigationBar mTnb;
    private RelativeLayout mRelativeOne, mRelativeTwo;
    private PwdEditText mEditTextOne, mEditTextTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_payment);
        initView();
        setListener();
    }

    private void setListener() {
        mTnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mEditTextTwo.setOnInputFinishListener(new PwdEditText.OnInputFinishListener() {
            @Override
            public void onInputFinish(String password) {
                String onePassword = mEditTextOne.getText().toString().trim();
                String twoPassword = mEditTextTwo.getText().toString().trim();
                if (onePassword.equals(twoPassword)) {
                    SetpaymentBean setbean = new SetpaymentBean();
                    String phone = SharedPreferencesUtils.getString(RetrievePaymentActivity.this, "phone", "");
                    setbean.setPayPassWord(MD5Utils.encode(phone + mEditTextTwo.getText().toString().trim()));
                    setbean.setClientType("2");
                    setbean.setToken(MyApplications.getToken());
                    Gson gson = new Gson();
                    String json = gson.toJson(setbean);
                    Log.d(TAG, "找回支付密码" + json);
                    int selectionEnd = mEditTextTwo.getSelectionEnd();
                    if (selectionEnd == 6) {
                        FormBody formBody = new FormBody.Builder().add("content", json).build();
                        OkhttpUtils.getInstance(RetrievePaymentActivity.this).asyncPost(NetConfig.MINE_PAY_BANKPASSWORD, formBody, new OkhttpUtils.HttpCallBack() {
                            @Override
                            public void onError(Request request, IOException e) {

                            }

                            @Override
                            public void onSuccess(Request request, String result) {
                                if (result != null) {
                                    Log.d(TAG, "找回支付密码" + result);
                                    try {
                                        JSONObject object = new JSONObject(result);
                                        String returnCode = object.getString("returnCode");
                                        String returnMsg = object.getString("returnMsg");
                                        if ("000000".equals(returnCode)) {
                                            ToastUtil.show(RetrievePaymentActivity.this, returnMsg);
                                            finish();
                                        } else if ("0017".equals(returnCode)) {
                                            startActivity(new Intent(RetrievePaymentActivity.this, LoginActivity.class));
                                            ToastUtil.show(RetrievePaymentActivity.this, returnMsg);
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
                } else {
                    ToastUtil.show(RetrievePaymentActivity.this, "两次密码输入不一致");
                    startActivity(new Intent(RetrievePaymentActivity.this, RetrievePaymentActivity.class));
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
                    mEditTextTwo.requestFocus();
                    mRelativeOne.setVisibility(View.INVISIBLE);
                    mRelativeTwo.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void initView() {
        mTnb = (TopNavigationBar) findViewById(R.id.tnb_retrievePayment);
        mRelativeOne = (RelativeLayout) findViewById(R.id.rl_retrievePayment_one);
        mRelativeTwo = (RelativeLayout) findViewById(R.id.rl_retrievePayment_two);
        mEditTextOne = (PwdEditText) findViewById(R.id.et_retrievePayment_one);
        mEditTextTwo = (PwdEditText) findViewById(R.id.et_retrievePayment_two);
    }
}
