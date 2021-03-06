package com.cheddd.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cheddd.R;
import com.cheddd.base.MyBaseAdapter;
import com.cheddd.bean.RefundRecordBean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/6 0006.
 * 还款记录的界面的适配器
 */

public class RefundRecordAdapter extends MyBaseAdapter {
    private List<RefundRecordBean> mData;
    private Context mContent;

    public RefundRecordAdapter(List data, Context mContent) {
        super(data);
        this.mData = data;
        this.mContent = mContent;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RefundRecordViewholder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContent).inflate(R.layout.adapter_refundrecord, null);
            holder = new RefundRecordViewholder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (RefundRecordViewholder) convertView.getTag();
        }
        holder.appoint.setText(mData.get(position).getAppoint());
        holder.reality.setText(mData.get(position).getReality());
        holder.refund.setText(mData.get(position).getRefund() + "");
        int stat = mData.get(position).getStat();
        if (2 == stat) {
            holder.stat.setText("提前还款");
        } else if (1 == stat) {
            holder.stat.setText("还款成功");
        } else {
            holder.stat.setText("还款中");
        }
        return convertView;
    }

    public static class RefundRecordViewholder {
        //约定还款日，实际还款日，还款金额，状态
        private TextView appoint;
        private TextView reality;
        private TextView refund;
        private TextView stat;

        public RefundRecordViewholder(View view) {
            appoint = (TextView) view.findViewById(R.id.tv_refundrecord_appoint);
            reality = (TextView) view.findViewById(R.id.tv_refundrecord_reality);
            refund = (TextView) view.findViewById(R.id.tv_refundrecord_refundmoney);
            stat = (TextView) view.findViewById(R.id.tv_refundrecord_stat);
        }
    }
}
