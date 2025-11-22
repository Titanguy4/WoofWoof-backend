package com.woofwoof.stayservice.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woofwoof.stayservice.entities.Review;
import com.woofwoof.stayservice.services.ReviewService;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/{id_review}")
    public Optional<Review> getReviewById(@PathVariable Long id_review) {
        return reviewService.getReviewById(id_review);
    }

    @GetMapping("/stay/{id}")
    public List<Review> getReviewsByStay_Id(@PathVariable Long id) {
        return reviewService.getReviewsByStay_Id(id);
    }

    @PostMapping
    public Review createReview(@RequestBody Review review) {
        return reviewService.createReview(review);
    }

    @PutMapping("/{id_review}")
    public Review updateReview(@PathVariable Long id_review, @RequestBody Review updatedReview) {
        return reviewService.updateReview(id_review, updatedReview);
    }

    @DeleteMapping("/{id_review}")
    public void deleteReview(@PathVariable Long id_review) {
        reviewService.deleteReview(id_review);
    }
}
