package com.example.uni_dubna.controllers;

import com.example.uni_dubna.models.Article;
import com.example.uni_dubna.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/author")
public class AuthorController {

    private final ArticleService articleService;

    @Autowired
    public AuthorController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles")
    public String listArticles(Authentication authentication, Model model) {
        String username = authentication.getName();
        model.addAttribute("articles", articleService.findArticlesByAuthor(username));
        return "author/articles"; // Создайте шаблон author/articles.html
    }

    @GetMapping("/articles/new")
    public String newArticleForm(Model model) {
        model.addAttribute("article", new Article());
        return "author/new-article"; // Создайте шаблон author/new-article.html
    }

    @PostMapping("/articles")
    public String createArticle(@ModelAttribute("article") @Valid Article article,
                                BindingResult result,
                                Authentication authentication,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", "Ошибка при создании статьи");
            return "author/new-article";
        }

        try {
            articleService.createArticle(article, authentication.getName());
        } catch (Exception e) {
            model.addAttribute("error", "Произошла ошибка при создании статьи");
            return "author/new-article";
        }

        return "redirect:/author/articles";
    }

    @GetMapping("/articles/{id}")
    public String viewArticle(@PathVariable Long id, Model model) {
        try {
            Article article = articleService.findArticleById(id);
            model.addAttribute("article", article);
            return "author/view-article"; // Создайте шаблон author/view-article.html
        } catch (Exception e) {
            model.addAttribute("error", "Статья не найдена");
            return "redirect:/author/articles";
        }
    }

    // Добавьте другие методы для редактирования и удаления статей при необходимости
}