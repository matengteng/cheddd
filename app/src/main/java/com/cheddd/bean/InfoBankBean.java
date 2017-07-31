package com.cheddd.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/6/29 0029.
 */

public class InfoBankBean implements Parcelable{
    private String clientType;
    private String token;
    private String custName;
    private String khBank;
    private String cardNo;
    private String signTelNo;
    private String khBankPro;
    private String khBankCity;
    private String khBankName;
    private String bankId;

    public InfoBankBean() {
    }

    public InfoBankBean(String clientType, String bankId, String khBankName, String khBankCity, String khBankPro, String signTelNo, String cardNo, String khBank, String custName, String token) {
        this.clientType = clientType;
        this.bankId = bankId;
        this.khBankName = khBankName;
        this.khBankCity = khBankCity;
        this.khBankPro = khBankPro;
        this.signTelNo = signTelNo;
        this.cardNo = cardNo;
        this.khBank = khBank;
        this.custName = custName;
        this.token = token;
    }

    protected InfoBankBean(Parcel in) {
        clientType = in.readString();
        token = in.readString();
        custName = in.readString();
        khBank = in.readString();
        cardNo = in.readString();
        signTelNo = in.readString();
        khBankPro = in.readString();
        khBankCity = in.readString();
        khBankName = in.readString();
        bankId = in.readString();
    }

    public static final Creator<InfoBankBean> CREATOR = new Creator<InfoBankBean>() {
        @Override
        public InfoBankBean createFromParcel(Parcel in) {
            return new InfoBankBean(in);
        }

        @Override
        public InfoBankBean[] newArray(int size) {
            return new InfoBankBean[size];
        }
    };

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getKhBankName() {
        return khBankName;
    }

    public void setKhBankName(String khBankName) {
        this.khBankName = khBankName;
    }

    public String getKhBankCity() {
        return khBankCity;
    }

    public void setKhBankCity(String khBankCity) {
        this.khBankCity = khBankCity;
    }

    public String getKhBankPro() {
        return khBankPro;
    }

    public void setKhBankPro(String khBankPro) {
        this.khBankPro = khBankPro;
    }

    public String getSignTelNo() {
        return signTelNo;
    }

    public void setSignTelNo(String signTelNo) {
        this.signTelNo = signTelNo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getKhBank() {
        return khBank;
    }

    public void setKhBank(String khBank) {
        this.khBank = khBank;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "InfoBankBean{" +
                "clientType='" + clientType + '\'' +
                ", token='" + token + '\'' +
                ", custName='" + custName + '\'' +
                ", khBank='" + khBank + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", signTelNo='" + signTelNo + '\'' +
                ", khBankPro='" + khBankPro + '\'' +
                ", khBankCity='" + khBankCity + '\'' +
                ", khBankName='" + khBankName + '\'' +
                ", bankId='" + bankId + '\'' +
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
        dest.writeString(custName);
        dest.writeString(khBank);
        dest.writeString(cardNo);
        dest.writeString(signTelNo);
        dest.writeString(khBankPro);
        dest.writeString(khBankCity);
        dest.writeString(khBankName);
        dest.writeString(bankId);
    }
}
