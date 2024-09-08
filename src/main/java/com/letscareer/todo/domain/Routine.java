package com.letscareer.todo.domain;


import com.letscareer.recruitment.domain.Recruitment;
import com.letscareer.todo.dto.request.RoutineReq;
import com.letscareer.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Routine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="routine_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_id", nullable = false)
    private Recruitment recruitment;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "routine")
    @Builder.Default
    private List<Todo> todos = new ArrayList<>();

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    public static Routine of(User user, Recruitment recruitment, String content, LocalDate startDate, LocalDate endDate) {
        return Routine.builder()
                .user(user)
                .recruitment(recruitment)
                .content(content)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }

    public void modifyRoutine(RoutineReq request) {
        this.content = request.getContent();
        this.startDate = request.getStartDate();
        this.endDate = request.getEndDate();
    }
}
