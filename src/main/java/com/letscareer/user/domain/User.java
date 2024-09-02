package com.letscareer.user.domain;

import com.letscareer.calendar.domain.Schedule;
import com.letscareer.global.domain.BaseTimeEntity;
import com.letscareer.user.dto.request.UserRequest;
import jakarta.persistence.*;
import lombok.*;

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

    private String name;

    private String email;

    public static User from(UserRequest request) {
        return User.builder()
                .name(request.name())
                .email(request.email())
                .build();
    }
}
