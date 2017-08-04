package com.cheddd.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cheddd.R;
import com.cheddd.application.MyApplications;
import com.cheddd.base.MyBaseActivity;
import com.cheddd.config.NetConfig;
import com.cheddd.utils.LoginTokenUtils;
import com.cheddd.utils.OkhttpUtils;
import com.cheddd.view.TopNavigationBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * 账户安全，已登录从我的界面跳转过来
 */

public class SafetyActivity extends MyBaseActivity implements View.OnClickListener {

    private static String TAG = StatsApproveActivity.class.getSimpleName();
    //我的银行卡、支付密码、登录密码、手势|指纹密码
    private RelativeLayout mRelativebank, mRelativePassword, mRelativeLogin, mRelativeGesture;
    private TopNavigationBar mTnb;
    private TextView mTextViewSet;
    private String returnCode;
    private String returnMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety);
        initView();
        initData();
        setListener();
    }

    private void initData() {
        paymentPassword();
    }

    private void setListener() {
        mRelativebank.setOnClickListener(this);
        mRelativeGesture.setOnClickListener(this);
        mRelativeLogin.setOnClickListener(this);
        mRelativePassword.setOnClickListener(this);
        mTnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mTnb = (TopNavigationBar) findViewById(R.id.tnb_safety);
        mRelativebank = (RelativeLayout) findViewById(R.id.rl_safety_bank);
        mRelativePassword = (RelativeLayout) findViewById(R.id.rl_safety_password);
        mRelativeLogin = (RelativeLayout) findViewById(R.id.rl_safety_amend);
        mRelativeGesture = (RelativeLayout) findViewById(R.id.rl_safety_gesture);
        mTextViewSet = (TextView) findViewById(R.id.tv_safety_password);
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                //未设置密码，跳转到忘记密码页面，已设置跳转到修改登录密码页面
                case R.id.rl_safety_amend:
                    amendPassword();
                    break;
                case R.id.rl_safety_password:
                    paymentPassword1();
                    break;
                //手势
                case R.id.rl_safety_gesture:
                    break;
                case R.id.rl_safety_bank:
                    startActivity(new Intent(this, MineBankActivity.class));
                    break;
                case R.id.rl_logout:

                    new AlertDialog.Builder(this).setMessage("确认退出账户吗？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MyApplications.setToken(null);
                            Toast.makeText(SafetyActivity.this, "退出登录", Toast.LENGTH_SHORT).show();
                           // System.exit(0);
                            finish();
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();

                    break;
                default:
                    break;
            }

        }
    }

    private void paymentPassword1() {
        if ("000000".equals(returnCode)) {
            startActivity(new Intent(SafetyActivity.this, SetPaymentActivity.class));
        } else if ("0017".equals(returnCode)) {
            startActivity(new Intent(SafetyActivity.this, LoginActivity.class));
        } else if ("0030".equals(returnCode)) {
            mTextViewSet.setText("已设置");
            startActivity(new Intent(SafetyActivity.this, AmendPaymentActivity.class));
        } else {
            startActivity(new Intent(SafetyActivity.this, LoginActivity.class));
        }
    }

    private void amendPassword() {
        startActivity(new Intent(this, RetrieveLoginActivity.class));
    }

    private void paymentPassword() {
        //设置支付密码页面
        String json = LoginTokenUtils.getJson();
        FormBody formBody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.MINE_SETZHIFU_PASSWORD, formBody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                 //   Log.d(TAG, "判断支付密码是否设置" + result);
                    try {
                        JSONObject object = new JSONObject(result);
                        returnCode = object.getString("returnCode");
                        returnMsg = object.getString("returnMsg");
                        if ("0030".equals(returnCode)) {
                            mTextViewSet.setText("已设置");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }
}
