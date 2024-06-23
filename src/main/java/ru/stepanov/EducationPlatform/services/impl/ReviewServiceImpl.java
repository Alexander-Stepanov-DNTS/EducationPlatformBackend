package ru.stepanov.EducationPlatform.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stepanov.EducationPlatform.DTO.ReviewDto;
import ru.stepanov.EducationPlatform.mappers.CourseMapper;
import ru.stepanov.EducationPlatform.mappers.ReviewMapper;
import ru.stepanov.EducationPlatform.mappers.UserMapper;
import ru.stepanov.EducationPlatform.models.Review;
import ru.stepanov.EducationPlatform.repositories.ReviewRepository;
import ru.stepanov.EducationPlatform.services.ReviewService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    @Transactional(readOnly = true)
    public ReviewDto getReviewById(Long id) {
        Optional<Review> review = reviewRepository.findById(id);
        return review.map(ReviewMapper.INSTANCE::toDto).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewDto> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(ReviewMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ReviewDto createReview(ReviewDto reviewDto) {
        Review review = ReviewMapper.INSTANCE.toEntity(reviewDto);
        review = reviewRepository.save(review);
        return ReviewMapper.INSTANCE.toDto(review);
    }

    @Override
    @Transactional
    public ReviewDto updateReview(Long id, ReviewDto reviewDto) {
        Optional<Review> existingReview = reviewRepository.findById(id);
        if (existingReview.isPresent()) {
            Review review = existingReview.get();
            review.setScore(reviewDto.getScore());
            review.setReviewText(reviewDto.getReviewText());
            review.setCreatedDate(reviewDto.getCreatedDate());
            review.setUser(UserMapper.INSTANCE.toEntity(reviewDto.getUser()));
            review.setCourse(CourseMapper.INSTANCE.toEntity(reviewDto.getCourse()));
            review = reviewRepository.save(review);
            return ReviewMapper.INSTANCE.toDto(review);
        } else {
            return null; // или выбросить исключение
        }
    }

    @Override
    @Transactional
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}

