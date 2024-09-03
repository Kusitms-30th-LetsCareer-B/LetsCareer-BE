package com.letscareer.user.domain;

import com.letscareer.career.domain.*;
import com.letscareer.global.domain.BaseTimeEntity;
import com.letscareer.recruitment.domain.Recruitment;
import com.letscareer.user.dto.request.UserRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name = "users")
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Recruitment> recruitments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Education> educations = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Certificate> certificates = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<WorkExperience> workExperiences = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Activity> activities = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Portfolio portfolio;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String fileName;

    private String fileUrl;

    private String fileKey;

    public static User from(UserRequest request) {
        return User.builder()
                .name(request.name())
                .email(request.email())
                .build();
    }
}
