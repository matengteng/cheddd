package com.cheddd.utils;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/5/13 0013.
 */

public class OkhttpGetUtils {
    /*指定url地址,用okhttp的get方式获取一个字符串回来
     * @return 返回服务器响应的字符串信息,如果失败,返回null
     */
    public static String getStringUrl(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //指定url,下载一个资源,用字节数组返回
    public static byte[] getByteArrayUrl(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().bytes();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //利用OkHttpClient获取到指定url的输入流
    public static InputStream getInputStreamUrl(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().byteStream();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
