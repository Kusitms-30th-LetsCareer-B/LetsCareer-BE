package com.letscareer.user.domain;

import com.letscareer.career.domain.*;
import com.letscareer.career.dto.request.CareerRequest;
import com.letscareer.global.domain.BaseTimeEntity;
import com.letscareer.recruitment.domain.Recruitment;
import com.letscareer.user.dto.request.UserRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private Set<Education> educations = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @Builder.Default
    private Set<Certificate> certificates = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @Builder.Default
    private Set<WorkExperience> workExperiences = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @Builder.Default
    private Set<Activity> activities = new HashSet<>();

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

    public void updateProfileImage(String fileName, String fileUrl, String fileKey) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileKey = fileKey;
    }

    public void updateFromRequest(CareerRequest request) {
        this.name = request.name();
        this.gender = Gender.valueOf(request.gender());
        this.birthDate = request.birthDate();
    }
}
