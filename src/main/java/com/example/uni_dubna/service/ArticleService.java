package com.example.uni_dubna.service;

import com.example.uni_dubna.models.Article;
import com.example.uni_dubna.repo.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void uploadArticle(Article article) {
        article = new Article();
        // Логика преобразования DTO в сущность
        articleRepository.save(article);
    }

    public Article getArticleById(Long id) throws Throwable {
        return (Article) articleRepository.findById(id).orElseThrow(() -> new RuntimeException("Article not found"));
    }
}