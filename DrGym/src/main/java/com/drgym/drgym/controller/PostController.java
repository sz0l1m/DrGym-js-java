package com.drgym.drgym.controller;

import com.drgym.drgym.model.Post;
import com.drgym.drgym.model.PostReaction;
import com.drgym.drgym.model.PostCreateRequestWorkout;
import com.drgym.drgym.model.PostCreateRequest;
import com.drgym.drgym.service.PostService;
import com.drgym.drgym.service.PostReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private PostReactionService postReactionService;

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPost(@PathVariable Long postId) {
        Optional<Post> postOptional = postService.findPostById(postId);
        if (postOptional.isPresent()) {
            return ResponseEntity.ok(postOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPost(@RequestBody PostCreateRequest postRequest) {
        return postService.createPost(postRequest);
    }

    @PostMapping("/create_with_workout")
    public ResponseEntity<?> createPost(@RequestBody PostCreateRequestWorkout postRequest) {
        return postService.createPostWorkout(postRequest);
    }

    @GetMapping("/{postId}/reactions")
    public ResponseEntity<?> getReactionsForPost(@PathVariable Long postId) {
        List<PostReaction> reactions = postReactionService.findByPostId(postId);
        if (reactions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reactions);
    }

    @PostMapping("/{postId}/reactions")
    public ResponseEntity<?> addReactionToPost(@PathVariable Long postId, @RequestBody PostReaction reaction) {
        reaction.setPostId(postId);
        PostReaction createdReaction = postReactionService.addReaction(reaction);
        return ResponseEntity.ok(createdReaction);
    }

    @DeleteMapping("/{postId}/reactions/{reactionId}")
    public ResponseEntity<?> deleteReaction(@PathVariable Long postId, @PathVariable Long reactionId) {
        postReactionService.removeReaction(reactionId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

}
