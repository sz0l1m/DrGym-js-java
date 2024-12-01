// author: ksiemion
package com.drgym.drgym.service;

import com.drgym.drgym.model.User;
import com.drgym.drgym.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByUsername() {
        User user = new User("ziutson", "test", "test", "test", "test", 80.0, 180.0);
        when(userRepository.findById("ziutson")).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByUsername("ziutson");

        assertTrue(result.isPresent());
        assertEquals("ziutson", result.get().getUsername());
    }

    @Test
    void testFindByEmail() {
        User user = new User("ziutson", "test", "test", "test@test.test", "test", 80.0, 180.0);
        when(userRepository.findByEmail("test@test.test")).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByEmail("test@test.test");

        assertTrue(result.isPresent());
        assertEquals("test@test.test", result.get().getEmail());
    }

    @Test
    void testCreateUser() {
        User user = new User("ziutson", "test", "test", "test", "test", 80.0, 180.0);
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.saveUser(user);

        assertNotNull(result);
        assertEquals("ziutson", result.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testDeleteUser() {
        String username = "ziutson";
        doNothing().when(userRepository).deleteById(username);

        userService.deleteUser(username);

        verify(userRepository, times(1)).deleteById(username);
    }
}
