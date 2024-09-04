package com.letscareer.todo.dto.response;

import com.letscareer.todo.domain.Routine;
import com.letscareer.todo.domain.Todo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class RoutineRes {

    private String content;
    private LocalDate startDate;
    private LocalDate endDate;

    public static RoutineRes from(Routine routine) {
        return RoutineRes.builder()
                .content(routine.getContent())
                .startDate(routine.getStartDate())
                .endDate(routine.getEndDate())
                .build();
    }
}
