package com.cheddd.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/6/22 0022.
 */

public class InfoStatBean implements Parcelable {
    //客户端来源、用户手机号、用户令牌、客户姓名、身份证、婚姻状况、性别、学历、详细地址、省、市
    private String clientType;
    private String telNo;
    private String token;
    private String custName;
    private String identityCode;
    private int sex;
    private int marriageStatus;
    private String degree;
    private String registerCity;
    private String province;
    private String city;


    public InfoStatBean() {
    }

    protected InfoStatBean(Parcel in) {
        clientType = in.readString();
        telNo = in.readString();
        token = in.readString();
        custName = in.readString();
        identityCode = in.readString();
        sex = in.readInt();
        marriageStatus = in.readInt();
        degree = in.readString();
        registerCity = in.readString();
        province = in.readString();
        city = in.readString();
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
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

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getIdentityCode() {
        return identityCode;
    }

    public void setIdentityCode(String identityCode) {
        this.identityCode = identityCode;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getMarriageStatu() {
        return marriageStatus;
    }

    public void setMarriageStatu(int marriageStatu) {
        this.marriageStatus = marriageStatu;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getRegisterCity() {
        return registerCity;
    }

    public void setRegisterCity(String registerCity) {
        this.registerCity = registerCity;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public InfoStatBean(String clientType, String city, String province, String registerCity, String degree, int marriageStatu, int sex, String identityCode, String custName, String token, String telNo) {
        this.clientType = clientType;
        this.city = city;
        this.province = province;
        this.registerCity = registerCity;
        this.degree = degree;
        this.marriageStatus = marriageStatu;
        this.sex = sex;
        this.identityCode = identityCode;
        this.custName = custName;
        this.token = token;
        this.telNo = telNo;
    }

    public static final Creator<InfoStatBean> CREATOR = new Creator<InfoStatBean>() {
        @Override
        public InfoStatBean createFromParcel(Parcel in) {
            return new InfoStatBean(in);
        }

        @Override
        public InfoStatBean[] newArray(int size) {
            return new InfoStatBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(clientType);
        dest.writeString(telNo);
        dest.writeString(token);
        dest.writeString(custName);
        dest.writeString(identityCode);
        dest.writeInt(sex);
        dest.writeInt(marriageStatus);
        dest.writeString(degree);
        dest.writeString(registerCity);
        dest.writeString(province);
        dest.writeString(city);
    }
}
