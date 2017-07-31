package com.cheddd.bean;

/**
 * Created by Administrator on 2017/7/10 0010.
 */

public class SetpaymentBean {
    private String clientType;
    private String token;
    private String payPassWord;

    public SetpaymentBean() {
    }

    public SetpaymentBean(String clientType, String payPassWord, String token) {
        this.clientType = clientType;
        this.payPassWord = payPassWord;
        this.token = token;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
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
        return "SetpaymentBean{" +
                "clientType='" + clientType + '\'' +
                ", token='" + token + '\'' +
                ", payPassWord='" + payPassWord + '\'' +
                '}';
    }
}
