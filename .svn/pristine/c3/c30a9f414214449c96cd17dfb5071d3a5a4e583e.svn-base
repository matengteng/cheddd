package com.cheddd.utils;

import com.cheddd.application.MyApplications;
import com.cheddd.bean.LoginTokenBean;
import com.google.gson.Gson;

/**
 * Created by Administrator on 2017/6/29 0029.
 */

public class LoginTokenUtils {
    public static String getJson() {
        LoginTokenBean tokenBean = new LoginTokenBean();
        tokenBean.setClientType("2");
        tokenBean.setToken(MyApplications.getToken());
        Gson gson = new Gson();
        String json = gson.toJson(tokenBean);
        return json;
    }
}
