package com.cheddd.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/6/16 0016.
 */

public class InfoLiveBean implements Parcelable {
    //客户端来源、用户令牌、住房状态、月租金、产权状态
    private String clientType;
    private String token;
    private int housingStatus;
    private int monthRent;
    private int propertyStatus;
    private String address;

    public InfoLiveBean() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public InfoLiveBean(String clientType, String token, int housingStatus, int monthRent, int propertyStatus, String address) {
        this.clientType = clientType;
        this.token = token;
        this.housingStatus = housingStatus;
        this.monthRent = monthRent;
        this.propertyStatus = propertyStatus;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public int getPropertyStatus() {
        return propertyStatus;
    }

    public void setPropertyStatus(int propertyStatus) {
        this.propertyStatus = propertyStatus;
    }

    public int getMonthRent() {
        return monthRent;
    }

    public void setMonthRent(int monthRent) {
        this.monthRent = monthRent;
    }

    public int getHousingStatus() {
        return housingStatus;
    }

    public void setHousingStatus(int housingStatus) {
        this.housingStatus = housingStatus;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "InfoLiveBean{" +
                "clientType='" + clientType + '\'' +
                ", token='" + token + '\'' +
                ", housingStatus=" + housingStatus +
                ", monthRent=" + monthRent +
                ", propertyStatus=" + propertyStatus +
                '}';
    }

    protected InfoLiveBean(Parcel in) {
        clientType = in.readString();
        token = in.readString();
        housingStatus = in.readInt();
        monthRent = in.readInt();
        propertyStatus = in.readInt();
    }

    public static final Creator<InfoLiveBean> CREATOR = new Creator<InfoLiveBean>() {
        @Override
        public InfoLiveBean createFromParcel(Parcel in) {
            return new InfoLiveBean(in);
        }

        @Override
        public InfoLiveBean[] newArray(int size) {
            return new InfoLiveBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(clientType);
        dest.writeString(token);
        dest.writeInt(housingStatus);
        dest.writeInt(monthRent);
        dest.writeInt(propertyStatus);
    }
}
