package com.letscareer.todo.domain;

import com.letscareer.global.domain.BaseTimeEntity;
import com.letscareer.recruitment.domain.Recruitment;
import com.letscareer.todo.dto.request.ModifyTodoReq;
import com.letscareer.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Todo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="todo_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_id", nullable = false)
    private Recruitment recruitment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routine_id", nullable = true)
    @Builder.Default
    private Routine routine = null;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean isCompleted;

    public static Todo of(User user, Recruitment recruitment, LocalDate date, String content, Boolean isCompleted) {
        return Todo.builder()
                .user(user)
                .recruitment(recruitment)
                .date(date)
                .content(content)
                .isCompleted(isCompleted)
                .build();
    }

    public static Todo ofRoutine(User user, Recruitment recruitment, Routine routine, LocalDate date, String content, Boolean isCompleted) {
        return Todo.builder()
                .user(user)
                .recruitment(recruitment)
                .routine(routine)
                .date(date)
                .content(content)
                .isCompleted(isCompleted)
                .build();
    }

    public void modifyTodo(ModifyTodoReq request) {
        this.content = request.getContent();
        this.date = request.getDate();
    }

    public void modifyTodoIsCompleted() {
        this.isCompleted = !this.isCompleted;
    }

    public void delayDate() {
        this.date = this.date.plusDays(1);
    }
}
