package com.drgym.drgym.service;

import com.drgym.drgym.model.PostReaction;
import com.drgym.drgym.repository.PostReactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostReactionService {

    private final PostReactionRepository postReactionRepository;

    @Autowired
    public PostReactionService(PostReactionRepository postReactionRepository) {
        this.postReactionRepository = postReactionRepository;
    }

    public List<PostReaction> findByPostId(Long postId) {
        return postReactionRepository.findByPostId(postId);
    }

    public PostReaction addReaction(PostReaction reaction) {
        return postReactionRepository.save(reaction);
    }

    public List<PostReaction> getReactionsByPostId(Long postId) {
        return postReactionRepository.findByPostId(postId);
    }

    public void removeReaction(Long reactionId) {
        postReactionRepository.deleteById(reactionId);
    }

    @Transactional
    public void removeReactionByUsername(Long postId, String username) {
        postReactionRepository.deleteByPostIdAndAuthorUsername(postId, username);
    }

    public int countByPostId(Long postId) {
        return postReactionRepository.countByPostId(postId);
    }

    public boolean existsByPostIdAndUsername(Long postId, String username) {
        return postReactionRepository.existsByPostIdAndAuthorUsername(postId, username);
    }
}