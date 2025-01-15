package com.drgym.drgym.service;

import com.drgym.drgym.model.Post;
import com.drgym.drgym.model.PostCreateRequestWorkout;
import com.drgym.drgym.model.PostCreateRequest;
import com.drgym.drgym.model.Workout;
import com.drgym.drgym.model.WorkoutCreateRequest;
import com.drgym.drgym.repository.PostRepository;
import com.drgym.drgym.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private WorkoutRepository workoutRepository;

    public List<Post> findPostsByUsername(String username) {
        return postRepository.findByUsername(username);
    }

    public Optional<Post> findPostById(Long postId) {
        return postRepository.findById(postId);
    }

    public ResponseEntity<String> createPost(PostCreateRequest postRequest) {
        try {
            Post post = new Post();
            post.setUsername(postRequest.getUsername());
            post.setTitle(postRequest.getTitle());
            post.setContent(postRequest.getContent());
            post.setDate(LocalDateTime.now());
            post.setTraining(postRequest.getWorkoutId());
            postRepository.save(post);
            return ResponseEntity.ok("Post created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to create post");
        }
    }

    public ResponseEntity<String> createPostWorkout(PostCreateRequestWorkout postRequest) {
        try {
            Post post = new Post();
            post.setUsername(postRequest.getUsername());
            post.setTitle(postRequest.getTitle());
            post.setContent(postRequest.getContent());
            post.setDate(LocalDateTime.now());

            WorkoutCreateRequest workoutRequest = postRequest.getWorkout();
            if (workoutRequest != null) {
                Workout workout = new Workout(
                        workoutRequest.getStartDate(),
                        postRequest.getUsername(),
                        workoutRequest.getEndDate(),
                        workoutRequest.getDescription(),
                        LocalDateTime.now()
                );
                Workout savedWorkout = workoutRepository.save(workout);
                post.setTraining(savedWorkout.getId());
            }

            postRepository.save(post);
            return ResponseEntity.ok("Post created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to create post");
        }
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}