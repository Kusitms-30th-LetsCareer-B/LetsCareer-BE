package com.letscareer.review.dto.response;

import com.letscareer.review.domain.Review;
import com.letscareer.review.domain.enums.ReviewType;

import java.util.List;
import java.util.stream.Collectors;

public record ReviewResponse(
        ReviewDto document,
        ReviewDto interview,
        List<ReviewDto> etc
) {

    public static ReviewResponse from(List<Review> reviews) {
        ReviewDto document = reviews.stream()
                .filter(review -> review.getReviewType() == ReviewType.DOCUMENT)
                .map(ReviewDto::from)
                .findFirst()
                .orElse(null);

        ReviewDto interview = reviews.stream()
                .filter(review -> review.getReviewType() == ReviewType.INTERVIEW)
                .map(ReviewDto::from)
                .findFirst()
                .orElse(null);

        List<ReviewDto> etc = reviews.stream()
                .filter(review -> review.getReviewType() == ReviewType.ETC)
                .map(ReviewDto::from)
                .collect(Collectors.toList());

        return new ReviewResponse(document, interview, etc);
    }

    public static record ReviewDto(Long id, String reviewName, String satisfaction, List<String> wellDonePoints,
                                   List<String> shortcomingPoints, String wellDoneMemo, String shortcomingMemo,
                                   String difficulty) {  // 난이도 필드 추가

        public static ReviewDto from(Review review) {
            return new ReviewDto(
                    review.getId(),
                    review.getReviewName(),
                    review.getSatisfaction() != null ? review.getSatisfaction().name() : null, // 만족도는 null일 수 있음
                    review.getWellDonePoints() != null ? review.getWellDonePoints().stream().map(Enum::name).collect(Collectors.toList()) : null,
                    review.getShortcomingPoints() != null ? review.getShortcomingPoints().stream().map(Enum::name).collect(Collectors.toList()) : null,
                    review.getWellDoneMemo(),
                    review.getShortcomingMemo(),
                    review.getDifficulty() != null ? review.getDifficulty().name() : null  // 난이도는 기타 리뷰에만 있음
            );
        }
    }
}
