package com.cheddd.bean;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public class PasswordLogin {
    private String clientType;
    private String telNo;
    private String passWord;

    public PasswordLogin() {
    }

    public PasswordLogin(String clientType, String passWord, String telNo) {
        this.clientType = clientType;
        this.passWord = passWord;
        this.telNo = telNo;
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

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String toString() {
        return "PasswordLogin{" +
                "clientType='" + clientType + '\'' +
                ", telNo='" + telNo + '\'' +
                ", passWord='" + passWord + '\'' +
                '}';
    }
}
