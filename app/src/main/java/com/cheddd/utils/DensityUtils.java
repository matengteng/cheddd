package com.cheddd.utils;

import android.content.Context;

/**
 * Created by Administrator on 2017/7/29 0029.
 */

public class DensityUtils {
    //dp转px
    public static int dpTwopsx(Context context, float dp) {
        float density = context.getResources().getDisplayMetrics().density;
        int px = (int) (dp * density + 0.5f);
        return px;
    }
    //
    public static float pxTwodp(Context context,int px){
        float density = context.getResources().getDisplayMetrics().density;
        float dp = px / density;
        return dp;
    }
}
