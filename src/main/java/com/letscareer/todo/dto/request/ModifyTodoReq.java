package com.letscareer.todo.dto.request;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ModifyTodoReq {
    private String content;
    private LocalDate date;
}
