package com.cheddd.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/6/22 0022.
 */

public class InfoCarBean implements Parcelable {
    //客户端来源、用户令牌、用户手机号、车辆信息编号、车辆品牌、车牌号、公里数、是否有抵押、上牌时间
    private String clientType;
    private String token;
    private String telNo;
    private String id;
    private String brand;
    private String license;
    private String mileage;
    private String mortgageFlag;
    private String registrationDate;

    public InfoCarBean() {
    }

    public InfoCarBean(String clientType, String token, String telNo, String id, String brand, String license, String mileage, String mortgageFlag, String registrationDate) {
        this.clientType = clientType;
        this.token = token;
        this.telNo = telNo;
        this.id = id;
        this.brand = brand;
        this.license = license;
        this.mileage = mileage;
        this.mortgageFlag = mortgageFlag;
        this.registrationDate = registrationDate;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getMortgageFlag() {
        return mortgageFlag;
    }

    public void setMortgageFlag(String mortgageFlag) {
        this.mortgageFlag = mortgageFlag;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "InfoCarBean{" +
                "clientType='" + clientType + '\'' +
                ", token='" + token + '\'' +
                ", telNo='" + telNo + '\'' +
                ", id='" + id + '\'' +
                ", brand='" + brand + '\'' +
                ", license='" + license + '\'' +
                ", mileage='" + mileage + '\'' +
                ", mortgageFlag='" + mortgageFlag + '\'' +
                ", registrationDate='" + registrationDate + '\'' +
                '}';
    }

    protected InfoCarBean(Parcel in) {
        clientType = in.readString();
        token = in.readString();
        telNo = in.readString();
        id = in.readString();
        brand = in.readString();
        license = in.readString();
        mileage = in.readString();
        mortgageFlag = in.readString();
        registrationDate = in.readString();
    }

    public static final Creator<InfoCarBean> CREATOR = new Creator<InfoCarBean>() {
        @Override
        public InfoCarBean createFromParcel(Parcel in) {
            return new InfoCarBean(in);
        }

        @Override
        public InfoCarBean[] newArray(int size) {
            return new InfoCarBean[size];
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
        dest.writeString(telNo);
        dest.writeString(id);
        dest.writeString(brand);
        dest.writeString(license);
        dest.writeString(mileage);
        dest.writeString(mortgageFlag);
        dest.writeString(registrationDate);
    }
}
