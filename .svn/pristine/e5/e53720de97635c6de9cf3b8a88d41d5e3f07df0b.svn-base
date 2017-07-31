package com.cheddd.bean;

/**
 * Created by Administrator on 2017/6/5 0005.
 */

public class LoanBean {
    /**
     * 订单号、借款时间、借款金额、借款时长、月利率、还款期数、还款方式
     * 借款明细
     * 已还金额，待还金额，本期应还
     */


    private String orderNo;
    private double contractAmt;
    private String loanCycle;
    private String interestTime;
    private double loanRate;
    private String loanPeriod;
    private int interestType;

    public LoanBean() {
    }

    public LoanBean(String orderNo, int interestType, String loanPeriod, double loanRate, String interestTime, String loanCycle, double contractAmt) {
        this.orderNo = orderNo;
        this.interestType = interestType;
        this.loanPeriod = loanPeriod;
        this.loanRate = loanRate;
        this.interestTime = interestTime;
        this.loanCycle = loanCycle;
        this.contractAmt = contractAmt;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getInterestType() {
        return interestType;
    }

    public void setInterestType(int interestType) {
        this.interestType = interestType;
    }

    public String getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(String loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    public double getLoanRate() {
        return loanRate;
    }

    public void setLoanRate(double loanRate) {
        this.loanRate = loanRate;
    }

    public String getInterestTime() {
        return interestTime;
    }

    public void setInterestTime(String interestTime) {
        this.interestTime = interestTime;
    }

    public String getLoanCycle() {
        return loanCycle;
    }

    public void setLoanCycle(String loanCycle) {
        this.loanCycle = loanCycle;
    }

    public double getContractAmt() {
        return contractAmt;
    }

    public void setContractAmt(double contractAmt) {
        this.contractAmt = contractAmt;
    }

    @Override
    public String toString() {
        return "LoanBean{" +
                "orderNo='" + orderNo + '\'' +
                ", contractAmt=" + contractAmt +
                ", loanCycle='" + loanCycle + '\'' +
                ", interestTime='" + interestTime + '\'' +
                ", loanRate=" + loanRate +
                ", loanPeriod='" + loanPeriod + '\'' +
                ", interestType=" + interestType +
                '}';
    }
}
