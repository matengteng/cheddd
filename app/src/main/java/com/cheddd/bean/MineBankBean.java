package com.cheddd.bean;

/**
 * Created by Administrator on 2017/6/7 0007.
 */

public class MineBankBean {
    private String number;
    private String mark;
    private String map;

    public MineBankBean() {
    }

    public MineBankBean(String number, String map, String mark) {
        this.number = number;
        this.map = map;
        this.mark = mark;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "MineBankBean{" +
                "number='" + number + '\'' +
                ", mark='" + mark + '\'' +
                ", map='" + map + '\'' +
                '}';
    }
}
