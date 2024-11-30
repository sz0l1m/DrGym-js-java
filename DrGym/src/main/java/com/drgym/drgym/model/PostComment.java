// author: ksiemion
package com.drgym.drgym.model;

import java.time.LocalDateTime;


public class PostComment {
    private Integer id;
    private String content;
    private User author;
    private LocalDateTime date;

    public PostComment() {}

    public PostComment(Integer id, String content, User author, LocalDateTime date) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
