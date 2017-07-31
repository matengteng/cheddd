package com.cheddd.bean;

/**
 * Created by Administrator on 2017/6/6 0006.
 */

public class BorrowMoneyBean {
    private String repayTime;
    private double interestAmt;
    private double principalAmt;

    public BorrowMoneyBean() {
    }

    public BorrowMoneyBean(String repayTime, double principalAmt, double interestAmt) {
        this.repayTime = repayTime;
        this.principalAmt = principalAmt;
        this.interestAmt = interestAmt;
    }

    public String getRepayTime() {
        return repayTime;
    }

    public void setRepayTime(String repayTime) {
        this.repayTime = repayTime;
    }

    public double getPrincipalAmt() {
        return principalAmt;
    }

    public void setPrincipalAmt(double principalAmt) {
        this.principalAmt = principalAmt;
    }

    public double getInterestAmt() {
        return interestAmt;
    }

    public void setInterestAmt(double interestAmt) {
        this.interestAmt = interestAmt;
    }

    @Override
    public String toString() {
        return "BorrowMoneyBean{" +
                "repayTime='" + repayTime + '\'' +
                ", interestAmt=" + interestAmt +
                ", principalAmt=" + principalAmt +
                '}';
    }
}
