package com.letscareer.review.domain.repository;

import com.letscareer.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByRecruitmentId(Long recruitmentId);

    @Query("SELECT r FROM Review r WHERE r.recruitment.id = :recruitmentId AND r.reviewType = 'ETC'")
    List<Review> findEtcReviewsByRecruitmentId(@Param("recruitmentId") Long recruitmentId);
}

