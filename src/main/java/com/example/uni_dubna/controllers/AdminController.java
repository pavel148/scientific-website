package com.example.uni_dubna.controllers;

import com.example.uni_dubna.models.Role;
import com.example.uni_dubna.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;



@Controller
@RequestMapping("/admin")
public class AdminController {

    private final RoleService roleService;

    @Autowired
    public AdminController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    public String manageRoles(Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("role", new Role());
        return "admin/roles"; // Создайте шаблон admin/roles.html
    }

    @PostMapping("/roles")
    public String createRole(@ModelAttribute("role") @Valid Role role, BindingResult result, Model model) {
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

    // Контроллер для Управления Пользователями
    @GetMapping("/users")
    public String manageUsers(Model model) {
        model.addAttribute("users", roleService.getAllUsers()); // Добавьте метод getAllUsers() в RoleService или отдельный UserService
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/users"; // Создайте шаблон admin/users.html
    }

    @PostMapping("/users/assign-role")
    public String assignRoleToUser(@RequestParam("userId") Long userId,
                                   @RequestParam("roleId") Long roleId,
                                   Model model) {
        try {
            roleService.assignRoleToUser(userId, roleId); // Добавьте метод assignRoleToUser() в RoleService или отдельный UserService
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при назначении роли");
        }
        return "redirect:/admin/users";
    }
}