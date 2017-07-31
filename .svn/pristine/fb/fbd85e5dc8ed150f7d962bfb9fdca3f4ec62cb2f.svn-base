package com.cheddd.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public class InfoWorkBean implements Parcelable{
    //客户端来源,用户令牌,工作单位,单位地址,单位电话,工作年限,职位，月收入
    private String clientType;
    private String token;
    private String company;
    private String comAddress;
    private String comTelno;
    private int experience;
    private String position;
    private int monthIncome;

    public InfoWorkBean() {
    }

    public InfoWorkBean(String clientType, String token, String company, String comAddress, String comTelno, int experience, String position, int monthIncome) {
        this.clientType = clientType;
        this.token = token;
        this.company = company;
        this.comAddress = comAddress;
        this.comTelno = comTelno;
        this.experience = experience;
        this.position = position;
        this.monthIncome = monthIncome;
    }

    protected InfoWorkBean(Parcel in) {
        clientType = in.readString();
        token = in.readString();
        company = in.readString();
        comAddress = in.readString();
        comTelno = in.readString();
        experience = in.readInt();
        position = in.readString();
        monthIncome = in.readInt();
    }

    public static final Creator<InfoWorkBean> CREATOR = new Creator<InfoWorkBean>() {
        @Override
        public InfoWorkBean createFromParcel(Parcel in) {
            return new InfoWorkBean(in);
        }

        @Override
        public InfoWorkBean[] newArray(int size) {
            return new InfoWorkBean[size];
        }
    };

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public int getMonthIncome() {
        return monthIncome;
    }

    public void setMonthIncome(int monthIncome) {
        this.monthIncome = monthIncome;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getComTelno() {
        return comTelno;
    }

    public void setComTelno(String comTelno) {
        this.comTelno = comTelno;
    }

    public String getComAddress() {
        return comAddress;
    }

    public void setComAddress(String comAddress) {
        this.comAddress = comAddress;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "InfoWorkBean{" +
                "clientType='" + clientType + '\'' +
                ", token='" + token + '\'' +
                ", company='" + company + '\'' +
                ", comAddress='" + comAddress + '\'' +
                ", comTelno='" + comTelno + '\'' +
                ", experience=" + experience +
                ", position='" + position + '\'' +
                ", monthIncome=" + monthIncome +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(clientType);
        dest.writeString(token);
        dest.writeString(company);
        dest.writeString(comAddress);
        dest.writeString(comTelno);
        dest.writeInt(experience);
        dest.writeString(position);
        dest.writeInt(monthIncome);
    }
}
