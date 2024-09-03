package com.letscareer.user.domain;

import com.letscareer.global.domain.BaseTimeEntity;
import com.letscareer.recruitment.domain.Recruitment;
import com.letscareer.user.dto.request.UserRequest;
import jakarta.persistence.*;
import lombok.*;

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

    // Recruitment 엔티티와의 일대다 관계 설정
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Recruitment> recruitments = new ArrayList<>();

    private String name;

    private String email;

    public static User from(UserRequest request) {
        return User.builder()
                .name(request.name())
                .email(request.email())
                .build();
    }
}
