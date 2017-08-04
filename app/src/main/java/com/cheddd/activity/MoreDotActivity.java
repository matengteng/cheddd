package com.cheddd.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cheddd.R;
import com.cheddd.adapter.MoreDotAdapter;
import com.cheddd.base.MyBaseActivity;
import com.cheddd.bean.MoreDotbean;
import com.cheddd.bean.Store;
import com.cheddd.config.NetConfig;
import com.cheddd.utils.LoginTokenUtils;
import com.cheddd.utils.OkhttpUtils;
import com.cheddd.view.TopNavigationBar;

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

public class MoreDotActivity extends MyBaseActivity {

    private static String TAG = MoreDotActivity.class.getSimpleName();
    private TopNavigationBar mTnb;
    private MoreDotAdapter mAdapter;
    private List<MoreDotbean> mData;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_dot);
        initView();
        initData();
        setData();
        setListener();
    }

    private void setData() {
        mListView.setAdapter(mAdapter);
    }


    private void initData() {
        mData = new ArrayList<>();
        final String json = LoginTokenUtils.getJson();
        FormBody frombody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.INDEX_EXTREME_GAIN, frombody, new OkhttpUtils.HttpCallBack() {

            @Override
            public void onError(Request request, IOException e) {
            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                   // Log.d(TAG, NetConfig.INDEX_EXTREME_GAIN + "content" + "=" + json);
                   // Log.d(TAG, "dcfvgbhnjmk,l.;/'" + result);
                    try {
                        JSONObject object = new JSONObject(result);
                        JSONArray rows = object.getJSONArray("rows");
                        for (int i = 0; i < rows.length(); i++) {
                            JSONObject jsonObject = rows.getJSONObject(i);
                            JSONArray store1 = jsonObject.getJSONArray("store");
                            for (int j = 0; j < store1.length(); j++) {
                                JSONObject storeJSONObject = store1.getJSONObject(j);
                                String address = storeJSONObject.getString("address");
                                String storeName = storeJSONObject.getString("storeName");
                                MoreDotbean bean = new MoreDotbean(address, storeName);
                                mData.add(bean);
                            }

                        }
                        mAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        mAdapter = new MoreDotAdapter(mData, this);
    }

    private void setListener() {
        mTnb.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.lv_more_dot);
        mTnb = (TopNavigationBar) findViewById(R.id.tnb_moredot);
    }
}
