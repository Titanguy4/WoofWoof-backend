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

    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public List<Review> getReviewsByStayId(Long stayId) {
        return reviewRepository.findByStay_IdStay(stayId);
    }

    public Review updateReview(Long id, Review updatedReview) {
        return reviewRepository.findById(id)
                .map(existing -> {
                    existing.setContent(updatedReview.getContent());
                    existing.setRating(updatedReview.getRating());
                    existing.setDate(updatedReview.getDate());
                    return reviewRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Review with id " + id + " not found"));
    }

    public void deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new RuntimeException("Review with id " + id + " not found");
        }
        reviewRepository.deleteById(id);
    }
}
