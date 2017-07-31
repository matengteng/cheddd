package com.cheddd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cheddd.R;
import com.cheddd.base.MyBaseAdapter;
import com.cheddd.bean.MoreDotbean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/31 0031.
 */

public class MoreDotAdapter extends MyBaseAdapter {
    private List<MoreDotbean> mData;
    private Context mContext;

    public MoreDotAdapter(List data, Context mContext) {
        super(data);
        this.mData = data;
        this.mContext = mContext;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DotViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_more_dot, null);
            holder = new DotViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (DotViewHolder) convertView.getTag();
        }
        holder.mTextViewaddress.setText(mData.get(position).getAddress());
        holder.TextViewstoreName.setText(mData.get(position).getStoreName());
        return convertView;
    }

    public static class DotViewHolder {
        private TextView mTextViewaddress, TextViewstoreName;

        public DotViewHolder(View view) {
            mTextViewaddress = (TextView) view.findViewById(R.id.tv_moreDot_hand);
            TextViewstoreName = (TextView) view.findViewById(R.id.tv_moreDot_storeName);
        }
    }
}
