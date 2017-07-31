package com.cheddd.bean;

/**
 * Created by Administrator on 2017/7/25 0025.
 */

public class SmsBean {

    private String name;
    private String phoneNumber;
    private String smsbody;
    private String date;
    private String type;

    public SmsBean() {
    }

    public SmsBean(String name, String type, String date, String smsbody, String phoneNumber) {
        this.name = name;
        this.type = type;
        this.date = date;
        this.smsbody = smsbody;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSmsbody() {
        return smsbody;
    }

    public void setSmsbody(String smsbody) {
        this.smsbody = smsbody;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "SmsBean{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", smsbody='" + smsbody + '\'' +
                ", date='" + date + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
