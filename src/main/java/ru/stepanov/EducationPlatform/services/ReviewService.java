package ru.stepanov.EducationPlatform.services;

import ru.stepanov.EducationPlatform.DTO.ReviewDto;

import java.util.List;

public interface ReviewService {
    ReviewDto getReviewById(Long id);
    List<ReviewDto> getAllReviewsByCourse(Long id);
    List<ReviewDto> getAllReviews();
    ReviewDto createReview(ReviewDto reviewDto);
    ReviewDto updateReview(Long id, ReviewDto reviewDto);
    void deleteReview(Long id);
}

