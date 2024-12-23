package com.example.uni_dubna.service;

import com.example.uni_dubna.models.Article;
import com.example.uni_dubna.repo.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ArticleService {

    /**
     * Создает новую статью.
     *
     * @param article статья для создания
     * @param authorUsername имя пользователя автора
     * @return созданная статья
     */
    Article createArticle(Article article, String authorUsername);

    /**
     * Находит все статьи, написанные данным автором.
     *
     * @param authorUsername имя пользователя автора
     * @return список статей
     */
    List<Article> findArticlesByAuthor(String authorUsername);

    /**
     * Находит статью по ее ID.
     *
     * @param id идентификатор статьи
     * @return статья
     */
    Article findArticleById(Long id);

    // Добавьте дополнительные методы по необходимости (редактирование, удаление и т.д.)
}