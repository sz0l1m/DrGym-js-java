// author: ksiemion
package com.drgym.drgym.model;

import java.time.LocalDateTime;


public class PostComment {
    private Long id;
    private String content;
    private String author;
    private LocalDateTime date;

    public PostComment() {}

    public PostComment(Long id, String content, String author, LocalDateTime date) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
