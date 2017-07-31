package com.cheddd.bean;

/**
 * Created by Administrator on 2017/6/29 0029.
 */

public class InfoBankList {
    private String id;
    private String name;
    private String prompt;

    public InfoBankList() {
    }

    public InfoBankList(String id, String prompt, String name) {
        this.id = id;
        this.prompt = prompt;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "InfoBankList{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", prompt='" + prompt + '\'' +
                '}';
    }
}
