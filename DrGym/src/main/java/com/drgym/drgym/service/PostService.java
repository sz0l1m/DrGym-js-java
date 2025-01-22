package com.drgym.drgym.service;

import com.drgym.drgym.model.*;
import com.drgym.drgym.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Post> findPostsByUsername(String username) {
        List<Post> posts = postRepository.findByUsername(username, Sort.by(Sort.Direction.DESC, "date"));
        posts.forEach(post -> {
            Optional<User> userOptional = userRepository.findByUsername(post.getUsername());
            userOptional.ifPresent(user -> post.setAvatar(user.getAvatar()));
        });
        return posts;
    }

    public Optional<Post> findPostById(Long postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            Workout workout = post.getTraining();
            if (workout != null) {
                List<Activity> activities = workoutRepository.findActivitiesByWorkoutId(workout.getId());
                activities.forEach(activity -> {
                    Exercise exercise = exerciseRepository.findById(activity.getExerciseId()).orElse(null);
                    if (exercise != null) {
                        activity.setExerciseName(exercise.getName());
                    }
                });
                workout.setActivities(activities);
            }
            Optional<User> userOptional = userRepository.findByUsername(post.getUsername());
            userOptional.ifPresent(user -> post.setAvatar(user.getAvatar()));
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
                if (workout != null) {
                    List<Activity> activities = workoutRepository.findActivitiesByWorkoutId(workout.getId());
                    activities.forEach(activity -> {
                        Exercise exercise = exerciseRepository.findById(activity.getExerciseId()).orElse(null);
                        if (exercise != null) {
                            activity.setExerciseName(exercise.getName());
                        }
                    });
                    workout.setActivities(activities);
                }
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
                List<Activity> activities = workoutRequest.getActivities();
                activities.forEach(activity -> {
                    activity.setWorkoutId(savedWorkout.getId());
                    Exercise exercise = exerciseRepository.findById(activity.getExerciseId()).orElse(null);
                    if (exercise != null) {
                        activity.setExerciseName(exercise.getName());
                    }
                    activityRepository.save(activity);
                });
                savedWorkout.setActivities(activities);
                post.setTraining(savedWorkout);
            }

            postRepository.save(post);
            return ResponseEntity.ok("Post created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to create post");
        }
    }

    @Transactional
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    public List<Post> findPostsByUsernames(List<String> usernames) {
        List<Post> posts = postRepository.findByUsernameIn(usernames, Sort.by(Sort.Direction.DESC, "date"));
        posts.forEach(post -> {
            Optional<User> userOptional = userRepository.findByUsername(post.getUsername());
            userOptional.ifPresent(user -> post.setAvatar(user.getAvatar()));
        });
        return posts;
    }

    public ResponseEntity<String> updatePost(Long postId, String title, String content, Long workoutId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Post post = postOptional.get();
        post.setTitle(title);
        post.setContent(content);

        Workout oldWorkout = post.getTraining();
        if (oldWorkout != null) {
            oldWorkout.setPosted(false);
            workoutRepository.save(oldWorkout);
        }

        if (workoutId != null) {
            Workout newWorkout = workoutRepository.findById(workoutId).orElse(null);
            if (newWorkout != null) {
                newWorkout.setPosted(true);
                post.setTraining(newWorkout);
                workoutRepository.save(newWorkout);
            } else {
                return ResponseEntity.badRequest().body("Invalid workout ID");
            }
        } else {
            post.setTraining(null);
        }

        postRepository.save(post);
        return ResponseEntity.ok("Post updated successfully");
    }

}