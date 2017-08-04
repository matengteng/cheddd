package com.cheddd.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheddd.R;
import com.cheddd.application.MyApplications;
import com.cheddd.base.MyBaseActivity;
import com.cheddd.bean.InfoLiveBean;
import com.cheddd.bean.LoginTokenBean;
import com.cheddd.config.NetConfig;
import com.cheddd.utils.LoginTokenUtils;
import com.cheddd.utils.OkhttpUtils;
import com.cheddd.utils.SharedPreferencesUtils;
import com.cheddd.utils.ToastUtil;
import com.cheddd.view.TopNavigationBar;
import com.google.gson.Gson;
import com.google.gson.internal.Streams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.Request;

import static android.R.attr.value;

/*
居住信息
*/
public class LiveActivity extends MyBaseActivity implements View.OnClickListener, TextWatcher {
    private static String TAG = LiveActivity.class.getSimpleName();
    //住房状态 、产权状态
    private TextView mTextViewHouse, mTextViewProduct;
    //月租金 、单位地址
    private EditText mEditTextMonth, mEditTextUnit;
    private TopNavigationBar mTnb;
    private Button mButtonSubmit;
    private int position = 0;
    private Map<String, Integer> map;
    private Map<String, Integer> mapHouse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);
        initView();
        initData();
        setListener();
    }

    private void initData() {
        map = new LinkedHashMap<>();
        map.put("租房", 0);
        map.put("自购", 1);
        map.put("亲友房屋", 2);
        map.put("父母同住", 3);
        map.put("其他", 4);
        mapHouse = new LinkedHashMap<>();
        mapHouse.put("正常", 0);
        mapHouse.put("按揭", 1);
        mapHouse.put("抵押", 2);
        mapHouse.put("质押", 3);
        String json = LoginTokenUtils.getJson();
        FormBody formBody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.INFO_LIVE_INFO, formBody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                  //  Log.d(TAG, "请求居住信息" + result);
                    try {
                        JSONObject object = new JSONObject(result);
                        JSONObject entity = object.getJSONObject("entity");
                        int housingStatus = entity.getInt("housingStatus");
                        int monthRent = entity.getInt("monthRent");
                        int propertyStatus = entity.getInt("propertyStatus");
                        String address = entity.getString("address");
                        for (Map.Entry<String, Integer> entry : map.entrySet()) {
                            int value = entry.getValue().intValue();
                            if (value == housingStatus) {
                                String key = entry.getKey();
                                mTextViewHouse.setText(key);
                            }
                        }
                        for (Map.Entry<String, Integer> entry : mapHouse.entrySet()) {
                            int value = entry.getValue();
                            if (value == propertyStatus) {
                                String key = entry.getKey();
                                mTextViewProduct.setText(key);
                            }
                        }
                        mEditTextMonth.setText(monthRent / 100 + "");
                        mEditTextUnit.setText(address);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        loanInfo();
    }

    private void loanInfo() {
        String json = LoginTokenUtils.getJson();
        final FormBody formbody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.INDEX_PLEDGE_INFO, formbody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                    try {
                        JSONObject object = new JSONObject(result);
                        JSONObject entity = object.getJSONObject("entity");
                        int loanInitAud = entity.getInt("loanInitAud");
                        if (loanInitAud == 0) {
                            mButtonSubmit.setVisibility(View.GONE);
                            mEditTextUnit.setCursorVisible(false);
                            mEditTextMonth.setCursorVisible(false);

                        } else {
                            mButtonSubmit.setVisibility(View.VISIBLE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void setListener() {
        mEditTextMonth.addTextChangedListener(this);
        mEditTextUnit.addTextChangedListener(this);
        mTextViewProduct.addTextChangedListener(this);
        mTextViewHouse.addTextChangedListener(this);
        mButtonSubmit.setOnClickListener(this);
        mTextViewProduct.setOnClickListener(this);
        mTextViewHouse.setOnClickListener(this);
        mTnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LiveActivity.this, StatsApproveActivity.class));
                finish();
            }
        });
    }

    private void initView() {
        mTnb = (TopNavigationBar) findViewById(R.id.tnb_live);
        mEditTextMonth = (EditText) findViewById(R.id.et_live_month);
        mEditTextUnit = (EditText) findViewById(R.id.et_live_unit);
        mTextViewHouse = (TextView) findViewById(R.id.tv_live_house_select);
        mTextViewProduct = (TextView) findViewById(R.id.tv_live_house_product);
        mButtonSubmit = (Button) findViewById(R.id.bt_live_submit);
        mButtonSubmit = (Button) findViewById(R.id.bt_live_submit);
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.tv_live_house_select:
                    house();
                    break;
                case R.id.tv_live_house_product:
                    houseProduct();
                    break;
                case R.id.bt_live_submit:
                    finishSubmit();
                    break;
                default:
                    break;
            }
        }
    }

    //提交
    private void finishSubmit() {
        String month = mEditTextMonth.getText().toString().trim();
        if (!month.matches("^[0-9]*$")) {
            ToastUtil.show(this, "月租金输入错误");
            return;
        }
        InfoLiveBean live = new InfoLiveBean();
        live.setClientType("2");
        live.setToken(MyApplications.getToken());
        live.setAddress(mEditTextUnit.getText().toString().trim());
        live.setMonthRent(Integer.parseInt(month) * 100);
        for (Integer key : mapHouse.values()) {
            if (mapHouse.get(mTextViewProduct.getText().toString()).equals(key)) {
                live.setPropertyStatus(new Integer(key).intValue());
            }
        }
        for (Integer key : map.values()) {
            if (map.get(mTextViewHouse.getText().toString()).equals(key)) {
                live.setHousingStatus(new Integer(key).intValue());
            }
        }
        Gson gson = new Gson();
        String json = gson.toJson(live);
        FormBody formBody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.INFO_LIVE, formBody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
             //   Log.d(TAG, result);
                if (request != null) {
                    try {
                        JSONObject object = new JSONObject(result);
                        String returnCode = object.getString("returnCode");
                        String returnMsg = object.getString("returnMsg");
                        if ("000000".equals(returnCode)) {
                            ToastUtil.show(LiveActivity.this, returnMsg);
                            finish();
                        } else if ("0002".equals(returnCode)) {
                            ToastUtil.show(LiveActivity.this, returnMsg);
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

    private void houseProduct() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(R.array.house_product, position, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                position = which;
                final String[] aryShop = getResources().getStringArray(R.array.house_product);
                mTextViewProduct.setText(aryShop[which]);
                mapHouse.put(aryShop[which], which);
                dialog.dismiss();
            }
        }).create().show();
    }

    private void house() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(R.array.house, position, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                position = which;
                final String[] aryShop = getResources().getStringArray(R.array.house);
                mTextViewHouse.setText(aryShop[which]);
                map.put(aryShop[which], which);
                dialog.dismiss();
            }
        }).create().show();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mTextViewHouse.getText().toString().length() > 0) {
            if (mEditTextMonth.getText().toString().trim().length() > 0) {
                if (mTextViewProduct.getText().toString().length() > 0) {
                    if (mEditTextUnit.getText().toString().trim().length() > 0) {
                        mButtonSubmit.setEnabled(true);
                    } else {
                        mButtonSubmit.setEnabled(false);
                    }
                } else {
                    mButtonSubmit.setEnabled(false);
                }
            } else {
                mButtonSubmit.setEnabled(false);
            }
        } else {
            mButtonSubmit.setEnabled(false);
        }
    }
}
