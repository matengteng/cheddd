package com.cheddd.bean;

/**
 * Created by Administrator on 2017/6/19 0019.
 */

public class RegisterBean {
    private String clientType;
    private String telNo;
    private String passWord;
    private String sms;

    public RegisterBean() {
    }

    public RegisterBean(String clientType, String sms, String passWord, String telNo) {
        this.clientType = clientType;
        this.sms = sms;
        this.passWord = passWord;
        this.telNo = telNo;
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

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    @Override
    public String toString() {
        return "RegisterBean{" +
                "clientType='" + clientType + '\'' +
                ", telNo='" + telNo + '\'' +
                ", passWord='" + passWord + '\'' +
                ", sms='" + sms + '\'' +
                '}';
    }
}
