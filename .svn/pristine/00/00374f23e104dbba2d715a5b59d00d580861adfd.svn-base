package com.cheddd.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.cheddd.R;
import com.cheddd.adapter.RelationsAdapter;
import com.cheddd.base.MyBaseActivity;
import com.cheddd.bean.InfoRelation;
import com.cheddd.config.NetConfig;
import com.cheddd.utils.LoginTokenUtils;
import com.cheddd.utils.OkhttpUtils;
import com.cheddd.view.TopNavigationBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Request;

public class RelationsActivity extends MyBaseActivity implements AdapterView.OnItemClickListener {

    private static String TAG = RelationsActivity.class.getSimpleName();
    private TopNavigationBar mTnb;
    private ListView mListView;
    private RelationsAdapter mAdapter;
    private List<String> mData;
    private Button mButtonAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relations);
        initView();
        initData();
        setData();
        setListener();
    }

    private void setData() {
        mListView.setAdapter(mAdapter);
    }

    private void initData() {
        mData = new ArrayList<>();
       /* final String json = LoginTokenUtils.getJson();
        FormBody formbody = new FormBody.Builder().add("content", json).build();
        OkhttpUtils.getInstance(this).asyncPost(NetConfig.INFO_RELATION_INFO, formbody, new OkhttpUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result) {
                if (result != null) {
                    Log.d(TAG, "请求联系人" + result);
                    try {
                        JSONObject object = new JSONObject(result);
                        JSONArray rows = object.getJSONArray("rows");
                        for (int i = 0; i < rows.length(); i++) {
                            JSONObject jsonObject = rows.getJSONObject(i);
                            String contractName = jsonObject.getString("contractName");
                            String telNo = jsonObject.getString("telNo");
                            int relation = jsonObject.getInt("relation");
                            InfoRelation inforelatio = new InfoRelation();
                            inforelatio.setTelNo(telNo);
                            inforelatio.setRelation(relation + "");
                            inforelatio.setContractName(contractName);
                            mData.add(inforelatio);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });*/
        for (int i = 0; i < 3; i++) {
            mData.add(i+"");
        }
        mAdapter = new RelationsAdapter(mData, this);
    }


    private void setListener() {
        mListView.setOnItemClickListener(this);
        mTnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mTnb = (TopNavigationBar) findViewById(R.id.tnb_relations);
        mListView = (ListView) findViewById(R.id.lv_relations_addView);
        mButtonAdd = (Button) findViewById(R.id.bt_relations_add);
    }

   /* @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            mListView.dispatchTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }*/

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {

        }
    }

    public void onClick(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.bt_relations_add:
                    relationsAdd();
                    break;
            }
        }
    }

    //增加联系人
    private void relationsAdd() {

    }
}
