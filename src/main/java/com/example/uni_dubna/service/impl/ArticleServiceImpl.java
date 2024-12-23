package com.example.uni_dubna.service.impl;

import com.example.uni_dubna.repo.ArticleRepository;
import com.example.uni_dubna.repo.ScientificUserRepository;
import com.example.uni_dubna.service.ArticleService;
import org.springframework.stereotype.Service;
import com.example.uni_dubna.models.Article;
import com.example.uni_dubna.models.ScientificUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ScientificUserRepository scientificUserRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository, ScientificUserRepository scientificUserRepository) {
        this.articleRepository = articleRepository;
        this.scientificUserRepository = scientificUserRepository;
    }

    @Override
    @Transactional
    public Article createArticle(Article article, String authorUsername) {
        // Находим автора по имени пользователя
        ScientificUser author = scientificUserRepository.findByUsername(authorUsername)
                .orElseThrow(() -> new IllegalArgumentException("Автор не найден"));

        // Устанавливаем автора статьи
        article.setAuthor(author);

        // Сохраняем статью
        return articleRepository.save(article);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Article> findArticlesByAuthor(String authorUsername) {
        // Метод 1: Использование findByAuthor
        ScientificUser author = scientificUserRepository.findByUsername(authorUsername)
                .orElseThrow(() -> new IllegalArgumentException("Автор не найден"));
        return articleRepository.findByAuthor(author);

        // Метод 2: Использование findArticlesByAuthorUsername (опционально)
        // return articleRepository.findArticlesByAuthorUsername(authorUsername);
    }

    @Override
    @Transactional(readOnly = true)
    public Article findArticleById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Статья не найдена"));
    }

    // Реализуйте другие методы по необходимости
}