package com.cheddd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cheddd.R;
import com.cheddd.base.MyBaseAdapter;
import com.cheddd.bean.IndexCheckBean;

import java.util.List;

import static com.cheddd.R.layout.adapter_refunddetail;

/**
 * Created by Administrator on 2017/5/31 0031.
 */

public class RefundDetailsAdapter extends MyBaseAdapter {
    private Context mCount;
    private List<IndexCheckBean> mData;

    public RefundDetailsAdapter(List data, Context mCount) {
        super(data);
        this.mData = data;
        this.mCount = mCount;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RefundViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mCount).inflate(adapter_refunddetail, null);
            holder = new RefundViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (RefundViewHolder) convertView.getTag();
        }
        holder.tvMoney.setText(mData.get(position).getRepaymentAmt()+"元");
        holder.tvTime.setText(mData.get(position).getRepaymentDate());
        holder.tvCount.setText("第" + mData.get(position).getNum() + "期");
        return convertView;
    }

    public static class RefundViewHolder {
        TextView tvTime, tvMoney, tvCount;

        public RefundViewHolder(View view) {
            tvTime = (TextView) view.findViewById(R.id.tv_refundAdapter_time);
            tvMoney = (TextView) view.findViewById(R.id.tv_refundadapter_money);
            tvCount = (TextView) view.findViewById(R.id.tv_refundadapter_count);
        }
    }
}
