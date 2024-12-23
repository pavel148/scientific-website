package com.example.uni_dubna.service.impl;
import com.example.uni_dubna.models.Article;
import com.example.uni_dubna.models.Review;
import com.example.uni_dubna.models.ScientificUser;
import com.example.uni_dubna.repo.ArticleRepository;
import com.example.uni_dubna.repo.ReviewRepository;
import com.example.uni_dubna.repo.ScientificUserRepository;

import com.example.uni_dubna.service.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ScientificUserRepository scientificUserRepository;
    private final ArticleRepository articleRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             ScientificUserRepository scientificUserRepository,
                             ArticleRepository articleRepository) {
        this.reviewRepository = reviewRepository;
        this.scientificUserRepository = scientificUserRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    @Transactional
    public Review createReview(Review review, String reviewerUsername) {
        // Находим рецензента по имени пользователя
        ScientificUser reviewer = scientificUserRepository.findByUsername(reviewerUsername)
                .orElseThrow(() -> new IllegalArgumentException("Рецензент не найден"));

        // Находим статью для рецензирования
        Article article = articleRepository.findById(review.getArticle().getId())
                .orElseThrow(() -> new IllegalArgumentException("Статья не найдена"));

        // Устанавливаем рецензента и статью
        review.setReviewer(reviewer);
        review.setArticle(article);
        review.setStatus(com.example.uni_dubna.models.ReviewStatus.PENDING); // Устанавливаем статус по умолчанию

        // Сохраняем рецензию
        return reviewRepository.save(review);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Review> findReviewsByReviewer(String reviewerUsername) {
        // Находим рецензента
        ScientificUser reviewer = scientificUserRepository.findByUsername(reviewerUsername)
                .orElseThrow(() -> new IllegalArgumentException("Рецензент не найден"));

        // Возвращаем все рецензии, назначенные этому рецензенту
        return reviewRepository.findByReviewer(reviewer);
    }

    @Override
    @Transactional(readOnly = true)
    public Review findReviewById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Рецензия не найдена"));
    }

    @Override
    @Transactional
    public Review updateReviewStatus(Long id, com.example.uni_dubna.models.ReviewStatus status) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Рецензия не найдена"));

        review.setStatus(status);
        reviewRepository.save(review);
        return review;
    }

    // Реализуйте другие методы по необходимости
}