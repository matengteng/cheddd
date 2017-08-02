package com.cheddd.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cheddd.R;
import com.cheddd.activity.LendDetailsActivity;
import com.cheddd.activity.RefundDetailsActivity;
import com.cheddd.adapter.MineRecordAdapter;
import com.cheddd.base.BaseFragment;
import com.cheddd.bean.MineRecord;
import com.cheddd.config.NetConfig;
import com.cheddd.utils.LoginTokenUtils;
import com.cheddd.utils.OkhttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * Created by Administrator on 2017/7/17 0017.
 * 还款记录
 */

public class MineRefundFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private static final String TAG = MineRefundFragment.class.getSimpleName();
    private ListView mListView;
    private MineRecordAdapter mAdapter;
    private List<MineRecord> mData;
    private Context mContent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContent = getActivity();
        View view = LayoutInflater.from(mContent).inflate(R.layout.fragment_mine_record, null);
        initView(view);
        initData();
        setData();
        setListener();
        return view;

    }

    private void setListener() {

        mListView.setOnItemClickListener(this);
    }

    private void setData() {

        mListView.setAdapter(mAdapter);
    }

    private void initData() {
        mData = new ArrayList<>();
      /*  mData.add(new MineRecord());
        mAdapter = new MineRecordAdapter(mData, getContext());*/
        final String json = LoginTokenUtils.getJson();
        FormBody formbody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(mContent).asyncPost(NetConfig.MINE_ALL_REPAY_RECORD, formbody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                Log.d(TAG, "还款记录" + result);
                try {
                    //{"token":null,"returnCode":"000000","returnMsg":"操作成功",
                    // "entity":null,"rows":[{"bindBankCardNo":"6216603600002515330",
                    // "overdueStatus":0,"orderNo":"M000002","transactionId":"292c47cd8eea47848bed9245fbe086f0",
                    // "actualRepayAmt":100000,"bindBankTag":"BOC",
                    // "bindBank":"中国银行","actualRepayTime":"2017-07-17 11:38:09"},
                    JSONObject object = new JSONObject(result);
                    String returnCode = object.getString("returnCode");
                    String returnMsg = object.getString("returnMsg");
                    if ("000000".equals(returnCode)) {
                        JSONArray rows = object.getJSONArray("rows");
                        for (int i = 0; i < rows.length(); i++) {
                            JSONObject jsonObject = rows.getJSONObject(i);
                            String bindBank = jsonObject.getString("bindBank");
                            String orderNo = jsonObject.getString("orderNo");
                            String transactionId = jsonObject.getString("transactionId");
                            String loanAudDate = jsonObject.getString("actualRepayTime");
                            double loanAmt = jsonObject.getDouble("actualRepayAmt");
                            int overdueStatus = jsonObject.getInt("overdueStatus");
                            String bindBankCardNo = jsonObject.getString("bindBankCardNo");
                            String substring = bindBankCardNo.substring(bindBankCardNo.length() - 4);
                            MineRecord record = new MineRecord();
                            record.setBank(bindBank);
                            record.setBankID(substring);
                            record.setOrderNo(orderNo);
                            record.setTransactionId(transactionId);
                            record.setMoney(loanAmt / 100);
                            if (0 == overdueStatus) {
                                record.setMark("还款中");
                            } else if (1 == overdueStatus) {
                                record.setMark("还款成功");
                            } else if (2 == overdueStatus) {
                                record.setMark("提前还款");
                            } else if (3 == overdueStatus) {
                                record.setMark("还款失败");
                                return;
                            }
                             record.setTime(loanAudDate);
                            mData.add(record);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        mAdapter = new MineRecordAdapter(mData, getContext());
    }

    private void initView(View view) {
        mListView = (ListView) view.findViewById(R.id.lv_record);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MineRecord mineRecord = mData.get(position);
        String transactionId = mineRecord.getTransactionId();
        String orderNo = mineRecord.getOrderNo();
        Intent intent = new Intent(getActivity(), RefundDetailsActivity.class);
        intent.putExtra("transactionId", transactionId);
        intent.putExtra("orderNo", orderNo);
        startActivity(intent);
    }
}
