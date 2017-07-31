package com.cheddd.bean;

/**
 * Created by Administrator on 2017/7/31 0031.
 */

public class MoreDotbean {
    private String address;
    private String storeName;

    public MoreDotbean() {
    }

    public MoreDotbean(String address, String storeName) {
        this.address = address;
        this.storeName = storeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    @Override
    public String toString() {
        return "MoreDotbean{" +
                "address='" + address + '\'' +
                ", storeName='" + storeName + '\'' +
                '}';
    }
}
