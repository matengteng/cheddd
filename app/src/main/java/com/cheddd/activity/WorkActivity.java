package com.cheddd.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.cheddd.R;
import com.cheddd.application.MyApplications;
import com.cheddd.base.MyBaseActivity;

import com.cheddd.bean.InfoWorkBean;
import com.cheddd.bean.LoginTokenBean;
import com.cheddd.bean.ProvinceBean;
import com.cheddd.config.NetConfig;
import com.cheddd.utils.JsonFileReader;
import com.cheddd.utils.LoginTokenUtils;
import com.cheddd.utils.OkhttpUtils;
import com.cheddd.utils.SharedPreferencesUtils;
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

import static android.R.attr.value;

/*
工作信息
*/
public class WorkActivity extends MyBaseActivity implements View.OnClickListener, TextWatcher {

    private static String TAG = WorkActivity.class.getSimpleName();
    private Map<String, Integer> mapExperience;
    private Map<String, Integer> mapMonthIncome;
    private Button mButtonfinsh;
    private TopNavigationBar mTnb;
    //单位地址，工作年限，月收入
    private TextView mTextViewComAddress, mTextViewExperience, mTextViewMonthIncome;
    //工作单位,详细地址，单位电话，职位
    private EditText mEditTextCompany, mEditTextAddress, mEditTextComTelno, mEditTextPosition;
    private int position = 0;
    OptionsPickerView pvOptions;
    //  省份
    ArrayList<ProvinceBean> provinceBeanList = new ArrayList<>();
    //  城市
    ArrayList<String> cities;
    ArrayList<List<String>> cityList = new ArrayList<>();
    //  区/县
    ArrayList<String> district;
    ArrayList<List<String>> districts;
    ArrayList<List<List<String>>> districtList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        initView();
        initData();
        setListener();
    }

    private void initData() {
        pvOptions = new OptionsPickerView(this);
        mapExperience = new LinkedHashMap<>();
        mapExperience.put("1-3年", 0);
        mapExperience.put("3-5年", 1);
        mapExperience.put("5-10", 2);
        mapExperience.put("10年以上", 3);
        mapMonthIncome = new LinkedHashMap<>();
        mapMonthIncome.put("2000以下", 0);
        mapMonthIncome.put("2000-5000", 1);
        mapMonthIncome.put("5000-10000", 2);
        mapMonthIncome.put("10000-20000", 3);
        mapMonthIncome.put("20000以上", 4);
        String json = LoginTokenUtils.getJson();
        FormBody formbody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.INFO_WORK_INFO, formbody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                    try {
                        // Log.d(TAG, "工作信息" + result);
                        JSONObject object = new JSONObject(result);
                        String returnCode = object.getString("returnCode");
                        if ("000000".equals(returnCode)) {
                            JSONObject entity = object.getJSONObject("entity");
                            String company = entity.getString("company");
                            String comAddress = entity.getString("comAddress");
                            String comTelno = entity.getString("comTelno");
                            String experience = entity.getString("experience");
                            String position = entity.getString("position");
                            String monthIncome = entity.getString("monthIncome");
                            mEditTextCompany.setText(company);
                            mTextViewComAddress.setText(comAddress);
                            mEditTextAddress.setText(comAddress);
                            mEditTextComTelno.setText(comTelno);
                            //  mTextViewExperience.setText(experience);
                            for (Map.Entry<String, Integer> entry : mapExperience.entrySet()) {
                                String string = entry.getValue().toString();
                                if (string.equals(experience)) {
                                    String key = entry.getKey();
                                    mTextViewExperience.setText(key);
                                }
                            }
                            mEditTextPosition.setText(position);
                            for (Map.Entry<String, Integer> entry : mapMonthIncome.entrySet()) {
                                String string = entry.getValue().toString();
                                if (string.equals(monthIncome)) {
                                    String key = entry.getKey();
                                    mTextViewMonthIncome.setText(key);
                                }
                            }
                        } else {
                            return;
                        }
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
                            mButtonfinsh.setVisibility(View.GONE);
                            mEditTextPosition.setCursorVisible(false);
                            mEditTextComTelno.setCursorVisible(false);
                            mEditTextCompany.setCursorVisible(false);
                            mEditTextAddress.setCursorVisible(false);
                        } else {
                            mButtonfinsh.setVisibility(View.VISIBLE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void setListener() {
        mButtonfinsh.setOnClickListener(this);
        mTextViewComAddress.addTextChangedListener(this);
        mTextViewExperience.addTextChangedListener(this);
        mTextViewMonthIncome.addTextChangedListener(this);
        mEditTextPosition.addTextChangedListener(this);
        mEditTextAddress.addTextChangedListener(this);
        mEditTextCompany.addTextChangedListener(this);
        mEditTextComTelno.addTextChangedListener(this);
        mTextViewMonthIncome.setOnClickListener(this);
        mTextViewExperience.setOnClickListener(this);
        mTextViewComAddress.setOnClickListener(this);
        mTnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initView() {
        mButtonfinsh = (Button) findViewById(R.id.bt_work_finsh);
        mTnb = (TopNavigationBar) findViewById(R.id.tnb_work);
        mTextViewComAddress = (TextView) findViewById(R.id.tv_work_comAddress);
        mTextViewExperience = (TextView) findViewById(R.id.tv_work_experience);
        mTextViewMonthIncome = (TextView) findViewById(R.id.tv_work_monthIncome);
        mEditTextCompany = (EditText) findViewById(R.id.et_work_company);
        mEditTextAddress = (EditText) findViewById(R.id.et_work_comAddress_detail);
        mEditTextComTelno = (EditText) findViewById(R.id.et_work_comTelno);
        mEditTextPosition = (EditText) findViewById(R.id.et_work_position);
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.tv_work_monthIncome:
                    montnMoney();
                    break;
                case R.id.tv_work_experience:
                    unitYear();
                    break;
                case R.id.tv_work_comAddress:
                    unitAddress();
                    break;
                case R.id.bt_work_finsh:
                    workfinsh();
                    break;
                default:
                    break;
            }
        }
    }

    //点击提交
    private void workfinsh() {
        String unitAddress = mEditTextCompany.getText().toString().trim();
        if (!unitAddress.matches("^[\\u4E00-\\u9FFF]+$")) {
            ToastUtil.show(this, "您输入的单位信息有误");
            return;
        }
        String unitphone = mEditTextComTelno.getText().toString().trim();
        if (!unitphone.matches("^[0-9]*$")) {
            ToastUtil.show(this, "单位电话输入有误");
            return;
        }
        String unitposition = mEditTextPosition.getText().toString().trim();
        if (!unitposition.matches("^[\\u4e00-\\u9fa5_a-zA-Z0-9]+$")) {
            ToastUtil.show(this, "职位输入错误");
            return;
        }
        String unitDetails = mEditTextAddress.getText().toString().trim();
        if (!unitDetails.matches("^[\\u4e00-\\u9fa5_a-zA-Z0-9]+$")) {
            ToastUtil.show(this, "单位地址输入有误");
        }
        //转换成字符串
        InfoWorkBean work = new InfoWorkBean();
        work.setClientType("2");
        String token = MyApplications.getToken();
        work.setToken(token);
        work.setCompany(mEditTextCompany.getText().toString().trim());
        work.setComAddress(mEditTextAddress.getText().toString().trim());
        work.setComTelno(mEditTextComTelno.getText().toString().trim());
        for (Integer key : mapExperience.values()) {
            if (mapExperience.get(mTextViewExperience.getText().toString()).equals(key)) {
                work.setExperience(key);
            }
        }
        work.setPosition(mEditTextPosition.getText().toString().trim());
        for (Integer key : mapMonthIncome.values()) {
            if (mapMonthIncome.get(mTextViewMonthIncome.getText().toString()).equals(key)) {
                work.setMonthIncome(key);
            }

        }
        Gson gson = new Gson();
        final String json = gson.toJson(work);
        Log.d(TAG, json);
        FormBody formBody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.INFO_WORK, formBody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                Log.d(TAG, "工作信息" + result);
                try {
                    Log.d(TAG, NetConfig.INFO_WORK + "content" + "=" + json);
                    JSONObject object = new JSONObject(result);
                    String returnCode = object.getString("returnCode");
                    String returnMsg = object.getString("returnMsg");
                    if ("000000".equals(returnCode)) {
                        ToastUtil.show(WorkActivity.this, returnMsg);
                        finish();
                    } else if ("0017".equals(returnCode)) {
                        ToastUtil.show(WorkActivity.this, returnMsg);
                        startActivity(new Intent(WorkActivity.this, LoginActivity.class));
                    } else {
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void unitAddress() {
        String province_data_json = JsonFileReader.getJson(this, "province_data.json");
        //  解析json数据
        parseJson(province_data_json);

        //  设置三级联动效果
        pvOptions.setPicker(provinceBeanList, cityList, districtList, true);

        pvOptions.setCyclic(false, false, false);
        // 设置默认选中的三级项目
        pvOptions.setSelectOptions(0, 0, 0);
        //  监听确定选择按钮
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String city = provinceBeanList.get(options1).getPickerViewText();
                String address;
                //  如果是直辖市或者特别行政区只设置市和区/县
                if ("北京市".equals(city) || "上海市".equals(city) || "天津市".equals(city) || "重庆市".equals(city) || "澳门".equals(city) || "香港".equals(city)) {
                    address = provinceBeanList.get(options1).getPickerViewText()
                            + " " + districtList.get(options1).get(option2).get(options3);
                } else {
                    address = provinceBeanList.get(options1).getPickerViewText()
                            + " " + cityList.get(options1).get(option2)
                            + " " + districtList.get(options1).get(option2).get(options3);
                }
                mTextViewComAddress.setText(address);
            }
        });


        //  点击弹出选项选择器
        mTextViewComAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvOptions.show();
            }
        });
    }

    public void parseJson(String str) {
        try {
            //  获取json中的数组
            JSONArray jsonArray = new JSONArray(str);
            //  遍历数据组
            for (int i = 0; i < jsonArray.length(); i++) {
                //  获取省份的对象
                JSONObject provinceObject = jsonArray.optJSONObject(i);
                //  获取省份名称放入集合
                String provinceName = provinceObject.getString("name");
                provinceBeanList.add(new ProvinceBean(provinceName));
                //  获取城市数组
                JSONArray cityArray = provinceObject.optJSONArray("city");
                cities = new ArrayList<>();//   声明存放城市的集合
                districts = new ArrayList<>();//声明存放区县集合的集合
                //  遍历城市数组
                for (int j = 0; j < cityArray.length(); j++) {
                    //  获取城市对象
                    JSONObject cityObject = cityArray.optJSONObject(j);
                    //  将城市放入集合
                    String cityName = cityObject.optString("name");
                    cities.add(cityName);
                    district = new ArrayList<>();// 声明存放区县的集合
                    //  获取区县的数组
                    JSONArray areaArray = cityObject.optJSONArray("area");
                    //  遍历区县数组，获取到区县名称并放入集合
                    for (int k = 0; k < areaArray.length(); k++) {
                        String areaName = areaArray.getString(k);
                        district.add(areaName);
                    }
                    //  将区县的集合放入集合
                    districts.add(district);
                }
                //  将存放区县集合的集合放入集合
                districtList.add(districts);
                //  将存放城市的集合放入集合
                cityList.add(cities);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //工作年限
    private void unitYear() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(R.array.work_year, position, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                position = which;
                final String[] aryShop = getResources().getStringArray(R.array.work_year);
                mTextViewExperience.setText(aryShop[which]);
                mapExperience.put(aryShop[which], which);
                dialog.dismiss();
            }
        }).create().show();

    }

    //月收入
    private void montnMoney() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(R.array.work_month, position, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                position = which;
                final String[] aryShop = getResources().getStringArray(R.array.work_month);
                mTextViewMonthIncome.setText(aryShop[which]);
                mapMonthIncome.put(aryShop[which], which);
                dialog.dismiss();
            }
        }).create().show();
    }

    //EditTextView的点击事件
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    //EditTextView的点击事件
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    //EditTextView的点击事件
    @Override
    public void afterTextChanged(Editable s) {
        if (mEditTextCompany.getText().toString().trim().length() > 1) {
            if (mEditTextAddress.getText().toString().trim().length() > 2) {
                if (mEditTextComTelno.getText().toString().trim().length() > 2) {
                    if (mEditTextPosition.getText().toString().trim().length() > 1) {
                        if (mTextViewExperience.getText().toString().length() > 0) {
                            if (mTextViewMonthIncome.getText().toString().length() > 0) {
                                mButtonfinsh.setEnabled(true);
                            } else {
                                mButtonfinsh.setEnabled(false);
                            }
                        } else {
                            mButtonfinsh.setEnabled(false);
                        }
                    } else {
                        mButtonfinsh.setEnabled(false);
                    }
                } else {
                    mButtonfinsh.setEnabled(false);
                }
            } else {
                mButtonfinsh.setEnabled(false);
            }
        } else {
            mButtonfinsh.setEnabled(false);
        }
    }
}
