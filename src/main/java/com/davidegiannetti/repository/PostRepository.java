package com.davidegiannetti.repository;

import com.davidegiannetti.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByTitle(String title);
}
