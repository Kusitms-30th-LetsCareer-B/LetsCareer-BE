package com.letscareer.career.domain;

import com.letscareer.career.domain.enums.EducationType;
import com.letscareer.career.domain.enums.GraduationStatus;
import com.letscareer.career.domain.enums.MajorType;
import com.letscareer.career.domain.enums.SchoolLocation;
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
public class Education extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "education_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EducationType educationType;

    @Column(nullable = false)
    private String schoolName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SchoolLocation schoolLocation;

    @Column(nullable = false)
    private LocalDate enrollmentDate;

    @Column(nullable = false)
    private LocalDate graduationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GraduationStatus graduationStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MajorType majorType;

    @Column(nullable = false)
    private String majorName;

    @Enumerated(EnumType.STRING)
    private MajorType subMajorType;

    private String subMajorName;
}
