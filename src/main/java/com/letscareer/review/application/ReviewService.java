package com.letscareer.review.application;

import com.letscareer.global.exception.CustomException;
import com.letscareer.global.exception.ExceptionContent;
import com.letscareer.recruitment.domain.Recruitment;
import com.letscareer.recruitment.domain.repository.RecruitmentRepository;
import com.letscareer.review.domain.Review;
import com.letscareer.review.dto.response.ReviewResponse;
import com.letscareer.review.domain.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
