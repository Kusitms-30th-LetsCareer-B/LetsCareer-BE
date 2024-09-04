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
        recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_RECRUITMENT));

        List<Review> reviews = reviewRepository.findByRecruitmentId(recruitmentId);
        return ReviewResponse.from(reviews);
    }

    @Transactional
    public void saveOrUpdateReviews(ReviewRequest request) {
        saveOrUpdateReview(request.document(), ReviewType.DOCUMENT);  // 서류
        saveOrUpdateReview(request.interview(), ReviewType.INTERVIEW);  // 면접

        // 기타 리뷰는 여러 개가 들어올 수 있음
        request.etc().forEach(this::saveOrUpdateEtcReview);
    }

    private void saveOrUpdateReview(ReviewRequest.ReviewDto reviewDto, ReviewType reviewType) {
        if (reviewDto == null) {
            return;
        }

        Recruitment recruitment = recruitmentRepository.findById(reviewDto.recruitmentId())
                .orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_RECRUITMENT));

        Optional<Review> existingReview = Optional.ofNullable(reviewDto.id())
                .flatMap(reviewRepository::findById);

        if (existingReview.isPresent()) {
            Review review = existingReview.get();
            updateReview(review, reviewDto, reviewType);
            reviewRepository.save(review);
        } else {
            Review review = createReview(recruitment, reviewDto, reviewType);
            reviewRepository.save(review);
        }
    }

    private void saveOrUpdateEtcReview(ReviewRequest.EtcReviewDto reviewDto) {
        if (reviewDto == null) {
            return;
        }

        Recruitment recruitment = recruitmentRepository.findById(reviewDto.recruitmentId())
                .orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_RECRUITMENT));

        Optional<Review> existingReview = Optional.ofNullable(reviewDto.id())
                .flatMap(reviewRepository::findById);

        DifficultyType difficulty = reviewDto.difficulty() != null ? DifficultyType.of(reviewDto.difficulty()) : null;

        if (existingReview.isPresent()) {
            Review review = existingReview.get();
            updateReview(review, reviewDto, difficulty);
            reviewRepository.save(review);
        } else {
            Review review = createReview(recruitment, reviewDto, difficulty);
            reviewRepository.save(review);
        }
    }

    // 오버로드된 createReview 메서드 (서류 및 면접용)
    private Review createReview(Recruitment recruitment, ReviewRequest.ReviewDto dto, ReviewType reviewType) {
        SatisfactionType satisfaction = dto.satisfaction() != null ? SatisfactionType.of(dto.satisfaction()) : null;

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
                null,  // 서류와 면접은 난이도가 없으므로 null
                dto.wellDoneMemo(),
                dto.shortcomingMemo(),
                reviewType.getName()  // 서류/면접은 타입 이름 사용
        );
    }

    // 오버로드된 createReview 메서드 (기타 리뷰용)
    private Review createReview(Recruitment recruitment, ReviewRequest.EtcReviewDto dto, DifficultyType difficulty) {
        SatisfactionType satisfaction = dto.satisfaction() != null ? SatisfactionType.of(dto.satisfaction()) : null;

        List<ReviewPointType> wellDonePoints = dto.wellDonePoints() != null ?
                dto.wellDonePoints().stream().map(ReviewPointType::of).collect(Collectors.toList()) : null;

        List<ReviewPointType> shortcomingPoints = dto.shortcomingPoints() != null ?
                dto.shortcomingPoints().stream().map(ReviewPointType::of).collect(Collectors.toList()) : null;

        return Review.create(
                recruitment,
                satisfaction,
                ReviewType.ETC,
                wellDonePoints,
                shortcomingPoints,
                difficulty,
                dto.wellDoneMemo(),
                dto.shortcomingMemo(),
                dto.reviewName()  // 기타 리뷰는 reviewName 사용
        );
    }

    // 오버로드된 updateReview 메서드 (서류 및 면접용)
    private void updateReview(Review review, ReviewRequest.ReviewDto dto, ReviewType reviewType) {
        SatisfactionType satisfaction = dto.satisfaction() != null ? SatisfactionType.of(dto.satisfaction()) : null;

        List<ReviewPointType> wellDonePoints = dto.wellDonePoints() != null ?
                dto.wellDonePoints().stream().map(ReviewPointType::of).collect(Collectors.toList()) : null;

        List<ReviewPointType> shortcomingPoints = dto.shortcomingPoints() != null ?
                dto.shortcomingPoints().stream().map(ReviewPointType::of).collect(Collectors.toList()) : null;

        review.updateReview(
                satisfaction,
                reviewType,
                wellDonePoints,
                shortcomingPoints,
                null,  // 서류와 면접은 난이도가 없으므로 null
                dto.wellDoneMemo(),
                dto.shortcomingMemo(),
                reviewType.getName()  // 서류/면접은 타입 이름 사용
        );
    }

    // 오버로드된 updateReview 메서드 (기타 리뷰용)
    private void updateReview(Review review, ReviewRequest.EtcReviewDto dto, DifficultyType difficulty) {
        SatisfactionType satisfaction = dto.satisfaction() != null ? SatisfactionType.of(dto.satisfaction()) : null;

        List<ReviewPointType> wellDonePoints = dto.wellDonePoints() != null ?
                dto.wellDonePoints().stream().map(ReviewPointType::of).collect(Collectors.toList()) : null;

        List<ReviewPointType> shortcomingPoints = dto.shortcomingPoints() != null ?
                dto.shortcomingPoints().stream().map(ReviewPointType::of).collect(Collectors.toList()) : null;

        review.updateReview(
                satisfaction,
                ReviewType.ETC,
                wellDonePoints,
                shortcomingPoints,
                difficulty,
                dto.wellDoneMemo(),
                dto.shortcomingMemo(),
                dto.reviewName()  // 기타 리뷰는 reviewName 사용
        );
    }
}
