package com.letscareer.calendar.domain;

import com.letscareer.calendar.dto.request.PersonalScheduleRequest;
import com.letscareer.global.domain.BaseTimeEntity;
import com.letscareer.recruitment.domain.Stage;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stage_id", nullable = false)
    private Stage stage;

    @Column(nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private ScheduleFilter filter;

    @Column(nullable = false)
    private String companyName;

    public static Schedule of(User user, Stage stage, LocalDate date, ScheduleFilter filter, String companyName) {
        return Schedule.builder()
                .user(user)
                .stage(stage)
                .date(date)
                .filter(filter)
                .companyName(companyName)
                .build();
    }
}
