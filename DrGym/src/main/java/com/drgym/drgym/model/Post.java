// author: ksiemion
package com.drgym.drgym.model;

import java.time.LocalDateTime;


public class Post {
    private Integer id;
    private LocalDateTime date;
    private String title;
    private String content;
    private Training training;


    public Post() {
        this.date = LocalDateTime.now();
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}