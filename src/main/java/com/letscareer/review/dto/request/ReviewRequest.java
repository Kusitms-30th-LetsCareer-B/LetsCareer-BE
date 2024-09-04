package com.letscareer.review.dto.request;

import java.util.List;

public record ReviewRequest(
        ReviewDto document,
        ReviewDto interview,
        List<ReviewDto> etc
) {

    public static record ReviewDto(Long id, Long recruitmentId, String satisfaction, String reviewType, String reviewName,
                                   List<String> wellDonePoints, List<String> shortcomingPoints, String wellDoneMemo,
                                   String shortcomingMemo, String difficulty) {
    }
}
