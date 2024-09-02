package com.letscareer.recruitment.domain;

import com.letscareer.calendar.domain.Schedule;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Stage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="stage_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_id", nullable = false)
    private Recruitment recruitment;

    @Column(nullable = false)
    private String stageName;

    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private StageStatusType status;

    @Column(nullable = false)
    private Boolean isFinal;

    public static Stage of(Recruitment recruitment, String stageName, LocalDate startDate, LocalDate endDate, StageStatusType status, Boolean isFinal) {
        return Stage.builder()
                .recruitment(recruitment)
                .stageName(stageName)
                .startDate(startDate)
                .endDate(endDate)
                .status(status)
                .isFinal(isFinal)
                .build();
    }
}
