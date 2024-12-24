package com.example.uni_dubna.controllers;

import com.example.uni_dubna.models.Role;
import com.example.uni_dubna.models.ScientificUser;
import com.example.uni_dubna.service.RoleService;
import com.example.uni_dubna.service.impl.ScientificUserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final RoleService roleService;
    private final ScientificUserServiceImpl userService;

    @Autowired
    public AdminController(RoleService roleService, ScientificUserServiceImpl userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    /**
     * Отображение страницы управления пользователями
     */
    @GetMapping("/users")
    public String manageUsers(Model model) {
        List<ScientificUser> users = userService.getAllUser();
        model.addAttribute("users", users);
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/users"; // Шаблон admin/users.html
    }

    /**
     * Обработка создания нового пользователя
     */
    @PostMapping("/users/create")
    public String createUser(@RequestParam String username,
                             @RequestParam String password,
                             @RequestParam String roleName,
                             Model model) {
        if (userService.existsByUsername(username)) {
            model.addAttribute("error", "Пользователь с таким именем уже существует.");
            model.addAttribute("users", userService.getAllUser());
            model.addAttribute("roles", roleService.getAllRoles());
            return "admin/users";
        }
        try {
            Role role = roleService.findByName(roleName);
            if (role == null) {
                model.addAttribute("error", "Роль не найдена.");
                model.addAttribute("users", userService.getAllUser());
                model.addAttribute("roles", roleService.getAllRoles());
                return "admin/users";
            }
            userService.createScientificUser(username, password, role);
            model.addAttribute("success", "Пользователь успешно создан.");
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при создании пользователя: " + e.getMessage());
        }
        model.addAttribute("users", userService.getAllUser());
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/users";
    }

    /**
     * Отображение страницы управления ролями
     */
    @GetMapping("/roles")
    public String manageRoles(Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("role", new Role());
        return "admin/roles"; // Шаблон admin/roles.html
    }

    /**
     * Обработка создания новой роли
     */
    @PostMapping("/roles")
    public String createRole(@ModelAttribute("role") @Valid Role role,
                             BindingResult result,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", "Ошибка при создании роли");
            model.addAttribute("roles", roleService.getAllRoles());
            return "admin/roles";
        }

        try {
            roleService.createRole(role.getName());
        } catch (Exception e) {
            model.addAttribute("error", "Роль с таким именем уже существует");
            model.addAttribute("roles", roleService.getAllRoles());
            return "admin/roles";
        }

        return "redirect:/admin/roles";
    }

    /**
     * Обработка назначения роли пользователю
     */
    @PostMapping("/users/assign-role")
    public String assignRoleToUser(@RequestParam Long userId,
                                   @RequestParam Long roleId,
                                   Model model) {
        try {
            userService.assignRoleToUser(userId, roleId);
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при назначении роли");
        }
        return "redirect:/admin/users";
    }

    /**
     * Отображение админской панели
     */
    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "admin/dashboard"; // Шаблон admin/dashboard.html
    }

    /**
     * Переадресация корневого админского маршрута на панель управления
     */
    @GetMapping("/")
    public String adminMenu() {
        return "redirect:/admin/dashboard";
    }
}