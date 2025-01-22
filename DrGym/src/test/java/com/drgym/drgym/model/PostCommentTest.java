package com.drgym.drgym.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PostCommentTest {

    private PostComment postComment;

    @BeforeEach
    void setUp() {
        postComment = new PostComment(1L, "Test Content", "Test Author", LocalDateTime.now());
    }

    @Test
    void testPostCommentConstructor() {
        assertEquals(1L, postComment.getId());
        assertEquals("Test Content", postComment.getContent());
        assertEquals("Test Author", postComment.getAuthor());
    }

    @Test
    void testSetId() {
        postComment.setId(2L);
        assertEquals(2L, postComment.getId());
    }

    @Test
    void testSetContent() {
        postComment.setContent("Updated Content");
        assertEquals("Updated Content", postComment.getContent());
    }

    @Test
    void testSetAuthor() {
        postComment.setAuthor("Updated Author");
        assertEquals("Updated Author", postComment.getAuthor());
    }

    @Test
    void testSetDate() {
        LocalDateTime now = LocalDateTime.now();
        postComment.setDate(now);
        assertEquals(now, postComment.getDate());
    }
}