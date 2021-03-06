package com.cheddd.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheddd.R;
import com.cheddd.activity.ExtremeMortageActivity;
import com.cheddd.activity.LoginActivity;
import com.cheddd.activity.MainActivity;
import com.cheddd.activity.PettyLoanActivity;
import com.cheddd.activity.PledgeActivity;
import com.cheddd.adapter.HomeHeadViewpage;
import com.cheddd.adapter.IndexHeaderAdapter;
import com.cheddd.application.MyApplications;
import com.cheddd.base.BaseFragment;
import com.cheddd.bean.Account;
import com.cheddd.bean.Carousel;
import com.cheddd.bean.ContentBean;
import com.cheddd.bean.MineRecord;
import com.cheddd.config.NetConfig;
import com.cheddd.parse.IndexParse;
import com.cheddd.utils.LoginTokenUtils;
import com.cheddd.utils.OkhttpUtils;
import com.cheddd.utils.SdCardUtils;
import com.cheddd.view.LooperTextView;
import com.cheddd.view.TopNavigationBar;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Request;


/**
 * Created by Administrator on 2017/5/18 0018.
 * 首页的界面
 */

public class IndexFragment extends BaseFragment implements View.OnClickListener {
    private static String TAG = InfoFragment.class.getSimpleName();
    private TopNavigationBar mTnb;
    private LooperTextView looperview;
    private RelativeLayout mRelativePetty, mRelativeExtreme;
    private TextView mSlideTextView, mRollTextView;
    private Context mContent;
    private String path = "http://chanyouji.com/api/trips/featured.json?page=2";
    private ViewPager mViewPage;
    private LinearLayout mRound;
    private List<Carousel> mData;
    private TextView mTextViewtitle;
    private List<Account> mAccount;
    private List<String> mTitle;
    private List<ImageView> mImageView;
    private IndexHeaderAdapter mAdapter;
    // 自动循环 标记 当前Activity 销毁 停止循环
    private boolean isStop = true;
    // 记录是否有重复添加页卡
    private boolean isAdd = false;
    // 记录当前的dot 位置 作为下一次滑动的改变值
    private int currentIntex;
    private ImageView mView;
    private HomeHeadViewpage mViewPageAdapter;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mViewPage.setCurrentItem(mViewPage.getCurrentItem() + 1);
            mHandler.sendEmptyMessageDelayed(0, 3000);
        }
    };
    private List<ContentBean> mNotice;
    private String returnCode;
    private String returnMsg;
    private String returnCode1;
    private String returnMsg1;
    private int phoneAuth;
    private int carInfoAuth;
    private int bankInfoAuth;
    private int personalAuth;
    private int loanInitAud;
    private int loanInitAud1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_index, null);
        mContent = getActivity();
        initView(view);
        initData();
        setData();
        setListener();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        indexPetty();
        extremeMoreage();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //isStop = false;
    }

    private void setData() {
        mViewPage.setAdapter(mViewPageAdapter);
        int value = Integer.MAX_VALUE / 2;
        int i = value % mData.size();
        mViewPage.setCurrentItem(value - i);
        updateIntro();
        mHandler.sendEmptyMessageDelayed(0, 3000);
       /* mViewPage.setAdapter(mAdapter);
        if (mAccount.size() != 0) {
            int current = Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % mAccount.size();
            mViewPage.setCurrentItem(current);
            mRound.getChildAt(0).setEnabled(true);
            //  mTextViewtitle.setText(mAccount.get(0).getName());
            mHandler.sendEmptyMessageDelayed(0, 3000);
        } else {
            return;
        }*/
    }

    private void updateIntro() {
        int currentItem = mViewPage.getCurrentItem() % mData.size();
        mTextViewtitle.setText(mData.get(currentItem).getText());
        for (int i = 0; i < mRound.getChildCount(); i++) {
            mRound.getChildAt(i).setEnabled(i == currentItem);
        }
    }

    private void setListener() {
        mRelativeExtreme.setOnClickListener(this);
        mRelativePetty.setOnClickListener(this);
        mViewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                updateIntro();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
       /* mViewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (isAdd) {
                    //   mTextViewtitle.setText(mAccount.get(position % mAccount.size()).getName());
                    mRound.getChildAt(position % (mImageView.size() / 2)).setEnabled(true);
                    mRound.getChildAt(currentIntex).setEnabled(false);
                    currentIntex = position % (mAccount.size() / 2);
                } else {
                    //  mTextViewtitle.setText(mAccount.get(position % mAccount.size()).getName());
                    mRound.getChildAt(position % (mImageView.size())).setEnabled(true);
                    mRound.getChildAt(currentIntex).setEnabled(false);
                    currentIntex = position % (mAccount.size());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                mHandler.sendEmptyMessageDelayed(0, 3000);
            }
        }).start();*/
    }

    private void initData() {
        // mTnb.setBackVisibility(false);
        looperview.setTipList(generateTips());
        mTitle = new ArrayList<>();
        mImageView = new ArrayList<>();
        mAccount = new ArrayList<>();
        mData = new ArrayList<>();
        mData.add(new Carousel("", R.mipmap.a));
        mData.add(new Carousel("", R.mipmap.b));
        mData.add(new Carousel("", R.mipmap.c));
        initRound();
        mViewPageAdapter = new HomeHeadViewpage(mData, getActivity());
        updateIntro();
       /* if (SdCardUtils.netWorkConnected(mContent)) {
            initNet();
        } else {

        }*/
        indexPetty();
        extremeMoreage();
        pledge();
    }

    private void pledge() {
        MineRecord record=new MineRecord();
        record.setClientType("2");
        record.setOrderNo("");
        record.setToken(MyApplications.getToken());
        Gson gson=new Gson();
        String json = gson.toJson(record);
        FormBody formbody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(mContent).asyncPost(NetConfig.INDEX_PLEDGE_INFO, formbody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                    Log.d(TAG, "获取抵押贷款贷款详情" + result);
                    try {
                        JSONObject object = new JSONObject(result);
                        String returnCode = object.getString("returnCode");
                        if ("000000".equals(returnCode)) {
                            JSONObject entity = object.getJSONObject("entity");
                            loanInitAud1 = entity.getInt("loanInitAud");
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


    private void initRound() {
        for (int i = 0; i < mData.size(); i++) {
            View view = new View(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            if (i != 0) {
                params.leftMargin = 10;
            }
            view.setLayoutParams(params);
            view.setBackgroundResource(R.drawable.viewpage_round);
            mRound.addView(view);
        }
    }

    //有网络的时候加载
  /*  private void initNet() {
        new NetTask() {
            @Override
            public List<String> onCallBack(String result) {
                if (result != null) {
                    SdCardUtils.saveJsonToSD(result, NetConfig.NOTICE, mContent);
                }
                List<String> list = new ArrayList<String>();
                try {
                    JSONArray array = new JSONArray(result);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        JSONObject object = obj.getJSONObject("user");
                        String image = object.getString("image");
                        String name = object.getString("name");
                        Account account = new Account(name, image);
                        mAccount.add(account);
                    }
                    intRound();
                    if (mAccount.size() < 4) {
                        isAdd = true;
                        for (int i = 0; i < mAccount.size() * 2; i++) {
                            ViewPager.LayoutParams params = new ViewPager.LayoutParams();
                            params.height = ViewPager.LayoutParams.MATCH_PARENT;
                            params.width = ViewPager.LayoutParams.MATCH_PARENT;
                            mView = new ImageView(mContent);
                            //设置图片充满imageView中
                            mView.setScaleType(ImageView.ScaleType.FIT_XY);
                            Picasso.with(mContent).load(mAccount.get(i % mAccount.size()).getImage()).into(mView);
                            mImageView.add(mView);
                        }
                    } else {
                        isAdd = false;
                        for (int i = 0; i < mAccount.size(); i++) {
                            ViewPager.LayoutParams params = new ViewPager.LayoutParams();
                            params.height = ViewPager.LayoutParams.MATCH_PARENT;
                            params.width = ViewPager.LayoutParams.MATCH_PARENT;
                            mView = new ImageView(mContent);
                            //设置图片充满imageView中
                            mView.setScaleType(ImageView.ScaleType.FIT_XY);
                            Picasso.with(mContent).load(mAccount.get(i).getImage()).into(mView);
                            mImageView.add(mView);
                        }
                    }

                    mAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return list;
            }

        }.execute(path);

        mAdapter = new IndexHeaderAdapter(mImageView);
    }
*/
    //底部的小圆
    private void intRound() {
        for (int i = 0; i < mAccount.size(); i++) {
            View dot = new View(mContent);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(40, 40);
            dot.setBackgroundResource(R.drawable.viewpage_round);
            dot.setLayoutParams(lp);
            dot.setEnabled(false);
            mRound.addView(dot);

        }
    }

    //消息滚动条
    private List<ContentBean> generateTips() {
        mNotice = new ArrayList<>();
        if (SdCardUtils.netWorkConnected(mContent)) {
            initNetNotice();
        } else {
            initJsonNotice();
        }
        return mNotice;
    }

    //没有网络的情况下加载滚动条的消息
    private void initJsonNotice() {
        String jsonToSD = SdCardUtils.getJsonToSD(NetConfig.NOTICE, mContent);
        if (jsonToSD != null) {
            List<ContentBean> notice = IndexParse.getNotice(jsonToSD);
            mNotice.addAll(notice);
        }

    }

    //有网的情况下加载滚动条的消息
    private void initNetNotice() {
        FormBody formBody = new FormBody.Builder().build();
        OkhttpUtils.getInstance(getContext()).asyncPost(NetConfig.NOTICE, formBody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
            }

            @Override
            public void onSuccess(Request request, String result) {
                //  Log.d("TAG", "消息滚动条的接口" + result);
                if (result != null) {
                    SdCardUtils.saveJsonToSD(result, NetConfig.NOTICE, mContent);
                    List<ContentBean> notice = IndexParse.getNotice(result);
                    mNotice.addAll(notice);
                }
            }
        });

    }

    //加载控件
    private void initView(View view) {
        //  mTnb = (TopNavigationBar) view.findViewById(R.id.tnb_index);
        looperview = (LooperTextView) view.findViewById(R.id.ltv_index_slide);
        mRelativePetty = (RelativeLayout) view.findViewById(R.id.rl_index_petty);
        mRelativeExtreme = (RelativeLayout) view.findViewById(R.id.rl_index_extreme);
        mViewPage = (ViewPager) view.findViewById(R.id.vp_index_head);
        mTextViewtitle = (TextView) view.findViewById(R.id.tv_index_text);
        mRound = (LinearLayout) view.findViewById(R.id.ll_index_round);
    }

    //对点击事件的处理
    @Override
    public void onClick(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.rl_index_petty:
                    //小额贷款
                    indexPetty1();
                    break;
                case R.id.rl_index_extreme:
                    //极速抵押
                    //getActivity().startActivity(new Intent(mContent, ExtremeMortageActivity.class));
                    extremeMoreage1();
                    break;
            }
        }
    }

    //极速抵押
    private void extremeMoreage1() {
        if ("0017".equals(returnCode1)) {
            //ToastUtil.show(mContent, returnMsg1);
            startActivity(new Intent(mContent, LoginActivity.class));
            return;
        }
        if (phoneAuth == 3 || phoneAuth == 2 || carInfoAuth == 3 || carInfoAuth == 2 || personalAuth == 2 || personalAuth == 3 || bankInfoAuth == 3 || bankInfoAuth == 2) {
            new AlertDialog.Builder(getContext())
                    .setMessage("请完成信息认证")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setPositiveButton("去认证", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    RadioGroup gp = ((MainActivity) getActivity()).mRg;
                    gp.check(gp.getChildAt(1).getId());
                    dialog.dismiss();
                }
            }).show();
            return;
        }

        if (loanInitAud1 == 1) {
            getActivity().startActivity(new Intent(mContent, ExtremeMortageActivity.class));
        } else if (loanInitAud1 == 0) {
            getActivity().startActivity(new Intent(mContent, PledgeActivity.class).putExtra("che", "11"));
        }
    }

    //小额借款
    private void indexPetty1() {
        if ("0017".equals(returnCode)) {
            mContent.startActivity(new Intent(getActivity(), LoginActivity.class));
            // ToastUtil.show(mContent, returnMsg);
        } else {
            mContent.startActivity(new Intent(getActivity(), PettyLoanActivity.class));
        }
    }

    //小额借款
    private void indexPetty() {
        final String json = LoginTokenUtils.getJson();
        FormBody formbody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(mContent).asyncPost(NetConfig.INDEX_PETTYLOAN_INFO, formbody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                    //Log.d(TAG, "获取可借额度和总额度，还款试算" + result);
                    // Log.d(TAG, "onSuccess:" + NetConfig.INDEX_PETTYLOAN_INFO + "content" + "=" + json);
                    try {
                        JSONObject object = new JSONObject(result);
                        returnCode = object.getString("returnCode");
                        returnMsg = object.getString("returnMsg");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    //极速抵押
    private void extremeMoreage() {
        String json = LoginTokenUtils.getJson();
        FormBody formBody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(getActivity()).asyncPost(NetConfig.OAUTH_SETP, formBody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                    // Log.d(TAG, "进度" + result);
                    try {
                        JSONObject object = new JSONObject(result);
                        returnCode1 = object.getString("returnCode");
                        returnMsg1 = object.getString("returnMsg");
                        if ("000000".equals(returnCode1)) {
                            phoneAuth = object.getInt("phoneAuth");
                            carInfoAuth = object.getInt("carInfoAuth");
                            bankInfoAuth = object.getInt("bankInfoAuth");
                            personalAuth = object.getInt("personalAuth");
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
