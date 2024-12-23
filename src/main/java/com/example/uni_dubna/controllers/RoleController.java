package com.example.uni_dubna.controllers;

import com.example.uni_dubna.models.Role;
import com.example.uni_dubna.service.RoleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/create")
    public Role createRole(@RequestParam String roleName) {
        // Создаем роль с переданным именем
        return roleService.createRole(roleName);
    }
}
