package com.letscareer.review.application;

import com.letscareer.global.exception.CustomException;
import com.letscareer.global.exception.ExceptionContent;
import com.letscareer.recruitment.domain.Recruitment;
import com.letscareer.recruitment.domain.repository.RecruitmentRepository;
import com.letscareer.review.domain.Review;
import com.letscareer.review.domain.enums.DifficultyType;
import com.letscareer.review.domain.enums.ReviewPointType;
import com.letscareer.review.domain.enums.ReviewType;
import com.letscareer.review.domain.enums.SatisfactionType;
import com.letscareer.review.dto.request.ReviewRequest;
import com.letscareer.review.dto.response.ReviewResponse;
import com.letscareer.review.domain.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final RecruitmentRepository recruitmentRepository;

    @Transactional(readOnly = true)
    public ReviewResponse getReviewsByRecruitmentId(Long recruitmentId) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_RECRUITMENT));

        List<Review> reviews = reviewRepository.findByRecruitmentId(recruitmentId);
        return ReviewResponse.from(reviews);
    }

    @Transactional
    public List<Long> saveOrUpdateReviews(ReviewRequest request) {
        List<Long> reviewIds = List.of(
                saveOrUpdateReview(request.document()),  // 서류
                saveOrUpdateReview(request.interview())  // 면접
        );

        // 기타 리뷰는 여러 개가 들어올 수 있음
        List<Long> etcReviewIds = request.etc().stream()
                .map(this::saveOrUpdateReview)
                .collect(Collectors.toList());

        reviewIds.addAll(etcReviewIds);
        return reviewIds;
    }

    private Long saveOrUpdateReview(ReviewRequest.ReviewDto reviewDto) {
        if (reviewDto == null) {
            return null;
        }

        Recruitment recruitment = recruitmentRepository.findById(reviewDto.recruitmentId())
                .orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_RECRUITMENT));

        Optional<Review> existingReview = Optional.ofNullable(reviewDto.id())
                .flatMap(reviewRepository::findById);

        if (existingReview.isPresent()) {
            Review review = existingReview.get();
            updateReview(review, reviewDto);
            reviewRepository.save(review);
            return review.getId();
        } else {
            Review review = createReview(recruitment, reviewDto);
            reviewRepository.save(review);
            return review.getId();
        }
    }

    private Review createReview(Recruitment recruitment, ReviewRequest.ReviewDto dto) {
        SatisfactionType satisfaction = dto.satisfaction() != null ? SatisfactionType.of(dto.satisfaction()) : null;
        ReviewType reviewType = ReviewType.of(dto.reviewType());
        DifficultyType difficulty = reviewType == ReviewType.ETC ? DifficultyType.of(dto.difficulty()) : null;

        List<ReviewPointType> wellDonePoints = dto.wellDonePoints() != null ?
                dto.wellDonePoints().stream().map(ReviewPointType::of).collect(Collectors.toList()) : null;

        List<ReviewPointType> shortcomingPoints = dto.shortcomingPoints() != null ?
                dto.shortcomingPoints().stream().map(ReviewPointType::of).collect(Collectors.toList()) : null;

        return Review.create(
                recruitment,
                satisfaction,
                reviewType,
                wellDonePoints,
                shortcomingPoints,
                difficulty,
                dto.wellDoneMemo(),
                dto.shortcomingMemo(),
                dto.reviewName()
        );
    }

    private void updateReview(Review review, ReviewRequest.ReviewDto dto) {
        SatisfactionType satisfaction = dto.satisfaction() != null ? SatisfactionType.of(dto.satisfaction()) : null;
        ReviewType reviewType = ReviewType.of(dto.reviewType());
        DifficultyType difficulty = reviewType == ReviewType.ETC ? DifficultyType.of(dto.difficulty()) : null;

        List<ReviewPointType> wellDonePoints = dto.wellDonePoints() != null ?
                dto.wellDonePoints().stream().map(ReviewPointType::of).collect(Collectors.toList()) : null;

        List<ReviewPointType> shortcomingPoints = dto.shortcomingPoints() != null ?
                dto.shortcomingPoints().stream().map(ReviewPointType::of).collect(Collectors.toList()) : null;

        review.updateReview(
                satisfaction,
                reviewType,
                wellDonePoints,
                shortcomingPoints,
                difficulty,
                dto.wellDoneMemo(),
                dto.shortcomingMemo(),
                dto.reviewName()
        );
    }
}
