package com.example.uni_dubna.repo;

import com.example.uni_dubna.models.Article;
import com.example.uni_dubna.models.ScientificUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    /**
     * Находит все статьи, написанные данным автором.
     *
     * @param author автор
     * @return список статей
     */
    List<Article> findByAuthor(ScientificUser author);

    /**
     * Дополнительный метод с использованием JPQL для более сложных запросов (опционально).
     */
    @Query("SELECT a FROM Article a WHERE a.author.username = ?1")
    List<Article> findArticlesByAuthorUsername(String username);
}