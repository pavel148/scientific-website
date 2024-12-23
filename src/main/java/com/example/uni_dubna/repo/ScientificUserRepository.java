package com.example.uni_dubna.repo;

import com.example.uni_dubna.models.Role;
import com.example.uni_dubna.models.ScientificUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScientificUserRepository extends JpaRepository<ScientificUser, Long> {
    Optional<ScientificUser> findByUsername(String username);
    boolean existsByUsername(String username);

    List<ScientificUser> findAll();
}