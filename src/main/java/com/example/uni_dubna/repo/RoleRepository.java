package com.example.uni_dubna.repo;

import com.example.uni_dubna.models.Role;

import com.example.uni_dubna.models.ScientificUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}