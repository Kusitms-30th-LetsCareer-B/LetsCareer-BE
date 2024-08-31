package com.letscareer.recruitment.domain;

import com.letscareer.global.domain.BaseTimeEntity;
import com.letscareer.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Recruitment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="recruitment_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String task;

    @Column(nullable = false)
    private Boolean isFavorite = false;

    @Column(nullable = false)
    private Boolean isRemind = true;

    private String announcementUrl;

    @Builder
    public static Recruitment of(User user, String companyName,  Boolean isFavorite, String task, Boolean isRemind, String announcementUrl) {
        return Recruitment.builder()
                .user(user)
                .companyName(companyName)
                .isFavorite(isFavorite)
                .task(task)
                .isRemind(isRemind)
                .announcementUrl(announcementUrl != null ? announcementUrl : "")
                .build();
    }
}
