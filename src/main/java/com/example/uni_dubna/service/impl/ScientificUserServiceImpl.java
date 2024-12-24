package com.example.uni_dubna.service.impl;

import com.example.uni_dubna.models.Role;
import com.example.uni_dubna.models.ScientificUser;
import com.example.uni_dubna.repo.RoleRepository;
import com.example.uni_dubna.repo.ScientificUserRepository;
import com.example.uni_dubna.security.CustomUserDetails;
import com.example.uni_dubna.service.interfaces.ScientificUserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ScientificUserServiceImpl implements ScientificUserService {

    private final ScientificUserRepository scientificUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ScientificUserServiceImpl(ScientificUserRepository scientificUserRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.scientificUserRepository = scientificUserRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void registerUser(ScientificUser scientificUser) {
        String encodedPassword = passwordEncoder.encode(scientificUser.getPassword());
        scientificUser.setPassword(encodedPassword);

        // Проверка на существование роли пользователя
        Role defaultRole = roleRepository.findByName("ROLE_USER");
        if (defaultRole == null) {
            // Если роль не существует, создаем новую роль
            defaultRole = new Role();
            defaultRole.setName("ROLE_USER");
            roleRepository.save(defaultRole);
        }

        // Устанавливаем роль для пользователя
        scientificUser.setRole(defaultRole);

        // Сохраняем пользователя с ролью
        scientificUserRepository.save(scientificUser);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return scientificUserRepository.existsByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public ScientificUser findByUsername(String username) {
        return scientificUserRepository.findByUsernameWithRole(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    @Transactional
    public void updateUser(String username, ScientificUser updatedUser) {
        ScientificUser existingUser = scientificUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        existingUser.setUsername(updatedUser.getUsername());
        // Обновите другие поля при необходимости

        scientificUserRepository.save(existingUser);
    }

    /**
     * Проверяет, существует ли пользователь с заданным именем пользователя.
     *
     * @param username имя пользователя для проверки
     * @return true, если пользователь существует, иначе false
     */


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

        // Назначение роли
        Role defaultRole = roleRepository.findByName("ROLE_USER");
        if (defaultRole == null) {
            defaultRole = new Role();
            defaultRole.setName("ROLE_USER");
            roleRepository.save(defaultRole);
        }

        // Назначаем роль пользователю
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

        // Назначаем роль пользователю
        user.setRole(persistedRole);

        // Сохраняем пользователя с ролью
        return scientificUserRepository.save(user);
    }


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ScientificUser> scientificUser = scientificUserRepository.findByUsername(username);

        // Если пользователь не найден
        if (scientificUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found!");
        }

        // Возвращаем пользовательские данные
        return new CustomUserDetails(scientificUser.get());
    }


    @Transactional
    public void assignRoleToUser(Long userId, Long roleId) {
        ScientificUser user = scientificUserRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Роль не найдена"));
        user.setRole(role);
        scientificUserRepository.save(user);
    }

    @Bean
    public List<ScientificUser> getAllUser() {
        return scientificUserRepository.findAll();
    }






}