package com.davidegiannetti.repository;

import com.davidegiannetti.entity.Validator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ValidatorRepository extends JpaRepository<Validator, Long> {

    Optional<Validator> findByFieldName(String fieldName);

}
