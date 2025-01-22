package com.drgym.drgym.repository;

import com.drgym.drgym.model.PostReaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostReactionRepository extends JpaRepository<PostReaction, Long> {
    List<PostReaction> findByPostId(Long postId);
    void deleteByPostIdAndAuthorUsername(Long postId, String authorUsername);
    int countByPostId(Long postId);
    boolean existsByPostIdAndAuthorUsername(Long postId, String authorUsername);
}