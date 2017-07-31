package com.cheddd.bean;

/**
 * Created by Administrator on 2017/6/23 0023.
 */

public class LoginTokenBean {
    private String clientType;
    private String token;

    public LoginTokenBean() {
    }

    public LoginTokenBean(String token, String clientType) {
        this.token = token;
        this.clientType = clientType;
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

    @Override
    public String toString() {
        return "LoginTokenBean{" +
                "clientType='" + clientType + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
