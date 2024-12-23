package com.example.uni_dubna.service;

import com.example.uni_dubna.models.Review;

import java.util.List;

public interface ReviewService {

    /**
     * Создает новую рецензию.
     *
     * @param review рецензия для создания
     * @param reviewerUsername имя пользователя рецензента
     * @return созданная рецензия
     */
    Review createReview(Review review, String reviewerUsername);

    /**
     * Находит все рецензии, назначенные данному рецензенту.
     *
     * @param reviewerUsername имя пользователя рецензента
     * @return список рецензий
     */
    List<Review> findReviewsByReviewer(String reviewerUsername);

    /**
     * Находит рецензию по ее ID.
     *
     * @param id идентификатор рецензии
     * @return рецензия
     */
    Review findReviewById(Long id);

    /**
     * Обновляет статус рецензии.
     *
     * @param id идентификатор рецензии
     * @param status новый статус
     * @return обновленная рецензия
     */
    Review updateReviewStatus(Long id, com.example.uni_dubna.models.ReviewStatus status);

    // Добавьте дополнительные методы по необходимости
}