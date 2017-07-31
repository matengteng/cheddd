package com.cheddd.bean;

/**
 * Created by Administrator on 2017/6/5 0005.
 */

public class MineRecord {
    private String clientType;
    private String token;
    private double money;
    private String time;
    private String bank;
    private String mark;
    private String bankID;
    private String orderNo;
    private String transactionId;

    public MineRecord() {
    }

    public MineRecord(String clientType, String transactionId, String orderNo, String bankID, String mark, String bank, String time, String token, double money) {
        this.clientType = clientType;
        this.transactionId = transactionId;
        this.orderNo = orderNo;
        this.bankID = bankID;
        this.mark = mark;
        this.bank = bank;
        this.time = time;
        this.token = token;
        this.money = money;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getBankID() {
        return bankID;
    }

    public void setBankID(String bankID) {
        this.bankID = bankID;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "MineRecord{" +
                "clientType='" + clientType + '\'' +
                ", token='" + token + '\'' +
                ", money=" + money +
                ", time='" + time + '\'' +
                ", bank='" + bank + '\'' +
                ", mark='" + mark + '\'' +
                ", bankID='" + bankID + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", transactionId='" + transactionId + '\'' +
                '}';
    }
}
