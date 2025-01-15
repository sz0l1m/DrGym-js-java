package com.drgym.drgym.service;

import com.drgym.drgym.model.*;
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
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            Workout workout = post.getTraining();
            if (workout != null) {
                List<Activity> activities = workoutRepository.findActivitiesByWorkoutId(workout.getId());
                workout.setActivities(activities);
            }
            return Optional.of(post);
        }
        return Optional.empty();
    }

    public ResponseEntity<String> createPost(PostCreateRequest postRequest) {
        try {
            Post post = new Post();
            post.setUsername(postRequest.getUsername());
            post.setTitle(postRequest.getTitle());
            post.setContent(postRequest.getContent());
            post.setDate(LocalDateTime.now());

            if (postRequest.getWorkoutId() != null) {
                Workout workout = workoutRepository.findById(postRequest.getWorkoutId()).orElse(null);
                post.setTraining(workout);
            }

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
                post.setTraining(savedWorkout);
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

    public List<Post> findPostsByUsernames(List<String> usernames) {
        return postRepository.findByUsernameIn(usernames);
    }
}