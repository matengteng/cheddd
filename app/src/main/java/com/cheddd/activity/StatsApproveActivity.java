package com.cheddd.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.cheddd.R;
import com.cheddd.application.MyApplications;
import com.cheddd.base.MyBaseActivity;
import com.cheddd.bean.InfoStatBean;
import com.cheddd.bean.LoginTokenBean;
import com.cheddd.bean.ProvinceBean;
import com.cheddd.config.NetConfig;
import com.cheddd.utils.JsonFileReader;
import com.cheddd.utils.LoginTokenUtils;
import com.cheddd.utils.OkhttpUtils;
import com.cheddd.utils.SdCardUtils;
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
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.Request;

import static android.R.attr.value;

/**
 * 信息认证
 */
public class StatsApproveActivity extends MyBaseActivity implements View.OnClickListener, TextWatcher {

    private static String TAG = StatsApproveActivity.class.getSimpleName();

    private Map<String, Integer> mapSex;
    private Map<String, Integer> mapMarriage;
    private Map<String, Integer> mapEducation;

    //姓名，身份证号，手机号码,详细地址
    private EditText mEditTextName, mEditTextIDCard, mEditTextPhone, mEditTextDetalis;
    //户籍、联系人，工作信息，居住；
    private RelativeLayout mRelativeHousehold, mRelativeRelation, mRelativeWork, mRelativeLive;
    //提交
    private Button mButtonSubmit;
    //性别，婚姻，教育,省,市
    private TextView mTextViewSex, mTextViewMarriage, mTextViewEducation, mTextViewProvince, mTextViewCity;
    private int position = 0;
    private TopNavigationBar mTnb;
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
    private Button mButtonWork, mButtonRelation, mButtonLive;
    private String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_approve);
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
        mapSex = new LinkedHashMap<>();
        mapSex.put("男", 0);
        mapSex.put("女", 1);
        mapSex.put("未知", 2);
        mapMarriage = new LinkedHashMap<>();
        mapMarriage.put("已婚", 0);
        mapMarriage.put("未婚", 1);
        mapMarriage.put("离异", 2);
        mapEducation = new LinkedHashMap<>();
        mapEducation.put("小学毕业", 0);
        mapEducation.put("中学毕业", 1);
        mapEducation.put("高中毕业", 2);
        mapEducation.put("大专毕业", 3);
        mapEducation.put("大学本科", 4);
        mapEducation.put("研究生", 5);
        mapEducation.put("博士", 6);
        pvOptions = new OptionsPickerView(this);
        json = LoginTokenUtils.getJson();
        InfoState();
        lookState();
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
                            mEditTextDetalis.setCursorVisible(false);
                            mEditTextIDCard.setCursorVisible(false);
                            mEditTextName.setCursorVisible(false);
                            mEditTextPhone.setCursorVisible(false);
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

    //请求数据
    private void InfoState() {
        final FormBody formBody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.INFO_INFO, formBody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                    Log.d(TAG, "个人信息请求" + result);
                    try {
                        JSONObject object = new JSONObject(result);
                        String returnCode = object.getString("returnCode");
                        String returnMsg = object.getString("returnMsg");
                        int personalAuth = object.getInt("personalAuth");
                        if ("000000".equals(returnCode)) {
                            JSONObject entity = object.getJSONObject("entity");
                            String custName = entity.getString("custName");
                            int sex = entity.getInt("sex");
                            String degree = entity.getString("degree");
                            int marriageStatus = entity.getInt("marriageStatus");
                            String identityCode = entity.getString("identityCode");
                            String telNo = entity.getString("telNo");
                            String province = entity.getString("province");
                            String city = entity.getString("city");
                            String registerCity = entity.getString("registerCity");
                            String id = entity.getString("id");
                            String phone = SharedPreferencesUtils.getString(StatsApproveActivity.this, "phone", "");
                           if(phone.equals(custName)){
                               mEditTextName.setText("");
                           }else {
                               mEditTextName.setText(custName);
                           }

                            mEditTextIDCard.setText(identityCode);
                            mEditTextPhone.setText(telNo);
                            for (Map.Entry<String, Integer> entry : mapSex.entrySet()) {
                                int value = entry.getValue().intValue();
                                if (value == sex) {
                                    String key = entry.getKey();
                                    mTextViewSex.setText(key);
                                }
                            }
                            for (Map.Entry<String, Integer> entry : mapEducation.entrySet()) {
                                String string = entry.getValue().toString();
                                if (string.equals(degree)) {
                                    String key = entry.getKey();
                                    mTextViewEducation.setText(key);
                                }
                            }
                            for (Map.Entry<String, Integer> entry : mapMarriage.entrySet()) {
                                int value = entry.getValue().intValue();
                                if (value == marriageStatus) {
                                    String key = entry.getKey();
                                    mTextViewMarriage.setText(key);
                                }
                            }

                            mTextViewProvince.setText(province);
                            mTextViewCity.setText(city);
                            mEditTextDetalis.setText(registerCity);
                            if (personalAuth == 0) {
                                mButtonSubmit.setEnabled(false);
                            } else {
                                addTextChanged();
                            }
                        } else if ("0017".equals(returnCode)) {
                            ToastUtil.show(StatsApproveActivity.this, returnMsg);
                            startActivity(new Intent(StatsApproveActivity.this, LoginActivity.class));
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

    //查看工作,联系人，居住的状态
    private void lookState() {
        FormBody formBody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.OAUTH_SETP, formBody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                    //  Log.d(TAG, "进度" + result);
                    try {
                        JSONObject object = new JSONObject(result);
                        int workInfoAuth = object.getInt("workInfoAuth");
                        int houseInfoAuth = object.getInt("houseInfoAuth");
                        int contactsInfoAuth = object.getInt("contactsInfoAuth");
                        if (3!= houseInfoAuth) {
                            mButtonLive.setEnabled(true);
                        } else {
                            mButtonLive.setEnabled(false);
                        }
                        if (3!= workInfoAuth) {
                            mButtonWork.setEnabled(true);
                        } else {
                            mButtonWork.setEnabled(false);
                        }
                        if (3!= contactsInfoAuth) {
                            mButtonRelation.setEnabled(true);
                        } else {
                            mButtonRelation.setEnabled(false);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    private void setListener() {
        addTextChanged();
        mButtonSubmit.setOnClickListener(this);
        mTextViewSex.setOnClickListener(this);
        mRelativeHousehold.setOnClickListener(this);
        mTextViewMarriage.setOnClickListener(this);
        mTextViewEducation.setOnClickListener(this);
        mTextViewMarriage.setOnClickListener(this);
        mRelativeWork.setOnClickListener(this);
        mRelativeRelation.setOnClickListener(this);
        mRelativeLive.setOnClickListener(this);
        mTnb.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addTextChanged() {
        mTextViewProvince.addTextChangedListener(this);
        mTextViewEducation.addTextChangedListener(this);
        mTextViewMarriage.addTextChangedListener(this);
        mTextViewSex.addTextChangedListener(this);
        mEditTextPhone.addTextChangedListener(this);
        mEditTextIDCard.addTextChangedListener(this);
        mEditTextName.addTextChangedListener(this);
        mEditTextDetalis.addTextChangedListener(this);
    }

    private void initView() {
        mButtonWork = (Button) findViewById(R.id.bt_stats_work_optfor);
        mButtonLive = (Button) findViewById(R.id.bt_stats_live_optfor);
        mButtonRelation = (Button) findViewById(R.id.bt_stats_relation_optfor);
        mTnb = (TopNavigationBar) findViewById(R.id.tnb_stats);
        mButtonSubmit = (Button) findViewById(R.id.bt_stats_input);
        mEditTextDetalis = (EditText) findViewById(R.id.et_stats_details);
        mEditTextIDCard = (EditText) findViewById(R.id.et_stats_IDCard);
        mEditTextPhone = (EditText) findViewById(R.id.et_stats_phone);
        mEditTextName = (EditText) findViewById(R.id.et_stats_name);
        mRelativeHousehold = (RelativeLayout) findViewById(R.id.rl_stats_provincecity);
        mRelativeRelation = (RelativeLayout) findViewById(R.id.rl_stats_relation);
        mRelativeLive = (RelativeLayout) findViewById(R.id.rl_stats_live);
        mRelativeWork = (RelativeLayout) findViewById(R.id.rl_stats_work);
        mTextViewSex = (TextView) findViewById(R.id.tv_stats_sex);
        mTextViewEducation = (TextView) findViewById(R.id.tv_stats_educations);
        mTextViewMarriage = (TextView) findViewById(R.id.tv_stats_marriage);
        mTextViewProvince = (TextView) findViewById(R.id.tv_stats_household);
        mTextViewCity = (TextView) findViewById(R.id.tv_stats_city);

    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                //提交
                case R.id.bt_stats_input:
                    submit();
                    break;
                //性别
                case R.id.tv_stats_sex:
                    change_sex();
                    break;
                //学历
                case R.id.tv_stats_educations:
                    education();
                    break;
                //婚姻
                case R.id.tv_stats_marriage:
                    marriage();
                    break;
                //工作
                case R.id.rl_stats_work:
                    Intent intent = new Intent(StatsApproveActivity.this, WorkActivity.class);
                    startActivity(intent);
                    break;
                //联系人
                case R.id.rl_stats_relation:
                    startActivity(new Intent(StatsApproveActivity.this, RelationActivity.class));
                    break;
                //居住
                case R.id.rl_stats_live:
                    startActivity(new Intent(StatsApproveActivity.this, LiveActivity.class));
                    break;
                case R.id.rl_stats_provincecity:
                    province();
                    break;
            }
        }
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
        mRelativeHousehold.setOnClickListener(new View.OnClickListener() {
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

    //婚姻状况
    private void marriage() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(R.array.marriage, position, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                position = which;
                final String[] aryShop = getResources().getStringArray(R.array.marriage);
                mTextViewMarriage.setText(aryShop[which]);
                mapMarriage.put(aryShop[which], which);
                dialog.dismiss();
            }
        }).create().show();
    }

    //学历
    private void education() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(R.array.education, position, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                position = which;
                final String[] aryShop = getResources().getStringArray(R.array.education);
                mTextViewEducation.setText(aryShop[which]);
                mapEducation.put(aryShop[which], which);
                dialog.dismiss();
            }
        }).create().show();

    }

    //性别的选择
    private void change_sex() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(R.array.sex, position, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                position = which;
                final String[] aryShop = getResources().getStringArray(R.array.sex);
                mTextViewSex.setText(aryShop[which]);
                mapSex.put(aryShop[which], which);

                dialog.dismiss();
            }
        }).create().show();

    }


    //提交的界面
    private void submit() {
        //用户名
        String name = mEditTextName.getText().toString().trim();
        String nameRegular = "[\u4E00-\u9FA5]{2,5}(?:·[\u4E00-\u9FA5]{2,5})*";
        if (!name.matches(nameRegular)) {
            ToastUtil.show(this, "姓名格式错误");
            return;
        }
        //身份证号码的判断
        String IdCard = mEditTextIDCard.getText().toString().trim();

        if (!IdCard.matches("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$")) {
            ToastUtil.show(this, "身份证格式错误");
            return;
        }
        //手机号码
        String phone = mEditTextPhone.getText().toString().trim();
        if (!phone.matches("1\\d{10}")) {
            ToastUtil.show(this, "手机格式错误");
            return;
        }
        String details = mEditTextDetalis.getText().toString().trim();
        if (!details.matches("^[\\u4e00-\\u9fa5_a-zA-Z0-9]+$")) {
            ToastUtil.show(this, "详细地址输入有误");
            return;
        }
        //客户端来源、用户手机号、用户令牌、客户姓名、身份证、婚姻状况、性别、学历、详细地址、省、市
        InfoStatBean stat = new InfoStatBean();
        stat.setClientType("2");
        stat.setTelNo(mEditTextPhone.getText().toString().trim());
        stat.setToken(MyApplications.getToken());
        stat.setCustName(mEditTextName.getText().toString().trim());
        stat.setIdentityCode(mEditTextIDCard.getText().toString().trim());
        for (Integer key : mapSex.values()) {
            if (mapSex.get(mTextViewSex.getText().toString()).equals(key)) {
                stat.setSex(new Integer(key).intValue());
            }
        }
        for (Integer key : mapEducation.values()) {
            if (mapEducation.get(mTextViewEducation.getText().toString()).equals(key)) {
                stat.setDegree(String.valueOf(key));
            }
        }
        for (Integer key : mapMarriage.values()) {
            if (mapMarriage.get(mTextViewMarriage.getText().toString()).equals(key)) {
                stat.setMarriageStatu(new Integer(key).intValue());
            }
        }
        stat.setRegisterCity(mEditTextDetalis.getText().toString().trim());
        stat.setProvince(mTextViewProvince.getText().toString());
        stat.setCity(mTextViewCity.getText().toString());
        Gson gson = new Gson();
        String json = gson.toJson(stat);
        Log.d(TAG, "fghjkl;'" + json);
        FormBody formBody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.INFO, formBody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                    Log.d(TAG, "个人信息" + result);
                    try {
                        JSONObject object = new JSONObject(result);
                        String returnCode = object.getString("returnCode");
                        String returnMsg = object.getString("returnMsg");
                        if ("0017".equals(returnCode)) {
                            ToastUtil.show(StatsApproveActivity.this, returnMsg);
                            startActivity(new Intent(StatsApproveActivity.this, LoginActivity.class));
                            finish();
                        } else if ("000000".equals(returnCode)) {
                            ToastUtil.show(StatsApproveActivity.this, returnMsg);
                            finish();
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
        if (mEditTextName.getText().toString().trim().length() > 1) {
            if (mEditTextPhone.getText().toString().trim().length() == 11) {
                if (mEditTextIDCard.getText().toString().trim().length() == 18) {
                    if (mEditTextDetalis.getText().toString().trim().length() > 3) {
                        if (mTextViewSex.getText().toString().length() > 0) {
                            if (mTextViewMarriage.getText().toString().length() > 0) {
                                if (mTextViewProvince.getText().toString().length() > 0) {
                                    if (mTextViewEducation.getText().toString().length() > 0) {
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
            } else {
                mButtonSubmit.setEnabled(false);
            }
        } else {
            mButtonSubmit.setEnabled(false);
        }

    }


}
