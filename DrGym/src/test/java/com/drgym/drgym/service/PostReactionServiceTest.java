package com.drgym.drgym.service;

import com.drgym.drgym.model.PostReaction;
import com.drgym.drgym.model.User;
import com.drgym.drgym.model.Post;
import com.drgym.drgym.repository.PostReactionRepository;
import com.drgym.drgym.repository.UserRepository;
import com.drgym.drgym.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostReactionServiceTest {

    @Autowired
    private PostReactionService postReactionService;

    @Autowired
    private PostReactionRepository postReactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    private User user;
    private Post post;
    private PostReaction postReaction;

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

        post = new Post();
        post.setUsername("testuser");
        post.setTitle("Test Title");
        post.setContent("Test Content");
        post.setDate(LocalDateTime.now());
        postRepository.save(post);

        postReaction = new PostReaction();
        postReaction.setPostId(post.getId());
        postReaction.setAuthorUsername("testuser");
        postReactionRepository.save(postReaction);
    }

    @Test
    void testFindByPostId() {
        List<PostReaction> reactions = postReactionService.findByPostId(post.getId());
        assertFalse(reactions.isEmpty());
        assertEquals(post.getId(), reactions.get(0).getPostId());
    }

    @Test
    void testAddReaction() {
        User newUser = new User();
        newUser.setUsername("testuser2");
        newUser.setName("Test");
        newUser.setSurname("User");
        newUser.setEmail("testuser2@example.com");
        newUser.setPassword("password");
        newUser.setVerified(true);
        userRepository.save(newUser);

        PostReaction newReaction = new PostReaction(post.getId(), "testuser2");
        PostReaction savedReaction = postReactionService.addReaction(newReaction);
        assertNotNull(savedReaction.getPostReactionId());
        assertEquals("testuser2", savedReaction.getAuthorUsername());
    }

    @Test
    void testRemoveReaction() {
        postReactionService.removeReaction(postReaction.getPostReactionId());
        assertFalse(postReactionRepository.existsById(postReaction.getPostReactionId()));
    }

    @Test
    void testRemoveReactionByUsername() {
        postReactionService.removeReactionByUsername(post.getId(), "testuser");
        List<PostReaction> reactions = postReactionRepository.findByPostId(post.getId());
        assertTrue(reactions.isEmpty());
    }

    @Test
    void testCountByPostId() {
        int count = postReactionService.countByPostId(post.getId());
        assertEquals(1, count);
    }

    @Test
    void testExistsByPostIdAndUsername() {
        boolean exists = postReactionService.existsByPostIdAndUsername(post.getId(), "testuser");
        assertTrue(exists);
    }
}