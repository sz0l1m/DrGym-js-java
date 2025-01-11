// author: ksiemion
package com.drgym.drgym.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Post {
    @Id
    private Long id;
    @Column(name = "author_name")
    private String authorUsername;
    @Column(name = "post_date")
    private LocalDateTime date;
    @Column(name = "content")
    private String content;
    @Column(name = "title")
    private String title;
    @Column(name = "workout_id")
    private Long trainingId;
    private List<Long> comments = new ArrayList<>();
    private List<Long> reactions = new ArrayList<>();

    public Post() {}

    public Post(Long id, LocalDateTime date, String title, String content) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public String getAuthorUsername() {
        return authorUsername;
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

    public Long getTraining() {
        return trainingId;
    }

    public List<Long> getComments() {
        return comments;
    }

    public List<Long> getReactions() {
        return reactions;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
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

    public void setTraining(Long training) {
        this.trainingId = training;
    }

    public void setComments(List<Long> comments) {
        this.comments = comments;
    }

    public void setReactions(List<Long> reactions) {
        this.reactions = reactions;
    }

    public void addComment(Long commentId) {
        comments.add(commentId);
    }

    public void addReaction(Long reactionId) {
        reactions.add(reactionId);
    }

    public void removeComment(Long commentId) {
        comments.remove(commentId);
    }

    public void removeReaction(Long reactionId) {
        reactions.remove(reactionId);
    }
}