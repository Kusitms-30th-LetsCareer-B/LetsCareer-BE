package com.letscareer.career.domain;

import com.letscareer.career.domain.enums.EducationType;
import com.letscareer.career.domain.enums.GraduationStatus;
import com.letscareer.career.domain.enums.MajorType;
import com.letscareer.career.domain.enums.SchoolLocation;
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

    public static Education createEmptyEducation(User user) {
        return Education.builder()
                .user(user)
                .build();
    }
    public void updateFromDto(CareerRequest.EducationRequest dto) {
        this.educationType = EducationType.of(dto.educationType());
        this.schoolName = dto.schoolName();
        this.schoolLocation = SchoolLocation.of(dto.schoolLocation());
        this.enrollmentDate = dto.enrollmentDate();
        this.graduationDate = dto.graduationDate();
        this.graduationStatus = GraduationStatus.of(dto.graduationStatus());
        this.majorType = MajorType.of(dto.majorType());
        this.majorName = dto.majorName();
        this.subMajorType = dto.subMajorType() != null ? MajorType.of(dto.subMajorType()) : null;
        this.subMajorName = dto.subMajorName();
    }

}
