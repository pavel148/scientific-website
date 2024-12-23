package com.example.uni_dubna.controllers;

import com.example.uni_dubna.models.Role;
import com.example.uni_dubna.models.ScientificUser;
import com.example.uni_dubna.service.RoleService;
import com.example.uni_dubna.service.ScientificUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/users")
public class ScientificUserController {

    private final ScientificUserService userService;
    private final RoleService roleService;

    @Autowired
    public ScientificUserController(ScientificUserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostMapping("/create")
    @Transactional
    public String createScientificUser(@RequestParam String username,
                                       @RequestParam String password,
                                       @RequestParam String roleName,  // Получаем строковое значение роли
                                       Model model) {

        // Ищем роль в базе данных или создаем, если ее нет
        Role role = roleService.createRole(roleName);

        // Создаем пользователя с заданными параметрами
        ScientificUser newUser = userService.createScientificUser(username, password, role);

        // Добавляем информацию в модель для отображения на странице
        model.addAttribute("newUser", newUser);

        // Возвращаем имя представления (например, success.html)
        return "success";  // Имя HTML-страницы для отображения
    }

}