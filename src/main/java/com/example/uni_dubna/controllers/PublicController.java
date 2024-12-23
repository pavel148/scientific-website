package com.example.uni_dubna.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/public")
public class PublicController {

    @GetMapping("/info")
    public String publicInfo() {
        return "public/info"; // Создайте шаблон public/info.html
    }

    @GetMapping("/home")
    public String publicHome() {
        return "public/home"; // Создайте шаблон public/home.html
    }
}