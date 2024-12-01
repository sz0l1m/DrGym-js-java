// author: ksiemion
package com.drgym.drgym.repository;

import com.drgym.drgym.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveAndFindUser() {
        User user = new User("ziutson", "test", "test", "test", "test", 80.0, 180.0);

        User savedUser = userRepository.save(user);

        assertNotNull(savedUser);
        assertEquals("ziutson", savedUser.getUsername());

        User foundUser = userRepository.findById("ziutson").orElse(null);
        assertNotNull(foundUser);
        assertEquals("ziutson", foundUser.getUsername());

        userRepository.deleteById("ziutson");
    }

    @Test
    void testFindByEmail() {
        User user = new User("ziutson", "test", "test", "test@test.test", "test", 80.0, 180.0);

        User savedUser = userRepository.save(user);

        assertNotNull(savedUser);
        assertEquals("ziutson", savedUser.getUsername());

        User foundUser = userRepository.findByEmail("test@test.test").orElse(null);
        assertNotNull(foundUser);
        assertEquals("test@test.test", foundUser.getEmail());

        userRepository.deleteById("ziutson");
    }

    @Test
    void testDeleteUser() {
        User user = new User("ziutson", "test", "test", "test", "test", 80.0, 180.0);
        userRepository.save(user);

        userRepository.deleteById("ziutson");

        assertFalse(userRepository.findById("ziutson").isPresent());
    }
}
