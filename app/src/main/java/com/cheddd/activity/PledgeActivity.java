package com.cheddd.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheddd.R;
import com.cheddd.application.MyApplications;
import com.cheddd.base.MyBaseActivity;
import com.cheddd.bean.MineRecord;
import com.cheddd.config.NetConfig;
import com.cheddd.utils.LoginTokenUtils;
import com.cheddd.utils.OkhttpUtils;
import com.cheddd.view.TopNavigationBar;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * 抵押贷款
 */


public class PledgeActivity extends MyBaseActivity implements View.OnClickListener {

    private static String TAG = PledgeActivity.class.getSimpleName();
    private TopNavigationBar mTnb;
    private Button mButtonProduct;
    private Dialog mDialog;
    private RelativeLayout mRelativeClose;
    private TextView mTextViewDot, mTextViewRelation, mTextViewMoney;
    private TextView mTextViewRisk, mTextViewSucceed, mTextViewFinance, mTextViewApplyfor;
    private Button mButtonRisk, mButtonSucceed, mBuutonFinance, mButtonApplyfor;
    private TextView mTvFinsh, mTvStatRisk, mTvFinance;
    private String extra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pledge);
        Intent intent = getIntent();
        if(intent!=null){
            extra = intent.getStringExtra("che");
        }
        initView();
        initData();
        setListener();
    }

    private void initData() {
        MineRecord record=new MineRecord();
       // record.setOrderNo(MyApplications.getOrderNo());
        record.setClientType("2");
        if(extra.equals("11")){
            record.setOrderNo("");
        }else if(extra.equals("12")){
            record.setOrderNo(MyApplications.getOrderNo());
        }
        record.setToken(MyApplications.getToken());
        Gson gson=new Gson();
        String json = gson.toJson(record);
        FormBody formbody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.INDEX_PLEDGE_INFO, formbody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                 //   Log.d(TAG, "获取抵押贷款贷款详情" + result);
                    try {
                        JSONObject object = new JSONObject(result);
                        String returnCode = object.getString("returnCode");
                        if ("000000".equals(returnCode)) {
                            JSONObject entity = object.getJSONObject("entity");
                            String storeName = entity.getString("storeName");
                            String phones = entity.getString("phones");
                            double contractAmt = entity.getDouble("contractAmt");
                            String loanInitAudDate = entity.getString("loanAudDate");
                            String riskAudDate = entity.getString("riskAudDate");
                            String financeAudDate = entity.getString("financeAudDate");
                            String payAudDate = entity.getString("payAudDate");
                            //状态
                       /* String financeAud = entity.getString("financeAud");
                        String riskAud = entity.getString("riskAud");
                        String loanAud = entity.getString("loanAud");
                        String payAud = entity.getString("payAud");*/
                            int financeAud = entity.getInt("financeAud");
                            int riskAud = entity.getInt("riskAud");
                            int loanAud = entity.getInt("loanAud");
                            int payAud = entity.getInt("payAud");
                            if (0 == loanAud) {
                                mButtonApplyfor.setEnabled(true);
                            } else {
                                mButtonApplyfor.setEnabled(false);
                            }
                            if (0 == financeAud) {
                                mBuutonFinance.setEnabled(true);
                                mTvFinance.setEnabled(true);
                            } else {
                                mBuutonFinance.setEnabled(false);
                                mTvFinance.setEnabled(false);
                            }
                            if (0 == riskAud) {
                                mButtonRisk.setEnabled(true);
                                mTvStatRisk.setEnabled(true);
                            } else {
                                mButtonRisk.setEnabled(false);
                                mTvStatRisk.setEnabled(false);
                            }
                            if (0 == payAud) {
                                mButtonSucceed.setEnabled(true);
                                mTvFinsh.setEnabled(true);
                            } else {
                                mButtonSucceed.setEnabled(false);
                                mTvFinsh.setEnabled(false);
                            }
                            mTextViewSucceed.setText(payAudDate);
                            mTextViewFinance.setText(financeAudDate);
                            mTextViewRisk.setText(riskAudDate);
                            mTextViewApplyfor.setText(loanInitAudDate);
                            mTextViewMoney.setText("￥" + contractAmt / 100 + "");
                            mTextViewDot.setText(storeName);
                            mTextViewRelation.setText(phones);
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

    private void setListener() {
        mButtonProduct.setOnClickListener(this);
        mTnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mTnb = (TopNavigationBar) findViewById(R.id.tnb_pledge);
        mButtonProduct = (Button) findViewById(R.id.bt_pledge_product);
        mTextViewDot = (TextView) findViewById(R.id.tv_pledge_dots);
        mTextViewRelation = (TextView) findViewById(R.id.tv_pledge_relations);
        mTextViewMoney = (TextView) findViewById(R.id.tv_pledge_moneys);
        mTextViewApplyfor = (TextView) findViewById(R.id.tv_pledge_applyforTime);
        mTextViewRisk = (TextView) findViewById(R.id.tv_pledge_riskTime);
        mTextViewSucceed = (TextView) findViewById(R.id.tv_pledge_succeedTime);
        mTextViewFinance = (TextView) findViewById(R.id.tv_pledge_financeTime);
        mButtonRisk = (Button) findViewById(R.id.bt_pledge_risk);
        mBuutonFinance = (Button) findViewById(R.id.bt_pledge_finance);
        mButtonSucceed = (Button) findViewById(R.id.bt_pledge_finsh);
        mButtonApplyfor = (Button) findViewById(R.id.bt_pledge_redtop);
        mTvFinance = (TextView) findViewById(R.id.tv_pledge_finance);
        mTvStatRisk = (TextView) findViewById(R.id.tv_pledge_applyfor);
        mTvFinsh = (TextView) findViewById(R.id.tv_pledge_finsh);
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.bt_pledge_product:
                    product();
                    break;
                case R.id.rl_dialog_product:
                    mDialog.dismiss();
                    break;
                default:
                    break;
            }
        }
    }

    private void product() {
        mDialog = new AlertDialog.Builder(this).create();
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_product, null);
        mDialog.setCanceledOnTouchOutside(false);
        mRelativeClose = (RelativeLayout) view.findViewById(R.id.rl_dialog_product);
        mRelativeClose.setOnClickListener(this);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();
        mDialog.getWindow().setContentView((LinearLayout) view);

    }
}
