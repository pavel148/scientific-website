package com.example.uni_dubna.repo;

import com.example.uni_dubna.models.Review;
import com.example.uni_dubna.models.ScientificUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    /**
     * Находит все рецензии, назначенные данному рецензенту.
     *
     * @param reviewer рецензент
     * @return список рецензий
     */
    List<Review> findByReviewer(ScientificUser reviewer);

    /**
     * Находит все рецензии для данной статьи.
     *
     * @param article статья
     * @return список рецензий
     */
    List<Review> findByArticle(com.example.uni_dubna.models.Article article);
}