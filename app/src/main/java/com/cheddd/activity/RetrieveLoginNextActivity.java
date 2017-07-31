package com.cheddd.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cheddd.R;
import com.cheddd.base.MyBaseActivity;
import com.cheddd.view.TopNavigationBar;

public class RetrieveLoginNextActivity extends MyBaseActivity implements TextWatcher, View.OnClickListener {

    private TopNavigationBar mTnb;
    private EditText mEditTextformer, mEditTextAffirm;
    private Button mButtonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_login_next);
        initView();
        setListener();
    }

    private void setListener() {
        mButtonSave.setOnClickListener(this);
        mTnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mEditTextAffirm.addTextChangedListener(this);
        mEditTextformer.addTextChangedListener(this);
    }

    private void initView() {
        mTnb = (TopNavigationBar) findViewById(R.id.tnb_retrieveLoginNext);
        mEditTextformer = (EditText) findViewById(R.id.et_retrieveLoginNext_former);
        mEditTextAffirm = (EditText) findViewById(R.id.et_retrieveLoginNext_afffirmformer);
        mButtonSave = (Button) findViewById(R.id.bt_retrieveLoginNext_save);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mEditTextformer.getText().toString().length() > 0)
            if (mEditTextAffirm.getText().toString().length() > 0) {
                mButtonSave.setEnabled(true);
            } else {
                mButtonSave.setEnabled(false);
            }
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.bt_retrieveLoginNext_save:
                    finish();
                    break;
            }
        }
    }
}
