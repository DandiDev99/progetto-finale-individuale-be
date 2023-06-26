package com.davidegiannetti.repository;

import com.davidegiannetti.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
