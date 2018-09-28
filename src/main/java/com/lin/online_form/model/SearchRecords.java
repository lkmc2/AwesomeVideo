package com.lin.online_form.model;

public class SearchRecords {
    private String id;

    private String content;

    public SearchRecords(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public SearchRecords() {
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