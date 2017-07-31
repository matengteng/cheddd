package com.cheddd.utils;


import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2017/5/7 0007.
 */

public class SharedPreferencesUtils {
    public static final String PREF_NAME = "chedd";
    public static boolean getBoolean(Context ctx, String key,
                                     boolean defaultValue) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

    public static void setBoolean(Context ctx, String key, boolean value) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    //读取String 类型
    public static String getString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, value);
    }

    //保存String类型
    public static void setString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    //读取手机号码
    public static String getPhone(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, value);
    }

    //保存手机号码类型
    public static void setPhone(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

}
