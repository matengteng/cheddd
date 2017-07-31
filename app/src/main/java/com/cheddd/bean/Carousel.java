package com.cheddd.bean;

/**
 * Created by Administrator on 2017/4/30 0030.
 * 轮播的
 */

public class Carousel {
    private int iconResId;
    private String text;

    public Carousel() {
    }

    public Carousel(String text, int iconResId) {
        this.text = text;
        this.iconResId = iconResId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }
}
