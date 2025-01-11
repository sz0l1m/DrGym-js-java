package com.drgym.drgym.service;

import com.drgym.drgym.model.Post;
import com.drgym.drgym.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public List<Post> findPostsByUsername(String username) {
        return postRepository.findByUsername(username);
    }

    public Post createPost(String username, String content) {
        Post post = new Post();
        post.setUsername(username);
        post.setContent(content);
        post.setDate(LocalDateTime.now());
        return postRepository.save(post);
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}