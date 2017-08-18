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
    private int orderType;

    public MineRecord() {
    }

    public MineRecord(String clientType, String token, double money, String time, String bank, String mark, String bankID, String orderNo, String transactionId, int orderType) {
        this.clientType = clientType;
        this.token = token;
        this.money = money;
        this.time = time;
        this.bank = bank;
        this.mark = mark;
        this.bankID = bankID;
        this.orderNo = orderNo;
        this.transactionId = transactionId;
        this.orderType = orderType;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getBankID() {
        return bankID;
    }

    public void setBankID(String bankID) {
        this.bankID = bankID;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
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
                ", orderType='" + orderType + '\'' +
                '}';
    }
}
