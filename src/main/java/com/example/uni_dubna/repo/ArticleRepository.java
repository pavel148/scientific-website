package com.example.uni_dubna.repo;

import com.example.uni_dubna.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}