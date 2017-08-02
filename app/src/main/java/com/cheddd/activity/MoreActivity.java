package com.cheddd.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cheddd.R;
import com.cheddd.base.MyBaseActivity;
import com.cheddd.view.TopNavigationBar;

public class MoreActivity extends MyBaseActivity implements View.OnClickListener {

    //意见反馈、常见问题、关于我们、门店地址、服务热线
    private RelativeLayout mRelativeIdea, RelativeQuestion, RelativeMine, RelativeDot, RelativeServe;
    private TopNavigationBar mTnb;
    private Button mButtonAffire, mButtonCancel;
    private Dialog mDialog;
    private TextView mTextViewPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        initView();
        setListener();
    }

    private void setListener() {
        mRelativeIdea.setOnClickListener(this);
        RelativeQuestion.setOnClickListener(this);
        RelativeMine.setOnClickListener(this);
        RelativeDot.setOnClickListener(this);
        RelativeServe.setOnClickListener(this);
        mTnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mRelativeIdea = (RelativeLayout) findViewById(R.id.rl_more_idea);
        RelativeQuestion = (RelativeLayout) findViewById(R.id.rl_more_question);
        RelativeMine = (RelativeLayout) findViewById(R.id.rl_more_mine);
        RelativeDot = (RelativeLayout) findViewById(R.id.rl_more_dot);
        RelativeServe = (RelativeLayout) findViewById(R.id.rl_more_serve);
        mTnb = (TopNavigationBar) findViewById(R.id.tnb_more);
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.rl_more_dot:
                    startActivity(new Intent(this, MoreDotActivity.class));
                    break;
                case R.id.rl_more_question:
                    Intent intent1 = new Intent(this, MoreQuestionActivity.class);
                    intent1.putExtra("url", "http://47.93.163.237:9080/agreement/4.html");
                    startActivity(intent1);
                    break;
                case R.id.rl_more_idea:
                    startActivity(new Intent(this, MoreIdeaActivity.class));
                    break;
                case R.id.rl_more_mine:
                    startActivity(new Intent(this, MoreMineActivity.class));
                    break;
                case R.id.rl_more_serve:
                    serve();
                    break;
                case R.id.bt_more_affire:
                    affire();
                    break;
                case R.id.bt_more_calloff:
                    mDialog.dismiss();
                    break;
                default:
                    break;
            }
        }
    }

    private void affire() {
        cellPhone();
        mDialog.dismiss();
    }


    private void serve() {
        mDialog = new AlertDialog.Builder(this).create();
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_more_phone, null);
        mDialog.setCanceledOnTouchOutside(false);
        mButtonAffire = (Button) view.findViewById(R.id.bt_more_affire);
        mButtonCancel = (Button) view.findViewById(R.id.bt_more_calloff);
        mTextViewPhone = (TextView) view.findViewById(R.id.tv_more_call);
        Window window = mDialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();
        mDialog.getWindow().setContentView(view);
    }

    //拨打电话
    private void cellPhone() {
        if (ContextCompat.checkSelfPermission(MoreActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MoreActivity.this, Manifest.permission.CALL_PHONE)) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            } else {
                ActivityCompat.requestPermissions(MoreActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 100);
            }
        } else {
            CallPhone();
        }

    }


    private void CallPhone() {
        String number = mTextViewPhone.getText().toString().trim();
        if (TextUtils.isEmpty(number)) {
            Toast.makeText(MoreActivity.this, "号码不能为空！", Toast.LENGTH_SHORT).show();
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
                } else {
                    // 授权失败！
                    Toast.makeText(this, "授权失败！", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

}
