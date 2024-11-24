// author: ksiemion
package com.drgym.drgym.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Post {
    private Integer id;
    private LocalDateTime date;
    private String title;
    private String content;
    private Training training;
    private List<PostComment> comments = new ArrayList<>();
    private List<PostReaction> reactions = new ArrayList<>();


    public Post() {
        this.date = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Training getTraining() {
        return training;
    }

    public List<PostComment> getComments() {
        return comments;
    }

    public List<PostReaction> getReactions() {
        return reactions;
    }
}