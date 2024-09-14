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
    private Long routineId;  // 새로 추가된 필드

    public static TodoRes from(Todo todo) {
        return TodoRes.builder()
            .todoId(todo.getId())
            .content(todo.getContent())
            .isCompleted(todo.getIsCompleted())
            .date(todo.getDate())
            .recruitmentId(todo.getRecruitment().getId())
            .isRoutine(todo.getRoutine() != null)
            .routineId(todo.getRoutine() != null ? todo.getRoutine().getId() : null)  // routine이 있는 경우 routineId 설정
            .build();
    }
}

