package com.letscareer.review.presentation;

import com.letscareer.global.domain.CommonResponse;
import com.letscareer.review.application.ReviewService;
import com.letscareer.review.dto.response.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 리크루트먼트 ID로 복기 노트 조회
     *
     * @param recruitmentId 리크루트먼트 ID
     * @return 리뷰 응답
     */
    @GetMapping("/recruitment")
    public ResponseEntity<CommonResponse<ReviewResponse>> getReviewsByRecruitment(
            @RequestParam Long recruitmentId) {
        ReviewResponse reviews = reviewService.getReviewsByRecruitmentId(recruitmentId);
        return ResponseEntity.ok(CommonResponse.ofSuccess("복기 노트 조회에 성공하였습니다.", reviews));
    }
}
