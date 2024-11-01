package com.example.demo.dto;

public class FetchedTextDTO {
    private long textId;
    private long uId;
    private String uName;

    public void setText(String text) {
        this.text = text;
    }

    private String text;

    public long getTextId() {
        return textId;
    }

    public long getuId() {
        return uId;
    }

    public String getuName() {
        return uName;
    }

    public String getText() {
        return text;
    }

    public void setTextId(long textId) {
        this.textId = textId;
    }
    public void setuId(long uId) {
        this.uId = uId;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }
}
