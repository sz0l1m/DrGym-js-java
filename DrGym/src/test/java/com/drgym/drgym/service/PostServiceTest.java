package com.drgym.drgym.service;

import com.drgym.drgym.model.*;
import com.drgym.drgym.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkoutRepository workoutRepository;

    private User user;
    private Post post;
    private Workout workout;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testuser");
        user.setName("Test");
        user.setSurname("User");
        user.setEmail("testuser@example.com");
        user.setPassword("password");
        user.setVerified(true);
        userRepository.save(user);

        workout = new Workout();
        workout.setStartDate(LocalDateTime.now());
        workout.setUsername("testuser");
        workout.setEndDate(LocalDateTime.now().plusHours(1));
        workout.setDescription("Test Workout");
        workout = workoutRepository.save(workout);

        post = new Post();
        post.setUsername("testuser");
        post.setTitle("Test Title");
        post.setContent("Test Content");
        post.setDate(LocalDateTime.now());
        post.setTraining(workout);
        postRepository.save(post);
    }

    @Test
    void testFindPostsByUsername() {
        List<Post> posts = postService.findPostsByUsername("testuser");
        assertFalse(posts.isEmpty());
        assertEquals("testuser", posts.get(0).getUsername());
    }

    @Test
    void testFindPostById() {
        Optional<Post> foundPost = postService.findPostById(post.getId());
        assertTrue(foundPost.isPresent());
        assertEquals(post.getId(), foundPost.get().getId());
    }

    @Test
    void testCreatePost() {
        PostCreateRequest postRequest = new PostCreateRequest();
        postRequest.setUsername("testuser");
        postRequest.setTitle("New Test Title");
        postRequest.setContent("New Test Content");
        postRequest.setWorkoutId(workout.getId());

        ResponseEntity<String> response = postService.createPost(postRequest);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testDeletePost() {
        postService.deletePost(post.getId());
        Optional<Post> deletedPost = postRepository.findById(post.getId());
        assertFalse(deletedPost.isPresent());
    }

    @Test
    void testUpdatePost() {
        ResponseEntity<String> response = postService.updatePost(post.getId(), "Updated Title", "Updated Content", workout.getId());
        assertEquals(200, response.getStatusCodeValue());

        Optional<Post> updatedPost = postRepository.findById(post.getId());
        assertTrue(updatedPost.isPresent());
        assertEquals("Updated Title", updatedPost.get().getTitle());
        assertEquals("Updated Content", updatedPost.get().getContent());
    }
}