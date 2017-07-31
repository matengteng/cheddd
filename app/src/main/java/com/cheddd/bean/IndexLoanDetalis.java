package com.cheddd.bean;

/**
 * Created by Administrator on 2017/7/7 0007.
 */

public class IndexLoanDetalis {
    private String clientType;
    private String token;
    private String orderNo;
    private String payPassWord;

    public IndexLoanDetalis() {
    }

    public IndexLoanDetalis(String clientType, String token, String orderNo, String payPassWord) {
        this.clientType = clientType;
        this.token = token;
        this.orderNo = orderNo;
        this.payPassWord = payPassWord;
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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPayPassWord() {
        return payPassWord;
    }

    public void setPayPassWord(String payPassWord) {
        this.payPassWord = payPassWord;
    }

    @Override
    public String toString() {
        return "IndexLoanDetalis{" +
                "clientType='" + clientType + '\'' +
                ", token='" + token + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", payPassWord='" + payPassWord + '\'' +
                '}';
    }
}
