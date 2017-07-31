package com.cheddd.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2017/6/14 0014.
 * 判断网络状态 以及将Json保存到sdcard中
 */

public class SdCardUtils {
    //判断是否有网，返回true有网，false没网
    public static boolean netWorkConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null) {
            if (info.isAvailable() && info.isConnected()) {
                return true;
            }
        }
        return false;
    }

    //保存Json数据到sd卡
    public static void saveJsonToSD(String str, String url, Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String path = context.getExternalCacheDir().getAbsolutePath() + File.separator
                    + url.substring(url.lastIndexOf("/") + 1) + ".json";
            BufferedWriter bw = null;
            File file = new File(path);
            try {
                file.createNewFile();
                bw = new BufferedWriter(new FileWriter(file));
                bw.write(str);
                bw.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    //从sd卡读取json
    public static String getJsonToSD(String url, Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String path = context.getExternalCacheDir().getAbsolutePath() + File.separator
                    + url.substring(url.lastIndexOf("/") + 1) + ".json";
            BufferedReader br = null;
            StringBuilder sb = null;
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
                sb = new StringBuilder();
                String str = null;
                while ((str = br.readLine()) != null) {
                    sb.append(str);
                }
                return sb.toString();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        return null;

    }
}
