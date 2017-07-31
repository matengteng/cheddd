package com.cheddd.bean;

/**
 * Created by Administrator on 2017/7/6 0006.
 */

public class IndexCheckBean {
    private String repaymentAmt;
    private String repaymentDate;
    private int num;

    public IndexCheckBean() {
    }

    public IndexCheckBean(String repaymentAmt, int num, String repaymentDate) {
        this.repaymentAmt = repaymentAmt;
        this.num = num;
        this.repaymentDate = repaymentDate;
    }

    public String getRepaymentAmt() {
        return repaymentAmt;
    }

    public void setRepaymentAmt(String repaymentAmt) {
        this.repaymentAmt = repaymentAmt;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(String repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    @Override
    public String toString() {
        return "IndexCheckBean{" +
                "repaymentAmt='" + repaymentAmt + '\'' +
                ", repaymentDate='" + repaymentDate + '\'' +
                ", num=" + num +
                '}';
    }
}
