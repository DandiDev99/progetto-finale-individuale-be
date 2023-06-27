package com.davidegiannetti.repository;

import com.davidegiannetti.entity.Post;
import com.davidegiannetti.entity.User;
import com.davidegiannetti.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface VoteRepository extends JpaRepository<Vote,Long> {

    Optional<Vote> findByUserAndPost(User user, Post post);
    Set<Vote> findByPost(Post post);
}
