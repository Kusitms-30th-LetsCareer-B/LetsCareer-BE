package com.letscareer.recruitment.domain;

import com.letscareer.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Recruitment {
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

    private Boolean isFavorite = false;

    private Boolean isRemind = true;

    private String announcementUrl;

    @Column(length = 1000)
    private String memo;

}
