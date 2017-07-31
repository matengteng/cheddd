package com.cheddd.bean;

/**
 * Created by Administrator on 2017/6/19 0019.
 */

public class PhoneTrend {
    private String token;
    private String clientType;
    private String telNo;
    private String sms;

    public PhoneTrend() {
    }

    public PhoneTrend(String token, String sms, String telNo, String clientType) {
        this.token = token;
        this.sms = sms;
        this.telNo = telNo;
        this.clientType = clientType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    @Override
    public String toString() {
        return "PhoneTrend{" +
                "token='" + token + '\'' +
                ", clientType='" + clientType + '\'' +
                ", telNo='" + telNo + '\'' +
                ", sms='" + sms + '\'' +
                '}';
    }
}
