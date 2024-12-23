package com.example.uni_dubna.controllers;

import com.example.uni_dubna.models.ScientificUser;
import com.example.uni_dubna.service.ScientificUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final ScientificUserService scientificUserService;
    @Autowired
    public AuthController(AuthenticationManager authenticationManager, ScientificUserService scientificUserService) {
        this.authenticationManager = authenticationManager;
        this.scientificUserService = scientificUserService;
    }

    @GetMapping("/login")
    public String loginPage(HttpServletRequest request) {
        // Пример проверки, если пользователь уже аутентифицирован
        return "auth/login"; // Возвращаем страницу логина, если пользователь не аутентифицирован
    }

    @PostMapping("/process_login")
    public String processLogin(@RequestParam String username, @RequestParam String password) {
        try {
            // Создаём объект аутентификации
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);

            // Пытаемся аутентифицировать пользователя
            Authentication authenticated = authenticationManager.authenticate(authentication);

            // Если аутентификация успешна, сохраняем информацию в контексте
            SecurityContextHolder.getContext().setAuthentication(authenticated);

            // Перенаправляем на главную страницу после успешного входа
            return "/index";
        } catch (BadCredentialsException e) {
            // Если аутентификация не удалась
            return "/auth/login?error";
        }
    }
    // Страница регистрации
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("scientificUser", new ScientificUser()); // Используем имя "scientificUser"
        return "auth/register"; // Имя шаблона для регистрации
    }

    // Обработка регистрации
    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String confirmPassword,
                           Model model) {

        // Проверяем, совпадают ли пароли
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", true);
            return "auth/register"; // Если пароли не совпадают, возвращаем на форму
        }

        // Регистрируем нового пользователя
        scientificUserService.createScientificUser(username, password);
        return "index"; // После успешной регистрации перенаправляем на страницу входа
    }
}
