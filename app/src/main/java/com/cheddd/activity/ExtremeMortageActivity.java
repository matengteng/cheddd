package com.cheddd.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
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
import android.widget.TextView;

import com.cheddd.R;
import com.cheddd.application.MyApplications;
import com.cheddd.base.MyBaseActivity;
import com.cheddd.bean.IndexExtremeInfo;
import com.cheddd.bean.Store;
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
 * 抵押贷款界面
 */

public class ExtremeMortageActivity extends MyBaseActivity implements View.OnClickListener, TextWatcher {

    private static String TAG = ExtremeMortageActivity.class.getSimpleName();
    //服务城市，门店
    private TextView mTextViewProvince, mTextViewDot;
    private Button mButtonSubmit;
    private EditText mEditTextMoney;
    private ListView mListViewProvince, mListViewDot;
    private ArrayAdapter<String> mAdapter;
    private ArrayAdapter<String> mAdapter1;

    private Dialog mDialog;
    private TopNavigationBar mTnb;
    //服务的城市
    private List<String> mCityList;
    //服务的门店
    private List<String> mStoreList;
    private LinkedHashMap<String, List<Store>> mMap;
    private Map<String, String> mapString;
    private String provincesData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extreme_mortage);
        initView();
        initData();
        setListener();
    }

    private void initData() {
        mCityList = new ArrayList<>();
        mStoreList = new ArrayList<>();
        gainCity();
        infoData();
    }

    private void infoData() {
        String json = LoginTokenUtils.getJson();
        FormBody formbody = new FormBody.Builder().add("content", json).build();

    }

    //获取城市
    private void gainCity() {
        final String json = LoginTokenUtils.getJson();
        FormBody frombody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.INDEX_EXTREME_GAIN, frombody, new OkhttpUtils.HttpCallBack() {


            private String id1;
            private String storeName1;
            private ArrayList<Store> mStore;
            private String city;

            @Override
            public void onError(Request request, IOException e) {
            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                  //  Log.d(TAG, NetConfig.INDEX_EXTREME_GAIN + "content" + "=" + json);
                   // Log.d(TAG,"dcfvgbhnjmk,l.;/'"+result);
                    try {
                        JSONObject object = new JSONObject(result);
                        JSONArray rows = object.getJSONArray("rows");
                        mMap = new LinkedHashMap<>();
                        mapString = new LinkedHashMap<>();
                        for (int i = 0; i < rows.length(); i++) {
                            JSONObject jsonObject = rows.getJSONObject(i);
                            JSONArray store1 = jsonObject.getJSONArray("store");
                            city = jsonObject.getString("city");
                            mStore = new ArrayList<Store>();
                            for (int j = 0; j < store1.length(); j++) {
                                JSONObject storeJSONObject = store1.getJSONObject(j);
                                String id = storeJSONObject.getString("id");
                                String cityName = storeJSONObject.getString("cityName");
                                String storeName = storeJSONObject.getString("storeName");
                                String shopkeeper = storeJSONObject.getString("shopkeeper");
                                String phones = storeJSONObject.getString("phones");
                                Store store = new Store(id, cityName, storeName, shopkeeper, phones);
                                mStore.add(store);
                                for (Store list : mStore) {
                                    storeName1 = list.getStoreName();
                                    id1 = list.getId();
                                    mapString.put(storeName1, id1);
                                }
                            }
                            mMap.put(city, mStore);
                        }
                        for (Map.Entry<String, List<Store>> entry : mMap.entrySet()) {
                            String key = entry.getKey();
                            mCityList.add(key);
                        }
                        // Log.d(TAG, "mCityList" + mCityList);
                        mAdapter = new ArrayAdapter<String>(ExtremeMortageActivity.this, android.R.layout.simple_list_item_1, mCityList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void setListener() {
        mEditTextMoney.addTextChangedListener(this);
        mTextViewProvince.addTextChangedListener(this);
        mTextViewDot.addTextChangedListener(this);
        mButtonSubmit.setOnClickListener(this);
        mTextViewProvince.setOnClickListener(this);
        mTextViewDot.setOnClickListener(this);
    }

    private void initView() {
        mTnb = (TopNavigationBar) findViewById(R.id.tnb_extreme);
        mButtonSubmit = (Button) findViewById(R.id.bt_extreme_submit);
        mEditTextMoney = (EditText) findViewById(R.id.et_extreme_money);
        mTextViewDot = (TextView) findViewById(R.id.tv_extreme_dots);
        mTextViewProvince = (TextView) findViewById(R.id.tv_extreme_provinces);
        mEditTextMoney = (EditText) findViewById(R.id.et_extreme_money);
        mTnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.bt_extreme_submit:
                    submit();
                    break;
                case R.id.tv_extreme_dots:
                    dot();
                    break;
                case R.id.tv_extreme_provinces:
                    provinces();
                    break;
            }
        }
    }

    //门店的选择
    private void dot() {
        if (mTextViewProvince.getText().toString().length() > 0) {
            mDialog = new AlertDialog.Builder(this).create();
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_listview, null);
            mDialog.setCanceledOnTouchOutside(false);
            mListViewDot = (ListView) view.findViewById(R.id.lv_dialog);
            mListViewDot.setAdapter(mAdapter1);
            mListViewDot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String s = mStoreList.get(position);
                    mTextViewDot.setText(s.toString());
                    mDialog.dismiss();
                }
            });
            mDialog.show();
            mDialog.getWindow().setContentView((LinearLayout) view);
        } else {
            ToastUtil.show(this, "请先选择服务城市");
            return;
        }
    }

    //服务城市的选择
    private void provinces() {
        mDialog = new AlertDialog.Builder(this).create();
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_listview, null);
        mDialog.setCanceledOnTouchOutside(false);
        mListViewProvince = (ListView) view.findViewById(R.id.lv_dialog);
        mListViewProvince.setAdapter(mAdapter);
        mListViewProvince.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            private List<Store> value;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mStoreList.clear();
                mTextViewDot.setText(null);
                provincesData = mCityList.get(position);
                mTextViewProvince.setText(provincesData.toString());
                String string = (String) parent.getItemAtPosition(position);
                for (Map.Entry<String, List<Store>> entry : mMap.entrySet()) {
                    String key = entry.getKey();
                    if (string.equals(key)) {
                        value = entry.getValue();
                        for (int i = 0; i < value.size(); i++) {
                            Store store = value.get(i);
                            String storeName = store.getStoreName();
                            mStoreList.add(storeName);
                        }
                    }
                }
               // Log.d(TAG, "mStoreList.toString()" + mStoreList.toString());
                mAdapter1 = new ArrayAdapter<String>(ExtremeMortageActivity.this, android.R.layout.simple_list_item_1, mStoreList);
                mDialog.dismiss();
            }
        });
        mDialog.show();
        mDialog.getWindow().setContentView((LinearLayout) view);
    }

    //提交的按钮
    private void submit() {
        IndexExtremeInfo extremeInfo = new IndexExtremeInfo();
        extremeInfo.setClientType("2");
        extremeInfo.setToken(MyApplications.getToken());
        // Log.d(TAG, "mapString" + mapString);
        for (Map.Entry<String, String> entry : mapString.entrySet()) {
            String key = entry.getKey();
            if (key.equals(mTextViewDot.getText().toString())) {
                String value = entry.getValue();
                extremeInfo.setStoreId(value);
            }
        }
        int money = Integer.parseInt(mEditTextMoney.getText().toString().trim()) * 100;
        extremeInfo.setContractAmt(String.valueOf(money));
        Gson gson = new Gson();
        String json = gson.toJson(extremeInfo);
        FormBody formBody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.INDEX_EXTREME_SUBMIT, formBody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                    // {"token":null,"returnCode":"000000","returnMsg":"操作成功","entity":null,"rows":[],"flag":"true"}
                    try {
                        JSONObject object = new JSONObject(result);
                        String returnCode = object.getString("returnCode");
                        String returnMsg = object.getString("returnMsg");
                        if ("000000".equals(returnCode)) {
                            ToastUtil.show(ExtremeMortageActivity.this, returnMsg);
                           startActivity(new Intent(ExtremeMortageActivity.this, PledgeActivity.class).putExtra("che","11"));
                            finish();
                        } else {
                            ToastUtil.show(ExtremeMortageActivity.this, "网络异常");
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mTextViewDot.getText().toString().length() > 0) {
            if (mTextViewProvince.getText().toString().length() > 0) {
                if (mEditTextMoney.getText().toString().trim().length() > 0) {
                    mButtonSubmit.setEnabled(true);
                }
            } else {
                mButtonSubmit.setEnabled(false);
            }
        } else {
            mButtonSubmit.setEnabled(false);
        }
    }

}
