package com.drgym.drgym.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToOne
    @JoinColumn(name = "workout_id")
    private Workout training;

    @OneToMany(mappedBy = "postId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostReaction> reactions = new ArrayList<>();

    @Transient
    private Long workoutId;

    @Transient
    private int reactionCount;

    @Transient
    private int userReaction;

    @Transient
    private String avatar;

    public Post() {}

    public Post(Long id, LocalDateTime date, String title, String content) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.content = content;
    }

    public Post(Long id, String title, String content, Long workoutId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.workoutId = workoutId;
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

    public Workout getTraining() { return training; }

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

    public void setTraining(Workout training) { this.training = training; }

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

    public Long getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(Long workoutId) {
        this.workoutId = workoutId;
    }

    public int getReactionCount() {
        return reactionCount;
    }

    public void setReactionCount(int reactionCount) {
        this.reactionCount = reactionCount;
    }

    public int getUserReaction() {
        return userReaction;
    }

    public void setUserReaction(int userReaction) {
        this.userReaction = userReaction;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
