package com.example.uni_dubna.repo;

import com.example.uni_dubna.models.Role;
import com.example.uni_dubna.models.ScientificUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ScientificUserRepository extends JpaRepository<ScientificUser, Long> {

    @Query("SELECT u FROM ScientificUser u JOIN FETCH u.role WHERE u.username = :username")
    Optional<ScientificUser> findByUsernameWithRole(@Param("username") String username);

    Optional<ScientificUser> findByUsername(String username);

    boolean existsByUsername(String username);

    List<ScientificUser> findAll();
}