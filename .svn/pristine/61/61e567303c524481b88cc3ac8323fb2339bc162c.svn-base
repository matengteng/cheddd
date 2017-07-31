package com.cheddd.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.cheddd.R;
import com.cheddd.adapter.RefundRecordAdapter;
import com.cheddd.application.MyApplications;
import com.cheddd.base.MyBaseActivity;
import com.cheddd.bean.MineRecord;
import com.cheddd.bean.RefundRecordBean;
import com.cheddd.config.NetConfig;
import com.cheddd.utils.OkhttpUtils;
import com.cheddd.view.TopNavigationBar;
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
 * 还款记录的界面，从PayOffAdapter中跳转过来;
 */
public class RefundRecordActivity extends MyBaseActivity {

    private ListView mListView;
    private RefundRecordAdapter mAdapter;
    private List<RefundRecordBean> mData;
    private TopNavigationBar mTnb;
    private String orderNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund_record);
        Intent intent = getIntent();
        if (intent != null) {
            orderNo = intent.getStringExtra("orderNo");
        } else {
            return;
        }
        initView();
        initData();
        setData();
        setListener();
    }

    private void setListener() {
        mTnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setData() {
        mListView.setAdapter(mAdapter);
    }

    private void initData() {
        mData = new ArrayList<>();
        /*for (int i = 0; i < 2; i++) {
            mData.add(new RefundRecordBean());
        }*/
        final MineRecord record = new MineRecord();
        record.setClientType("2");
        record.setOrderNo(orderNo);
        record.setToken(MyApplications.getToken());
        Gson gson = new Gson();
        String json = gson.toJson(record);
        FormBody formBody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.MINE_REFUNDSINGLE_RECORD, formBody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                    Log.d("TAG", "+++++++++++++++++" + result);
                    //{"entity":null,"flag":"true","returnCode":"000000","returnMsg":"操作成功",
                    // "rows":[{"overdueStatus":2,"orderNo":"EM000015E1",
                    // "actualRepayAmt":40559,"repayTime":"2017/08/20","actualRepayTime":"2017/07/20"},
                    try {
                        JSONObject object = new JSONObject(result);
                        String returnCode = object.getString("returnCode");
                        String returnMsg = object.getString("returnMsg");
                        if ("000000".equals(returnCode)) {
                            JSONArray rows = object.getJSONArray("rows");
                            for (int i = 0; i < rows.length(); i++) {
                                JSONObject jsonObject = rows.getJSONObject(i);
                                String repayTime = jsonObject.getString("repayTime");
                                String actualRepayTime = jsonObject.getString("actualRepayTime");
                                double actualRepayAmt = jsonObject.getDouble("actualRepayAmt");
                                int overdueStatus = jsonObject.getInt("overdueStatus");
                                RefundRecordBean bean = new RefundRecordBean(repayTime, overdueStatus, actualRepayAmt/100, actualRepayTime);
                                mData.add(bean);
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
        mAdapter = new RefundRecordAdapter(mData, this);
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.lv_refundrecord);
        mTnb = (TopNavigationBar) findViewById(R.id.tnb_refundrecord);
    }
}
