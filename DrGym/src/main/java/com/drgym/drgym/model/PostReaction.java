// author: ksiemion
package com.drgym.drgym.model;


public class PostReaction {
    private Long reactionId;
    private Long postId;
    private Long userId;

    public PostReaction() {}

    public PostReaction(Long reactionId, Long postId, Long userId) {
        this.reactionId = reactionId;
        this.postId = postId;
        this.userId = userId;
    }

    public Long getReactionId() {
        return reactionId;
    }

    public void setReactionId(Long reactionId) {
        this.reactionId = reactionId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
