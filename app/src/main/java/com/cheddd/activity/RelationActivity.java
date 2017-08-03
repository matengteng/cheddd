package com.cheddd.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cheddd.R;
import com.cheddd.application.MyApplications;
import com.cheddd.base.MyBaseActivity;
import com.cheddd.bean.InfoRelation;
import com.cheddd.bean.RelationsBean;
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
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;

/*
联系人界面
*/
public class RelationActivity extends MyBaseActivity implements View.OnClickListener, TextWatcher {

    //联系人姓名、电话、关系
    private final static String
            CONTACT_NAME = "CONTACT_NAME",
            CONTACT_PHONE_NUM = "CONTACT_PHONE_NUM",
            CONTACT_RELATION = "CONTACT_RELATION";

    private static String TAG = RegisterActivity.class.getSimpleName();
    private Map<String, Integer> map = new LinkedHashMap<>();
    private ImageView mImageView, mImageViewPhone;//打电话、紧急联系人打电话
    private LinearLayout mLayout, mLayout2, mLayout3;
    //增加、提交
    private Button mButtonAdd, mButtonSubmit;
    private TopNavigationBar mTnb;
    //关系
    private TextView mTextViewRelation;
    //直系 姓名，手机号码；紧急姓名，手机号码
    private EditText mEditTextUrgencyPhone, mEditTextrgencyName;

    private int position;
    private int mCount = 0;

    private boolean textIsEmpty = false;
    private String id;
    private JSONArray rows;
    private StringBuffer mStringBufferId;
    private JSONObject entity;
    private String urgentId;

