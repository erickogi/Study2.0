package com.erickogi14gmail.study20.Main.models;

/**
 * Created by kimani kogi on 5/31/2017.
 */

public class Files_model {
    private int id;
    private String post_title;
    private String post_date;
    private String post_author;
    private String post_pdf;
    private String post_content;

    public Files_model() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public String getPost_date() {
        return post_date;
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }

    public String getPost_author() {
        return post_author;
    }

    public void setPost_author(String post_author) {
        this.post_author = post_author;
    }

    public String getPost_pdf() {
        return post_pdf;
    }

    public void setPost_pdf(String post_pdf) {
        this.post_pdf = post_pdf;
    }

    public String getPost_content() {
        return post_content;
    }

    public void setPost_content(String post_content) {
        this.post_content = post_content;
    }
}
