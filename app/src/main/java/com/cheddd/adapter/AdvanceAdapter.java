package com.cheddd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.cheddd.R;
import com.cheddd.base.MyBaseAdapter;
import com.cheddd.bean.AdvanceBean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/7 0007.
 */

public class AdvanceAdapter extends MyBaseAdapter {
    private List<AdvanceBean> mData;
    private Context mContext;
    private boolean mMode;

    public AdvanceAdapter(List data, Context mContext) {
        super(data);
        this.mData = data;
        this.mContext = mContext;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        AdvanceViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_advance, null);
            holder = new AdvanceViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (AdvanceViewHolder) convertView.getTag();
        }
        holder.mTextViewIndent.setText(mData.get(position).getIndent());
        holder.mTextViewTime.setText(mData.get(position).getTime());
        holder.mTextViewRefund.setText("贷款" + mData.get(position).getRefund() + "");
        holder.mTextViewRespond.setText(mData.get(position).getRespond() + "");
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mContext instanceof OnItemCheckedChangeListener)
                    ((OnItemCheckedChangeListener) mContext).onCheckedChanged(buttonView, isChecked, position);
            }
        });
        holder.mCheckBox.setChecked(mData.get(position).isChecked);
        return convertView;
    }


    public interface OnItemCheckedChangeListener {
        void onCheckedChanged(CompoundButton button, boolean isChecked, int position);
    }

    public static class AdvanceViewHolder {
        //订单号 、时间、贷款 、应还
        private TextView mTextViewIndent, mTextViewTime, mTextViewRefund, mTextViewRespond;
        private CheckBox mCheckBox;

        public AdvanceViewHolder(View view) {
            mTextViewIndent = (TextView) view.findViewById(R.id.tv_advance_indent);
            mTextViewTime = (TextView) view.findViewById(R.id.tv_advance_time);
            mTextViewRefund = (TextView) view.findViewById(R.id.tv_advance_refund);
            mTextViewRespond = (TextView) view.findViewById(R.id.tv_advance_respond);
            mCheckBox = (CheckBox) view.findViewById(R.id.cb_advance_check);
        }
    }

    public void setMode(boolean mode) {
        this.mMode = mode;
        notifyDataSetChanged();
    }

    public int getMoney() {
        int money = 0;
        if (mData == null || mData.size() == 0) {
            return money;
        }
        for (int i = 0; i < mData.size(); i++) {
            AdvanceBean advanceBean = mData.get(i);
            if (advanceBean.isChecked) {
                double itemMoney = advanceBean.getRespond();
                money += itemMoney;
            }
        }
        return money;
    }
}