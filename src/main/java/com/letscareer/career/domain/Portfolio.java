package com.letscareer.career.domain;

import com.letscareer.global.domain.BaseTimeEntity;
import com.letscareer.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Portfolio extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String organizationUrl;

    private String fileName;

    private String fileUrl;

    private String fileKey;

    public static Portfolio createEmptyPortfolio(User user) {
        return Portfolio.builder()
                .user(user)
                .build();
    }

    public void updatePortfolioFile(String fileName, String fileUrl, String fileKey) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileKey = fileKey;
    }
}

