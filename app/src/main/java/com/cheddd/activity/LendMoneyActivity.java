package com.cheddd.activity;

import android.app.Dialog;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.cheddd.R;
import com.cheddd.adapter.RefundDetailsAdapter;
import com.cheddd.application.MyApplications;
import com.cheddd.base.MyBaseActivity;
import com.cheddd.bean.IndexCheckBean;
import com.cheddd.bean.IndexLoanBean;
import com.cheddd.config.NetConfig;
import com.cheddd.utils.LoginTokenUtils;
import com.cheddd.utils.OkhttpUtils;
import com.cheddd.utils.ToastUtil;
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
 * 借款金额
 */


public class LendMoneyActivity extends MyBaseActivity implements View.OnClickListener, TextWatcher {

    private static String TAG = LendMoneyActivity.class.getSimpleName();
    //下一步、借款须知
    private Button mButtonNext, mButtonKonw;
    private TopNavigationBar mTnb;
    private RelativeLayout mRelativeCheck;
    private RefundDetailsAdapter mRefundAdapter;
    private List<IndexCheckBean> mList;
    private ListView mListView;
    private RelativeLayout mRelativeClose, mRelativeKnow;
    private Dialog mDialog;
    //借款方式，还款期数,首期还款，查看,本月应还本金
    private TextView mTextViewStyle, mTextViewRefund, mTextViewfirstRepayTime, mTextViewMonthMOney, mTextViewTotalInset;
    private RelativeLayout mRelativeStyle, mRelativeRefund;
    private int position = 0;
    private EditText mEdittextMoney;
    private TextView mTextViewInterest;
    private ListView mListViewStyle, mListViewRefund;
    private ArrayAdapter<String> mAdapterStyle;
    private ArrayAdapter<String> mAdapterRefund;
    private Map<String, List<String>> mMap;
    private ArrayList<String> mListStyle;
    private ArrayList<String> mListRefund;
    private String monthly;
    private String dailyinterest;
    private Map<String, Integer> mMapStyle;
    private Map<String, Integer> mMapMonth;
    private Map<String, Integer> mMapDay;
    private double monthPrincipalAmt;
    private double monthInterestAmt;
    private String loanCycle;
    private ArrayList<String> list1;
    private ArrayList<String> list2;
    private int loanLimit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lend_money);
        initView();
        initData();
        setData();
        setListener();
    }

    private void setData() {
    }

    private void initData() {
        lendMoney();
        mMapStyle();
        mList = new ArrayList<>();
        MonthlyINterest();

    }

    private void lendMoney() {
        final String json = LoginTokenUtils.getJson();
        FormBody formbody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.INDEX_PETTYLOAN_INFO, formbody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                    // Log.d(TAG, "获取可借额度和总额度，还款试算" + result);
                    // Log.d(TAG, "onSuccess:" + NetConfig.INDEX_PETTYLOAN_INFO + "content" + "=" + json);
                    try {
                        JSONObject object = new JSONObject(result);
                        String returnCode = object.getString("returnCode");
                        String returnMsg = object.getString("returnMsg");
                        if ("000000".equals(returnCode)) {
                            JSONObject entity = object.getJSONObject("entity");
                            loanLimit = entity.getInt("loanLimit");
                            Log.d(TAG,"+++++++++++++++++++++++++++++++++++"+String.valueOf(loanLimit).toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    private void mMapStyle() {
        mMapStyle = new LinkedHashMap<>();
        mMapStyle.put("现金分期", 0);
        mMapStyle.put("单期借款", 1);
        mMapMonth = new LinkedHashMap<>();
      /*  mMapMonth.put("3月", 0);
        mMapMonth.put("6月", 1);
        mMapMonth.put("9月", 2);
        mMapMonth.put("12月", 3);*/
        mMapDay = new LinkedHashMap<>();
       /* mMapDay.put("7天", 0);
        mMapDay.put("10天", 1);
        mMapDay.put("15天", 2);
        mMapDay.put("30天", 3);*/
        mMap = new LinkedHashMap<>();
        list1 = new ArrayList<>();
       /* list1.add("7天");
        list1.add("10天");
        list1.add("15天");
        list1.add("30天");
        mMap.put("单期借款", list1);*/
        list2 = new ArrayList<>();
       /* list2.add("3月");
        list2.add("6月");
        list2.add("9月");
        list2.add("12月");
        mMap.put("现金分期", list2);*/
        mListStyle = new ArrayList<>();


    }

    //获取小额借款月利率、日利率
    private void MonthlyINterest() {
        String json = LoginTokenUtils.getJson();
        FormBody formbody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.INDEX_PETTYLOAN_INSTERT, formbody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                    try {
                        // Log.d(TAG, "获取小额借款月利率、日利率" + result);
                        JSONObject object = new JSONObject(result);
                        String returnCode = object.getString("returnCode");
                        if ("000000".equals(returnCode)) {
                            JSONObject entity = object.getJSONObject("entity");
                            monthly = entity.getString("monthly");
                            dailyinterest = entity.getString("dailyinterest");
                            JSONArray singleCycleList = entity.getJSONArray("singleCycleList");
                            for (int i = 0; i < singleCycleList.length(); i++) {
                                JSONObject jsonObject = singleCycleList.getJSONObject(i);
                                String name = jsonObject.getString("name");
                                String value = jsonObject.getString("value");
                                //添加到map集合
                                mMapDay.put(name, Integer.parseInt(value));
                                list1.add(name);
                                mMap.put("单期借款", list1);
                            }
                            // Log.d(TAG,mMapDay.toString()+);
                            JSONArray cashCycleList = entity.getJSONArray("cashCycleList");
                            for (int i = 0; i < cashCycleList.length(); i++) {
                                JSONObject jsonObject = cashCycleList.getJSONObject(i);
                                String name = jsonObject.getString("name");
                                String value = jsonObject.getString("value");
                                mMapMonth.put(name, Integer.parseInt(value));
                                list2.add(name);
                                mMap.put("现金分期", list2);
                            }

                        }
                        for (Map.Entry<String, List<String>> entry : mMap.entrySet()) {
                            String key = entry.getKey();
                            mListStyle.add(key);
                        }
                        if (mAdapterStyle == null) {
                            mAdapterStyle = new ArrayAdapter<String>(LendMoneyActivity.this, android.R.layout.simple_list_item_1, mListStyle);
                        } else {
                            mAdapterStyle.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private void setListener() {
        mEdittextMoney.addTextChangedListener(this);
        mTextViewStyle.addTextChangedListener(this);
        mTextViewRefund.addTextChangedListener(this);
        mRelativeStyle.setOnClickListener(this);
        mRelativeRefund.setOnClickListener(this);
        mButtonNext.setOnClickListener(this);
        mRelativeCheck.setOnClickListener(this);
        mTnb.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {

        mTextViewfirstRepayTime = (TextView) findViewById(R.id.tv_lendMoney_firstRepayTime);
        mTextViewInterest = (TextView) findViewById(R.id.tv_lendMoney_insert);
        mButtonKonw = (Button) findViewById(R.id.bt_lendmoney_know);
        mTnb = (TopNavigationBar) findViewById(R.id.tnb_lendmoney);
        mRelativeCheck = (RelativeLayout) findViewById(R.id.rl_lendmoney_check);
        mButtonNext = (Button) findViewById(R.id.bt_lendmoney_next);
        mRelativeStyle = (RelativeLayout) findViewById(R.id.rl_lendmoney_style);
        mRelativeRefund = (RelativeLayout) findViewById(R.id.rl_lendmoney_refund);
        mTextViewStyle = (TextView) findViewById(R.id.tv_lendmoney_style);
        mRelativeRefund = (RelativeLayout) findViewById(R.id.rl_lendmoney_refund);
        mTextViewRefund = (TextView) findViewById(R.id.tv_lendmoney_month);
        mEdittextMoney = (EditText) findViewById(R.id.et_lendmoney_money);
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.rl_lendmoney_check:
                    check();
                    break;
                case R.id.bt_lendmoney_next:
                    next();
                    break;
                case R.id.rl_lendmoney_style:
                    style();
                    break;
                case R.id.rl_lendmoney_refund:
                    refund();
                    break;
                case R.id.rl_dialogRefund_close:
                    mDialog.dismiss();
                    break;
                case R.id.bt_lendmoney_know:
                    know();
                    break;

                case R.id.bt_petty_problem:
                    Intent intent = new Intent(LendMoneyActivity.this, MoreQuestionActivity.class);
                    intent.putExtra("url", "http://47.93.163.237:9080/agreement/4.html");
                    startActivity(intent);
                    break;
                case R.id.rl_dialog_close:
                    mDialog.dismiss();
                    break;
                default:
                    break;
            }
        }
    }

    //借款须知
    private void know() {
        mDialog = new AlertDialog.Builder(this).create();
        View view = LayoutInflater.from(this).inflate(R.layout.dialong_lend_konw, null);
        mDialog.setCanceledOnTouchOutside(false);
        mRelativeKnow = (RelativeLayout) view.findViewById(R.id.rl_dialog_close);
        mRelativeKnow.setOnClickListener(this);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();
        mDialog.getWindow().setContentView((LinearLayout) view);
    }

    //还款期数
    private void refund() {
        if (mTextViewStyle.getText().toString().length() > 0) {
            mDialog = new AlertDialog.Builder(this).create();
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_listview, null);
            mDialog.setCanceledOnTouchOutside(false);
            mListViewRefund = (ListView) view.findViewById(R.id.lv_dialog);
            mListViewRefund.setAdapter(mAdapterRefund);
            mListViewRefund.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String s = mListRefund.get(position);
                    mTextViewRefund.setText(s.toString());
                    //借贷试算，提交数据
                    LoanCompute();
                    mDialog.dismiss();
                }
            });
            mDialog.show();
            mDialog.getWindow().setContentView((LinearLayout) view);
        } else {
            ToastUtil.show(this, "请先选择借款方式");
            return;
        }

    }

    //借贷试算，提交数据
    private void LoanCompute() {
        IndexLoanBean loanBean = new IndexLoanBean();
        loanBean.setClientType("2");
        loanBean.setToken(MyApplications.getToken());
        for (Map.Entry<String, Integer> entry : mMapStyle.entrySet()) {
            String key = entry.getKey();
            if (mTextViewStyle.getText().toString().equals(key)) {
                Integer value = entry.getValue();
                loanBean.setInterestType(value);
            }
        }
        if (mTextViewStyle.getText().toString().equals("单期借款")) {
            for (Map.Entry<String, Integer> entry : mMapDay.entrySet()) {
                String key = entry.getKey();
                if (mTextViewRefund.getText().toString().equals(key)) {
                    Integer value = entry.getValue();
                    loanBean.setLoanCycle(value);
                }
            }
        } else {
            for (Map.Entry<String, Integer> entry : mMapMonth.entrySet()) {
                String key = entry.getKey();
                if (mTextViewRefund.getText().toString().equals(key)) {
                    Integer value = entry.getValue();
                    loanBean.setLoanCycle(value);
                }
            }
        }
        String money = mEdittextMoney.getText().toString().trim();
        if (!money.matches("^[0-9]*$")) {
            ToastUtil.show(this, "金额输入有误");
            return;
        }
        loanBean.setContractAmt(100 * Integer.parseInt(money));
        Gson gson = new Gson();
        String json = gson.toJson(loanBean);
        FormBody formBody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.INDEX_PRTTYLOAN_TRIAL, formBody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                    //  Log.d(TAG, "0现金分期；1单期借款" + result);
                    try {
                        JSONObject objece = new JSONObject(result);
                        String returnCode = objece.getString("returnCode");
                        String returnMsg = objece.getString("returnMsg");
                        if ("000000".equals(returnCode)) {
                            //获取小额借贷试算结果
                            LoanRequest();
                            ToastUtil.show(LendMoneyActivity.this, returnMsg);
                            JSONObject entity = objece.getJSONObject("entity");
                            monthPrincipalAmt = entity.getDouble("monthPrincipalAmt");
                            monthInterestAmt = entity.getDouble("monthInterestAmt");
                            loanCycle = entity.getString("loanCycle");
                            JSONArray rows = objece.getJSONArray("rows");
                            for (int i = 0; i < rows.length(); i++) {
                                JSONObject jsonObject = rows.getJSONObject(i);
                                String repaymentDate = jsonObject.getString("repaymentDate");
                                double repaymentAmt = jsonObject.getDouble("repaymentAmt");
                                IndexCheckBean checkBean = new IndexCheckBean();
                                checkBean.setNum(i + 1);
                                checkBean.setRepaymentAmt(repaymentAmt / 100 + "");
                                checkBean.setRepaymentDate(repaymentDate);
                                mList.add(checkBean);
                            }
                            mRefundAdapter = new RefundDetailsAdapter(mList, LendMoneyActivity.this);
                        } else if ("0022".equals(returnCode)) {
                            ToastUtil.show(LendMoneyActivity.this, returnMsg);
                        } else if ("0017".equals(returnCode)) {
                            startActivity(new Intent(LendMoneyActivity.this, LoginActivity.class));
                        } else {
                            ToastUtil.show(LendMoneyActivity.this, returnMsg);
                            mButtonNext.setEnabled(false);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    //获取小额借贷试算结果
    private void LoanRequest() {
        String json = LoginTokenUtils.getJson();
        FormBody formbody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.INDEX_PETTLYLOAN_RESULT, formbody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                     Log.d(TAG, "获取小额借贷试算结果" + result);
                    try {
                        JSONObject object = new JSONObject(result);
                        String returnCode = object.getString("returnCode");
                        String returnMsg = object.getString("returnMsg");
                        if ("000000".equals(returnCode)) {
                            JSONObject entity = object.getJSONObject("entity");
                            double firstRepayTime = entity.getDouble("firstRepayment");
                            String repayTime = entity.getString("repayTime");
                            mTextViewfirstRepayTime.setText("￥" + firstRepayTime / 100 + "元" + "(" + repayTime + ")");
                        }else {
                            ToastUtil.show(LendMoneyActivity.this,returnMsg);
                            mEdittextMoney.setText("");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    //借款方式
    private void style() {
        if (mEdittextMoney.getText().toString().length() > 0) {
            if (Integer.valueOf(mEdittextMoney.getText().toString()).intValue() < loanLimit) {
                mDialog = new AlertDialog.Builder(this).create();
                View view = LayoutInflater.from(this).inflate(R.layout.dialog_listview, null);
                mDialog.setCanceledOnTouchOutside(false);
                mListViewStyle = (ListView) view.findViewById(R.id.lv_dialog);
                mListViewStyle.setAdapter(mAdapterStyle);
                mListViewStyle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mTextViewRefund.setText(null);
                        String s = mListStyle.get(position);
                        mTextViewStyle.setText(s.toString());
                        String style = (String) parent.getItemAtPosition(position);
                        if (s.equals("单期借款")) {
                            mTextViewInterest.setText("*按日计息，日利率为" + dailyinterest);
                        }
                        if (s.equals("现金分期")) {
                            mTextViewInterest.setText("*按月计息，月利率为" + monthly);
                        }
                        for (Map.Entry<String, List<String>> entry : mMap.entrySet()) {
                            String key = entry.getKey();
                            if (key.equals(mTextViewStyle.getText().toString())) {
                                List<String> value = entry.getValue();
                                mListRefund = new ArrayList<>();
                                for (int i = 0; i < value.size(); i++) {
                                    String refund = value.get(i);
                                    mListRefund.add(refund);
                                }
                                mAdapterRefund = new ArrayAdapter<String>(LendMoneyActivity.this, android.R.layout.simple_list_item_1, mListRefund);
                                mAdapterRefund.notifyDataSetInvalidated();
                            }
                        }
                        mDialog.dismiss();
                    }
                });
                mDialog.show();
                mDialog.getWindow().setContentView((LinearLayout) view);
            } else {
                ToastUtil.show(this, "超额了");
             return;
            }
        } else {
            ToastUtil.show(this, "请选择借款金额");
            return;
        }


    }

    //点击下一步
    private void next() {
        Intent intet = new Intent(this, LendBankActivity.class);
        startActivity(intet);
    }

    //查看还款详情
    private void check() {
        if (mTextViewRefund.getText().toString().length() > 0) {
            mDialog = new AlertDialog.Builder(this).create();
            mDialog.setCanceledOnTouchOutside(false);
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_refunddetails, null);
            mTextViewMonthMOney = (TextView) view.findViewById(R.id.tv_dilaogrefund_2);
            mTextViewTotalInset = (TextView) view.findViewById(R.id.tv_dilaogrefund_3);
            mListView = (ListView) view.findViewById(R.id.lv_dialogrefund_details);
            mRelativeClose = (RelativeLayout) view.findViewById(R.id.rl_dialogRefund_close);
            mTextViewMonthMOney.setText("每期应还本金￥" + monthPrincipalAmt / 100);
            mTextViewTotalInset.setText("借满" + loanCycle + "总利息￥" + monthInterestAmt / 100);
            mRelativeClose.setOnClickListener(this);
            mListView.setAdapter(mRefundAdapter);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mDialog.show();
            mDialog.getWindow().setContentView((LinearLayout) view);
        } else {
            ToastUtil.show(this, "请选择还款期数");
            return;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mEdittextMoney.getText().toString().length() > 0)
            if (mTextViewStyle.getText().toString().length() > 0)
                if (mTextViewRefund.getText().toString().length() > 0) {
                    mButtonNext.setEnabled(true);
                } else {
                    mButtonNext.setEnabled(false);
                }

    }
}


