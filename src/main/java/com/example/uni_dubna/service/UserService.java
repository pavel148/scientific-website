package com.example.uni_dubna.service;

import com.example.uni_dubna.models.Role;
import com.example.uni_dubna.models.ScientificUser;
import com.example.uni_dubna.repo.RoleRepository;
import com.example.uni_dubna.repo.ScientificUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements com.example.uni_dubna.service.interfaces.UserService {

    private final ScientificUserRepository userRepository;
    private final RoleService roleService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(ScientificUserRepository userRepository, RoleService roleService, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Получение всех пользователей
    public List<ScientificUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<ScientificUser> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Создание нового пользователя
//    public void createUser(String username, String password, String roleName) throws Exception {
//        if (userRepository.existsByUsername(username)) {
//            throw new Exception("Пользователь с таким именем уже существует.");
//        }
//        Role role = roleService.findByName(roleName);
//        if (role == null) {
//            throw new Exception("Роль не найдена.");
//        }
//        ScientificUser user = new ScientificUser();
//        user.setUsername(username);
//        user.setPassword(passwordEncoder.encode(password));
//        user.setRole(role);
//        userRepository.save(user);
//    }
    @Override
    @Transactional
    public void assignRoleToUser(Long userId, Long roleId) {
        ScientificUser user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Роль не найдена"));
        user.setRole(role);
        userRepository.save(user);
    }
    @Override
    @Transactional
    public ScientificUser createUser(String username, String password, String roleName) throws Exception {
        if (userRepository.existsByUsername(username)) {
            throw new Exception("Пользователь с таким именем уже существует.");
        }

        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            throw new Exception("Роль не найдена.");
        }

        ScientificUser user = new ScientificUser();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        return userRepository.save(user);
    }
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

//    @Transactional
//    public void assignRoleToUser(Long userId, Long roleId) {
//        ScientificUser user = userRepository.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
//        Role role = roleRepository.findById(roleId)
//                .orElseThrow(() -> new IllegalArgumentException("Роль не найдена"));
//        user.setRole(role);
//        userRepository.save(user);
//    }
    // Другие методы управления пользователями по мере необходимости
}