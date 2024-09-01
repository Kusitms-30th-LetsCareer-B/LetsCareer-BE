package com.letscareer.calendar.domain;

import com.letscareer.calendar.dto.request.PersonalScheduleRequest;
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
public class Schedule extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private ScheduleFilter filter;

    @Column(nullable = false)
    private String content;

    public static Schedule of(PersonalScheduleRequest request, User user, ScheduleFilter filter) {
        return Schedule.builder()
                .user(user)
                .date(request.date())
                .filter(filter)
                .content(request.content())
                .build();
    }
}
