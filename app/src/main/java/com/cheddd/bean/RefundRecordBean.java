package com.cheddd.bean;

/**
 * Created by Administrator on 2017/6/6 0006.
 */

public class RefundRecordBean {
    //约定还款日，实际还款日，还款金额，状态
    private String appoint;
    private String reality;
    private double refund;
    private int stat;

    public RefundRecordBean() {
    }

    public RefundRecordBean(String appoint, int stat, double refund, String reality) {
        this.appoint = appoint;
        this.stat = stat;
        this.refund = refund;
        this.reality = reality;
    }

    public String getAppoint() {
        return appoint;
    }

    public void setAppoint(String appoint) {
        this.appoint = appoint;
    }

    public int getStat() {
        return stat;
    }

    public void setStat(int stat) {
        this.stat = stat;
    }

    public double getRefund() {
        return refund;
    }

    public void setRefund(double refund) {
        this.refund = refund;
    }

    public String getReality() {
        return reality;
    }

    public void setReality(String reality) {
        this.reality = reality;
    }

    @Override
    public String toString() {
        return "RefundRecordBean{" +
                "appoint='" + appoint + '\'' +
                ", reality='" + reality + '\'' +
                ", refund=" + refund +
                ", stat=" + stat +
                '}';
    }
}
