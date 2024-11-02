package com.example.demo.dto;

public class FetchGroupsDTO {
    private String groupName;
    private String text;
    private long textId;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTextId() {
        return textId;
    }

    public void setTextId(long textId) {
        this.textId = textId;
    }
}
