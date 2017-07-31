package com.cheddd.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.cheddd.R;
import com.cheddd.application.MyApplications;
import com.cheddd.base.MyBaseActivity;
import com.cheddd.bean.ContactGroup;
import com.cheddd.bean.InfoPhoneBean;
import com.cheddd.bean.SmsBean;
import com.cheddd.config.NetConfig;
import com.cheddd.fragment.NetProgressDialog;
import com.cheddd.utils.ContactManager;
import com.cheddd.utils.LoginTokenUtils;
import com.cheddd.utils.OkhttpUtils;
import com.cheddd.utils.ToastUtil;
import com.cheddd.view.TopNavigationBar;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Request;

/*
手机认证
*/
public class PhoneApproveActivity extends MyBaseActivity implements View.OnClickListener, TextWatcher, CompoundButton.OnCheckedChangeListener {

    private static String TAG = PhoneApproveActivity.class.getSimpleName();
    private TopNavigationBar mTnb;
    private EditText mEditTextPhone, mEditTextPassword;//手机号码，服务密码
    private RelativeLayout mRelativeAddress, mRelativeLocation, mRelativeDuanxin;//通讯录，位置，短信
    private CheckBox mCheckBoxAgree; //是否同意
    private TextView mTextViewPassword;//忘记密码
    private Button mButtonSubmit;
    private TextView mTextViewMotion;
    private SharedPreferences mSpf;
    private StringBuilder smsBuilder;
    private List<SmsBean> mSmsBeen;
    private TextView mAddressBook, mLocation, mSms;
    private Button mButtonAddressBook, mButtonLocation, mButtonSms;

