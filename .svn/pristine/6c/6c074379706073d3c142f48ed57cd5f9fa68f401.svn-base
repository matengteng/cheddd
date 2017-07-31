package com.cheddd.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cheddd.R;
import com.cheddd.adapter.PayoffAdapter;
import com.cheddd.base.BaseFragment;
import com.cheddd.bean.LoanBean;
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
 * 已还清，我的借款中的界面
 */

public class PayoffFragment extends BaseFragment {
    private static final String TAG = PayoffFragment.class.getSimpleName();
    private Context mContext;
    private List<LoanBean> mData;
    private ListView mListView;
    private PayoffAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_listview, null);
        initView(view);
        initData();
        setData();
        return view;
    }

    private void setData() {
        mListView.setAdapter(mAdapter);
    }

    private void initData() {
        mData = new ArrayList<>();
        String json = LoginTokenUtils.getJson();
        FormBody formBody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(mContext).asyncPost(NetConfig.MINE_REPAIED_LIST, formBody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                    Log.d(TAG, "获取已还清列表" + result);
                    try {
                        JSONObject object = new JSONObject(result);
                        String returnCode = object.getString("returnCode");
                        if ("000000".equals(returnCode)) {
                            JSONArray rows = object.getJSONArray("rows");
                            for (int i = 0; i < rows.length(); i++) {
                                JSONObject jsonObject = rows.getJSONObject(i);
                                String orderNo = jsonObject.getString("orderNo");
                                String loanCycle = jsonObject.getString("loanCycle");
                                String interestTime = jsonObject.getString("interestTime");
                                double contractAmt = jsonObject.getDouble("contractAmt");
                                int interestType = jsonObject.getInt("interestType");
                                String loanPeriod = jsonObject.getString("loanPeriod");
                                double loanRate = jsonObject.getDouble("loanRate");
                                LoanBean bean = new LoanBean(orderNo, interestType, loanPeriod, loanRate / 100, interestTime, loanCycle, contractAmt / 100);
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
        mAdapter = new PayoffAdapter(mData, mContext);
    }

    private void initView(View view) {
        mListView = (ListView) view.findViewById(R.id.lv_fragment);
    }
}
