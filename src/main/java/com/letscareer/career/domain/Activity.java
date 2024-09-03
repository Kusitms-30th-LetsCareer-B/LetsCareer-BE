package com.letscareer.career.domain;

import com.letscareer.career.domain.enums.ActivityType;
import com.letscareer.career.dto.request.CareerRequest;
import com.letscareer.global.domain.BaseTimeEntity;
import com.letscareer.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Activity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "active_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityType activeType;

    @Column(nullable = false, length = 255)
    private String organization;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private Boolean isActive;

    @Column(nullable = false, length = 255)
    private String activeUrl;

    public static Activity createEmptyActivity(User user) {
        return Activity.builder()
                .user(user)
                .build();
    }

    public void updateFromDto(CareerRequest.ActivityRequest dto) {
        this.activeType = ActivityType.valueOf(dto.activeType());
        this.organization = dto.organization();
        this.startDate = dto.startDate();
        this.endDate = dto.endDate();
        this.isActive = dto.isActive();
        this.activeUrl = dto.activeUrl();
    }
}

