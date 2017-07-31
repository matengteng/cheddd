package com.cheddd.activity;

import android.app.Dialog;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cheddd.R;
import com.cheddd.adapter.AdvanceAdapter;
import com.cheddd.application.MyApplications;
import com.cheddd.base.MyBaseActivity;
import com.cheddd.bean.AdvanceBean;
import com.cheddd.bean.IndexLoanDetalis;
import com.cheddd.config.NetConfig;
import com.cheddd.utils.LoginTokenUtils;
import com.cheddd.utils.MD5Utils;
import com.cheddd.utils.OkhttpUtils;
import com.cheddd.utils.SharedPreferencesUtils;
import com.cheddd.utils.ToastUtil;
import com.cheddd.view.PwdEditText;
import com.cheddd.view.TopNavigationBar;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * 提前还请借款
 */

public class AdvanceActivtiy extends MyBaseActivity implements AdvanceAdapter.OnItemCheckedChangeListener, View.OnClickListener {


    private static String TAG = AdvanceActivtiy.class.getSimpleName();
    private TopNavigationBar mTnb;
    private AdvanceAdapter mAdapter;
    private ListView mListView;
    private CheckBox mCheckBox;
    //总计
    private TextView mTextViewTotal;
    private List<AdvanceBean> mData;
    private Button mButton;

