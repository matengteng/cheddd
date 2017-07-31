package com.cheddd.bean;

/**
 * Created by Administrator on 2017/7/31 0031.
 */

public class ContentBean {
    private String content;
    private String amt;

    public ContentBean() {
    }

    public ContentBean(String content, String amt) {
        this.content = content;
        this.amt = amt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }
}
