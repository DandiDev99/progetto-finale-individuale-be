package com.davidegiannetti.repository;

import com.davidegiannetti.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}