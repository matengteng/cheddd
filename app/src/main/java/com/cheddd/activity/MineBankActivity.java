package com.cheddd.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cheddd.R;
import com.cheddd.adapter.MinebankAdapter;
import com.cheddd.base.MyBaseActivity;
import com.cheddd.bean.MineBankBean;
import com.cheddd.config.NetConfig;
import com.cheddd.utils.LoginTokenUtils;
import com.cheddd.utils.OkhttpUtils;
import com.cheddd.view.TopNavigationBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Request;

public class MineBankActivity extends MyBaseActivity implements AdapterView.OnItemClickListener {

    private static String TAG = MineBankActivity.class.getSimpleName();
    private TopNavigationBar mTnb;
    private ListView mListView;
    private MinebankAdapter mAdapter;
    private List<MineBankBean> mData;
    private JSONObject entity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_bank);
        initView();
        initData();
        setData();
        setListener();
    }

    private void setData() {

        mListView.setAdapter(mAdapter);
    }

    private void initData() {
        mData = new ArrayList<MineBankBean>();
        String json = LoginTokenUtils.getJson();
        FormBody formbody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.INDEX_PETTLYLOAN_BANK, formbody, new OkhttpUtils.HttpCallBack() {

            private String substring;

            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                   Log.d(TAG, "借款银行卡" + result);
                    try {
                        JSONObject object = new JSONObject(result);
                        try{
                            entity = object.getJSONObject("entity");
                        }catch (Exception e){
                           return;
                        }
                        String dictionaryName = entity.getString("dictionaryName");
                        String khBankPro = entity.getString("cardNo");
                        if (khBankPro.length() > 4) {
                            substring = khBankPro.substring(khBankPro.length() - 4);
                        }
                        MineBankBean bankBean = new MineBankBean();
                        bankBean.setMark(dictionaryName);
                        bankBean.setNumber(substring);
                        bankBean.setMap("");
                        mData.add(bankBean);
                        mAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        mAdapter = new MinebankAdapter(mData, MineBankActivity.this);
    }

    private void setListener() {
        mListView.setOnItemClickListener(this);
        mTnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mTnb = (TopNavigationBar) findViewById(R.id.tnb_minebank);
        mListView = (ListView) findViewById(R.id.lv_minebank);
    }

    //listView的点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MineBankBean mineBankBean = mData.get(position);

    }
}
