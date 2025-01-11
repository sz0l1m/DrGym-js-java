package com.drgym.drgym.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="posts")
public class Post {
    @Id
    @Column(name = "post_id")
    private Long id;

    @Column(name = "author_username")
    private String username;

    @Column(name = "post_date")
    private LocalDateTime date;

    @Column(name = "content")
    private String content;

    @Column(name = "title")
    private String title;

    @Column(name = "workout_id")
    private Long trainingId;

    @OneToMany(mappedBy = "postId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostReaction> reactions = new ArrayList<>();

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

    public String getUsername() {
        return username;
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

    public List<PostReaction> getReactions() {
        return reactions;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void setReactions(List<PostReaction> reactions) {
        this.reactions = reactions;
    }

    public void addReaction(PostReaction reaction) {
        reactions.add(reaction);
        reaction.setPostId(this.id);
    }

    public void removeReaction(PostReaction reaction) {
        reactions.remove(reaction);
        reaction.setPostId(null);
    }
}
