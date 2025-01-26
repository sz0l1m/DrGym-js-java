package com.drgym.drgym.repository;

import com.drgym.drgym.model.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUsername(String username, Sort sort);
    List<Post> findByUsernameIn(List<String> usernames, Sort sort);
}