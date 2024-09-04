package com.letscareer.review.dto.request;

import java.util.List;

public record ReviewRequest(
        ReviewDto document,
        ReviewDto interview,
        List<EtcReviewDto> etc  // 기타 리뷰는 별도의 DTO로 처리
) {

    public static record ReviewDto(Long id, Long recruitmentId, String satisfaction,
                                   List<String> wellDonePoints, List<String> shortcomingPoints,
                                   String wellDoneMemo, String shortcomingMemo) {
    }

    public static record EtcReviewDto(Long id, Long recruitmentId, String reviewName, String satisfaction,
                                      List<String> wellDonePoints, List<String> shortcomingPoints,
                                      String wellDoneMemo, String shortcomingMemo, String difficulty) {
    }
}
