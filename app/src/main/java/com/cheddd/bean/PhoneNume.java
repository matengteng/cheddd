package com.cheddd.bean;

/**
 * Created by Administrator on 2017/6/19 0019.
 * 获取验证码的手机号码
 */

public class PhoneNume {
    private String clientType;
    private String telNo;

    public PhoneNume() {
    }

    public PhoneNume(String clientType, String telNo) {
        this.clientType = clientType;
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

    @Override
    public String toString() {
        return "PhoneNume{" +
                "clientType='" + clientType + '\'' +
                ", telNo='" + telNo + '\'' +
                '}';
    }
}
