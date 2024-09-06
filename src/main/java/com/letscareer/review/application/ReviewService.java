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
    public void saveOrUpdateReviews(Long recruitmentId, ReviewRequest request) {
        // 서류 및 면접 리뷰 처리
        handleDocumentAndInterviewReviews(recruitmentId, request);

        // 기타 리뷰 처리
        handleEtcReviews(recruitmentId, request);
    }

    private void handleDocumentAndInterviewReviews(Long recruitmentId, ReviewRequest request) {
        List<Review> existingReviews = reviewRepository.findByRecruitmentId(recruitmentId);

        // 서류 처리
        saveOrUpdateReview(recruitmentId, request.document(), existingReviews, ReviewType.DOCUMENT);

        // 면접 처리
        saveOrUpdateReview(recruitmentId, request.interview(), existingReviews, ReviewType.INTERVIEW);
    }

    private void handleEtcReviews(Long recruitmentId, ReviewRequest request) {
        List<Review> existingEtcReviews = reviewRepository.findEtcReviewsByRecruitmentId(recruitmentId);

        // 기타 리뷰 처리 (생성 또는 수정)
        for (ReviewRequest.EtcReviewDto etcDto : request.etc()) {
            Optional<Review> existingReview = existingEtcReviews.stream()
                    .filter(review -> review.getReviewName().equals(etcDto.reviewName()))
                    .findFirst();

            if (existingReview.isPresent()) {
                updateReview(existingReview.get(), etcDto, DifficultyType.of(etcDto.difficulty()));
            } else {
                Review savedReview = createReview(recruitmentId, etcDto, DifficultyType.of(etcDto.difficulty()));
                reviewRepository.save(savedReview);
            }
        }

        // 요청 데이터에 없는 기타 리뷰 삭제
        List<String> incomingReviewNames = request.etc().stream()
                .map(ReviewRequest.EtcReviewDto::reviewName)
                .collect(Collectors.toList());

        existingEtcReviews.stream()
                .filter(review -> !incomingReviewNames.contains(review.getReviewName()))
                .forEach(reviewRepository::delete);
    }

    private void saveOrUpdateReview(Long recruitmentId,ReviewRequest.ReviewDto reviewDto, List<Review> existingReviews, ReviewType reviewType) {
        Optional<Review> existingReview = existingReviews.stream()
                .filter(review -> review.getReviewType() == reviewType)
                .findFirst();

        if (existingReview.isPresent()) {
            updateReview(existingReview.get(), reviewDto, reviewType);
        } else {
            Review savedReview = createReview(recruitmentId, reviewDto, reviewType);
            reviewRepository.save(savedReview);
        }
    }

    // createReview 메서드 (서류 및 면접용)
    private Review createReview(Long recruitmentId, ReviewRequest.ReviewDto dto, ReviewType reviewType) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_RECRUITMENT));

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

    // createReview 메서드 (기타 리뷰용)
    private Review createReview(Long recruitmentId, ReviewRequest.EtcReviewDto dto, DifficultyType difficulty) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_RECRUITMENT));

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

    // updateReview 메서드 (서류 및 면접용)
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

    // updateReview 메서드 (기타 리뷰용)
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