package com.cheddd.application;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;


import com.cheddd.utils.OkhttpUtils;
import com.cheddd.utils.SharedPreferencesUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2017/5/24 0024.
 */

public class MyApplications extends Application {
    public static String token = null;
    public static String orderNo = null;

    public static String getOrderNo() {
        return orderNo;
    }

    public static void setOrderNo(String orderNo) {
        MyApplications.orderNo = orderNo;
    }

    public static String getToken() {

        return token;
    }

    public static void setToken(String token) {

        MyApplications.token = token;
    }

    private static MyApplications mApp;
    private OkhttpUtils mHttpUtils;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        initOkHttpUtils();
        //对未捕获的异常处理
        abnormal();
        // 判断是否开启log日志输出
        initLogModel();
    }

    private void initLogModel() {
       // LogUtils.setDebugable(getApplicationContext());
        // 初始化输出日志级别
       // BaseConfigure.setDebug(isApkDebugable(getApplicationContext()));
    }

    private void abnormal() {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

            private FileOutputStream fileOutputStream;

            @Override
            public void uncaughtException(Thread t, Throwable e) {
                try {
                    fileOutputStream = openFileOutput("error.log", Context.MODE_PRIVATE);
                    PrintStream ps=new PrintStream(fileOutputStream);
                    e.printStackTrace(ps);
                    ps.close();

                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }finally {
                    if(fileOutputStream!=null){
                        try {
                            fileOutputStream.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
                e.printStackTrace();
            }
        });
    }

    private void initOkHttpUtils() {
        mHttpUtils = OkhttpUtils.getInstance(mApp);
    }

    public static MyApplications getApp() {

        return mApp;
    }

    public OkhttpUtils getOkHttpUtils() {

        return this.mHttpUtils;
    }


    @Override
    public void onTerminate() {

        super.onTerminate();
    }

}