    private Map<String, Integer> mapRelation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relation);
        initView();
        initData();
        setData();
        setListener();
        findViewAllValidateEmpty(findViewById(android.R.id.content));
       // Log.d(TAG, "findViewAllValidateEmpty():" + textIsEmpty);
        mLayout.addView(addView());
    }

    private void initData() {
        //0.配偶1父亲.2母亲.3子女4亲属5同事6朋友
        mapRelation = new LinkedHashMap<>();
        mapRelation.put("配偶", 0);
        mapRelation.put("父亲", 1);
        mapRelation.put("母亲", 2);
        mapRelation.put("子女", 3);
        mapRelation.put("亲属", 4);
        mapRelation.put("同事", 5);
        mapRelation.put("朋友", 6);
        final String json = LoginTokenUtils.getJson();
        FormBody formBody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.INFO_RELATION_INFO, formBody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                Log.d(TAG, e.getMessage());
            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                    mStringBufferId = new StringBuffer();
                    Log.d(TAG, "获取联系人：" + result);
                    try {
                        JSONObject object = new JSONObject(result);
                        String returnCode = object.getString("returnCode");
                        String returnMsg = object.getString("returnMsg");

                        if ("000000".equals(returnCode)) {
                            try {
                                entity = object.getJSONObject("entity");
                            } catch (Exception e) {
                                return;
                            }

                            urgentId = entity.getString("urgentId");
                            String urgentTelNo = entity.getString("urgentTelNo");
                            String urgentContractName = entity.getString("urgentContractName");
                            mEditTextUrgencyPhone.setText(urgentTelNo);
                            mEditTextrgencyName.setText(urgentContractName);
                            rows = object.getJSONArray("rows");
                            if (rows.length() > 0) {
                                mLayout.removeAllViews();
                            }
                            for (int i = 0; i < rows.length(); i++) {
                                JSONObject jsonObject = rows.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String contractName = jsonObject.getString("contractName");
                                int relation = jsonObject.getInt("relation");
                                String telNo = jsonObject.getString("telNo");
                                mStringBufferId.append(id).append(",");
                                View view = addView();
                                TextView viewById = (TextView) view.findViewById(R.id.et_relation_name);
                                TextView viewById1 = (TextView) view.findViewById(R.id.et_relation_phone);
                                TextView viewById2 = (TextView) view.findViewById(R.id.tv_relation_relation);
                                viewById.setText(contractName);
                                viewById1.setText(telNo);
                                for (Map.Entry<String, Integer> entry : mapRelation.entrySet()) {
                                    Integer value = entry.getValue();
                                    if (value == relation) {
                                        String key = entry.getKey();
                                        viewById2.setText(key);
                                    }
                                }

                                mLayout.addView(view);
                            }
                            if (rows.length() > 0) {
                                mStringBufferId.delete(mStringBufferId.length() - 1, mStringBufferId.length());
                            } else {
                                return;
                            }
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
                            mButtonAdd.setVisibility(View.GONE);

                        } else {
                            mButtonSubmit.setVisibility(View.VISIBLE);
                            mButtonAdd.setVisibility(View.VISIBLE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    private void setData() {

    }

    private void setListener() {

        mButtonSubmit.setOnClickListener(this);
        mImageViewPhone.setOnClickListener(this);
        mButtonAdd.setOnClickListener(this);
        mButtonSubmit.setOnClickListener(this);
        mTnb.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v != null) {
                    finish();
                }
            }
        });
    }

    private void initView() {
        mTnb = (TopNavigationBar) findViewById(R.id.tnb_relation);
        mImageViewPhone = (ImageView) findViewById(R.id.iv_relation_urgency_phone);
        mEditTextrgencyName = (EditText) findViewById(R.id.et_relation_urgency_name);
        mEditTextUrgencyPhone = (EditText) findViewById(R.id.et_relation_regency_phone);
        mLayout = (LinearLayout) findViewById(R.id.ll_relation_container1);
        mButtonAdd = (Button) findViewById(R.id.bt_relation_add);
        mButtonSubmit = (Button) findViewById(R.id.bt_relation_submit);
        mButtonSubmit.setEnabled(true);
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.iv_relation_urgency_phone:
                    cellPhone();
                    break;
                case R.id.bt_relation_add:
                    mCount++;
                    mLayout.addView(addView());
                    if (mCount == 2) {
                        mButtonAdd.setVisibility(View.GONE);
                    }
                    break;
                case R.id.tv_relation_relation:
                    relation(v);
                    break;
                case R.id.bt_relation_submit:
                    submit();
                    break;
            }
        }
    }

    //拨打电话
    private void cellPhone() {
        if (ContextCompat.checkSelfPermission(RelationActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(RelationActivity.this, Manifest.permission.CALL_PHONE)) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            } else {
                ActivityCompat.requestPermissions(RelationActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 100);
            }
        } else {
            CallPhone();
        }

    }


    private void CallPhone() {
        String number = mEditTextUrgencyPhone.getText().toString().trim();
        if (TextUtils.isEmpty(number)) {
            Toast.makeText(RelationActivity.this, "号码不能为空！", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            Uri data = Uri.parse("tel:" + number);
            intent.setData(data);
            startActivity(intent);
        }
    }

    // 处理权限申请的回调
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 100: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 授权成功，继续打电话
                    CallPhone();
                    //cellPhoneurgency();
                } else {
                    // 授权失败！
                    Toast.makeText(this, "授权失败！", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }


    //点击提交时
    private void submit() {

        textIsEmpty = true;
        findViewAllValidateEmpty(findViewById(android.R.id.content));
        if (!textIsEmpty) {
            ToastUtil.show(this, "请将信息填写完整");
            return;
        }
        if (stringBuffer.length() > 0)
            stringBuffer.delete(0, stringBuffer.length());
        String contact_name = getContactInfo(mLayout, CONTACT_NAME);
        contact_name = contact_name.substring(0, contact_name.length() - 1);
        if (stringBuffer.length() > 0)
            stringBuffer.delete(0, stringBuffer.length());
        String contact_phone_num = getContactInfo(mLayout, CONTACT_PHONE_NUM);
        contact_phone_num = contact_phone_num.substring(0, contact_phone_num.length() - 1);

        Log.d(TAG, contact_phone_num);
        if (stringBuffer.length() > 0)
            stringBuffer.delete(0, stringBuffer.length());
        String contact_relation = getContactInfo(mLayout, CONTACT_RELATION);
        contact_relation = contact_relation.substring(0, contact_relation.length() - 1);
//        String name = mEditTextName.getText().toString().trim();
//        //用户名
        String nameRegular = "[\u4E00-\u9FA5]{2,5}(?:·[\u4E00-\u9FA5]{2,5})*";
//        if (!name.matches(nameRegular)) {
//            ToastUtil.show(this, "姓名格式错误");
//            return;
//        }
//        String phone = mEditTextPhone.getText().toString().trim();
//        if (!phone.matches("1\\d{10}")) {
//            ToastUtil.show(this, "手机格式错误");
//            return;
//        }
        String gencyName = mEditTextrgencyName.getText().toString().trim();
        if (!gencyName.matches(nameRegular)) {
            ToastUtil.show(this, "姓名格式错误");
            return;
        }
        String urgencyphone = mEditTextUrgencyPhone.getText().toString().trim();
        if (!urgencyphone.matches("1\\d{10}")) {
            ToastUtil.show(this, "手机格式错误");
            return;
        }
        final InfoRelation relation = new InfoRelation();
        //客户端来源、用户令牌、直系亲属人编号、直系亲属姓名、直系亲属手机号、直系亲属关系、紧急联系人编号、紧急联系人姓名、紧急联系人
        relation.setClientType("2");
        relation.setToken(MyApplications.getToken());
        if (rows != null && rows.length() > 0) {
            relation.setId(new String(mStringBufferId + "," + ""));
        } else if (rows == null) {
            relation.setId("");
        }
        relation.setContractName(contact_name);
        relation.setTelNo(contact_phone_num);
        if (entity == null) {
            relation.setUrgentId("");
        } else {
            relation.setUrgentId(urgentId);
        }
        relation.setUrgentContractName(mEditTextrgencyName.getText().toString().trim());
        relation.setUrgentTelNo(mEditTextUrgencyPhone.getText().toString().trim());
       /* for (Integer key : map.values()) {
            //  relation.setRelation(new Integer(key).toString());
            Log.d(TAG, "key" + key);

        }
        for (String s : map.keySet()) {
            Log.i(TAG, s);
        }*/
        StringBuffer stringBuffer = new StringBuffer();
        String[] relations = contact_relation.split(",");
        for (int i = 0; i < relations.length; i++) {
            //  Log.i(TAG, "+++" + map.get(relations[i]));
            if (i == relations.length - 1) {
                stringBuffer.append(map.get(relations[i]));
            } else {
                stringBuffer.append(map.get(relations[i]) + ",");
            }
        }
        relation.setRelation(new String(stringBuffer));
        Gson gson = new Gson();
        String json = gson.toJson(relation);
        Log.d(TAG, json);
        FormBody formbody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.INFO_RELATION, formbody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                    try {
                        Log.d(TAG, "提交联系人:" + result);
                        JSONObject object = new JSONObject(result);
                        String returnCode = object.getString("returnCode");
                        String returnMsg = object.getString("returnMsg");
                        if ("000000".equals(returnCode)) {
                            ToastUtil.show(RelationActivity.this, returnMsg);
                            finish();
                        } else if (returnCode.equals("0042")) {
                            startActivity(new Intent(RelationActivity.this, CarApproveActivity.class));
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

    private StringBuffer stringBuffer = new StringBuffer();

    private String getContactInfo(View view, String tag) {
        if (view instanceof ViewGroup) {
            ViewGroup container = (ViewGroup) view;
            for (int i = 0, n = container.getChildCount(); i < n; i++) {
                View child = container.getChildAt(i);
                if (child instanceof TextView && child.getTag() != null && child.getTag().toString().equals(tag)) {
                    stringBuffer.append((((TextView) child).getText()).toString());
                    stringBuffer.append(",");
                }
                getContactInfo(child, tag);
            }
        }
        return new String(stringBuffer);
    }


    private void findViewAllValidateEmpty(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup container = (ViewGroup) view;
            for (int i = 0, n = container.getChildCount(); i < n; i++) {
                View child = container.getChildAt(i);
                if (child instanceof TextView) {
                    if (((TextView) child).getText() == null || TextUtils.isEmpty(((TextView) child).getText())) {
                        textIsEmpty = false;
                    }
                }
                if (textIsEmpty) findViewAllValidateEmpty(child);
            }
        }
    }


    private void relation(final View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(R.array.relation, position, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                position = which;
                final String[] aryShop = getResources().getStringArray(R.array.relation);
                map.put(aryShop[which], which);
                ((TextView) view).setText(aryShop[which]);
                dialog.dismiss();
            }
        }).create().show();

    }


    //增加view的视图
    private View addView() {
        View view = LayoutInflater.from(this).inflate(R.layout.relation_emergency, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_relation_cellPhone);
        final EditText editTextPhone = (EditText) view.findViewById(R.id.et_relation_phone);
        final TextView textViewRelation = (TextView) view.findViewById(R.id.tv_relation_relation);
        textViewRelation.setOnClickListener(this);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cellPhoneurgency(editTextPhone.getText().toString());
            }
        });
        return view;
    }


    private void cellPhoneurgency(String number) {
        if (TextUtils.isEmpty(number)) {
            Toast.makeText(RelationActivity.this, "号码不能为空！", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            Uri data = Uri.parse("tel:" + number);
            intent.setData(data);
            startActivity(intent);
        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
