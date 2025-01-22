package com.drgym.drgym.repository;

import com.drgym.drgym.model.Post;
import com.drgym.drgym.model.PostReaction;
import com.drgym.drgym.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostReactionRepositoryTest {

    @Autowired
    private PostReactionRepository postReactionRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private PostReaction testReaction;
    private Post testPost;
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setEmail("testuser@example.com");
        testUser.setPassword(passwordEncoder.encode("password"));
        testUser.setName("Test");
        testUser.setSurname("User");
        testUser.setVerified(true);
        userRepository.save(testUser);

        testPost = new Post();
        testPost.setUsername("testuser");
        testPost.setDate(LocalDateTime.now());
        testPost.setTitle("Test Title");
        testPost.setContent("Test Content");
        postRepository.save(testPost);

        testReaction = new PostReaction();
        testReaction.setPostId(testPost.getId());
        testReaction.setAuthorUsername("testuser");
        postReactionRepository.save(testReaction);
    }

    @AfterEach
    void tearDown() {
        if (testReaction != null) {
            postReactionRepository.delete(testReaction);
        }
        if (testPost != null) {
            postRepository.delete(testPost);
        }
        if (testUser != null) {
            userRepository.deleteById(testUser.getUsername());
        }
    }

    @Test
    void testFindByPostId() {
        List<PostReaction> reactions = postReactionRepository.findByPostId(testPost.getId());
        assertFalse(reactions.isEmpty());
        assertEquals("testuser", reactions.get(0).getAuthorUsername());
    }

    @Test
    void testCountByPostId() {
        int count = postReactionRepository.countByPostId(testPost.getId());
        assertEquals(1, count);
    }

    @Test
    void testExistsByPostIdAndAuthorUsername() {
        boolean exists = postReactionRepository.existsByPostIdAndAuthorUsername(testPost.getId(), "testuser");
        assertTrue(exists);
    }
}