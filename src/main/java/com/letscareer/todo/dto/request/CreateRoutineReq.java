package com.letscareer.todo.dto.request;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CreateRoutineReq {
    private String content;
    private LocalDate startDate;
    private LocalDate endDate;
}
