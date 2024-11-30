// author: ksiemion
package com.drgym.drgym.model;


public class PostReaction {
    private Integer reactionId;
    private Integer postId;
    private Integer userId;

    public PostReaction() {}

    public PostReaction(Integer reactionId, Integer postId, Integer userId) {
        this.reactionId = reactionId;
        this.postId = postId;
        this.userId = userId;
    }

    public Integer getReactionId() {
        return reactionId;
    }

    public void setReactionId(Integer reactionId) {
        this.reactionId = reactionId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
