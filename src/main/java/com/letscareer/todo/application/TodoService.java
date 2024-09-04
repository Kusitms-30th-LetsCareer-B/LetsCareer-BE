package com.letscareer.todo.application;

import com.letscareer.global.exception.CustomException;
import com.letscareer.global.exception.ExceptionContent;
import com.letscareer.recruitment.domain.Recruitment;
import com.letscareer.recruitment.domain.repository.RecruitmentRepository;
import com.letscareer.recruitment.dto.response.FindAllRecruitmentsRes;
import com.letscareer.todo.domain.Todo;
import com.letscareer.todo.domain.repository.TodoRepository;
import com.letscareer.todo.dto.request.CreateTodoReq;
import com.letscareer.todo.dto.request.ModifyTodoReq;
import com.letscareer.todo.dto.response.CompanyTodosRes;
import com.letscareer.todo.dto.response.GroupedByCompanyRes;
import com.letscareer.user.domain.User;
import com.letscareer.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    private final RecruitmentRepository recruitmentRepository;

    @Transactional
    public void createTodo(Long userId, Long recruitmentId, CreateTodoReq request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_USER));
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId).orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_RECRUITMENT));
        todoRepository.save(Todo.of(user, recruitment, request.getDate(), request.getContent(),false));
    }

    @Transactional(readOnly = true)
    public List<GroupedByCompanyRes> getTodosGroupedByCompanyName(Long userId, LocalDate date) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_USER));
        List<Todo> todos = todoRepository.findAllByUserAndDate(user, date);

        Map<String, List<Todo>> groupedByCompany = todos.stream()
                .collect(Collectors.groupingBy(todo -> {
                    return todo.getRecruitment().getCompanyName();
                }));

        return groupedByCompany
                .entrySet().stream()
                .sorted((entry1, entry2) -> {
                    Recruitment recruitment1 = entry1.getValue().get(0).getRecruitment();
                    Recruitment recruitment2 = entry2.getValue().get(0).getRecruitment();
                    return Boolean.compare(recruitment2.getIsFavorite(), recruitment1.getIsFavorite());
                })
                .map(entry -> GroupedByCompanyRes.of(entry.getKey(), entry.getValue()))
                .toList();
    }



    @Transactional(readOnly = true)
    public CompanyTodosRes getTodosByCompanyName(Long recruitmentId, LocalDate date){
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId).orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_RECRUITMENT));
        List<Todo> todos = todoRepository.findAllByRecruitmentAndDate(recruitment, date);
        return CompanyTodosRes.of(todos);
    }

    @Transactional
    public void modifyTodo(Long todoId, ModifyTodoReq request) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_TODO));
        todo.modifyTodo(request);
    }

    @Transactional
    public void modifyTodoIsCompleted(Long todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_TODO));
        todo.modifyTodoIsCompleted();
    }

    @Transactional
    public void deleteTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_TODO));
        todoRepository.delete(todo);
    }
}
