package com.woofwoof.stayservice.services;

import com.woofwoof.stayservice.models.Review;
import com.woofwoof.stayservice.repositories.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public Review createReview(Review review) {
        if (review.getContent() == null || review.getContent().isEmpty()) {
            throw new IllegalArgumentException("Review content cannot be empty");
        }
        if (review.getStay() == null) {
            throw new IllegalArgumentException("Review must be linked to a stay");
        }
        return reviewRepository.save(review);
    }

    public Optional<Review> getReviewById(long id_review) {
        return reviewRepository.findById(id_review);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public List<Review> getReviewsByStay_Id(long id) {
        return reviewRepository.findByStay_Id(id);
    }

    public Review updateReview(long id_review, Review updatedReview) {
        return reviewRepository.findById(id_review)
                .map(existing -> {
                    existing.setContent(updatedReview.getContent());
                    existing.setRating(updatedReview.getRating());
                    existing.setDate(updatedReview.getDate());
                    return reviewRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Review with id " + id_review + " not found"));
    }

    public void deleteReview(long id_review) {
        if (!reviewRepository.existsById(id_review)) {
            throw new RuntimeException("Review with id " + id_review + " not found");
        }
        reviewRepository.deleteById(id_review);
    }
}
