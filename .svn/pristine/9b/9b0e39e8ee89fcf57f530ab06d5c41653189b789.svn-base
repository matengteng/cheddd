package com.cheddd.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheddd.R;
import com.cheddd.base.MyBaseAdapter;
import com.cheddd.bean.MineBankBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/6/7 0007.
 */

public class MinebankAdapter extends MyBaseAdapter {

    private List<MineBankBean> mData;
    private Context mContext;

    public MinebankAdapter(List data, Context mContext) {
        super(data);
        this.mContext = mContext;
        this.mData = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MinebankViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_minebank, null);
            holder = new MinebankViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (MinebankViewHolder) convertView.getTag();
        }
        holder.number.setText(mData.get(position).getNumber());
        holder.mark.setText(mData.get(position).getMark());
        if (holder.mark.getText().toString().equals("交通银行 ")) {
            holder.icon.setImageResource(R.mipmap.bank_com);
            holder.mLayout.setBackgroundColor(Color.parseColor("#00367a "));
        } else if (holder.mark.getText().toString().equals("招商银行")) {
            holder.icon.setImageResource(R.mipmap.bank_cmbc);
            holder.mLayout.setBackgroundColor(Color.parseColor("#db323f"));
        } else if (holder.mark.getText().toString().equals("民生银行")) {
            holder.icon.setImageResource(R.mipmap.bank_cmsb);
            holder.mLayout.setBackgroundColor(Color.parseColor("#3690c0"));
        } else if (holder.mark.getText().toString().equals("农业银行")) {
            holder.icon.setImageResource(R.mipmap.bank_abc);
            holder.mLayout.setBackgroundColor(Color.parseColor("#00947e"));
        } else if (holder.mark.getText().toString().equals("上海银行")) {
            holder.icon.setImageResource(R.mipmap.bank_sh);
            holder.mLayout.setBackgroundColor(Color.parseColor("#203a87"));
        } else if (holder.mark.getText().toString().equals("广发银行")) {
            holder.icon.setImageResource(R.mipmap.bank_cgb);
            holder.mLayout.setBackgroundColor(Color.parseColor("#e60012"));
        } else if (holder.mark.getText().toString().equals("中国银行")) {
            holder.icon.setImageResource(R.mipmap.bank_boc);
            holder.mLayout.setBackgroundColor(Color.parseColor("#a4001d"));
        } else if (holder.mark.getText().toString().equals("建设银行")) {
            holder.icon.setImageResource(R.mipmap.bank_ccb);
            holder.mLayout.setBackgroundColor(Color.parseColor("#0e3192"));
        } else if (holder.mark.getText().toString().equals("兴业银行")) {
            holder.icon.setImageResource(R.mipmap.bank_cib);
            holder.mLayout.setBackgroundColor(Color.parseColor("#004187"));
        } else if (holder.mark.getText().toString().equals("中信银行")) {
            holder.icon.setImageResource(R.mipmap.bank_citic);
            holder.mLayout.setBackgroundColor(Color.parseColor("#d6000f"));
        } else if (holder.mark.getText().toString().equals("华夏银行")) {
            holder.icon.setImageResource(R.mipmap.bank_hxb);
            holder.mLayout.setBackgroundColor(Color.parseColor("#f5130b"));
        } else if (holder.mark.getText().toString().equals("平安银行")) {
            holder.icon.setImageResource(R.mipmap.bank_pa);
            holder.mLayout.setBackgroundColor(Color.parseColor("#e76e12"));
        } else if (holder.mark.getText().toString().equals("工商银行")) {
            holder.icon.setImageResource(R.mipmap.bank_icbc);
            holder.mLayout.setBackgroundColor(Color.parseColor("#c4110a"));
        } else if (holder.mark.getText().toString().equals("光大银行 ")) {
            holder.icon.setImageResource(R.mipmap.bank_ceb);
            holder.mLayout.setBackgroundColor(Color.parseColor("#2f224e"));
        } else if (holder.mark.getText().toString().equals("邮储银行")) {
            holder.icon.setImageResource(R.mipmap.bank_yz);
            holder.mLayout.setBackgroundColor(Color.parseColor("#04632f"));
        } else if (holder.mark.getText().toString().equals("浦发银行")) {
            holder.icon.setImageResource(R.mipmap.bank_spdp);
            holder.mLayout.setBackgroundColor(Color.parseColor("#003473"));
        } else {

        }
        return convertView;
    }

    public static class MinebankViewHolder {
        //背景图片 图标
        private ImageView bigMap, icon;
        //银行、卡号
        private TextView mark, number;
        private RelativeLayout mLayout;

        //login_sms.setBackgroundColor(Color.parseColor("#C5C5C5"));
        public MinebankViewHolder(View view) {
            bigMap = (ImageView) view.findViewById(R.id.iv_minebank_bigmap);
            icon = (ImageView) view.findViewById(R.id.iv_minebank_icon);
            mark = (TextView) view.findViewById(R.id.tv_minebank_bank);
            number = (TextView) view.findViewById(R.id.tv_minebank_number);
            mLayout = (RelativeLayout) view.findViewById(R.id.rl_minebank_back);
        }
    }
}
