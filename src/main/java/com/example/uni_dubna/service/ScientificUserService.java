package com.example.uni_dubna.service;

import com.example.uni_dubna.models.Role;
import com.example.uni_dubna.models.ScientificUser;
import com.example.uni_dubna.repo.RoleRepository;
import com.example.uni_dubna.repo.ScientificUserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class ScientificUserService {

    private final ScientificUserRepository scientificUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ScientificUserService(ScientificUserRepository scientificUserRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.scientificUserRepository = scientificUserRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registerUser(String login, String password) {
        // Проверка входных данных
        if (login == null || login.trim().isEmpty()) {
            throw new IllegalArgumentException("Login cannot be empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        // Шифрование пароля
        String encodedPassword = passwordEncoder.encode(password);

        // Проверка существования пользователя с данным логином
        if (scientificUserRepository.existsByUsername(login)) {
            throw new IllegalArgumentException("User with this username already exists.");
        }

        // Создание пользователя
        ScientificUser user = new ScientificUser();
        user.setUsername(login);
        user.setPassword(encodedPassword);

        // Назначение роли пользователю
        Role defaultRole = roleRepository.findByName("ROLE_USER");
        if (defaultRole == null) {
            defaultRole = new Role();
            defaultRole.setName("ROLE_USER");
            roleRepository.save(defaultRole);
        }

        // Назначаем роль как одиночное поле (не Set)
        user.setRole(defaultRole);

        // Сохраняем пользователя в базе данных
        scientificUserRepository.save(user);
    }

    // Метод с ролью по умолчанию (ROLE_USER)
    public ScientificUser createScientificUser(String login, String password) {
        // Шифрование пароля
        String encodedPassword = passwordEncoder.encode(password);

        // Проверка существования пользователя с данным логином
        if (scientificUserRepository.existsByUsername(login)) {
            throw new IllegalArgumentException("User with this username already exists.");
        }

        // Создание пользователя
        ScientificUser user = new ScientificUser();
        user.setUsername(login);
        user.setPassword(encodedPassword);

        // Сохраняем пользователя
        user = scientificUserRepository.save(user);

        // Проверка, что пользователь был сохранен
        if (user.getId() == null) {
            throw new RuntimeException("User creation failed. ID is null.");
        }

        // Назначение роли
        Role defaultRole = roleRepository.findByName("ROLE_USER");
        if (defaultRole == null) {
            defaultRole = new Role();
            defaultRole.setName("ROLE_USER");
            roleRepository.save(defaultRole);
        }

        // Назначаем роль как одиночное поле (не Set)
        user.setRole(defaultRole);

        // Сохраняем пользователя с ролью
        return scientificUserRepository.save(user);
    }

    // Метод с переданной ролью
    @Transactional
    public ScientificUser createScientificUser(String login, String password, @NotNull Role role) {
        // Проверка входных данных
        if (login == null || login.trim().isEmpty()) {
            throw new IllegalArgumentException("Login cannot be empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        // Шифрование пароля
        String encodedPassword = passwordEncoder.encode(password);

        // Создание нового пользователя
        ScientificUser user = new ScientificUser();
        user.setUsername(login);
        user.setPassword(encodedPassword);

        // Сохраняем пользователя и получаем его ID
        user = scientificUserRepository.save(user);

        // Загружаем роль из базы данных
        Role persistedRole = roleRepository.findById(role.getId())
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        // Назначаем роль как одиночное поле (не Set)
        user.setRole(persistedRole);

        // Сохраняем пользователя с ролью
        return scientificUserRepository.save(user);
    }
}