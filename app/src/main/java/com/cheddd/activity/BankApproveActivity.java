package com.cheddd.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.cheddd.R;
import com.cheddd.application.MyApplications;
import com.cheddd.base.MyBaseActivity;
import com.cheddd.bean.InfoBankBean;
import com.cheddd.bean.InfoBankList;
import com.cheddd.bean.ProvinceBean;
import com.cheddd.config.NetConfig;
import com.cheddd.parse.InfoParse;
import com.cheddd.utils.JsonFileReader;
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

import static android.R.attr.id;
import static android.R.attr.value;

/*
银行卡认证
*/
public class BankApproveActivity extends MyBaseActivity implements View.OnClickListener, TextWatcher, CompoundButton.OnCheckedChangeListener {

    private static String TAG = BankApproveActivity.class.getSimpleName();
    private Map<String, String> map;
    private TopNavigationBar mTnb;
    //持卡人，卡号，开户行，预留手机
    private EditText mEditTextName, mEditTextCard, mEditTextBank, mEditTextPhone;
    //银行，省,市；
    private TextView mTextViewBank, mTextViewProvince, mTextViewCity;
    //代扣协议
    private CheckBox mCheckBox;
    private Button mButtonSubmit;
    private RelativeLayout mRelativeProvince, mRelativeBank;
    private ListView myGridView;
    private List<String> mList;
    private ArrayAdapter<String> mAdapter;
    private int posiotion = 0;
    private OptionsPickerView pvOptions;
    //  省份
    private ArrayList<ProvinceBean> provinceBeanList = new ArrayList<>();
    //  城市
    private ArrayList<String> cities;
    private ArrayList<List<String>> cityList = new ArrayList<>();
    //  区/县
    private ArrayList<String> district;
    private ArrayList<List<String>> districts;
    private ArrayList<List<List<String>>> districtList = new ArrayList<>();
    private Dialog mDialog;
    private String bankId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_approve);
        initView();
        initData();
        setListener();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }

    private void initData() {
        pvOptions = new OptionsPickerView(this);
        mList = new ArrayList<>();
        map = new LinkedHashMap<>();
        //请求银行列表
        initNetBank();
        //请求数据
        initBankInfo();
        //判断按钮是否可点击
        loanInfo();
    }

    private void initBankInfo() {
        String json = LoginTokenUtils.getJson();
        FormBody formbody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.INFO_BANK_INFO, formbody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                    try {
                      //  Log.d(TAG, "银行卡请求" + result);
                        JSONObject object = new JSONObject(result);
                        String returnCode = object.getString("returnCode");
                        String returnMsg = object.getString("returnMsg");
                        if ("000000".equals(returnCode)) {
                            JSONObject entity = object.getJSONObject("entity");
                            String dictionaryName = entity.getString("dictionaryName");
                            String cardNo = entity.getString("cardNo");
                            String khBankPro = entity.getString("khBankPro");
                            String signTelNo = entity.getString("signTelNo");
                            String custName = entity.getString("custName");
                            String khBankCity = entity.getString("khBankCity");
                            String khBankName = entity.getString("khBankName");
                            String khBank = entity.getString("khBank");
                            bankId = entity.getString("bankId");
                            mEditTextName.setText(custName);
                            mEditTextCard.setText(cardNo);
                            mTextViewProvince.setText(khBankPro);
                            mTextViewCity.setText(khBankCity);
                            mEditTextBank.setText(khBankName);
                            mEditTextPhone.setText(signTelNo);
                            mTextViewBank.setText(dictionaryName);
                        } else if ("0017".equals(returnCode)) {
                            ToastUtil.show(BankApproveActivity.this, returnMsg);
                            startActivity(new Intent(BankApproveActivity.this, LoginActivity.class));
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
                            mEditTextBank.setCursorVisible(false);
                            mEditTextPhone.setCursorVisible(false);
                            mEditTextCard.setCursorVisible(false);
                            mEditTextName.setCursorVisible(false);
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

    //请求银行的列表
    private void initNetBank() {
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
                        map.put(name, id);
                    }
                    mAdapter = new ArrayAdapter<String>(BankApproveActivity.this, android.R.layout.simple_list_item_1, mList);
                }
            }
        });
    }

    private void setListener() {
        mCheckBox.setOnCheckedChangeListener(this);
        mEditTextName.addTextChangedListener(this);
        mTextViewBank.addTextChangedListener(this);
        mEditTextCard.addTextChangedListener(this);
        mTextViewProvince.addTextChangedListener(this);
        mEditTextBank.addTextChangedListener(this);
        mEditTextPhone.addTextChangedListener(this);
        mTextViewBank.setOnClickListener(this);
        mButtonSubmit.setOnClickListener(this);
        mRelativeProvince.setOnClickListener(this);
        mTnb.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mTnb = (TopNavigationBar) findViewById(R.id.tnb_bank);
        mEditTextName = (EditText) findViewById(R.id.et_bank_peoplenme);
        mEditTextCard = (EditText) findViewById(R.id.et_bank_card);
        mEditTextPhone = (EditText) findViewById(R.id.et_bank_obligate);
        mEditTextBank = (EditText) findViewById(R.id.et_bank_open);
        mCheckBox = (CheckBox) findViewById(R.id.cb_bankapprove_is);
        mTextViewBank = (TextView) findViewById(R.id.tv_bank_banks);
        mTextViewProvince = (TextView) findViewById(R.id.tv_bank_provinces);
        mTextViewCity = (TextView) findViewById(R.id.tv_bank_city);
        mRelativeProvince = (RelativeLayout) findViewById(R.id.rl_bank_province);
        mButtonSubmit = (Button) findViewById(R.id.bt_bank_sunmit);
        mRelativeBank = (RelativeLayout) findViewById(R.id.rl_bank_banks);
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.rl_bank_province:
                    province();
                    break;
                case R.id.tv_bank_banks:
                    banks();
                    break;
                case R.id.bt_bank_sunmit:
                    bankSubmit();
                    break;
                default:
                    break;
            }
        }
    }

    //点击提交银行的信息
    private void bankSubmit() {
        String name = mEditTextName.getText().toString().trim();
        if (!name.matches("^[\\u4e00-\\u9fa5]{0,}$")) {
            ToastUtil.show(this, "姓名输入错误");
        }
        String card = mEditTextCard.getText().toString().trim();
        /*if (!name.matches("^(\\d{16}|\\d{19})$")) {
            ToastUtil.show(this, "卡号输入有误");
        }*/
        String bank = mEditTextBank.getText().toString().trim();
        if (!bank.matches("^[\\u4E00-\\u9FA5A-Za-z0-9_]+$")) {
            ToastUtil.show(this, "开户行输入有误");
        }
        String phone = mEditTextPhone.getText().toString().trim();
        if (!phone.matches("1\\d{10}")) {
            ToastUtil.show(this, "电话号码输入有误");
        }
        InfoBankBean bankBean = new InfoBankBean();
        bankBean.setClientType("2");
        bankBean.setToken(MyApplications.getToken());
        bankBean.setCustName(mEditTextName.getText().toString().trim());
        bankBean.setSignTelNo(mEditTextPhone.getText().toString().trim());
        bankBean.setCardNo(mEditTextCard.getText().toString().trim());
        bankBean.setKhBankCity(mTextViewCity.getText().toString());
        bankBean.setKhBankPro(mTextViewProvince.getText().toString());
        if(!TextUtils.isEmpty(bankId)){
            bankBean.setBankId(bankId);
        }else {
            bankBean.setBankId("");
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            if (key.equals(mTextViewBank.getText().toString())) {
                String value = entry.getValue();
                bankBean.setKhBank(value);
            }
        }

        bankBean.setKhBankName(mEditTextBank.getText().toString().trim());
        Gson gson = new Gson();
        String json = gson.toJson(bankBean);
       // Log.d(TAG, "银行卡认证的提交" + json);
        FormBody formbody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.INFO_BANK, formbody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                    //  Log.d(TAG, result);
                    try {
                        JSONObject object = new JSONObject(result);
                        String returnCode = object.getString("returnCode");
                        String returnMsg = object.getString("returnMsg");
                        if ("000000".equals(returnCode)) {
                            ToastUtil.show(BankApproveActivity.this, returnMsg);
                            finish();
                        }else if ("0017".equals(returnCode)) {
                            ToastUtil.show(BankApproveActivity.this, returnMsg);
                            startActivity(new Intent(BankApproveActivity.this, LoginActivity.class));
                            finish();
                        }else if("0042".equals(returnCode)){
                            ToastUtil.show(BankApproveActivity.this, returnMsg);
                            startActivity(new Intent(BankApproveActivity.this, CarApproveActivity.class));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    //获取支持的银行
    private void banks() {
        mDialog = new AlertDialog.Builder(this).create();
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_listview, null);
        mDialog.setCanceledOnTouchOutside(false);
        myGridView = (ListView) view.findViewById(R.id.lv_dialog);
        myGridView.setAdapter(mAdapter);
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = mList.get(position);
                mTextViewBank.setText(s.toString());
                mDialog.dismiss();
            }
        });
        mDialog.show();
        mDialog.getWindow().setContentView((LinearLayout) view);
    }

    private void province() {
        String province_data_json = JsonFileReader.getJson(this, "province_data.json");
        //  解析json数据
        parseJson(province_data_json);
        //  设置三级联动效果
        pvOptions.setPicker(provinceBeanList, cityList, true);
        pvOptions.setCyclic(false, false, false);
        // 设置默认选中的三级项目
        pvOptions.setSelectOptions(0, 0);
        //  监听确定选择按钮
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String city = provinceBeanList.get(options1).getPickerViewText();
                String address;
                String citys = null;
                //  如果是直辖市或者特别行政区只设置市和区/县
                if ("北京市".equals(city) || "上海市".equals(city) || "天津市".equals(city) || "重庆市".equals(city) || "澳门".equals(city) || "香港".equals(city)) {
                    address = provinceBeanList.get(options1).getPickerViewText();
                    //+ " " + districtList.get(options1).get(option2).get(options3);
                    citys = districtList.get(options1).get(option2).get(0);
                } else {
                    address = provinceBeanList.get(options1).getPickerViewText();
                    // + " " + cityList.get(options1).get(option2);
                    // + " " + districtList.get(options1).get(option2).get(options3);
                    citys = cityList.get(options1).get(option2);
                }
                mTextViewProvince.setText(address);
                mTextViewCity.setText(citys);
            }
        });


        //  点击弹出选项选择器
        mRelativeProvince.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        if (mEditTextName.getText().toString().trim().length() > 0) {
            if (mTextViewBank.getText().toString().length() > 0) {
                if (mEditTextCard.getText().toString().trim().length() > 15) {
                    if (mTextViewProvince.getText().toString().length() > 0) {
                        if (mEditTextBank.getText().toString().trim().length() > 0) {
                            if (mEditTextPhone.getText().toString().trim().length() == 11) {
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
        } else {
            mButtonSubmit.setEnabled(false);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (mEditTextName.getText().toString().trim().length() > 1) {
            if (mTextViewBank.getText().toString().length() > 0) {
                if (mEditTextCard.getText().toString().trim().length() > 15) {
                    if (mTextViewProvince.getText().toString().length() > 0) {
                        if (mEditTextBank.getText().toString().trim().length() > 0) {
                            if (mEditTextPhone.getText().toString().trim().length() == 11) {
                                mButtonSubmit.setEnabled(true);
                                mButtonSubmit.setEnabled(isChecked);
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
        } else {
            mButtonSubmit.setEnabled(false);
        }
    }
}

