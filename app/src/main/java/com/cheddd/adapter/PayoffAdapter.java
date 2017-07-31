package com.cheddd.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheddd.R;
import com.cheddd.activity.RefundRecordActivity;
import com.cheddd.base.MyBaseAdapter;
import com.cheddd.bean.LoanBean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/6 0006.
 */

public class PayoffAdapter extends MyBaseAdapter implements View.OnClickListener {
    private List<LoanBean> mData;
    private Context mContext;
    private String orderNo;

    public PayoffAdapter(List data, Context mContext) {
        super(data);
        this.mData = data;
        this.mContext = mContext;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PayOffViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_payoff, null);
            holder = new PayOffViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (PayOffViewHolder) convertView.getTag();
        }
        orderNo = mData.get(position).getOrderNo();
        holder.mTextViewIndent.setText(orderNo);
        holder.mTextViewtime.setText(mData.get(position).getInterestTime());
        holder.mTextViewMoney.setText(mData.get(position).getContractAmt() + "元");
        holder.mTextViewMonth.setText(mData.get(position).getLoanCycle());
        holder.mTextViewInsert.setText(mData.get(position).getLoanRate() + "%");
        holder.mTextViewRefund.setText(mData.get(position).getLoanPeriod() + "期");
        int interestType = mData.get(position).getInterestType();
        if (0 == interestType) {
            holder.mTextViewWay.setText("现金分期");
            holder.mMonthDay.setText("月利率");
        } else {
            holder.mTextViewWay.setText("单期还款");
            holder.mMonthDay.setText("日利率");
        }
        holder.mTextViewList.setOnClickListener(this);
        holder.mRelative.setOnClickListener(this);
        return convertView;
    }


    public static class PayOffViewHolder {
        //订单号、借款时间、借款金额、借款时长、月利率、还款期数、还款方式
        private TextView mTextViewIndent, mTextViewtime, mTextViewMoney, mTextViewMonth, mTextViewInsert, mTextViewRefund, mTextViewWay;
        //还款明细
        private TextView mTextViewList;
        //跳转到还款记录的界面
        private RelativeLayout mRelative;
        private TextView mMonthDay;

        public PayOffViewHolder(View view) {
            mTextViewIndent = (TextView) view.findViewById(R.id.tv_payoff_indent);
            mTextViewtime = (TextView) view.findViewById(R.id.tv_payoff_time);
            mTextViewMoney = (TextView) view.findViewById(R.id.tv_payoff_money);
            mTextViewMonth = (TextView) view.findViewById(R.id.tv_payoff_month);
            mTextViewInsert = (TextView) view.findViewById(R.id.tv_payoff_interest);
            mTextViewRefund = (TextView) view.findViewById(R.id.tv_payoff_refund);
            mTextViewWay = (TextView) view.findViewById(R.id.tv_payoff_way);
            mTextViewList = (TextView) view.findViewById(R.id.tv_payoff_list);
            mRelative = (RelativeLayout) view.findViewById(R.id.rl_payoff_record);
            mMonthDay= (TextView) view.findViewById(R.id.tv_montn_day);
        }
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                //还款明细
                case R.id.tv_payoff_list:
                    break;
                case R.id.rl_payoff_record:
                    Intent intent = new Intent(mContext, RefundRecordActivity.class);
                    intent.putExtra("orderNo", orderNo);
                    mContext.startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    }
}
