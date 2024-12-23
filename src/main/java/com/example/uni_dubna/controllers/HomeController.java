package com.example.uni_dubna.controllers;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.logging.Logger;

@Controller
public class HomeController {


    @GetMapping("/home")
    public String homePage(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        return "home"; // Название шаблона домашней страницы (например, home.html)
    }
}