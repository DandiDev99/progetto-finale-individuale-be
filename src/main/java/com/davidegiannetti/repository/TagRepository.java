package com.davidegiannetti.repository;

import com.davidegiannetti.entity.Post;
import com.davidegiannetti.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface TagRepository extends JpaRepository<Tag,Long> {

}