    private Dialog mDialog;
    private PwdEditText mEditTextPay;
    private TextView mTextViewDialogMoney, mTextViewDialogBank;
    private ImageView mImageViewDialog;
    private String bindBank;
    private LinearLayout mLinearLayoutSet;
    private List<String> mList;
    private String orderNo;
    private String indent;
    private StringBuffer str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_activtiy);
        initView();
        initData();
        setData();
        setListener();
    }

    private void setData() {
        mListView.setAdapter(mAdapter);
    }

    private void setListener() {
        mButton.setOnClickListener(this);
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (AdvanceBean advanceBean : mData) {
                        advanceBean.isChecked = true;
                    }
                    mAdapter.notifyDataSetChanged();
                } else {
                    boolean isUnCheckedAll = true;
                    for (AdvanceBean advanceBean : mData) {
                        if (!advanceBean.isChecked) {
                            isUnCheckedAll = false;
                            break;
                        }
                    }
                    if (isUnCheckedAll)
                        for (AdvanceBean advanceBean : mData) {
                            advanceBean.isChecked = false;
                        }
                    mAdapter.notifyDataSetChanged();
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

    private void initData() {
        mData = new ArrayList<>();
        mList = new ArrayList<>();
        String json = LoginTokenUtils.getJson();
        FormBody formbody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.INDEX_PETTYLOAN_ADVANCE, formbody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                    Log.d(TAG, "提前还请借款" + result);
                    try {
                        JSONObject object = new JSONObject(result);
                        String returnCode = object.getString("returnCode");
                        String returnMsg = object.getString("returnMsg");
                        if ("000000".equals(returnCode)) {
                            JSONArray rows = object.getJSONArray("rows");
                            for (int i = 0; i < rows.length(); i++) {
                                JSONObject jsonObject = rows.getJSONObject(i);
                                String bindBankCardNo = jsonObject.getString("bindBankCardNo");
                                String interestTime = jsonObject.getString("interestTime");
                                orderNo = jsonObject.getString("orderNo");
                                bindBank = jsonObject.getString("bindBank");
                                double repayAmt = jsonObject.getDouble("repayAmt");
                                double loanAmt = jsonObject.getDouble("loanAmt");
                                AdvanceBean advanceBean = new AdvanceBean();
                                advanceBean.setTime(interestTime);
                                advanceBean.setIndent(orderNo);
                                advanceBean.setRefund(loanAmt / 100);
                                advanceBean.setRespond(repayAmt / 100);
                                mData.add(advanceBean);
                            }
                            mAdapter.notifyDataSetChanged();
                        } else {
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        mAdapter = new AdvanceAdapter(mData, AdvanceActivtiy.this);
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.lv_advance);
        mCheckBox = (CheckBox) findViewById(R.id.cb_advance_checkAll);
        mTextViewTotal = (TextView) findViewById(R.id.tv_advance_total);
        mButton = (Button) findViewById(R.id.bt_advance_refund);
        mTnb = (TopNavigationBar) findViewById(R.id.tnb_advance);
    }


    @Override
    public void onCheckedChanged(CompoundButton button, boolean isChecked, int position) {
        mData.get(position).isChecked = isChecked;
        mCheckBox.setChecked(checkAllSelected());
        if (isChecked) {
            mButton.setEnabled(true);
        } else if (checkAllUnSelected()) {
            mCheckBox.setChecked(false);
            mButton.setEnabled(false);
        }
        int money = mAdapter.getMoney();
        mTextViewTotal.setText(money + "");
    }

    private boolean checkAllSelected() {
        for (AdvanceBean advanceBean : mData) {
            if (!advanceBean.isChecked) {
                return false;
            }
        }
        return true;
    }

    private boolean checkAllUnSelected() {
        for (AdvanceBean advanceBean : mData) {
            if (advanceBean.isChecked) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        Integer position = (Integer) v.getTag();
        if (v != null) {
            switch (v.getId()) {
                case R.id.bt_advance_refund:
                    payoff();
                    break;
                case R.id.tv_advance_indent:
                    if (position != null) {
                        for (int i = 0; i < mData.size(); i++) {
                            indent = mData.get(position).getIndent();
                            mList.add(indent);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                    break;
                case R.id.pet_pwd:
                    paymentPassword();
                    break;
                default:
                    break;
            }
        }
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
                    Log.d(TAG, "判断支付密码是否设置" + result);
                    try {
                        JSONObject object = new JSONObject(result);
                        String returnCode = object.getString("returnCode");
                        if ("0030".equals(returnCode)) {
                            return;
                        } else {
                            startActivity(new Intent(AdvanceActivtiy.this, SetPaymentActivity.class));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private void payoff() {
        mDialog = new AlertDialog.Builder(this).create();
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_refund_pwd,null);
        mEditTextPay = (PwdEditText) view.findViewById(R.id.pet_pwd);
        mEditTextPay.setOnClickListener(this);
        mLinearLayoutSet = (LinearLayout) view.findViewById(R.id.ll_refund_refund_setPassword);
        mTextViewDialogBank = (TextView) view.findViewById(R.id.tv_dialog_bank);
        mTextViewDialogMoney = (TextView) view.findViewById(R.id.tv_dialog_money);
        mImageViewDialog = (ImageView) view.findViewById(R.id.iv_dialog_bank);
        mLinearLayoutSet.setOnClickListener(this);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
       // mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dialog_loan_notice_back));
        mDialog.getWindow().setContentView((LinearLayout) view);
        mDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        mDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mTextViewDialogMoney.setText("￥" + mTextViewTotal.getText().toString() + "");
        mTextViewDialogBank.setText(bindBank);
        if (mTextViewDialogBank.getText().toString().equals("交通银行 ")) {
            mImageViewDialog.setImageResource(R.mipmap.bank_com);
        } else if (mTextViewDialogBank.getText().toString().equals("招商银行")) {
            mImageViewDialog.setImageResource(R.mipmap.bank_cmbc);
        } else if (mTextViewDialogBank.getText().toString().equals("民生银行")) {
            mImageViewDialog.setImageResource(R.mipmap.bank_cmsb);
        } else if (mTextViewDialogBank.getText().toString().equals("农业银行")) {
            mImageViewDialog.setImageResource(R.mipmap.bank_abc);
        } else if (mTextViewDialogBank.getText().toString().equals("上海银行")) {
            mImageViewDialog.setImageResource(R.mipmap.bank_sh);
        } else if (mTextViewDialogBank.getText().toString().equals("广发银行")) {
            mImageViewDialog.setImageResource(R.mipmap.bank_cgb);
        } else if (mTextViewDialogBank.getText().toString().equals("中国银行")) {
            mImageViewDialog.setImageResource(R.mipmap.bank_boc);
        } else if (mTextViewDialogBank.getText().toString().equals("建设银行")) {
            mImageViewDialog.setImageResource(R.mipmap.bank_ccb);
        } else if (mTextViewDialogBank.getText().toString().equals("兴业银行")) {
            mImageViewDialog.setImageResource(R.mipmap.bank_cib);
        } else if (mTextViewDialogBank.getText().toString().equals("中信银行")) {
            mImageViewDialog.setImageResource(R.mipmap.bank_citic);
        } else if (mTextViewDialogBank.getText().toString().equals("华夏银行")) {
            mImageViewDialog.setImageResource(R.mipmap.bank_hxb);
        } else if (mTextViewDialogBank.getText().toString().equals("平安银行")) {
            mImageViewDialog.setImageResource(R.mipmap.bank_pa);
        } else if (mTextViewDialogBank.getText().toString().equals("工商银行")) {
            mImageViewDialog.setImageResource(R.mipmap.bank_icbc);
        } else if (mTextViewDialogBank.getText().toString().equals("光大银行 ")) {
            mImageViewDialog.setImageResource(R.mipmap.bank_ceb);
        } else if (mTextViewDialogBank.getText().toString().equals("邮储银行")) {
            mImageViewDialog.setImageResource(R.mipmap.bank_yz);
        } else if (mTextViewDialogBank.getText().toString().equals("浦发银行")) {
            mImageViewDialog.setImageResource(R.mipmap.bank_spdp);
        } else {
            ToastUtil.show(this, "次行目前不支持");
        }
        mEditTextPay.setOnInputFinishListener(new PwdEditText.OnInputFinishListener() {
            @Override
            public void onInputFinish(String password) {
                IndexLoanDetalis detalis = new IndexLoanDetalis();
                detalis.setClientType("2");
                detalis.setToken(MyApplications.getToken());
                str = new StringBuffer();
                for (AdvanceBean advanceBean : mData) {
                    if (advanceBean.isChecked) {
                        str.append(advanceBean.getIndent()).append(",");
                    }
                }
                str.delete(str.length() - 1, str.length());
                detalis.setOrderNo(new String(str));
                String phone = SharedPreferencesUtils.getString(AdvanceActivtiy.this, "phone", "");
                detalis.setPayPassWord(MD5Utils.encode(phone + mEditTextPay.getText().toString().trim()));
                Gson gson = new Gson();
                String json = gson.toJson(detalis);
                Log.d(TAG, json);
                if (mEditTextPay.getSelectionEnd() == 6) {
                    FormBody formBody = new FormBody.Builder().add("content", json).build();
                    OkhttpUtils.getInstance(AdvanceActivtiy.this).asyncPost(NetConfig.INDEX_PETTY_PREREPAY, formBody, new OkhttpUtils.HttpCallBack() {
                        @Override
                        public void onError(Request request, IOException e) {

                        }

                        @Override
                        public void onSuccess(Request request, String result) {
                            if (result != null) {
                                Log.d(TAG, "提前还款提交" + result);
                                try {
                                    JSONObject object = new JSONObject(result);
                                    String returnCode = object.getString("returnCode");
                                    String returnMsg = object.getString("returnMsg");
                                    if ("000000".equals(returnCode)) {
                                        ToastUtil.show(AdvanceActivtiy.this, returnMsg);
                                        finish();
                                    } else if ("0023".equals(returnCode)) {
                                        ToastUtil.show(AdvanceActivtiy.this, returnMsg);
                                    } else {
                                        return;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                    mDialog.dismiss();
                }
            }
        });
    }

}
