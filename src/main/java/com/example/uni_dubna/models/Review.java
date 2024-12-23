
package com.example.uni_dubna.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import javax.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Связь с рецензией (отзывом)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id", nullable = false)
    private ScientificUser reviewer;

    // Связь со статьей
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @NotEmpty(message = "Текст рецензии не может быть пустым")
    @Size(max = 2000, message = "Рецензия не может превышать 2000 символов")
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ScientificUser getReviewer() {
        return reviewer;
    }

    public void setReviewer(ScientificUser reviewer) {
        this.reviewer = reviewer;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ReviewStatus getStatus() {
        return status;
    }

    public void setStatus(ReviewStatus status) {
        this.status = status;
    }

    public LocalDateTime getReviewedAt() {
        return reviewedAt;
    }

    public void setReviewedAt(LocalDateTime reviewedAt) {
        this.reviewedAt = reviewedAt;
    }

    @Enumerated(EnumType.STRING)
    private ReviewStatus status;

    private LocalDateTime reviewedAt;

    // Конструкторы
    public Review() {
        this.reviewedAt = LocalDateTime.now();
    }

    // Getters and Setters

    // toString, equals и hashCode методы
}