    private LocationClient mLocationClient = null;
    private BDLocationListener myListener = new MyLocationListener();
    private String toJson;
    private String addr;
    private String duanxin;
    private int isContacts;
    private int isLocation;
    private int isMessgae;
    private SmsBean smsBean;
    private List<SmsBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_approve);
        initView();
        initData();
        setListener();

    }


    private void initData() {
        final String json = LoginTokenUtils.getJson();
        final FormBody formbody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.INFO_PHONE_INFO, formbody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                    try {
                        Log.d(TAG, "请求手机认证：" + result);
                        Log.d(TAG, NetConfig.INFO_PHONE_INFO + "content" + "=" + json);
                        JSONObject object = new JSONObject(result);
                        String returnCode = object.getString("returnCode");
                        String returnMsg = object.getString("returnMsg");
                        if ("000000".equals(returnCode)) {
                            JSONObject entity = object.getJSONObject("entity");
                            String telNo = entity.getString("telNo");
                            String spPassword = entity.getString("spPassword");
                            isContacts = entity.getInt("isContacts");
                            isLocation = entity.getInt("isLocation");
                            isMessgae = entity.getInt("isMessgae");
                            mEditTextPassword.setText(spPassword);
                            mEditTextPhone.setText(telNo);
                            if (isContacts == 0) {
                                mButtonAddressBook.setEnabled(true);
                            } else {
                                mButtonAddressBook.setEnabled(false);
                            }
                            if (isLocation == 0) {
                                mButtonLocation.setEnabled(true);
                            } else {
                                mButtonLocation.setEnabled(false);
                            }
                            if (isMessgae == 0) {
                                mButtonSms.setEnabled(true);
                            } else {
                                mButtonSms.setEnabled(false);
                            }
                        } else if ("0017".equals(returnCode)) {
                            ToastUtil.show(PhoneApproveActivity.this, returnMsg);
                            startActivity(new Intent(PhoneApproveActivity.this, LoginActivity.class));
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
                            mButtonSubmit.setEnabled(false);
                            mEditTextPhone.setCursorVisible(false);
                            mEditTextPassword.setCursorVisible(false);
                        } else {
                            mButtonSubmit.setEnabled(true);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }

    private void setListener() {
        mEditTextPhone.addTextChangedListener(this);
        mEditTextPhone.setInputType(InputType.TYPE_CLASS_NUMBER);
        mEditTextPassword.addTextChangedListener(this);
        mCheckBoxAgree.addTextChangedListener(this);
        mRelativeLocation.setOnClickListener(this);
        mRelativeDuanxin.setOnClickListener(this);
        mRelativeAddress.setOnClickListener(this);
        mButtonSubmit.setOnClickListener(this);
        mTextViewMotion.setOnClickListener(this);
        mCheckBoxAgree.setOnCheckedChangeListener(this);
        mTnb.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v != null) {
                    finish();
                }
            }
        });
    }

    private void initLocation() {

        //声明LocationClient类
        mLocationClient = new LocationClient(getApplicationContext());
        //注册监听函数
        mLocationClient.registerLocationListener(myListener);

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系

        int span = 10000;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要

        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps

        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果

        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”

        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集

        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

        mLocationClient.setLocOption(option);


        mLocationClient.start();
    }

    private void motain() {
        Intent intent = new Intent(PhoneApproveActivity.this, MotionActivity.class);
        intent.putExtra("url", "http://baidu.com");
        startActivity(intent);
        finish();
    }

    private void initView() {
        mButtonAddressBook = (Button) findViewById(R.id.bt_phoneApprove_address);
        mButtonLocation = (Button) findViewById(R.id.bt_phoneApprove_location);
        mButtonSms = (Button) findViewById(R.id.bt_phoneApprove_duanxin);
        mAddressBook = (TextView) findViewById(R.id.tv_phoneapprove_address);
        mLocation = (TextView) findViewById(R.id.tv_phoneapprove_map);
        mSms = (TextView) findViewById(R.id.tv_phoneapprove_dunaxin);
        mTnb = (TopNavigationBar) findViewById(R.id.tnb_phoneApprove);
        mEditTextPhone = (EditText) findViewById(R.id.et_phoneApprove_phone);
        mEditTextPassword = (EditText) findViewById(R.id.et_phoneApprove_password);
        mRelativeAddress = (RelativeLayout) findViewById(R.id.rl_phoneApprove_address);
        mRelativeDuanxin = (RelativeLayout) findViewById(R.id.rl_phoneApprove_duanxin);
        mRelativeLocation = (RelativeLayout) findViewById(R.id.rl_phoneApprove_location);
        mCheckBoxAgree = (CheckBox) findViewById(R.id.cb_phone_apporve);
        mButtonSubmit = (Button) findViewById(R.id.bt_phoneApprove_submit);
        mTextViewMotion = (TextView) findViewById(R.id.tv_phoneApprove_motain);
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.rl_phoneApprove_location:
                    if (Build.VERSION.SDK_INT >= 23) {
                        initLocation1();
                    } else {
                        initLocation();
                    }
                    initLocation1();
                    if (mLocation.getText().toString().length() > 0) {
                        mButtonLocation.setEnabled(true);
                    } else {
                        mButtonLocation.setEnabled(false);
                    }
                    break;
                case R.id.rl_phoneApprove_duanxin:
                    duanxin();
                    if (mSms.getText().toString().length() > 0) {
                        mButtonSms.setEnabled(true);
                    } else {
                        mButtonSms.setEnabled(false);
                    }
                    break;
                case R.id.rl_phoneApprove_address:
                    address();
                    if (mAddressBook.getText().toString().length() > 0) {
                        mButtonAddressBook.setEnabled(true);
                    } else {
                        mButtonAddressBook.setEnabled(false);
                    }
                    break;
                case R.id.bt_phoneApprove_submit:
                    phone();
                    break;
                case R.id.tv_phoneApprove_motain:
                    mSpf = getSharedPreferences("PhoneApproveActivity", MODE_PRIVATE);
                    boolean config = mSpf.getBoolean("config", true);
                    if (config) {
                        mCheckBoxAgree.setChecked(true);
                        motain();
                    } else {
                        mCheckBoxAgree.setChecked(false);
                        ToastUtil.show(this, "请勾选");
                    }
                    break;
            }
        }
    }

    private void initLocation1() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
                ) {
            initLocation();
        } else {
            ActivityCompat.requestPermissions(PhoneApproveActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE}, 113);
            // Toast.makeText(getApplicationContext(), "没有权限,请手动开启定位权限", Toast.LENGTH_SHORT).show();
        }
    }

    //短信授权
    private void duanxin() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED) {
            getSmsInPhone();
            Gson gson = new Gson();
            duanxin = gson.toJson(mList);
            Log.d(TAG, duanxin);
            mSms.setText(duanxin);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, 112);
        }
        //  Log.d(TAG, duanxin);
    }

    public String getSmsInPhone() {
        final String SMS_URI_ALL = "content://sms/";
        final String SMS_URI_INBOX = "content://sms/inbox";
        final String SMS_URI_SEND = "content://sms/sent";
        final String SMS_URI_DRAFT = "content://sms/draft";

        //smsBuilder = new StringBuilder();
        mList = new ArrayList<>();
        try {
            ContentResolver cr = getContentResolver();
            String[] projection = new String[]{"_id", "address", "person",
                    "body", "date", "type"};
            Uri uri = Uri.parse(SMS_URI_ALL);
            Cursor cur = cr.query(uri, projection, null, null, "date desc");

            while (cur.moveToNext()) {
                String name;
                String phoneNumber;
                String smsbody;
                String date;
                String type;

                int nameColumn = cur.getColumnIndex("person");
                int phoneNumberColumn = cur.getColumnIndex("address");
                int smsbodyColumn = cur.getColumnIndex("body");
                int dateColumn = cur.getColumnIndex("date");
                int typeColumn = cur.getColumnIndex("type");
                name = cur.getString(nameColumn);
                phoneNumber = cur.getString(phoneNumberColumn);
                smsbody = cur.getString(smsbodyColumn);

                SimpleDateFormat dateFormat = new SimpleDateFormat(
                        "yyyy-MM-dd hh:mm:ss");
                Date d = new Date(Long.parseLong(cur.getString(dateColumn)));
                date = dateFormat.format(d);

                int typeId = cur.getInt(typeColumn);
                if (typeId == 1) {
                    type = "接收";
                } else if (typeId == 2) {
                    type = "发送";
                } else {
                    type = "";
                }
                smsBean = new SmsBean();
                smsBean.setName(name);
                smsBean.setPhoneNumber(phoneNumber);
                smsBean.setDate(date);
                smsBean.setType(type);
                smsBean.setSmsbody(smsbody);
                mList.add(smsBean);
            }
            // smsBuilder.append("getSmsInPhone has executed!");
        } catch (SQLiteException ex) {
            // Log.d("SQLiteException in getSmsInPhone", ex.getMessage());
        }
        return mList.toString();
    }

    //通讯录授权
    private void address() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS)
                == PackageManager.PERMISSION_GRANTED) {
            List<ContactManager.Contact> contacts = ContactManager.getContacts(this);
            Gson gson = new Gson();
            toJson = gson.toJson(contacts);
            Log.d(TAG, "contacts+++++++++++++++++++++++++++" + toJson);
            mAddressBook.setText(toJson);
        } else {
            //第一步申请运行时权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 111);
        }
        ////////////////////////////////////////////////////////
       /* if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) this,
                    new String[]{android.Manifest.permission.READ_CONTACTS},
                    111);
        } else {
            List<ContactGroup> contacts = ContactManager.getInstance(this).getContacts();
            Gson gson = new Gson();
            toJson = gson.toJson(contacts);
            mAddressBook.setText(toJson);
            Log.d(TAG, toJson);
        }*/
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 111 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            List<ContactManager.Contact> contacts = ContactManager.getContacts(this);
            Gson gson = new Gson();
            toJson = gson.toJson(contacts);
            mAddressBook.setText(toJson);
        }
        if (requestCode == 112 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getSmsInPhone();
            Gson gson = new Gson();
            duanxin = gson.toJson(smsBuilder);
            mSms.setText(duanxin);
        }
        if (requestCode == 113 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initLocation();
        } else {
            Toast.makeText(getApplicationContext(), "获取位置权限失败，请手动开启", Toast.LENGTH_SHORT).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //点击提交
    private void phone() {
        if (!mEditTextPhone.getText().toString().trim().matches("1\\d{10}")) {
            ToastUtil.show(this, "手机格式错误");
            return;
        }
        if (!mEditTextPassword.getText().toString().trim().matches("^[A-Za-z0-9]+$")) {
            ToastUtil.show(this, "服务密码错误");
        }
        if (mAddressBook.getText().toString().length() < 2) {
            ToastUtil.show(this, "请通讯录授权");
            return;
        }
        if (mLocation.getText().toString().length() < 2) {
            ToastUtil.show(this, "请位置授权");
            return;
        }
        if (mSms.getText().toString().length() < 2) {
            ToastUtil.show(this, "请短信授权");
            return;
        }
        InfoPhoneBean phoneBean = new InfoPhoneBean();
        String token = MyApplications.getToken();
        phoneBean.setClientType("2");
        phoneBean.setToken(token);
        phoneBean.setContacts(toJson);
        phoneBean.setTelNo(mEditTextPhone.getText().toString().trim());
        phoneBean.setSpPassword(mEditTextPassword.getText().toString().trim());
        phoneBean.setLocation(addr);
        phoneBean.setMessgae(duanxin);
        if (mAddressBook.getText().toString().length() > 1) {
            phoneBean.setIsContacts(0);
        } else {
            phoneBean.setIsContacts(1);
        }
        if (mLocation.getText().toString().length() > 0) {
            phoneBean.setIsLocation(0);
        } else {
            phoneBean.setIsLocation(1);
        }
        if (mSms.getText().toString().length() > 0) {
            phoneBean.setIsMessgae(0);
        } else {
            phoneBean.setIsMessgae(1);
        }
        Gson gson = new Gson();
        String json = gson.toJson(phoneBean);
        FormBody formBody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.INFO_PHONE, formBody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                    Log.d(TAG, "result:" + result);
                    try {
                        JSONObject object = new JSONObject(result);
                        String returnCode = object.getString("returnCode");
                        String returnMsg = object.getString("returnMsg");
                        if ("000000".equals(returnCode)) {
                            ToastUtil.show(PhoneApproveActivity.this, returnMsg);
                            finish();
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
        if (mEditTextPhone.getText().toString().length() == 11) {
            if (mEditTextPassword.getText().toString().length() > 2) {
                mButtonSubmit.setEnabled(true);
            } else {
                mButtonSubmit.setEnabled(false);
            }
        } else {
            mButtonSubmit.setEnabled(false);
        }

    }

    //checkbox的点击事件
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            if (mEditTextPhone.getText().toString().length() == 11) {
                if (mEditTextPassword.getText().toString().length() > 2) {
                    mButtonSubmit.setEnabled(true);
                }
            }
            mButtonSubmit.setClickable(isChecked);
            mTextViewMotion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    motain();
                }
            });
        } else {
            mButtonSubmit.setEnabled(false);
        }
    }

    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            Log.d(TAG, "===================" + location.getLocType());
            if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                // Log.d(TAG, "==================="+location.getLocType());
                //获取地址信息
                addr = location.getAddrStr();
                Log.d(TAG, "获取地址信息" + addr);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.bt_phoneApprove_location).setEnabled(true);
                        mLocation.setText(addr);
                    }
                });
            }

            mLocationClient.unRegisterLocationListener(myListener);
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }
}
