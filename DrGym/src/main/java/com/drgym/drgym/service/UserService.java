package com.drgym.drgym.service;

import com.drgym.drgym.model.User;
import com.drgym.drgym.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> findByUsername(String username) {
        return userRepository.findById(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(String username) {
        userRepository.deleteByUsername(username);
    }

    public List<User> findBySearch(String search) {
        return userRepository.findByUsernameContaining(search);
    }

    public boolean areUsersFriends(String username1, String username2) {
        return userRepository.areUsersFriends(username1, username2);
    }

    @Transactional
    public Optional<User> updateUser(String currentUsername, User updatedUser) {
        return userRepository.findById(currentUsername).map(user -> {
            user.setName(updatedUser.getName());
            user.setSurname(updatedUser.getSurname());
            user.setWeight(updatedUser.getWeight());
            user.setHeight(updatedUser.getHeight());
            user.setFavoriteExercise(updatedUser.getFavoriteExercise());
            user.setAvatar(updatedUser.getAvatar());
            return userRepository.save(user);
        });
    }
}
