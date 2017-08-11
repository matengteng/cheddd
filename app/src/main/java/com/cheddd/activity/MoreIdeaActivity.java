package com.cheddd.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cheddd.R;
import com.cheddd.application.MyApplications;
import com.cheddd.base.MyBaseActivity;
import com.cheddd.bean.MoreideaBean;
import com.cheddd.config.NetConfig;
import com.cheddd.utils.OkhttpUtils;
import com.cheddd.utils.SharedPreferencesUtils;
import com.cheddd.utils.ToastUtil;
import com.cheddd.view.TopNavigationBar;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Request;

public class MoreIdeaActivity extends MyBaseActivity implements TextWatcher, View.OnClickListener {

    private EditText mEditTextText, mEditTextPhone;
    private Button mButtonSubmit;
    private TopNavigationBar mTnb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_idea);
        initView();
        initData();
        setListener();
    }

    private void initData() {
        String phone = SharedPreferencesUtils.getString(MoreIdeaActivity.this, "phone", "");
        mEditTextPhone.setText(phone);
    }

    private void setListener() {
        mEditTextText.addTextChangedListener(this);
        mEditTextPhone.addTextChangedListener(this);
        mButtonSubmit.setOnClickListener(this);
        mTnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mEditTextText = (EditText) findViewById(R.id.et_moreidea_input);
        mEditTextPhone = (EditText) findViewById(R.id.et_moreidea_style);
        mButtonSubmit = (Button) findViewById(R.id.bt_moreidea_submit);
        mTnb = (TopNavigationBar) findViewById(R.id.tnb_moreidea);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mEditTextPhone.getText().toString().length() > 0)
            if (mEditTextText.getText().toString().length() > 0) {
                mButtonSubmit.setEnabled(true);
            } else {
                mButtonSubmit.setEnabled(false);
            }
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.bt_moreidea_submit:
                    moreIdeaSubmit();
                    break;
                default:
                    break;
            }
        }
    }

    //提交更多
    private void moreIdeaSubmit() {
        MoreideaBean bean = new MoreideaBean();
        bean.setClientType("2");
        bean.setToken(MyApplications.getToken());
        bean.setContent(mEditTextText.getText().toString().trim());
        Gson gson = new Gson();
        String json = gson.toJson(bean);
        FormBody formBody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.MINE_SUGGESTION_SUBMIT, formBody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                    try {
                        Log.d("TAG",result);
                        JSONObject object = new JSONObject(result);
                        String returnCode = object.getString("returnCode");
                        String returnMsg = object.getString("returnMsg");
                        if ("000000".equals(returnCode)) {
                            ToastUtil.show(MoreIdeaActivity.this, returnMsg);
                            finish();
                        } else if("0002".equals(returnCode)){
                            ToastUtil.show(MoreIdeaActivity.this, returnMsg);
                        }else {
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
