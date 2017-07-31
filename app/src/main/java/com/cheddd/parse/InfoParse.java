package com.cheddd.parse;

import com.cheddd.bean.InfoBankList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/29 0029.
 */

public class InfoParse {
    public static List<InfoBankList> getBankList(String string) {
        ArrayList<InfoBankList> mBankData = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(string);
            JSONArray rows = object.getJSONArray("rows");
            for (int i = 0; i < rows.length(); i++) {
                JSONObject jsonObject = rows.getJSONObject(i);
                String name = jsonObject.getString("name");
                String id = jsonObject.getString("id");
                String prompt = jsonObject.getString("prompt");
                InfoBankList list = new InfoBankList(id, prompt, name);
                mBankData.add(list);
            }
            return mBankData;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
