package com.letscareer.todo.dto.request;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CreateTodoReq {
    private LocalDate date;
    private String content;
}
