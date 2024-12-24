package com.example.uni_dubna.service;

import com.example.uni_dubna.models.Role;
import com.example.uni_dubna.models.ScientificUser;
import com.example.uni_dubna.repo.ScientificUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final ScientificUserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(ScientificUserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    // Получение всех пользователей
    public List<ScientificUser> getAllUsers() {
        return userRepository.findAll();
    }

    // Создание нового пользователя
    public void createUser(String username, String password, String roleName) throws Exception {
        if (userRepository.existsByUsername(username)) {
            throw new Exception("Пользователь с таким именем уже существует.");
        }
        Role role = roleService.findByName(roleName);
        if (role == null) {
            throw new Exception("Роль не найдена.");
        }
        ScientificUser user = new ScientificUser();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        userRepository.save(user);
    }

    // Другие методы управления пользователями по мере необходимости
}