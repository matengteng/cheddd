package com.cheddd.parse;

import com.cheddd.bean.ContentBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/14 0014.
 */

public class IndexParse {
    //消息滚动跳的解析
    public static List<ContentBean> getNotice(String string) {
        ArrayList<ContentBean> mNoticeData = new ArrayList<>();
        if (string != null) {
            try {
                JSONObject obj = new JSONObject(string);
                JSONArray array = obj.getJSONArray("rows");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    String content = jsonObject.getString("content");
                    String amt = jsonObject.getString("amt");
                    ContentBean bean = new ContentBean(content, amt);
                    mNoticeData.add(bean);
                }
                return mNoticeData;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
