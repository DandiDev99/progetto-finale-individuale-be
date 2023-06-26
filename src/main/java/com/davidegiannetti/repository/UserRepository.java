package com.davidegiannetti.repository;

import com.davidegiannetti.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

}
