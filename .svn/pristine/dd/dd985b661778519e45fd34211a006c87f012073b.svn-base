package com.cheddd.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheddd.R;
import com.cheddd.base.MyBaseActivity;
import com.cheddd.bean.InfoBankList;
import com.cheddd.config.NetConfig;
import com.cheddd.parse.InfoParse;
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

//借款银行卡
public class LendBankActivity extends MyBaseActivity implements View.OnClickListener {

    private static String TAG = LendBankActivity.class.getSimpleName();
    private Dialog mDialog;
    private TopNavigationBar mTnb;
    //查看支持的银行、银行、卡号
    private TextView mTextViewBank, mTextViewMark, mTextViewnumber;
    private ListView mListView;
    private List<String> mList;
    private ArrayAdapter<String> mAdapter;
    private Button mBbuttonNext, mButtonKonw;
    private RelativeLayout mRelativeBank, mRelativeKnow;
    //图片
    private ImageView mImageView;
    private String mark;
    private String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lend_bank);
        initView();
        initData();
        setData();
        setListener();

    }

    private void setData() {
    }


    private void initData() {
        mList = new ArrayList<>();
       /* for (int i = 0; i < 50; i++) {
            mList.add(i + "");
        }
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mList);*/
        requestData();
        requestBank();
    }

    //点击查看支持的银行
    private void requestBank() {
        String json = LoginTokenUtils.getJson();
        FormBody formbody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.INFO_BANK_LIST, formbody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                    List<InfoBankList> bankList = InfoParse.getBankList(result);
                    String name = null;
                    for (InfoBankList list : bankList) {
                        name = list.getName();
                        String id = list.getId();
                        mList.add(name);
                    }
                    mAdapter = new ArrayAdapter<String>(LendBankActivity.this, android.R.layout.simple_list_item_1, mList);
                }
            }
        });
    }

    //请求网络数据局
    private void requestData() {
        String json = LoginTokenUtils.getJson();
        FormBody formbody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.INDEX_PETTLYLOAN_BANK, formbody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                    Log.d(TAG, "借款银行卡" + result);
                    try {
                        JSONObject object = new JSONObject(result);
                        JSONObject entity = object.getJSONObject("entity");
                        String dictionaryName = entity.getString("dictionaryName");
                        String khBankPro = entity.getString("cardNo");
                        mTextViewMark.setText(dictionaryName);
                        if (khBankPro.length() > 4) {
                            String substring = khBankPro.substring(khBankPro.length() - 4);
                            mTextViewnumber.setText("储存卡" + "(" + substring + ")");
                        }
                        if (mTextViewMark.getText().toString().equals("交通银行 ")) {
                            mImageView.setImageResource(R.mipmap.bank_com);
                        } else if (mTextViewMark.getText().toString().equals("招商银行")) {
                            mImageView.setImageResource(R.mipmap.bank_cmbc);
                        } else if (mTextViewMark.getText().toString().equals("民生银行")) {
                            mImageView.setImageResource(R.mipmap.bank_cmsb);
                        } else if (mTextViewMark.getText().toString().equals("农业银行")) {
                            mImageView.setImageResource(R.mipmap.bank_abc);
                        } else if (mTextViewMark.getText().toString().equals("上海银行")) {
                            mImageView.setImageResource(R.mipmap.bank_sh);
                        } else if (mTextViewMark.getText().toString().equals("广发银行")) {
                            mImageView.setImageResource(R.mipmap.bank_cgb);
                        } else if (mTextViewMark.getText().toString().equals("中国银行")) {
                            mImageView.setImageResource(R.mipmap.bank_boc);
                        } else if (mTextViewMark.getText().toString().equals("建设银行")) {
                            mImageView.setImageResource(R.mipmap.bank_ccb);
                        } else if (mTextViewMark.getText().toString().equals("兴业银行")) {
                            mImageView.setImageResource(R.mipmap.bank_cib);
                        } else if (mTextViewMark.getText().toString().equals("中信银行")) {
                            mImageView.setImageResource(R.mipmap.bank_citic);
                        } else if (mTextViewMark.getText().toString().equals("华夏银行")) {
                            mImageView.setImageResource(R.mipmap.bank_hxb);
                        } else if (mTextViewMark.getText().toString().equals("平安银行")) {
                            mImageView.setImageResource(R.mipmap.bank_pa);
                        } else if (mTextViewMark.getText().toString().equals("工商银行")) {
                            mImageView.setImageResource(R.mipmap.bank_icbc);
                        } else if (mTextViewMark.getText().toString().equals("光大银行 ")) {
                            mImageView.setImageResource(R.mipmap.bank_ceb);
                        } else if (mTextViewMark.getText().toString().equals("邮储银行")) {
                            mImageView.setImageResource(R.mipmap.bank_yz);
                        } else if (mTextViewMark.getText().toString().equals("浦发银行")) {
                            mImageView.setImageResource(R.mipmap.bank_spdp);
                        } else {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void setListener() {
        mButtonKonw.setOnClickListener(this);
        mRelativeBank.setOnClickListener(this);
        mBbuttonNext.setOnClickListener(this);
        mTextViewBank.setOnClickListener(this);
        mTnb.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mButtonKonw = (Button) findViewById(R.id.bt_bank_know);
        mTextViewnumber = (TextView) findViewById(R.id.tv_lendbank_number);
        mTextViewMark = (TextView) findViewById(R.id.tv_lendbank_mark);
        mImageView = (ImageView) findViewById(R.id.iv_lendbank_mark);
        mRelativeBank = (RelativeLayout) findViewById(R.id.rl_lendmoney_bank);
        mTnb = (TopNavigationBar) findViewById(R.id.tnb_lendbank);
        mTextViewBank = (TextView) findViewById(R.id.tv_lendbank_bank);
        mBbuttonNext = (Button) findViewById(R.id.bt_lendbank_next);
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.tv_lendbank_bank:
                    bank();
                    break;
                case R.id.bt_lendbank_next:
                    startActivity(new Intent(this, LendEntryActivity.class));
                    break;
                case R.id.rl_lendmoney_bank:
                    Intent intent = new Intent(this, MineBankActivity.class);
                    startActivityForResult(intent, 100);
                    break;
                case R.id.bt_bank_know:
                    know();
                    break;
                case R.id.rl_dialog_close:
                    mDialog.dismiss();
                    break;
                default:
                    break;
            }
        }
    }

    private void know() {
        mDialog = new AlertDialog.Builder(this).create();
        View view = LayoutInflater.from(this).inflate(R.layout.dialong_lend_konw, null);
        mDialog.setCanceledOnTouchOutside(false);
        mRelativeKnow = (RelativeLayout) view.findViewById(R.id.rl_dialog_close);
        mRelativeKnow.setOnClickListener(this);
        mDialog.show();
        mDialog.getWindow().setContentView((LinearLayout) view);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (100 == resultCode) {
            mark = data.getExtras().getString("mark");
            number = data.getExtras().getString("number");
            String icon = data.getExtras().getString("icon");
            mTextViewMark.setText(mark);
            mTextViewnumber.setText(number);
            // Picasso.with(this).load(icon).into(mImageView);
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    //查看支持的银行卡
    private void bank() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_lendbank, null);
        builder.setView(view);
        mListView = (ListView) view.findViewById(R.id.gv_lendbank_bank);
        mListView.setAdapter(mAdapter);
        builder.create().show();
    }
}
