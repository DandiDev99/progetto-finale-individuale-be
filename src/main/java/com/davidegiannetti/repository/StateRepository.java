package com.davidegiannetti.repository;

import com.davidegiannetti.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, Long> {}