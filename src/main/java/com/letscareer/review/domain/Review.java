package com.letscareer.review.domain;

import com.letscareer.review.domain.enums.DifficultyType;
import com.letscareer.review.domain.enums.SatisfactionType;
import com.letscareer.review.domain.enums.ReviewPointType;
import com.letscareer.review.domain.enums.ReviewType;
import com.letscareer.global.domain.BaseTimeEntity;
import com.letscareer.recruitment.domain.Recruitment;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_id", nullable = false)
    private Recruitment recruitment;

    @Enumerated(EnumType.STRING)
    private SatisfactionType satisfaction;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReviewType reviewType;

    private String reviewName;  // 리뷰 이름 (기타의 경우 커스텀)

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "well_done_points", joinColumns = @JoinColumn(name = "review_id"))
    @Column(name = "well_done_point")
    private List<ReviewPointType> wellDonePoints;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "shortcoming_points", joinColumns = @JoinColumn(name = "review_id"))
    @Column(name = "shortcoming_point")
    private List<ReviewPointType> shortcomingPoints;

    @Enumerated(EnumType.STRING)
    private DifficultyType difficulty;

    @Column(length = 500)
    private String wellDoneMemo;

    @Column(length = 500)
    private String shortcomingMemo;

    public static Review create(Recruitment recruitment, SatisfactionType satisfaction, ReviewType reviewType,
                                List<ReviewPointType> wellDonePoints, List<ReviewPointType> shortcomingPoints,
                                DifficultyType difficulty, String wellDoneMemo, String shortcomingMemo, String reviewName) {
        return Review.builder()
                .recruitment(recruitment)
                .satisfaction(satisfaction)
                .reviewType(reviewType)
                .wellDonePoints(wellDonePoints)
                .shortcomingPoints(shortcomingPoints)
                .difficulty(difficulty)
                .wellDoneMemo(wellDoneMemo)
                .shortcomingMemo(shortcomingMemo)
                .reviewName(reviewType == ReviewType.ETC ? reviewName : null)  // 기타 리뷰에만 reviewName 설정
                .build();
    }

    public void updateReview(SatisfactionType satisfaction, ReviewType reviewType, List<ReviewPointType> wellDonePoints,
                             List<ReviewPointType> shortcomingPoints, DifficultyType difficulty, String wellDoneMemo,
                             String shortcomingMemo, String reviewName) {
        this.satisfaction = satisfaction;
        this.reviewType = reviewType;
        this.wellDonePoints = wellDonePoints;
        this.shortcomingPoints = shortcomingPoints;
        this.difficulty = reviewType == ReviewType.ETC ? difficulty : null;
        this.wellDoneMemo = wellDoneMemo;
        this.shortcomingMemo = shortcomingMemo;
        this.reviewName = reviewType == ReviewType.ETC ? reviewName : null;  // 기타 리뷰에만 reviewName 설정
    }
}
