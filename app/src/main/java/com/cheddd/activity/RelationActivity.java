package com.cheddd.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.cheddd.bean.AddContactParams;
import com.cheddd.bean.ConstantBean;
import com.cheddd.config.NetConfig;
import com.cheddd.fragment.NetProgressDialog;
import com.cheddd.utils.LoginTokenUtils;
import com.cheddd.utils.OkhttpUtils;
import com.cheddd.utils.ToastUtil;
import com.cheddd.view.TopNavigationBar;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

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
    //增加、提交
    private Button mButtonAdd, mButtonSubmit;
    private TopNavigationBar mTnb;
    //关系
    private TextView mTextViewRelation;
    //直系 姓名，手机号码；紧急姓名，手机号码
    private EditText mEditTextUrgencyPhone, mEditTextrgencyName;

    private Map<Integer, String> mapRelation;
    private LinearLayout ll_relation_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relation);


        //0.配偶1父亲.2母亲.3子女4亲属5同事6朋友
        mapRelation = new LinkedHashMap<>();
        mapRelation.put(0, "配偶");
        mapRelation.put(1, "父亲");
        mapRelation.put(2, "母亲");
        mapRelation.put(3, "子女");
        mapRelation.put(4, "亲属");
        mapRelation.put(5, "同事");
        mapRelation.put(6, "朋友");


        initView();
        initData();
        setListener();
    }

    private void showRelationDialog(final TextView textView, int position) {
        final String[] strings = mapRelation.values().toArray(new String[]{});

        new AlertDialog.Builder(this).setSingleChoiceItems(strings, position, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                textView.setText(strings[which]);
                textView.setTag(which);
                dialog.dismiss();
            }
        }).show();
    }

    private void initData() {

        final NetProgressDialog netProgressDialog = new NetProgressDialog();
        netProgressDialog.show(getSupportFragmentManager(), "");

        final String json = LoginTokenUtils.getJson();
        FormBody formBody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.INFO_RELATION_INFO, formBody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                Log.d(TAG, e.getMessage());
                netProgressDialog.dismissAllowingStateLoss();
            }

            @Override
            public void onSuccess(Request request, String result) {

                netProgressDialog.dismissAllowingStateLoss();

                Log.d(TAG, "res:" + result);
                try {
                    ConstantBean constantBean = new Gson().fromJson(result, ConstantBean.class);
                    if (constantBean.getRows().size() > 0) {
                        ll_relation_container.removeAllViews();
                    }

                    for (ConstantBean.RowsBean rowsBean : constantBean.getRows()) {
                        View view = LayoutInflater.from(RelationActivity.this).inflate(R.layout.relation_emergency, null, false);

                        TextView tv_relation_relation = (TextView) view.findViewById(R.id.tv_relation_relation);
                        EditText et_relation_name = (EditText) view.findViewById(R.id.et_relation_name);
                        final EditText et_relation_phone = (EditText) view.findViewById(R.id.et_relation_phone);
                        ImageView imageView = (ImageView) view.findViewById(R.id.iv_relation_cellPhone);
                        et_relation_name.setText(rowsBean.getContractName());
                        et_relation_phone.setText(rowsBean.getTelNo());
                        tv_relation_relation.setText(mapRelation.get(rowsBean.getRelation()));
                        tv_relation_relation.setTag(rowsBean.getRelation());
                        view.setTag(rowsBean.getId());
                        tv_relation_relation.setOnClickListener(RelationActivity.this);
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CallPhone(et_relation_phone);
                            }
                        });
                        ll_relation_container.addView(view);
                    }

                    if (constantBean.getRows().size() == 3) {
                        mButtonAdd.setVisibility(View.GONE);
                    }

                    if (constantBean.getEntity() != null) {
                        mEditTextrgencyName.setText(constantBean.getEntity().getUrgentContractName());
                        mEditTextUrgencyPhone.setText(constantBean.getEntity().getUrgentTelNo());
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }

            }
        });
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
        ll_relation_container = (LinearLayout) findViewById(R.id.ll_relation_container1);
        mTnb = (TopNavigationBar) findViewById(R.id.tnb_relation);
        mImageViewPhone = (ImageView) findViewById(R.id.iv_relation_urgency_phone);
        mEditTextrgencyName = (EditText) findViewById(R.id.et_relation_urgency_name);
        mEditTextUrgencyPhone = (EditText) findViewById(R.id.et_relation_regency_phone);
        mButtonAdd = (Button) findViewById(R.id.bt_relation_add);
        mButtonSubmit = (Button) findViewById(R.id.bt_relation_submit);
        mButtonSubmit.setEnabled(true);

        View view = LayoutInflater.from(RelationActivity.this).inflate(R.layout.relation_emergency, null, false);
        view.findViewById(R.id.tv_relation_relation).setOnClickListener(this);
        ll_relation_container.addView(view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_relation_urgency_phone:
                CallPhone(mEditTextUrgencyPhone);
                break;
            case R.id.bt_relation_add:
                View view = LayoutInflater.from(RelationActivity.this).inflate(R.layout.relation_emergency, null, false);
                view.findViewById(R.id.tv_relation_relation).setOnClickListener(this);
                ll_relation_container.addView(view);
                if (ll_relation_container.getChildCount() == 3) {
                    mButtonAdd.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_relation_relation:
                if (v.getTag() != null) {
                    v.setTag(0);
                }
                showRelationDialog((TextView) v, (Integer) v.getTag());
                break;
            case R.id.bt_relation_submit:
                if (textIsEmpty()) {
                    StringBuffer nameSB = new StringBuffer();
                    StringBuffer phoneSB = new StringBuffer();
                    StringBuffer idSB = new StringBuffer();
                    StringBuffer relationSB = new StringBuffer();
                    for (int i = 0; i < ll_relation_container.getChildCount(); i++) {
                        view = ll_relation_container.getChildAt(i);
                        TextView tv_relation_relation = (TextView) view.findViewById(R.id.tv_relation_relation);
                        EditText et_relation_name = (EditText) view.findViewById(R.id.et_relation_name);
                        EditText et_relation_phone = (EditText) view.findViewById(R.id.et_relation_phone);
                        String id = (String) view.getTag();
                        String name = et_relation_name.getText().toString();
                        String phone = et_relation_phone.getText().toString();
                        nameSB.append(name);
                        phoneSB.append(phone);
                        if (id != null)
                            idSB.append(id);
                        relationSB.append(tv_relation_relation.getTag().toString());

                        if (i != ll_relation_container.getChildCount() - 1) {
                            nameSB.append(",");
                            phoneSB.append(",");
                            idSB.append(",");
                            relationSB.append(",");
                        }
                    }


                    AddContactParams addContactParams = new AddContactParams("2", MyApplications.getToken(), idSB.toString(), nameSB.toString(), phoneSB.toString(), relationSB.toString(), "", mEditTextrgencyName.getText().toString(), mEditTextUrgencyPhone.getText().toString());
                    Log.d(TAG, new Gson().toJson(addContactParams));

                    final NetProgressDialog netProgressDialog = new NetProgressDialog();
                    netProgressDialog.show(getSupportFragmentManager(), "");
                    FormBody formBody = new FormBody.Builder().add("content", new Gson().toJson(addContactParams)).build();
                    OkhttpUtils.getInstance(this).asyncPost(NetConfig.INFO_RELATION, formBody, new OkhttpUtils.HttpCallBack() {
                        @Override
                        public void onError(Request request, IOException e) {
                            netProgressDialog.dismissAllowingStateLoss();
                        }

                        @Override
                        public void onSuccess(Request request, String result) {
                            Log.d(TAG, result);
                            netProgressDialog.dismissAllowingStateLoss();
                            if (result != null) {
                                try {
                                    JSONObject object = new JSONObject(result);
                                    Log.d(TAG, object.getString("returnCode"));
                                    if (object.getString("returnCode").equals("000000")) {
                                        finish();
                                    } else if ("0042".equals(object.getString("returnCode"))) {
                                        new AlertDialog.Builder(RelationActivity.this)
                                                .setMessage(object.getString("returnMsg"))
                                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                })
                                                .setPositiveButton("去填写", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                        startActivity(new Intent(RelationActivity.this, CarApproveActivity.class));
                                                    }
                                                }).show();
                                    } else {
                                        ToastUtil.show(RelationActivity.this, object.getString("returnMsg"));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    });


                } else {
                    ToastUtil.show(this, "所有信息均为必填项");
                }
                break;
        }
    }


    private void CallPhone(TextView textView) {
        String number = textView.getText().toString().trim();
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


    private boolean textIsEmpty() {
        for (int i = 0; i < ll_relation_container.getChildCount(); i++) {
            for (int i1 = 0; i1 < ((ViewGroup) ll_relation_container.getChildAt(i)).getChildCount(); i1++) {
                if (((ViewGroup) ll_relation_container.getChildAt(i)).getChildAt(i1) instanceof TextView) {
                    String cont = ((TextView) ((ViewGroup) ll_relation_container.getChildAt(i)).getChildAt(i1)).getText().toString();
                    if (TextUtils.isEmpty(cont))
                        return true;
                }
            }
        }

        return TextUtils.isEmpty(mEditTextUrgencyPhone.getText()) || TextUtils.isEmpty(mEditTextrgencyName.getText());

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
