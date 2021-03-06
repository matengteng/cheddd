package com.cheddd.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cheddd.R;
import com.cheddd.base.MyBaseAdapter;
import com.cheddd.bean.MineRecord;

import java.util.List;

/**
 * Created by Administrator on 2017/6/5 0005.
 * 交易记录的适配器（还款记录，借款记录）
 */

public class MineRecordAdapter extends MyBaseAdapter {

    private Context mContext;
    private List<MineRecord> mData;

    public MineRecordAdapter(List data, Context mContext) {
        super(data);
        this.mContext = mContext;
        this.mData = data;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MineRecordViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_mine_record, null);
            holder = new MineRecordViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (MineRecordViewHolder) convertView.getTag();
        }
        holder.money.setText(mData.get(position).getMoney() + "元");
        holder.bank.setText(mData.get(position).getBank() + "(" + mData.get(position).getBankID() + ")");
        holder.time.setText(mData.get(position).getTime());
        holder.mark.setText(mData.get(position).getMark());
        if ("审核中".equals(holder.mark.getText().toString())||"还款失败".equals(holder.mark.getText().toString())) {
            holder.mark.setTextColor(Color.parseColor("#E83D2E"));
        } else if("还款中".equals(holder.mark.getText().toString())||"还款成功".equals(holder.mark.getText().toString())
                ||"提前还款".equals(holder.mark.getText().toString())||"放款中".equals(holder.mark.getText().toString())){
            holder.mark.setTextColor(Color.parseColor("#24CA86"));
        }else if("放款成功".equals(holder.mark.getText().toString())){
            holder.mark.setTextColor(Color.parseColor("#999999"));
        }
        return convertView;
    }

    public static class MineRecordViewHolder {
        TextView money, mark, time, bank;

        public MineRecordViewHolder(View view) {
            money = (TextView) view.findViewById(R.id.tv_minerecordadapter_money);
            mark = (TextView) view.findViewById(R.id.tv_minerecordadapter_mark);
            time = (TextView) view.findViewById(R.id.tv_minerecordadapter_time);
            bank = (TextView) view.findViewById(R.id.tv_minerecordadapter_bank);
        }
    }
}
