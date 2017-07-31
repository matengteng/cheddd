package com.cheddd.bean;

/**
 * Created by Administrator on 2017/7/10 0010.
 */

public class AmendPayBean {
    private String clientType;
    private String token;
    private String payPassWord;
    private String oldPayPassWord;
    private String telNo;

    public AmendPayBean() {
    }

    public AmendPayBean(String clientType, String telNo, String oldPayPassWord, String payPassWord, String token) {
        this.clientType = clientType;
        this.telNo = telNo;
        this.oldPayPassWord = oldPayPassWord;
        this.payPassWord = payPassWord;
        this.token = token;
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

    public String getOldPayPassWord() {
        return oldPayPassWord;
    }

    public void setOldPayPassWord(String oldPayPassWord) {
        this.oldPayPassWord = oldPayPassWord;
    }

    public String getPayPassWord() {
        return payPassWord;
    }

    public void setPayPassWord(String payPassWord) {
        this.payPassWord = payPassWord;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "AmendPayBean{" +
                "clientType='" + clientType + '\'' +
                ", token='" + token + '\'' +
                ", payPassWord='" + payPassWord + '\'' +
                ", oldPayPassWord='" + oldPayPassWord + '\'' +
                ", telNo='" + telNo + '\'' +
                '}';
    }
}
