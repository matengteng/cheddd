package com.cheddd.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheddd.R;
import com.cheddd.application.MyApplications;
import com.cheddd.base.MyBaseActivity;
import com.cheddd.config.NetConfig;
import com.cheddd.utils.LoginTokenUtils;
import com.cheddd.utils.OkhttpUtils;
import com.cheddd.utils.ToastUtil;
import com.cheddd.view.TopNavigationBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * 小额借款 显示的列表，从LendbankActivity中条转过来
 */

public class LendEntryActivity extends MyBaseActivity implements View.OnClickListener, TextWatcher {

    private static String TAG = LendEntryActivity.class.getSimpleName();
    private Dialog mDialog;
    private RelativeLayout mRelativeClose,mRelativeLayoutHetong;
    private Button mButton, mButtonKonow;
    private TopNavigationBar mTnb;
    //借款金额、收款账户、日利率、起止时间、首次还款日、还款日、借款期限、借款用途、还款银行卡，贷款发方法人
    private TextView mTextViewMoney, mTextViewBank, mTextViewInsert, mTextViewStartTime, mTextViewtime, mTextViewrefundDay,
            mTextViewDeadline, mTextViewUse, mTextViewrefundBank, mTextViewIsuse;
    private TextView mInsert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lend_entry);
        initView();
        initData();
        setListener();
    }


    private void initData() {
        String json = LoginTokenUtils.getJson();
        FormBody formhody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.INDEX_PETTLYLOAN_RESULT, formhody, new OkhttpUtils.HttpCallBack() {

            private String substring;

            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                    Log.d(TAG, "确认借钱" + result);
                    try {
                        JSONObject object = new JSONObject(result);
                        String returnCode = object.getString("returnCode");
                        if ("000000".equals(returnCode)) {
                            JSONObject entity = object.getJSONObject("entity");
                            String bindBankCardNo = entity.getString("bindBankCardNo");
                            String orderNo = entity.getString("orderNo");
                            MyApplications.setOrderNo(orderNo);
                            String firstRepayTime = entity.getString("firstRepayTime");
                            String startEndTime = entity.getString("startEndTime");
                            String loanCycle = entity.getString("loanCycle");
                            int firstRepayment = entity.getInt("firstRepayment");
                            double loanRate = entity.getDouble("loanRate");
                            String repayTime = entity.getString("repayTime");
                            String bindBankTag = entity.getString("bindBankTag");
                            String bindBank = entity.getString("bindBank");
                            double contractAmt = entity.getDouble("contractAmt");
                            int interestType = entity.getInt("interestType");
                            if (interestType == 0) {
                                mInsert.setText("月利息");
                            } else {
                                mInsert.setText("日利息");
                            }
                            mTextViewMoney.setText("￥" + contractAmt / 100 + "" + "元");
                            if (bindBankCardNo.length() > 4) {
                                substring = bindBankCardNo.substring(bindBankCardNo.length() - 4);
                            }
                            mTextViewBank.setText(bindBank + "(" + substring + ")");
                            mTextViewInsert.setText(loanRate / 100 + "" + "%");
                            mTextViewStartTime.setText(startEndTime);
                            mTextViewtime.setText(firstRepayTime);
                            mTextViewrefundDay.setText(repayTime);
                            mTextViewDeadline.setText(loanCycle);
                            mTextViewUse.setText("个人消费");
                            mTextViewrefundBank.setText(bindBank + "(" + substring + ")");
                            mTextViewIsuse.setText("车贷贷");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void setListener() {
        mTextViewIsuse.addTextChangedListener(this);
        mTextViewrefundBank.addTextChangedListener(this);
        mTextViewUse.addTextChangedListener(this);
        mTextViewtime.addTextChangedListener(this);
        mTextViewDeadline.addTextChangedListener(this);
        mTextViewrefundDay.addTextChangedListener(this);
        mTextViewStartTime.addTextChangedListener(this);
        mTextViewInsert.addTextChangedListener(this);
        mTextViewBank.addTextChangedListener(this);
        mTextViewMoney.addTextChangedListener(this);
        mButton.setOnClickListener(this);
        mRelativeLayoutHetong.setOnClickListener(this);
        mTnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mButtonKonow = (Button) findViewById(R.id.bt_petty_money);
        mTextViewIsuse = (TextView) findViewById(R.id.tv_lendEntry_refundIsuse);
        mTextViewrefundBank = (TextView) findViewById(R.id.tv_lendEntry_refundBank);
        mTextViewUse = (TextView) findViewById(R.id.tv_lendEntry_refundUse);
        mTextViewDeadline = (TextView) findViewById(R.id.tv_lendEntry_refundDeadline);
        mTextViewrefundDay = (TextView) findViewById(R.id.tv_lendEntry_refundDay);
        mTextViewStartTime = (TextView) findViewById(R.id.tv_lendEntry_startTime);
        mTextViewtime = (TextView) findViewById(R.id.tv_lendEntry_refundTime);
        mTextViewInsert = (TextView) findViewById(R.id.tv_lendEntry_insert);
        mTextViewBank = (TextView) findViewById(R.id.tv_lendEntry_bank);
        mTextViewMoney = (TextView) findViewById(R.id.tv_lendEntry_momey);
        mButton = (Button) findViewById(R.id.bt_lendEntry_affirm);
        mTnb = (TopNavigationBar) findViewById(R.id.tnb_lendEntry);
        mInsert = (TextView) findViewById(R.id.tv_lendentry_insert);
        mRelativeLayoutHetong= (RelativeLayout) findViewById(R.id.rl_lend_hetonh);
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.bt_lendEntry_affirm:
                   /* startActivity(new Intent(LendEntryActivity.this, LendSucceedActivity.class));
                    finish();*/
                    affirm();
                    break;
                case R.id.bt_petty_money:
                    know();
                    break;
                case R.id.rl_dialog_close:
                    mDialog.dismiss();
                    break;
                case R.id.rl_lend_hetonh:
                    startActivity(new Intent(this,HetongActivity.class));
                    break;
                case R.id.bt_petty_problem:
                    Intent intent = new Intent(LendEntryActivity.this, MotionActivity.class);
                    intent.putExtra("url", "http://47.93.163.237:9080/agreement/4.html");
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    }

    //确认借钱
    private void affirm() {
        String json = LoginTokenUtils.getJson();
        FormBody formbody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.INDEX_PETTLYLOAN_lIST, formbody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                    Log.d(TAG, "cvbnkml,;.==============" + result);
                    try {
                        JSONObject object = new JSONObject(result);
                        String returnCode = object.getString("returnCode");
                        String returnMsg = object.getString("returnMsg");
                        if ("000000".equals(returnCode)) {
                            startActivity(new Intent(LendEntryActivity.this, LendSucceedActivity.class));
                            ToastUtil.show(LendEntryActivity.this, returnMsg);
                            finish();
                        } else if ("0017".equals(returnCode)) {
                            startActivity(new Intent(LendEntryActivity.this, LoginActivity.class));
                        } else if ("0024".equals(returnCode)) {
                            ToastUtil.show(LendEntryActivity.this, returnMsg);
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

    private void know() {
        mDialog = new AlertDialog.Builder(this).create();
        View view = LayoutInflater.from(this).inflate(R.layout.dialong_lend_konw, null);
        mDialog.setCanceledOnTouchOutside(false);
        mRelativeClose = (RelativeLayout) view.findViewById(R.id.rl_dialog_close);
        mRelativeClose.setOnClickListener(this);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();
        mDialog.getWindow().setContentView((LinearLayout) view);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

}
