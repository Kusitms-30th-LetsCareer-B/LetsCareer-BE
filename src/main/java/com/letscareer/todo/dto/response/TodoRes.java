package com.letscareer.todo.dto.response;

import com.letscareer.todo.domain.Todo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class TodoRes {
    private Long todoId;
    private String content;
    private Boolean isCompleted;
    private LocalDate date;
    private Long recruitmentId;
    private Boolean isRoutine;

    public static TodoRes from(Todo todo) {
        return TodoRes.builder()
                .todoId(todo.getId())
                .content(todo.getContent())
                .isCompleted(todo.getIsCompleted())
                .date(todo.getDate())
                .recruitmentId(todo.getRecruitment().getId())
                .isRoutine(todo.getRoutine() != null)
                .build();
    }
}
