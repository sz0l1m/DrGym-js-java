package com.drgym.drgym.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class UserTest {

    @Test
    public void testUserConstructor() {
        User user = new User(1, "username", "name", "surname", "email@example.com", "password", 70.0, 180.0);
        assertEquals(1, user.getId());
        assertEquals("username", user.getUsername());
        assertEquals("name", user.getName());
        assertEquals("surname", user.getSurname());
        assertEquals("email@example.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertEquals(70.0, user.getWeight());
        assertEquals(180.0, user.getHeight());
    }

    @Test
    public void testSetters() {
        User user = new User();
        user.setId(1);
        user.setUsername("username");
        user.setName("name");
        user.setSurname("surname");
        user.setEmail("email@example.com");
        user.setPassword("password");
        user.setWeight(70.0);
        user.setHeight(180.0);

        assertEquals(1, user.getId());
        assertEquals("username", user.getUsername());
        assertEquals("name", user.getName());
        assertEquals("surname", user.getSurname());
        assertEquals("email@example.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertEquals(70.0, user.getWeight());
        assertEquals(180.0, user.getHeight());
    }

    @Test
    public void testAddFriend() {
        User user1 = new User(1, "username1", "name1", "surname1", "email1@example.com", "password1", 70.0, 180.0);
        User user2 = new User(2, "username2", "name2", "surname2", "email2@example.com", "password2", 75.0, 175.0);

        user1.addFriend(user2);

        assertTrue(user1.getFriends().contains(user2));
        assertTrue(user2.getFriends().contains(user1));
    }

    @Test
    public void testRemoveFriend() {
        User user1 = new User(1, "username1", "name1", "surname1", "email1@example.com", "password1", 70.0, 180.0);
        User user2 = new User(2, "username2", "name2", "surname2", "email2@example.com", "password2", 75.0, 175.0);

        user1.addFriend(user2);
        user1.removeFriend(user2);

        assertFalse(user1.getFriends().contains(user2));
        assertFalse(user2.getFriends().contains(user1));
    }

    @Test
    public void testTrainingHistory() {
        User user = new User();
        List<Training> trainingHistory = new ArrayList<>();
        Training training = new Training();
        trainingHistory.add(training);

        user.setTrainingHistory(trainingHistory);

        assertEquals(1, user.getTrainingHistory().size());
        assertEquals(training, user.getTrainingHistory().get(0));
    }

    @Test
    public void testPosts() {
        User user = new User();
        List<Post> posts = new ArrayList<>();
        Post post = new Post();
        posts.add(post);

        user.setPosts(posts);

        assertEquals(1, user.getPosts().size());
        assertEquals(post, user.getPosts().get(0));
    }
}

