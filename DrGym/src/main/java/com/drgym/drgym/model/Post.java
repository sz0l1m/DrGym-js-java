// author: ksiemion
package com.drgym.drgym.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Post {
    private Integer id;
    private Integer authorId;
    private LocalDateTime date;
    private String content;
    private String title;
    private Integer trainingId;
    private List<Integer> comments = new ArrayList<>();
    private List<Integer> reactions = new ArrayList<>();

    public Post() {}

    public Post(Integer id, LocalDateTime date, String title, String content) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public Integer getAuthorId() {
        return authorId;
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

    public Integer getTraining() {
        return trainingId;
    }

    public List<Integer> getComments() {
        return comments;
    }

    public List<Integer> getReactions() {
        return reactions;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTraining(Integer training) {
        this.trainingId = training;
    }

    public void setComments(List<Integer> comments) {
        this.comments = comments;
    }

    public void setReactions(List<Integer> reactions) {
        this.reactions = reactions;
    }

    public void addComment(Integer commentId) {
        comments.add(commentId);
    }

    public void addReaction(Integer reactionId) {
        reactions.add(reactionId);
    }

    public void removeComment(Integer commentId) {
        comments.remove(commentId);
    }

    public void removeReaction(Integer reactionId) {
        reactions.remove(reactionId);
    }
}