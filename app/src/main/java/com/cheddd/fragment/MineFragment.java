package com.cheddd.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheddd.R;
import com.cheddd.activity.LendDetailsActivity;
import com.cheddd.activity.LendMoneyActivity;
import com.cheddd.activity.LoanActivity;
import com.cheddd.activity.MineLoanActivity;
import com.cheddd.activity.PettyLoanActivity;
import com.cheddd.activity.SafetyActivity;
import com.cheddd.activity.LoginActivity;
import com.cheddd.activity.MoreActivity;
import com.cheddd.activity.RecordActivity;
import com.cheddd.adapter.RelationsAdapter;
import com.cheddd.application.MyApplications;
import com.cheddd.base.BaseFragment;
import com.cheddd.config.NetConfig;
import com.cheddd.utils.LoginTokenUtils;
import com.cheddd.utils.OkhttpUtils;
import com.cheddd.utils.ToastUtil;
import com.cheddd.view.TopNavigationBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;

import okhttp3.FormBody;
import okhttp3.Request;


/**
 * Created by Administrator on 2017/5/18 0018.
 * 我的界面
 */

public class MineFragment extends BaseFragment implements View.OnClickListener {
    private static String TAG = MineFragment.class.getSimpleName();
    //累计待还、交易记录、账户安全、更多、我的头像
    private RelativeLayout mRelativeLoan, mRelativeRecord, mRelativeBank, mRelativemore, mRelativeMine;
    private Button mButtonWithDraw;
    private TextView mTextViewAddup, mTextViewMoney;
    private Button mButtonLogin;
    private Context mContext;
    private RelativeLayout mRelativeLayoutWithDraw, mRelativeLayoutLodin;
    private String returnCode;
    private String returnMsg;
    private int canLoanYN;
    private String returnCode1;
    private String returnMsg1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_mine, null);
        mContext = getActivity();
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
        FormBody formbody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(mContext).asyncPost(NetConfig.MINE_UNPAID, formbody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
            }

            @Override
            public void onSuccess(Request request, String result) {
                Log.d(TAG, "操作成功" + result);
                if (result != null) {
                    try {
                        JSONObject object = new JSONObject(result);
                        returnCode = object.getString("returnCode");
                        returnMsg = object.getString("returnMsg");
                        if ("0017".equals(returnCode)) {
                            mRelativeLayoutLodin.setVisibility(View.VISIBLE);
                            mRelativeLayoutWithDraw.setVisibility(View.GONE);
                            //  ToastUtil.show(mContext, returnMsg);
                        } else if ("000000".equals(returnCode)) {
                            mRelativeLayoutWithDraw.setVisibility(View.VISIBLE);
                            mRelativeLayoutLodin.setVisibility(View.GONE);
                            JSONObject entity = object.getJSONObject("entity");
                            double stayRepayAmtSum = entity.getDouble("stayRepayAmtSum");
                            mTextViewAddup.setText(stayRepayAmtSum / 100 + "元");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        lendMoney();
        cashMoney();
    }

    //可提现的金额
    private void cashMoney() {
        final String json = LoginTokenUtils.getJson();
        FormBody formbody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(mContext).asyncPost(NetConfig.INDEX_PETTYLOAN_INFO, formbody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                    try {
                        Log.d(TAG,"借钱"+result);
                        JSONObject object = new JSONObject(result);
                        returnCode1 = object.getString("returnCode");
                        returnMsg1 = object.getString("returnMsg");
                        if ("000000".equals(returnCode1)) {
                            JSONObject entity = object.getJSONObject("entity");
                            int loanLimit = entity.getInt("loanLimit");
                            int smallLoanSum = entity.getInt("smallLoanSum");
                            String orderNo = entity.getString("orderNo");
                            MyApplications.setOrderNo(orderNo);
                            String newRepaymentDate = entity.getString("newRepaymentDate");
                            double newRepayment = entity.getDouble("newRepayment");
                            DecimalFormat format = new DecimalFormat("#,###.00");
                            if(loanLimit<0){
                                mTextViewMoney.setText(0.00+"");
                                mButtonWithDraw.setVisibility(View.GONE);

                            }else {
                                mTextViewMoney.setText(format.format(loanLimit / 100));
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

    }

    //借钱
    private void lendMoney() {
        final String json = LoginTokenUtils.getJson();
        FormBody formbody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(mContext).asyncPost(NetConfig.INDEX_PETTLYLOAN_ORDER, formbody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                    // Log.d(TAG, "判断借钱的状态" + result);
                    try {
                        JSONObject object = new JSONObject(result);
                        String returnCode = object.getString("returnCode");
                        String returnMsg = object.getString("returnMsg");
                        if ("000000".equals(returnCode)) {
                            JSONObject entity = object.getJSONObject("entity");
                            canLoanYN = entity.getInt("canLoanYN");
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


    private void setData() {
    }

    private void setListener() {
        mRelativeLoan.setOnClickListener(this);
        mRelativeRecord.setOnClickListener(this);
        mRelativeBank.setOnClickListener(this);
        mButtonLogin.setOnClickListener(this);
        mRelativemore.setOnClickListener(this);
        mButtonWithDraw.setOnClickListener(this);
    }

    private void initView(View view) {
        mRelativeLoan = (RelativeLayout) view.findViewById(R.id.rl_mine_loan);
        mRelativeBank = (RelativeLayout) view.findViewById(R.id.rl_mine_safety);
        mRelativemore = (RelativeLayout) view.findViewById(R.id.rl_mine_more);
        mRelativeRecord = (RelativeLayout) view.findViewById(R.id.rl_mine_record);
        mButtonWithDraw = (Button) view.findViewById(R.id.bt_mine_withdraw);
        mTextViewAddup = (TextView) view.findViewById(R.id.tv_mine_money);
        mButtonLogin = (Button) view.findViewById(R.id.bt_mine_login);
        mRelativeMine = (RelativeLayout) view.findViewById(R.id.rl_mine_head);
        mRelativeLayoutWithDraw = (RelativeLayout) view.findViewById(R.id.rl_mine_withdraw);
        mRelativeLayoutLodin = (RelativeLayout) view.findViewById(R.id.rl_mine_login);
        mTextViewMoney = (TextView) view.findViewById(R.id.tv_mine_cash_money);
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                //累计待还
                case R.id.rl_mine_loan:
                    if ("000000".equals(returnCode)) {
                        startActivity(new Intent(getActivity(), MineLoanActivity.class));
                    } else if ("0017".equals(returnCode)) {
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                      //  ToastUtil.show(mContext, returnMsg);
                    } else {
                        return;
                    }
                    break;
                case R.id.bt_mine_login:
                    if ("0017".equals(returnCode)) {
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                    } else {
                        return;
                    }
                    break;
                //交易记录
                case R.id.rl_mine_record:
                    if ("000000".equals(returnCode)) {
                        startActivity(new Intent(getActivity(), RecordActivity.class));
                    } else if ("0017".equals(returnCode)) {
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                       // ToastUtil.show(mContext, returnMsg);
                    } else {
                        return;
                    }
                    break;
                //账户安全
                case R.id.rl_mine_safety:
                    if ("000000".equals(returnCode)) {
                        startActivity(new Intent(getActivity(), SafetyActivity.class));
                    } else if ("0017".equals(returnCode)) {
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                       // ToastUtil.show(mContext, returnMsg);
                    } else {
                        return;
                    }
                    break;
                //更多
                case R.id.rl_mine_more:
                    if ("000000".equals(returnCode)) {
                        startActivity(new Intent(getActivity(), MoreActivity.class));
                    } else if ("0017".equals(returnCode)) {
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                       // ToastUtil.show(mContext, returnMsg);
                    } else {
                        return;
                    }
                    break;
                case R.id.bt_mine_withdraw:
                    withDraw();
                    break;
                default:
                    break;

            }
        }

    }

    //借钱
    private void withDraw() {
        if ("000000".equals(returnCode1)) {
            if (canLoanYN == 0) {
                startActivity(new Intent(mContext, PettyLoanActivity.class));
            } else {
                startActivity(new Intent(mContext, LendDetailsActivity.class));
            }
        } else if ("0021".equals(returnCode1)) {
            ToastUtil.show(mContext, returnMsg1);
            return;
        } else {
            return;
        }
    }
}
