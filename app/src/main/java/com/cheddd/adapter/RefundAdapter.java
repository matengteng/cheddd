package com.cheddd.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheddd.R;
import com.cheddd.activity.BorrowMoneyActivtiy;
import com.cheddd.base.MyBaseAdapter;
import com.cheddd.bean.LoanBean;
import com.cheddd.bean.MoreLoansBean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/5 0005.
 */

public class RefundAdapter extends BaseExpandableListAdapter {
    private List<MoreLoansBean> mData;
    private Context mContext;

    public RefundAdapter(List<MoreLoansBean> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getGroupCount() {
        return mData==null?0:mData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }




  /*  @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RefundViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_refund, null);
            holder = new RefundViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (RefundViewHolder) convertView.getTag();
        }
        *//*holder.mTextViewIndent.setText(mData.get(position).getIndent());
        holder.mTextViewtime.setText(mData.get(position).getTime());
        holder.mTextViewMoney.setText(mData.get(position).getMoney());
        holder.mTextViewMonth.setText(mData.get(position).getMonth());
        holder.mTextViewInsert.setText(mData.get(position).getInterest());
        holder.mTextViewRefund.setText(mData.get(position).getRefund());
        holder.mTextViewWay.setText(mData.get(position).getWay());
        holder.mTextViewalready.setText(mData.get(position).getAlready());
        holder.mTextViewstay.setText(mData.get(position).getStay());
        holder.mTextViewcurrent.setText(mData.get(position).getCurrent());*//*
        holder.mTextViewList.setOnClickListener(this);
        holder.mRelative.setOnClickListener(this);
        return convertView;
    }


    public static class RefundViewHolder {
        //订单号、借款时间、借款金额、借款时长、月利率、还款期数、还款方式
        private TextView mTextViewIndent, mTextViewtime, mTextViewMoney, mTextViewMonth, mTextViewInsert, mTextViewRefund, mTextViewWay;
        //还款明细
        private TextView mTextViewList;
        //已还金额，待还金额，本期应还
        private TextView mTextViewalready, mTextViewstay, mTextViewcurrent;
        private RelativeLayout mRelative;

        public RefundViewHolder(View view) {
            mTextViewIndent = (TextView) view.findViewById(R.id.tv_refund_indent);
            mTextViewtime = (TextView) view.findViewById(R.id.tv_refund_time);
            mTextViewMoney = (TextView) view.findViewById(R.id.tv_refund_money);
            mTextViewMonth = (TextView) view.findViewById(R.id.tv_refund_month);
            mTextViewInsert = (TextView) view.findViewById(R.id.tv_refund_interest);
            mTextViewRefund = (TextView) view.findViewById(R.id.tv_refund_refund);
            mTextViewWay = (TextView) view.findViewById(R.id.tv_refund_way);
            mTextViewList = (TextView) view.findViewById(R.id.tv_refund_list);
            mTextViewalready = (TextView) view.findViewById(R.id.tv_refund_already_money);
            mTextViewstay = (TextView) view.findViewById(R.id.tv_refund_stay_money);
            mTextViewcurrent = (TextView) view.findViewById(R.id.tv_refund_current_money);
            mRelative = (RelativeLayout) view.findViewById(R.id.rl_refund_current);
        }
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                //还款明细
                case R.id.tv_refund_list:
                    break;
                case R.id.rl_refund_current:
                    mContext.startActivity(new Intent(mContext, BorrowMoneyActivtiy.class));
                    break;
                default:
                    break;
            }
        }
    }*/
}
