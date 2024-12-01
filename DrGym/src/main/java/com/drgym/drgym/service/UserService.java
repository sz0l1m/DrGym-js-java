package com.drgym.drgym.service;

import com.drgym.drgym.model.User;
import com.drgym.drgym.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> findByUsername(String username) {
        return userRepository.findById(username);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(String username) {
        userRepository.deleteById(username);
    }

    public Optional<User> updateUser(String username, String new_name, Double height) {
        return userRepository.findById(username).map(user -> {
            user.setName(new_name);
            return userRepository.save(user);
        });
    }
}
