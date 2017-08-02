package com.cheddd.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cheddd.R;
import com.cheddd.activity.LendDetailsActivity;
import com.cheddd.activity.PettyLoanActivity;
import com.cheddd.activity.PledgeActivity;
import com.cheddd.adapter.MineRecordAdapter;
import com.cheddd.application.MyApplications;
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
 * Created by Administrator on 2017/6/5 0005.
 * 交易记录，记录的界面
 */

public class MineRecordFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private ListView mListView;
    private MineRecordAdapter mAdapter;
    private List<MineRecord> mData;
    private Context mContent;
    private static final String TAG = MineRecordFragment.class.getSimpleName();


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
        final String json = LoginTokenUtils.getJson();
        FormBody formbody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(mContent).asyncPost(NetConfig.MINE_ACCOUNT_LIST, formbody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                Log.d(TAG, "借款记录" + result);
                //{"token":null,"returnCode":"000000","returnMsg":"操作成功","entity":null,
                // "rows":[{"bindBankCardNo":"1234567o","loanAmt":6700,"orderNo":"EM000001E3",
                // "loanStatus":0,"bindBankTag":"CMB","bindBank":"招商银行","loanAudDate":"2017-07-12 18:23:47"},
                // {"bindBankCardNo":"1234567o","loanAmt":8940000,"orderNo":"M000001","loanStatus":0,"bindBankTag":"CMB",
                // "bindBank":"招商银行","loanAudDate":"2017-07-12 23:24:23"}],"flag":"true"}
                try {
                    JSONObject object = new JSONObject(result);
                    String returnCode = object.getString("returnCode");
                    String returnMsg = object.getString("returnMsg");
                    if ("000000".equals(returnCode)) {
                        JSONArray rows = object.getJSONArray("rows");
                        for (int i = 0; i < rows.length(); i++) {
                            JSONObject jsonObject = rows.getJSONObject(i);
                            String bindBankCardNo = jsonObject.getString("bindBankCardNo");
                            String orderNo = jsonObject.getString("orderNo");
                            String bindBank = jsonObject.getString("bindBank");
                            String loanAudDate = jsonObject.getString("loanAudDate");
                            double loanAmt = jsonObject.getDouble("loanAmt");
                            int loanStatus = jsonObject.getInt("loanStatus");
                            MineRecord record = new MineRecord();
                            record.setBank(bindBank);
                            String substring = bindBankCardNo.substring(bindBankCardNo.length() - 4);
                            record.setBankID(substring);
                            record.setMoney(loanAmt / 100);
                            record.setOrderNo(orderNo);
                            if (0 == loanStatus) {
                                record.setMark("放款成功");
                            } else if (1 == loanStatus) {
                                record.setMark("放款中");
                            } else if (2 == loanStatus) {
                                record.setMark("审核中");
                            } else {
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
        String orderNo = mData.get(position).getOrderNo();
        MyApplications.setOrderNo(orderNo);
        CharSequence charSequence = orderNo.subSequence(0, 1);
        if("M".equals(charSequence)){
            startActivity(new Intent(getActivity(), PledgeActivity.class));
        }else {
            startActivity(new Intent(getActivity(), LendDetailsActivity.class));


        }

    }
}
