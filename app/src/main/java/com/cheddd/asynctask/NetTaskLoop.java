package com.cheddd.asynctask;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.cheddd.utils.OkhttpGetUtils;

/**
 * Created by Administrator on 2017/6/6 0006.
 */

public abstract class NetTaskLoop extends AsyncTask<String, Void, String>{
    public abstract void onCallBack(String result);

    @Override
    protected String doInBackground(String... params) {
        String path = params[0];
        byte[] data = OkhttpGetUtils.getByteArrayUrl(path);
        if (data != null && data.length > 0) {
            return new String(data);
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (!TextUtils.isEmpty(s)) {
            onCallBack(s);
        }
    }
}
