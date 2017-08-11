package com.cheddd.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheddd.R;
import com.cheddd.activity.BankApproveActivity;
import com.cheddd.activity.CarApproveActivity;
import com.cheddd.activity.ExtremeMortageActivity;
import com.cheddd.activity.LoginActivity;
import com.cheddd.activity.PhoneApproveActivity;
import com.cheddd.activity.PledgeActivity;
import com.cheddd.activity.StatsApproveActivity;
import com.cheddd.base.BaseFragment;
import com.cheddd.config.NetConfig;
import com.cheddd.utils.LoginTokenUtils;
import com.cheddd.utils.OkhttpUtils;
import com.cheddd.view.TopNavigationBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * Created by Administrator on 2017/5/18 0018.
 * 信息认证
 */

public class InfoFragment extends BaseFragment implements View.OnClickListener {
    private Context mContext;
    private static String TAG = InfoFragment.class.getSimpleName();
    //手机认证，信息认证，车辆信息,银行卡
    private RelativeLayout mRelativePhone, mRelativeStats, mRelativeCar, mRelativebank, mTextViewPledge;
    private TopNavigationBar mTnb;
    //手机认证，信息认证，车辆信息,银行卡的图片按钮
    private Button mButtonPhone, mButtonState, mButtonCar, mButtonBank;
    private ProgressBar mProgressBar;
    private TextView mTextViewInfo;
    private int loanInitAud;
    private String returnCode;
    private String returnMsg;
    private ImageView mPhoneShenhe, mPhoneShenhetongguo, mPhoneShenheJujue;
    private ImageView mCarShenHe, mCarShenhetongguo, mCarShenheJujue;
    private ImageView mStatesShenhe, mStatesShenhetongguo, mStatesShenheJujue;
    private ImageView mBnakShen, mBankShenhetongguo, mBankShenheJujue;
    private String returnCode1;
    private String returnMsg1;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_info, null);
        initView(view);
        initData();
        setData();
        setListener();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        String json = LoginTokenUtils.getJson();
        final FormBody formBody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(getActivity()).asyncPost(NetConfig.OAUTH_SETP, formBody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                  //  Log.d(TAG, "进度" + result);
                    try {
                        JSONObject object = new JSONObject(result);
                        returnCode1 = object.getString("returnCode");
                        returnMsg1 = object.getString("returnMsg");
                        if ("000000".equals(returnCode1)) {
                            int phoneAuth = object.getInt("phoneAuth");
                            int carInfoAuth = object.getInt("carInfoAuth");
                            int bankInfoAuth = object.getInt("bankInfoAuth");
                            int personalAuth = object.getInt("personalAuth");

                            mProgressBar.setProgress(0);

                            if (phoneAuth != 3) {
                                int p = mProgressBar.getProgress() + 25;
                                mProgressBar.setProgress(p);
                                mTextViewInfo.setText(p + "%");
                            }
                            if (0 == phoneAuth) {
                                mPhoneShenhe.setVisibility(View.VISIBLE);
                                mButtonPhone.setEnabled(false);
                            } else if (1 == phoneAuth) {
                                mPhoneShenhetongguo.setVisibility(View.VISIBLE);
                                mButtonPhone.setEnabled(false);
                            } else if (2 == phoneAuth) {
                                mPhoneShenheJujue.setVisibility(View.VISIBLE);
                                mButtonPhone.setEnabled(false);
                            }
                            if (carInfoAuth != 3) {
                                int p = mProgressBar.getProgress() + 25;
                                mProgressBar.setProgress(p);
                                mTextViewInfo.setText(p + "%");
                            }
                            if (0 == carInfoAuth) {
                                mCarShenHe.setVisibility(View.VISIBLE);
                                mButtonCar.setEnabled(false);
                            } else if (1 == carInfoAuth) {
                                mCarShenhetongguo.setVisibility(View.VISIBLE);
                                mButtonCar.setEnabled(false);
                            } else if (2 == carInfoAuth) {
                                mCarShenheJujue.setVisibility(View.VISIBLE);
                                mButtonCar.setEnabled(false);
                            }
                            if (personalAuth != 3) {
                                int p = mProgressBar.getProgress() + 25;
                                mProgressBar.setProgress(p);
                                mTextViewInfo.setText(p + "%");
                            }
                            if (0 == personalAuth) {
                                mStatesShenhe.setVisibility(View.VISIBLE);
                                mButtonState.setEnabled(false);
                            } else if (1 == personalAuth) {
                                mStatesShenhetongguo.setVisibility(View.VISIBLE);
                                mButtonState.setEnabled(false);
                            } else if (2 == personalAuth) {
                                mStatesShenheJujue.setVisibility(View.VISIBLE);
                                mButtonState.setEnabled(false);
                            }
                            if (bankInfoAuth != 3) {
                                int p = mProgressBar.getProgress()+25;
                                mProgressBar.setProgress(p);
                                mTextViewInfo.setText(p + "%");
                            }
                            if (0 == bankInfoAuth) {
                                mBnakShen.setVisibility(View.VISIBLE);
                                mButtonBank.setEnabled(false);
                            } else if (1 == bankInfoAuth) {
                                mBankShenhetongguo.setVisibility(View.VISIBLE);
                                mButtonBank.setEnabled(false);
                            } else if (2 == bankInfoAuth) {
                                mBankShenheJujue.setVisibility(View.VISIBLE);
                                mButtonBank.setEnabled(false);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        infoPledge();
        // loanInfo();
    }

    private void loanInfo() {
        String json = LoginTokenUtils.getJson();
        FormBody formbody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(mContext).asyncPost(NetConfig.INDEX_PLEDGE_INFO, formbody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                    try {
                     //   Log.d(TAG, result);
                        JSONObject object = new JSONObject(result);
                        JSONObject entity = object.getJSONObject("entity");
                        int loanInitAud = entity.getInt("loanInitAud");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    private void setData() {
        mTnb.setBackVisibility(false);
    }

    private void setListener() {
        mTextViewPledge.setOnClickListener(this);
        mRelativePhone.setOnClickListener(this);
        mRelativeCar.setOnClickListener(this);
        mRelativeStats.setOnClickListener(this);
        mRelativebank.setOnClickListener(this);
    }

    private void initView(View view) {
        mProgressBar = (ProgressBar) view.findViewById(R.id.pb_info_plan);
        mButtonPhone = (Button) view.findViewById(R.id.bt_info_phone);
        mButtonBank = (Button) view.findViewById(R.id.bt_info_bank);
        mButtonState = (Button) view.findViewById(R.id.bt_info_status);
        mButtonCar = (Button) view.findViewById(R.id.bt_info_car);
        mRelativePhone = (RelativeLayout) view.findViewById(R.id.rl_info_phone);
        mRelativeStats = (RelativeLayout) view.findViewById(R.id.rl_info_stats);
        mRelativeCar = (RelativeLayout) view.findViewById(R.id.rl_info_car);
        mRelativebank = (RelativeLayout) view.findViewById(R.id.rl_info_bank);
        mTnb = (TopNavigationBar) view.findViewById(R.id.tnb_info);
        mTextViewInfo = (TextView) view.findViewById(R.id.tv_info_plan);
        mTextViewPledge = (RelativeLayout) view.findViewById(R.id.rl_info_pledge);
        mPhoneShenhe = (ImageView) view.findViewById(R.id.iv_phone_shenhe);
        mPhoneShenhetongguo = (ImageView) view.findViewById(R.id.iv_phone_shenhetonghuo);
        mPhoneShenheJujue = (ImageView) view.findViewById(R.id.iv_phone_shenhejujue);
        mCarShenHe = (ImageView) view.findViewById(R.id.iv_car_shenhe);
        mCarShenhetongguo = (ImageView) view.findViewById(R.id.iv_car_shenhetonghuo);
        mCarShenheJujue = (ImageView) view.findViewById(R.id.iv_car_shenhejujue);
        mStatesShenhe = (ImageView) view.findViewById(R.id.iv_states_shenhe);
        mStatesShenhetongguo = (ImageView) view.findViewById(R.id.iv_states_shenhetonghuo);
        mStatesShenheJujue = (ImageView) view.findViewById(R.id.iv_states_shenhejujue);
        mBnakShen = (ImageView) view.findViewById(R.id.iv_bank_shenhe);
        mBankShenhetongguo = (ImageView) view.findViewById(R.id.iv_bank_shenhetonghuo);
        mBankShenheJujue = (ImageView) view.findViewById(R.id.iv_bank_shenhejujue);

        View status_bar = view.findViewById(R.id.status_bar);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) status_bar.getLayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            params.height = getResources().getDimensionPixelSize(resourceId);
        } else {
            params.height = 0;
        }

        status_bar.setLayoutParams(params);

    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                //手机认证
                case R.id.rl_info_phone:
                    if ("000000".equals(returnCode1)) {
                        Intent intent1 = new Intent(getActivity(), PhoneApproveActivity.class);
                        getActivity().startActivity(intent1);
                    } else {
                        startActivity(new Intent(mContext, LoginActivity.class));
                    }
                    break;
                //信息认证
                case R.id.rl_info_stats:
                    if ("000000".equals(returnCode1)) {
                        Intent intent2 = new Intent(getActivity(), StatsApproveActivity.class);
                        startActivity(intent2);
                    } else {
                        startActivity(new Intent(mContext, LoginActivity.class));
                    }
                    break;
                //车辆认证
                case R.id.rl_info_car:
                    if ("000000".equals(returnCode1)) {
                        Intent intent3 = new Intent(getActivity(), CarApproveActivity.class);
                        startActivity(intent3);
                    } else {
                        startActivity(new Intent(mContext, LoginActivity.class));
                    }

                    break;
                //银行卡
                case R.id.rl_info_bank:
                    if ("000000".equals(returnCode1)) {
                        Intent intent4 = new Intent(getActivity(), BankApproveActivity.class);
                        startActivity(intent4);
                    } else {
                        startActivity(new Intent(mContext, LoginActivity.class));
                    }
                    break;
                case R.id.rl_info_pledge:
                    info();
                    break;
                default:
                    break;
            }
        }

    }

    //极速抵押，最高获取90%估值额度
    private void info() {
        if ("0017".equals(returnCode)) {
            mContext.startActivity(new Intent(mContext, LoginActivity.class));
        } else {
            if (loanInitAud == 1) {
                getActivity().startActivity(new Intent(mContext, ExtremeMortageActivity.class));
            } else {
                getActivity().startActivity(new Intent(mContext, PledgeActivity.class).putExtra("che","11"));
            }
        }
    }

    //
    private void infoPledge() {
        String json = LoginTokenUtils.getJson();
        FormBody formBody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(getActivity()).asyncPost(NetConfig.OAUTH_SETP, formBody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                    Log.d(TAG, "进度" + result);
                    try {
                        JSONObject object = new JSONObject(result);
                        returnCode = object.getString("returnCode");
                        returnMsg = object.getString("returnMsg");
                        if ("000000".equals(returnCode)) {
                            loanInitAud = object.getInt("loanInitAud");
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
}
