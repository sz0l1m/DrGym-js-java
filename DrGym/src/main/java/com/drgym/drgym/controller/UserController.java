package com.drgym.drgym.controller;

import com.drgym.drgym.model.User;
import com.drgym.drgym.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        Optional<User> user = userService.findByUsername(username);
        return user.map(u -> ResponseEntity.ok(new UserDTO(u.getUsername(), u.getName(), u.getSurname(), u.getWeight(), u.getHeight())))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }


    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }

    private record UserDTO(String username, String name, String surname, Double weight, Double height) {}
}
