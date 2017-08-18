package com.cheddd.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheddd.R;
import com.cheddd.activity.AdvanceActivtiy;
import com.cheddd.activity.BorrowMoneyActivtiy;
import com.cheddd.application.MyApplications;
import com.cheddd.base.MyBaseAdapter;
import com.cheddd.bean.LoanBean;
import com.cheddd.bean.MoreLoansBean;
import com.cheddd.fragment.MoreLoansFragment;

import java.util.List;

import static com.cheddd.R.layout.adapter_refunddetail;

/**
 * Created by Administrator on 2017/6/5 0005.
 */

public class MoreLoanAdapter extends BaseExpandableListAdapter {
    private List<MoreLoansBean> mData;
    private Context mContext;
    private OnGroupItemClickListener onGroupItemClickListener;

    public void setOnGroupItemClickListener(OnGroupItemClickListener onGroupItemClickListener) {
        this.onGroupItemClickListener = onGroupItemClickListener;
    }

    public MoreLoanAdapter(List<MoreLoansBean> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getGroupCount() {

        return mData == null ? 0 : mData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mData.get(groupPosition).getRepaymentPlan() == null ? 0 : mData.get(groupPosition).getRepaymentPlan().size();
    }

    @Override
    public Object getGroup(int groupPosition) {

        return mData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mData.get(groupPosition).getRepaymentPlan().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        MoreLoanGroupIdViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_refund, null);
            holder = new MoreLoanGroupIdViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (MoreLoanGroupIdViewHolder) convertView.getTag();
        }
        holder.mTextViewIndent.setText(mData.get(groupPosition).getOrderNo());

        holder.mTextViewtime.setText(mData.get(groupPosition).getInterestTime());
        holder.mTextViewMoney.setText(mData.get(groupPosition).getContractAmt() + "元");
        holder.mTextViewMonth.setText(mData.get(groupPosition).getLoanCycle());
        holder.mTextViewInsert.setText(mData.get(groupPosition).getLoanRate() + "%");
        holder.mTextViewRefund.setText(mData.get(groupPosition).getSurplusPeriod() + "期");
        holder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onGroupItemClickListener != null){
                   onGroupItemClickListener.onGroupItemClick(groupPosition,v);
                }
            }
        });

        int interestType = mData.get(groupPosition).getInterestType();
        if (interestType == 0) {
            holder.mTextViewWay.setText("现金分期");
            holder.mMonthDay.setText("月利息");
        } else if (interestType == 1) {
            holder.mTextViewWay.setText("单期借款");
            holder.mMonthDay.setText("日利息");
        } else {
            holder.mTextViewWay.setText("现金分期");
        }
        holder.mTextViewalready.setText(mData.get(groupPosition).getAlreadyRepayAmt() + "");
        holder.mTextViewstay.setText(mData.get(groupPosition).getStayRepayAmt() + "");
        holder.mTextViewcurrent.setText(mData.get(groupPosition).getThisRepayAmt() + "");
        holder.mRelative.setTag(holder);
        holder.mRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoreLoanGroupIdViewHolder tag = (MoreLoanGroupIdViewHolder) v.getTag();
                mContext.startActivity(new Intent(mContext, AdvanceActivtiy.class).putExtra("order",tag.mTextViewIndent.getText().toString()));
            }
        });
        return convertView;
    }

   public interface OnGroupItemClickListener{
        void onGroupItemClick(int position,View view);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        MoreLoanChildViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_borrowmoney, null);
            holder = new MoreLoanChildViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (MoreLoanChildViewHolder) convertView.getTag();
        }
        holder.insert.setText("含利息" + mData.get(groupPosition).getRepaymentPlan().get(childPosition).getInterestAmt() + "");
        holder.time.setText(mData.get(groupPosition).getRepaymentPlan().get(childPosition).getRepayTime());
        holder.money.setText(mData.get(groupPosition).getRepaymentPlan().get(childPosition).getPrincipalAmt() + "");
        MoreLoansFragment.setMoney(mData.get(groupPosition).getThisRepayAmt() + "");
        return convertView;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public static class MoreLoanGroupIdViewHolder {
        //订单号、借款时间、借款金额、借款时长、月利率、还款期数、还款方式
        private TextView mTextViewIndent, mTextViewtime, mTextViewMoney, mTextViewMonth, mTextViewInsert, mTextViewRefund, mTextViewWay;
        //还款明细
        private TextView mTextViewList,mMonthDay;
        //已还金额，待还金额，本期应还
        private TextView mTextViewalready, mTextViewstay, mTextViewcurrent;
        private RelativeLayout mRelative,mRelativeLayout;

        public MoreLoanGroupIdViewHolder(View view) {
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
            mRelative = (RelativeLayout) view.findViewById(R.id.rl_refund_advance);
            mRelativeLayout= (RelativeLayout) view.findViewById(R.id.adapter_refund);
            mMonthDay= (TextView) view.findViewById(R.id.tv_monthDay);
        }
    }

    public static class MoreLoanChildViewHolder {
        private TextView time, insert, money;

        public MoreLoanChildViewHolder(View view) {
            time = (TextView) view.findViewById(R.id.tv_borrowmoney_adapter_time);
            insert = (TextView) view.findViewById(R.id.tv_borrowmoney_adapter_insert);
            money = (TextView) view.findViewById(R.id.tv_borrowmoney_adapter_money);
        }
    }
}
