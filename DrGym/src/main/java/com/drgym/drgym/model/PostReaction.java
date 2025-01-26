package com.drgym.drgym.model;

import jakarta.persistence.*;

@Entity
@Table(name="post_reactions")
public class PostReaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_reaction_id")
    private Long postReactionId;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "author_username")
    private String authorUsername;

    public PostReaction() {}

    public PostReaction(Long postId, String authorUsername) {
        this.postId = postId;
        this.authorUsername = authorUsername;
    }

    public Long getPostReactionId() {
        return postReactionId;
    }

    public void setPostReactionId(Long postReactionId) {
        this.postReactionId = postReactionId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }
}
