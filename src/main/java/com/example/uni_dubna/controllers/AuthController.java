package com.example.uni_dubna.controllers;

import com.example.uni_dubna.models.ScientificUser;
import com.example.uni_dubna.service.impl.ScientificUserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final ScientificUserServiceImpl scientificUserServiceImpl;

    @Autowired
    public AuthController(ScientificUserServiceImpl scientificUserServiceImpl) {
        this.scientificUserServiceImpl = scientificUserServiceImpl;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login"; // Страница авторизации
    }

    @GetMapping("/register")
    public String registerPage(@ModelAttribute ScientificUser scientificUser) {
        return "auth/register"; // Страница регистрации
    }

    @PostMapping("/register")
    public String performRegistration(@ModelAttribute("scientificUser") @Valid ScientificUser scientificUser,
                                      BindingResult result, Model model) {
        // Проверка наличия ошибок валидации
        if (result.hasErrors()) {
            model.addAttribute("error", "Ошибка при регистрации");
            return "auth/register";
        }

        // Проверка существования пользователя
        if (scientificUserServiceImpl.existsByUsername(scientificUser.getUsername())) {
            model.addAttribute("error", "Пользователь с таким именем уже существует");
            return "auth/register";
        }

        try {
            // Регистрация пользователя
            scientificUserServiceImpl.registerUser(scientificUser);
        } catch (Exception e) {
            // Обработка возможных исключений при регистрации
            model.addAttribute("error", "Произошла ошибка при регистрации пользователя");
            return "auth/register";
        }

        return "redirect:/home"; // После успешной регистрации редирект на домашнюю страницу
    }

    // Удаляем метод logout, чтобы Spring Security обрабатывал его самостоятельно
}
