package com.cheddd.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;


import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/5/9 0009.
 */

public class OkhttpUtils {
    private OkHttpClient client;
    private Handler mHandler;
    private static OkhttpUtils mInstance;
    private Context mContext;

    private OkhttpUtils(Context context) {
        client = new OkHttpClient().newBuilder().readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS).build();
        mHandler = new Handler(Looper.getMainLooper());
        mContext = context;
    }

    public static OkhttpUtils getInstance(Context context) {
        if (mInstance == null) {
            synchronized (OkhttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new OkhttpUtils(context);
                }
            }
        }
        return mInstance;
    }

    class StringCallBack implements Callback {
        private HttpCallBack httpCallBack;
        private Request request;

        public StringCallBack(HttpCallBack httpCallBack, Request request) {
            this.httpCallBack = httpCallBack;
            this.request = request;
        }

        @Override
        public void onFailure(Call call, IOException e) {
            final IOException fe = e;
            if (httpCallBack != null) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        httpCallBack.onError(request, fe);
                    }
                });
            }
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            final String result = response.body().string();
            if (httpCallBack != null) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        httpCallBack.onSuccess(request, result);
                    }
                });
            }
        }
    }

    //使用get
    public void asynGet(String url, HttpCallBack httpCallBack) {
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new StringCallBack(httpCallBack, request));
    }

    //使用post
    public void asyncPost(String url, FormBody formBody, HttpCallBack httpCallBack) {
        String session = SharedPreferencesUtils.getString(mContext, "session", "");
        Request request = new Request.Builder().addHeader("cookie", session).url(url).post(formBody).build();
        client.newCall(request).enqueue(new StringCallBack(httpCallBack, request));
    }

    public interface HttpCallBack {
        void onError(Request request, IOException e);

        void onSuccess(Request request, String result);
    }
}
