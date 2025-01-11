// author: ksiemion
package com.drgym.drgym.model;

import jakarta.persistence.*;

@Entity
public class PostReaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reactionId;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "user_id")
    private Long userId;

    public PostReaction() {}

    public PostReaction(Post post, Long userId) {
        this.post = post;
        this.userId = userId;
    }

    public Long getReactionId() {
        return reactionId;
    }

    public void setReactionId(Long reactionId) {
        this.reactionId = reactionId;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}