package com.cheddd.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/1 0001.
 */

public class IndexExtremeBean {

    private String city;
    private List<Store> store;

    public IndexExtremeBean() {
    }

    public IndexExtremeBean(String city, List<Store> store) {
        this.city = city;
        this.store = store;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Store> getStore() {
        return store;
    }

    public void setStore(List<Store> store) {
        this.store = store;
    }

    @Override
    public String toString() {
        return "IndexExtremeBean{" +
                "city='" + city + '\'' +
                ", store=" + store +
                '}';
    }
}
