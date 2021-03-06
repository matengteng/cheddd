package com.cheddd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cheddd.R;
import com.cheddd.base.MyBaseAdapter;
import com.cheddd.bean.RepaymentPlan;

import java.util.List;

/**
 * Created by Administrator on 2017/6/6 0006.
 */

public class BorrowMoneyAdapter extends MyBaseAdapter {
    private Context mContext;
    private List<RepaymentPlan> mData;

    public BorrowMoneyAdapter(List data, Context mContext) {
        super(data);
        this.mData = data;
        this.mContext = mContext;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BorrowMoneyViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_borrowmoney, null);
            holder = new BorrowMoneyViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (BorrowMoneyViewHolder) convertView.getTag();
        }
        holder.money.setText(mData.get(position).getPrincipalAmt() + "");
        holder.insert.setText("含利息" + mData.get(position).getInterestAmt() + "");
        holder.time.setText(mData.get(position).getRepayTime());
        return convertView;
    }

    public static class BorrowMoneyViewHolder {
        private TextView time, insert, money;

        public BorrowMoneyViewHolder(View view) {
            time = (TextView) view.findViewById(R.id.tv_borrowmoney_adapter_time);
            insert = (TextView) view.findViewById(R.id.tv_borrowmoney_adapter_insert);
            money = (TextView) view.findViewById(R.id.tv_borrowmoney_adapter_money);
        }
    }
}
