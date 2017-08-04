package com.cheddd.activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.cheddd.R;
import com.cheddd.application.MyApplications;
import com.cheddd.base.MyBaseActivity;
import com.cheddd.bean.InfoCarBean;
import com.cheddd.config.NetConfig;
import com.cheddd.utils.LoginTokenUtils;
import com.cheddd.utils.OkhttpUtils;
import com.cheddd.utils.SharedPreferencesUtils;
import com.cheddd.utils.ToastUtil;
import com.cheddd.view.TopNavigationBar;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;

import static android.R.attr.key;

/*
* 车辆认证
*
*/
public class CarApproveActivity extends MyBaseActivity implements View.OnClickListener, TextWatcher {
    private static String TAG = CarApproveActivity.class.getSimpleName();
    private Map<String, Integer> mapNot;
    //车辆品牌，是否抵押 ，过户时间
    private TextView mTextViewIf, mTextViewTime;
    //车辆号、公里数、车辆品牌
    private EditText mEditTextMark, mEditTextKm, mEditTextType;
    private int position = 0;
    private TopNavigationBar mTnb;
    private Button mButtonSubmit;
    private JSONObject entity;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_approve);
        initView();
        initData();
        setListener();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData1();
    }

    private void initData1() {
        mapNot = new LinkedHashMap<>();
        mapNot.put("是", 1);
        mapNot.put("否", 0);

        String json = LoginTokenUtils.getJson();
        FormBody formbody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.INFO_CAR_INFO, formbody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                    try {
                      //  Log.d("TAG", "车辆信息：" + result);
                        JSONObject object = new JSONObject(result);
                        String returnCode = object.getString("returnCode");
                        String returnMsg = object.getString("returnMsg");
                        if ("000000".equals(returnCode)) {
                            entity = object.getJSONObject("entity");
                            String registrationDate = entity.getString("registrationDate");
                            String mileage = entity.getString("mileage");
                            id = entity.getString("id");
                            String brand = entity.getString("brand");
                            String license = entity.getString("license");
                            int mortgageFlag = entity.getInt("mortgageFlag");
                            mEditTextMark.setText(license);
                            mEditTextType.setText(brand);

                            mEditTextKm.setText(String.valueOf(Integer.parseInt(mileage) / 100));
                            mTextViewTime.setText(registrationDate);
                            for (Map.Entry<String, Integer> entry : mapNot.entrySet()) {
                                int value = entry.getValue().intValue();
                                if (value == mortgageFlag) {
                                    String key = entry.getKey();
                                    mTextViewIf.setText(key);
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

    private void initData() {
        mapNot = new LinkedHashMap<>();
        mapNot.put("是", 1);
        mapNot.put("否", 0);

        String json = LoginTokenUtils.getJson();
        FormBody formbody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.INFO_CAR_INFO, formbody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                    try {
                      //  Log.d("TAG", "车辆信息：" + result);
                        JSONObject object = new JSONObject(result);
                        String returnCode = object.getString("returnCode");
                        String returnMsg = object.getString("returnMsg");
                        if ("000000".equals(returnCode)) {
                            entity = object.getJSONObject("entity");
                            String registrationDate = entity.getString("registrationDate");
                            String mileage = entity.getString("mileage");
                            id = entity.getString("id");
                            String brand = entity.getString("brand");
                            String license = entity.getString("license");
                            int mortgageFlag = entity.getInt("mortgageFlag");
                            mEditTextMark.setText(license);
                            mEditTextType.setText(brand);

                            mEditTextKm.setText(String.valueOf(Integer.parseInt(mileage) / 100));
                            mTextViewTime.setText(registrationDate);
                            for (Map.Entry<String, Integer> entry : mapNot.entrySet()) {
                                int value = entry.getValue().intValue();
                                if (value == mortgageFlag) {
                                    String key = entry.getKey();
                                    mTextViewIf.setText(key);
                                }
                            }
                        } else if ("0017".equals(returnCode)) {
                            ToastUtil.show(CarApproveActivity.this, returnMsg);
                            startActivity(new Intent(CarApproveActivity.this, LoginActivity.class));
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
                            mButtonSubmit.setVisibility(View.GONE);
                            mEditTextKm.setCursorVisible(false);
                            mEditTextType.setCursorVisible(false);
                            mEditTextMark.setCursorVisible(false);
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
        mButtonSubmit.setOnClickListener(this);
        mEditTextKm.addTextChangedListener(this);
        mEditTextMark.addTextChangedListener(this);
        mTextViewTime.addTextChangedListener(this);
        mEditTextType.addTextChangedListener(this);
        mTextViewIf.addTextChangedListener(this);
        mTextViewTime.setOnClickListener(this);
        mTextViewIf.setOnClickListener(this);
        mTnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mButtonSubmit = (Button) findViewById(R.id.bt_car_submit);
        mTextViewIf = (TextView) findViewById(R.id.tv_car_whether);
        mTextViewTime = (TextView) findViewById(R.id.tv_car_user_time);
        mEditTextType = (EditText) findViewById(R.id.et_car_types);
        mEditTextMark = (EditText) findViewById(R.id.et_car_mark);
        mEditTextKm = (EditText) findViewById(R.id.et_car_km);
        mTnb = (TopNavigationBar) findViewById(R.id.tnb_car);
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.tv_car_whether:
                    whether();
                    break;
                case R.id.tv_car_user_time:
                    userTime();
                    break;
                case R.id.bt_car_submit:
                    carSubmit();
                    break;
                default:
                    break;
            }
        }
    }

    //提交车辆品牌
    private void carSubmit() {
        String type = mEditTextType.getText().toString().trim();
        if (!type.matches("^(?!_)(?!.*?_$)[a-zA-Z0-9_\\u4e00-\\u9fa5]+$")) {
            ToastUtil.show(this, "车辆品牌输入有误");
            return;
        }
        String mark = mEditTextMark.getText().toString().trim();
       /* if (!mark.matches("^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$")) {
            ToastUtil.show(this, "车牌号输入有误");
            return;
        }*/
        String km = mEditTextKm.getText().toString().trim();
        if (!km.matches("[a-zA-Z0-9]+")) {
            ToastUtil.show(this, "公里数有误");
            return;
        }
        //客户端来源、用户令牌、用户手机号、车辆信息编号、车辆品牌、车牌号、公里数、是否有抵押、上牌时间
        InfoCarBean car = new InfoCarBean();
        car.setClientType("2");
        car.setToken(MyApplications.getToken());
        if (entity == null) {
            car.setId("");
        } else {
            car.setId(id);
        }
        car.setBrand(mEditTextType.getText().toString().trim());
        car.setLicense(mEditTextMark.getText().toString().trim());
        int kms = Integer.parseInt(mEditTextKm.getText().toString().trim()) * 100;
        car.setMileage(String.valueOf(kms));
        for (Integer key : mapNot.values()) {
            if (mapNot.get(mTextViewIf.getText().toString()).equals(key)) {
                car.setMortgageFlag(String.valueOf(key));
            }
        }
        car.setRegistrationDate(mTextViewTime.getText().toString());
        Gson gson = new Gson();
        String json = gson.toJson(car);
      //  Log.d(TAG,"车辆车辆提交"+json);
    FormBody formBody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.INFO_CAR, formBody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (request != null) {
                    try {
                        JSONObject json = new JSONObject(result);
                        String returnCode = json.getString("returnCode");
                        String returnMsg = json.getString("returnMsg");
                        if ("0017".equals(returnCode)) {
                            startActivity(new Intent(CarApproveActivity.this, LoginActivity.class));
                            finish();
                        } else if ("000000".equals(returnCode)) {
                            ToastUtil.show(CarApproveActivity.this, returnMsg);
                            finish();
                        }else if("0002".equals(returnCode)){
                            ToastUtil.show(CarApproveActivity.this, returnMsg);
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    //过户时间
    private void userTime() {
        Calendar calender = Calendar.getInstance();
        DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mTextViewTime.setText(year + "-" + (month + 1) + "-" + dayOfMonth);

            }
        }, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH));
        dpd.getDatePicker().setMaxDate(System.currentTimeMillis());
        dpd.show();
    }

    //是否抵押
    private void whether() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(R.array.whether, position, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                position = which;
                final String[] aryShop = getResources().getStringArray(R.array.whether);
                mapNot.put(aryShop[which], which);
                mTextViewIf.setText(aryShop[which]);
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
        if (mEditTextMark.getText().toString().toString().trim().length() > 1) {
            if (mEditTextKm.getText().toString().trim().length() > 1) {
                if (mTextViewIf.getText().toString().length() > 0) {
                    if (mTextViewTime.getText().toString().length() > 0) {
                        if (mEditTextType.getText().toString().length() > 1) {
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
        } else {
            mButtonSubmit.setEnabled(false);
        }
    }
}
