package com.lin.model;

public class SearchRecord {
    private String id;

    private String content;

    public SearchRecord(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public SearchRecord() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}