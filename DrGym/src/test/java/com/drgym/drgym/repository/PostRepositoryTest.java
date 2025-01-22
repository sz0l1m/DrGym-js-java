package com.drgym.drgym.repository;

import com.drgym.drgym.model.Post;
import com.drgym.drgym.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
    }

    @AfterEach
    void tearDown() {
        if (testPost != null) {
            postRepository.delete(testPost);
        }
        if (testUser != null) {
            userRepository.deleteById(testUser.getUsername());
        }
    }

    @Test
    void testFindByUsername() {
        List<Post> posts = postRepository.findByUsername("testuser", Sort.by(Sort.Direction.DESC, "date"));
        assertFalse(posts.isEmpty());
        assertEquals("testuser", posts.get(0).getUsername());
    }

    @Test
    void testFindByUsernameIn() {
        List<Post> posts = postRepository.findByUsernameIn(List.of("testuser"), Sort.by(Sort.Direction.DESC, "date"));
        assertFalse(posts.isEmpty());
        assertEquals("testuser", posts.get(0).getUsername());
    }
}