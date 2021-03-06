package com.cheddd.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheddd.R;
import com.cheddd.activity.AdvanceActivtiy;
import com.cheddd.activity.LoginActivity;
import com.cheddd.activity.RecordActivity;
import com.cheddd.activity.SetPaymentActivity;
import com.cheddd.adapter.BorrowMoneyAdapter;
import com.cheddd.adapter.MoreLoanAdapter;
import com.cheddd.application.MyApplications;
import com.cheddd.base.BaseFragment;
import com.cheddd.bean.IndexLoanDetalis;
import com.cheddd.bean.MoreLoansBean;
import com.cheddd.bean.RepaymentPlan;
import com.cheddd.config.NetConfig;
import com.cheddd.utils.LoginTokenUtils;
import com.cheddd.utils.MD5Utils;
import com.cheddd.utils.OkhttpUtils;
import com.cheddd.utils.SharedPreferencesUtils;
import com.cheddd.utils.ToastUtil;
import com.cheddd.view.PwdEditText;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * Created by Administrator on 2017/7/13 0013.
 */

public class MoreLoansFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = MoreLoansFragment.class.getSimpleName();
    private Context mContext;
    private ExpandableListView mExpandableListView;
    private MoreLoanAdapter mAdapter;
    private List<MoreLoansBean> mList;
    private List<RepaymentPlan> repaymentPlan;
    private Button mButtonFinish;
    private Dialog mDialog;
    private TextView mTextViewDialogMoney, mTextViewDialogBank;
    private ImageView mImageViewDialog;
    private String bindBank;
    private double thisRepayAmt;
    private PwdEditText mEditTextPay;
    private LinearLayout mLinearLayoutSet;
    private String orderNo;
    private boolean flag;
    public static String money;
    private String orderNo1;

    public static String getMoney() {
        return money;
    }

    public static void setMoney(String money) {
        MoreLoansFragment.money = money;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_mine_loans, null);
        initView(view);
        initData();
        setData();
        setListener();
        return view;
    }

    private void setListener() {

        mAdapter.setOnGroupItemClickListener(new MoreLoanAdapter.OnGroupItemClickListener() {
            @Override
            public void onGroupItemClick(int position, View view) {
                if (mExpandableListView.isGroupExpanded(position)) {
                    mExpandableListView.collapseGroup(position);
                    mButtonFinish.setVisibility(View.GONE);
                } else {
                    mExpandableListView.expandGroup(position);
                    mButtonFinish.setVisibility(View.VISIBLE);
                    orderNo1 = mList.get(position).getOrderNo();

                }

            }
        });
        mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                for (int i = 0, count = mExpandableListView
                        .getExpandableListAdapter().getGroupCount(); i < count; i++) {
                    if (groupPosition != i) {// 关闭其他分组
                        mExpandableListView.collapseGroup(i);
                    }
                }
            }
        });
    }

    private void setData() {
        mExpandableListView.setAdapter(mAdapter);
        mButtonFinish.setOnClickListener(this);
    }

    private void initData() {
        mList = new ArrayList<>();
        String json = LoginTokenUtils.getJson();
        FormBody formbody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(mContext).asyncPost(NetConfig.MINE_ALL_REPAYLIST, formbody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                    //  Log.d(TAG, "" + result);
                    try {
                        JSONObject object = new JSONObject(result);
                        String returnCode = object.getString("returnCode");
                        String returnMsg = object.getString("returnMsg");
                        if ("0017".equals(returnCode)) {
                            mContext.startActivity(new Intent(mContext, LoginActivity.class));
                            ToastUtil.show(mContext, returnMsg);
                        } else if ("000000".equals(returnCode)) {
                            JSONArray rows = object.getJSONArray("rows");
                            for (int i = 0; i < rows.length(); i++) {
                                JSONObject jsonObject = rows.getJSONObject(i);
                                String bindBankCardNo = jsonObject.getString("bindBankCardNo");
                                int loanPeriod = jsonObject.getInt("loanPeriod");
                                thisRepayAmt = jsonObject.getDouble("thisRepayAmt");
                                double stayRepayAmt = jsonObject.getDouble("stayRepayAmt");
                                int interestType = jsonObject.getInt("interestType");
                                double alreadyRepayAmt = jsonObject.getDouble("alreadyRepayAmt");
                                orderNo = jsonObject.getString("orderNo");
                                String loanCycle = jsonObject.getString("loanCycle");
                                String bindBankTag = jsonObject.getString("bindBankTag");
                                String interestTime = jsonObject.getString("interestTime");
                                double loanRate = jsonObject.getDouble("loanRate");
                                JSONArray repaymentPlan1 = jsonObject.getJSONArray("repaymentPlan");
                                repaymentPlan = new ArrayList<>();
                                for (int j = 0; j < repaymentPlan1.length(); j++) {
                                    JSONObject repayment = repaymentPlan1.getJSONObject(j);
                                    String repayTime = repayment.getString("repayTime");
                                    double interestAmt = repayment.getDouble("interestAmt");
                                    double principalAmt = repayment.getDouble("principalAmt");
                                    RepaymentPlan plan = new RepaymentPlan(interestAmt / 100, principalAmt / 100, repayTime);
                                    repaymentPlan.add(plan);
                                }
                                bindBank = jsonObject.getString("bindBank");
                                int surplusPeriod = jsonObject.getInt("surplusPeriod");
                                double contractAmt = jsonObject.getDouble("contractAmt");
                                double repayAmt = jsonObject.getDouble("repayAmt");

                                MoreLoansBean loanBean = new MoreLoansBean(bindBankCardNo, repaymentPlan, repayAmt / 100, contractAmt / 100, surplusPeriod, bindBank, loanRate / 100, interestTime, loanCycle, orderNo, alreadyRepayAmt / 100,
                                        interestType, stayRepayAmt / 100, thisRepayAmt / 100, bindBankTag, loanPeriod);
                                mList.add(loanBean);
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
        mAdapter = new MoreLoanAdapter(mList, mContext);
    }

    private void initView(View view) {
        mExpandableListView = (ExpandableListView) view.findViewById(R.id.elv_mineloan_list);
        mButtonFinish = (Button) view.findViewById(R.id.bt_mineloan_sunmit);
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.bt_mineloan_sunmit:
                    repayment();
                    break;
                case R.id.ll_refund_refund_setPassword:
                    //;
                    break;
                case R.id.pet_pwd:
                    setPassword();
                    break;
                default:
                    break;
            }
        }
    }

    //判断是否设置密码
    private void setPassword() {
        String json = LoginTokenUtils.getJson();
        FormBody formBody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(mContext).asyncPost(NetConfig.MINE_SETZHIFU_PASSWORD, formBody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                    //  Log.d(TAG, "判断支付密码是否设置" + result);
                    try {
                        JSONObject object = new JSONObject(result);
                        String returnCode = object.getString("returnCode");
                        String returnMsg = object.getString("returnMsg");
                        if ("0030".equals(returnCode)) {
                            return;
                        } else {
                            mContext.startActivity(new Intent(mContext, SetPaymentActivity.class));
                            ToastUtil.show(mContext, returnCode);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    //还款
    private void repayment() {
        mDialog = new AlertDialog.Builder(mContext).create();
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_refund_pwd, null);
        mEditTextPay = (PwdEditText) view.findViewById(R.id.pet_pwd);
        mEditTextPay.setOnClickListener(this);
        mLinearLayoutSet = (LinearLayout) view.findViewById(R.id.ll_refund_refund_setPassword);
        mTextViewDialogBank = (TextView) view.findViewById(R.id.tv_dialog_bank);
        mTextViewDialogMoney = (TextView) view.findViewById(R.id.tv_dialog_money);
        mImageViewDialog = (ImageView) view.findViewById(R.id.iv_dialog_bank);
        mLinearLayoutSet.setOnClickListener(this);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();
        mDialog.getWindow().setContentView((LinearLayout) view);
        mDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        mDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        String money = getMoney();
        double parseDouble = Double.parseDouble(money);
        mTextViewDialogMoney.setText("￥" + parseDouble + "");
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
            ToastUtil.show(mContext, "次行目前不支持");
        }
        mEditTextPay.setOnInputFinishListener(new PwdEditText.OnInputFinishListener() {
            @Override
            public void onInputFinish(String password) {
                IndexLoanDetalis detalis = new IndexLoanDetalis();
                detalis.setClientType("2");
                detalis.setToken(MyApplications.getToken());
                detalis.setOrderNo(orderNo1);
                String phone = SharedPreferencesUtils.getString(mContext, "phone", "");
                detalis.setPayPassWord(MD5Utils.encode(phone + mEditTextPay.getText().toString().trim()));
                Gson gson = new Gson();
                String json = gson.toJson(detalis);
                Log.d(TAG, "多个订单的支付密码" + json);
                if (mEditTextPay.getSelectionEnd() == 6) {
                    FormBody formBody = new FormBody.Builder().add("content", json).build();
                    OkhttpUtils.getInstance(mContext).asyncPost(NetConfig.INDEX_PETTY_SINGLE_PAY, formBody, new OkhttpUtils.HttpCallBack() {
                        @Override
                        public void onError(Request request, IOException e) {

                        }

                        @Override
                        public void onSuccess(Request request, String result) {
                            if (result != null) {
                                Log.d(TAG, "多个订单的提交" + result);
                                //{"token":null,"returnCode":"0023","returnMsg":"支付密码不正确","entity":null,"rows":[],"flag":"false"}
                                try {
                                    JSONObject object = new JSONObject(result);
                                    String returnCode = object.getString("returnCode");
                                    String returnMsg = object.getString("returnMsg");
                                    if ("000000".equals(returnCode)) {
                                        Intent intent = new Intent(mContext, RecordActivity.class);
                                        intent.putExtra("result", 1001);
                                        startActivity(intent);
                                        getActivity().finish();
                                    } else if ("0023".equals(returnCode)) {
                                        ToastUtil.show(mContext, returnMsg);
                                    } else if ("0046".equals(returnCode)) {
                                        ToastUtil.show(mContext, returnMsg);
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
