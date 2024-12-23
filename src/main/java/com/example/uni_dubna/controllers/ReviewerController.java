package com.example.uni_dubna.controllers;

import com.example.uni_dubna.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.uni_dubna.models.Review;
import com.example.uni_dubna.models.ReviewStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reviewer")
public class ReviewerController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewerController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/reviews")
    public String listReviews(Authentication authentication, Model model) {
        String username = authentication.getName();
        model.addAttribute("reviews", reviewService.findReviewsByReviewer(username));
        return "reviewer/reviews"; // Создайте шаблон reviewer/reviews.html
    }

    @GetMapping("/reviews/{id}")
    public String viewReview(@PathVariable Long id, Model model) {
        try {
            Review review = reviewService.findReviewById(id);
            model.addAttribute("review", review);
            model.addAttribute("statuses", ReviewStatus.values());
            return "reviewer/view-review"; // Создайте шаблон reviewer/view-review.html
        } catch (Exception e) {
            model.addAttribute("error", "Рецензия не найдена");
            return "redirect:/reviewer/reviews";
        }
    }

    @PostMapping("/reviews/{id}/update-status")
    public String updateReviewStatus(@PathVariable Long id,
                                     @RequestParam("status") ReviewStatus status,
                                     Model model) {
        try {
            reviewService.updateReviewStatus(id, status);
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при обновлении статуса рецензии");
            return "reviewer/view-review";
        }

        return "redirect:/reviewer/reviews";
    }

    // Добавьте другие методы для создания и редактирования рецензий при необходимости
}