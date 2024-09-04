package com.letscareer.career.domain;

import com.letscareer.career.domain.enums.EmploymentType;
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
public class WorkExperience extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_experience_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmploymentType employmentType;

    @Column(nullable = false, length = 255)
    private String companyName;

    @Column(nullable = false, length = 255)
    private String departmentName;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private String jobTitle;

    @Column(nullable = false)
    private String jobDescription;

    public static WorkExperience createEmptyWorkExperience(User user) {
        return WorkExperience.builder()
                .user(user)
                .build();
    }

    public void updateFromDto(CareerRequest.WorkExperienceRequest dto) {
        this.employmentType = EmploymentType.valueOf(dto.employmentType());
        this.companyName = dto.companyName();
        this.departmentName = dto.departmentName();
        this.startDate = dto.startDate();
        this.endDate = dto.endDate();
        this.jobTitle = dto.jobTitle();
        this.jobDescription = dto.jobDescription();
    }
}

