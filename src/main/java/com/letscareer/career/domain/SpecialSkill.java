package com.letscareer.career.domain;

import com.letscareer.career.domain.enums.ExperienceType;
import com.letscareer.global.domain.BaseTimeEntity;
import com.letscareer.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class SpecialSkill extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "special_skill_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExperienceType experienceType;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    public void updateSpecialSkill(String title, String content, ExperienceType experienceType) {
        this.title = title;
        this.content = content;
        this.experienceType = experienceType;
    }

    public static SpecialSkill create(User user, String title, String content, ExperienceType experienceType) {
        return SpecialSkill.builder()
                .user(user)
                .title(title)
                .content(content)
                .experienceType(experienceType)
                .build();
    }
}
