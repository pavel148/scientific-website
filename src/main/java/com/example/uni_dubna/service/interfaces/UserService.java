package com.example.uni_dubna.service.interfaces;


import com.example.uni_dubna.models.ScientificUser;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<ScientificUser> getAllUsers();
    Optional<ScientificUser> findByUsername(String username);
    ScientificUser createUser(String username, String password, String roleName) throws Exception;
    boolean existsByUsername(String username);
    void assignRoleToUser(Long userId, Long roleId);



}
