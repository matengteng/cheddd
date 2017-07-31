package com.cheddd.adapter;

import android.content.Context;
import android.content.pm.LabeledIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cheddd.R;
import com.cheddd.base.MyBaseAdapter;
import com.cheddd.bean.InfoRelation;

import java.util.List;

/**
 * Created by Administrator on 2017/7/1 0001.
 */

public class RelationsAdapter extends MyBaseAdapter {
    private Context mContent;
    private List<String> mData;

    public RelationsAdapter(List data, Context mContent) {
        super(data);
        this.mContent = mContent;
        this.mData = data;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RelationsViewHolder holder = null;
        int count=0;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContent).inflate(R.layout.relations_emergency, null);
            holder = new RelationsViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (RelationsViewHolder) convertView.getTag();
        }
      /*  holder.mTextViewRelation.setText(mData.get(position).getRelation());
        holder.mEditTextName.setText(mData.get(position).getContractName());
        holder.mEditTextPhone.setText(mData.get(position).getTelNo());*/

        return convertView;
    }

    public static class RelationsViewHolder {
        private TextView mTextViewRelation;
        private EditText mEditTextPhone, mEditTextName;
        private ImageView mImageViewCall;

        public RelationsViewHolder(View view) {
            mEditTextName = (EditText) view.findViewById(R.id.et_relation_name);
            mImageViewCall = (ImageView) view.findViewById(R.id.iv_relation_cellPhone);
            mEditTextPhone = (EditText) view.findViewById(R.id.et_relation_phone);
            mTextViewRelation = (TextView) view.findViewById(R.id.tv_relation_relation);
        }
    }
}
