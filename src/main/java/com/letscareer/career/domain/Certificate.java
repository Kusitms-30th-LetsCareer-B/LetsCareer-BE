package com.letscareer.career.domain;

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
public class Certificate extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certificate_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String certificateName;

    @Column(nullable = false)
    private String certificateIssuer;

    @Column(nullable = false)
    private LocalDate acquiredDate;

    public static Certificate createEmptyCertificate(User user) {
        return Certificate.builder()
                .user(user)
                .build();
    }

    public void updateFromDto(CareerRequest.CertificateRequest dto) {
        this.certificateName = dto.certificateName();
        this.certificateIssuer = dto.certificateIssuer();
        this.acquiredDate = dto.acquiredDate();
    }
}

