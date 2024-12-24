package com.example.uni_dubna.service;

import com.example.uni_dubna.models.Role;
import com.example.uni_dubna.models.ScientificUser;
import com.example.uni_dubna.repo.RoleRepository;
import com.example.uni_dubna.repo.ScientificUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Service

public class RoleService {
    private final RoleRepository roleRepository;
    private final ScientificUserRepository scientificUserRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository, ScientificUserRepository scientificUserRepository) {
        this.roleRepository = roleRepository;
        this.scientificUserRepository = scientificUserRepository;
    }
    // Список ролей по умолчанию
    private static final String[] DEFAULT_ROLES = {"ROLE_ADMIN", "ROLE_REVIEWER", "ROLE_AUTHOR", "ROLE_SYS_ADMIN","ROLE_USER"};

    // Метод для создания ролей по умолчанию
    @PostConstruct
    public void createDefaultRoles() {
        for (String roleName : DEFAULT_ROLES) {
            // Если роль с таким именем не существует, создаем её
            if (roleRepository.findByName(roleName) == null) {
                Role role = new Role();
                role.setName(roleName);
                roleRepository.save(role);
            }
        }
        roleRepository.findAll();
    }

    @PreAuthorize("hasRole('ROLE_SYS_ADMIN')")
    public void addRole(String roleName) {
        // Проверяем, если такая роль уже существует
        if (roleRepository.findByName(roleName) == null) {
            Role newRole = new Role();
            newRole.setName(roleName);
            roleRepository.save(newRole);
        }
    }
    @Transactional
    public Role createRole(String roleName) {
        // Проверка, существует ли роль с таким именем
        Role existingRole = roleRepository.findByName(roleName);

        if (existingRole != null) {
            // Если роль существует, возвращаем её
            return existingRole;
        }

        // Если роль не существует, создаем новую
        Role role = new Role();
        role.setName(roleName);
        return roleRepository.save(role);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll(); // Здесь вы получаете все роли
    }

    @Bean
    public List<ScientificUser> getAllUsers() {
        return scientificUserRepository.findAll();
    }

    public void assignRoleToUser(Long userId, Long roleId) {
        ScientificUser user = scientificUserRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Роль не найдена"));
        user.setRole(role);
        scientificUserRepository.save(user);
    }


    public Role findByName(String roleName) {
        return roleRepository.findByName(roleName);
    }
}
