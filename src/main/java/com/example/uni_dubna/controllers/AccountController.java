package com.example.uni_dubna.controllers;



import com.example.uni_dubna.models.ScientificUser;

import com.example.uni_dubna.service.impl.ScientificUserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AccountController {

    private final ScientificUserServiceImpl scientificUserServiceImpl;

    @Autowired
    public AccountController(ScientificUserServiceImpl scientificUserServiceImpl) {
        this.scientificUserServiceImpl = scientificUserServiceImpl;
    }

    @GetMapping("/account")
    public String accountPage(Authentication authentication, Model model) {
        String username = authentication.getName();
        ScientificUser user = scientificUserServiceImpl.findByUsername(username);
        model.addAttribute("user", user);
        return "account"; // Создайте шаблон account.html
    }

    @PostMapping("/account/update")
    public String updateAccount(@ModelAttribute("user") @Valid ScientificUser updatedUser,
                                BindingResult result,
                                Authentication authentication,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", "Ошибка при обновлении информации");
            return "account";
        }

        try {
            scientificUserServiceImpl.updateUser(authentication.getName(), updatedUser);
        } catch (Exception e) {
            model.addAttribute("error", "Произошла ошибка при обновлении информации");
            return "account";
        }

        model.addAttribute("success", "Информация успешно обновлена");
        return "account";
    }
}