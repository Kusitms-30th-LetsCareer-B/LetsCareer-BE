package com.letscareer.todo.dto.response;

import com.letscareer.todo.domain.Todo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GroupedByCompanyRes {
    private String companyName;
    private List<TodoRes> todos;

    public static GroupedByCompanyRes of(String companyName, List<Todo> todos) {
        List<TodoRes> resTodos = todos.stream()
                .map(TodoRes::from)
                .toList();

        return GroupedByCompanyRes
                .builder()
                .companyName(companyName)
                .todos(resTodos)
                .build();
    }

}
