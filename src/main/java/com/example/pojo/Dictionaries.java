package com.example.pojo;

public class Dictionaries {
    private String id;
    private String content;

    public Dictionaries() {
    }

    public Dictionaries(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
