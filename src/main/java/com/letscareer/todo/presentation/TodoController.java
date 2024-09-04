package com.letscareer.todo.presentation;

import com.letscareer.global.domain.CommonResponse;
import com.letscareer.todo.application.TodoService;
import com.letscareer.todo.dto.request.CreateTodoReq;
import com.letscareer.todo.dto.request.ModifyTodoReq;
import com.letscareer.todo.dto.response.CompanyTodosRes;
import com.letscareer.todo.dto.response.GroupedByCompanyRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    /**
     * 투두 추가
     * @param userId
     * @param recruitmentId
     * @param request
     * @return
     */
    @PostMapping("/todos")
    public ResponseEntity<CommonResponse<Void>> createTodo(@RequestParam(name = "userId") Long userId,
                                                           @RequestParam(name = "recruitmentId") Long recruitmentId,
                                                           @RequestBody CreateTodoReq request){
        todoService.createTodo(userId, recruitmentId, request);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("투두를 추가하였습니다.", null));
    }

    /**
     * 해당 날짜의 투두리스트 반환(전체기업)
     * @param userId
     * @param date
     * @return
     */
    @GetMapping("/todos/groupedByCompany")
    public ResponseEntity<CommonResponse<List<GroupedByCompanyRes>>> getTodosGroupedByCompanyName(@RequestParam(name = "userId") Long userId,
                                                                                                  @RequestParam(name = "date") LocalDate date) {
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("전체 투두 목록을 조회하였습니다.", todoService.getTodosGroupedByCompanyName(userId, date)));
    }

    /**
     * 해다 기업의 해당 날짜의 투두리스트 반환
     * @param recruitmentId
     * @param date
     * @return
     */
    @GetMapping("/todos")
    public ResponseEntity<CommonResponse<CompanyTodosRes>> getTodosByCompanyName(@RequestParam(name = "recruitmentId") Long recruitmentId,
                                                                                 @RequestParam(name = "date") LocalDate date) {
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("전체 투두 목록을 조회하였습니다.", todoService.getTodosByCompanyName(recruitmentId, date)));
    }

    @PatchMapping("/todos/{todoId}")
    public ResponseEntity<CommonResponse<Void>> modifyTodo(@PathVariable(name = "todoId") Long todoId, @RequestBody ModifyTodoReq request){
        todoService.modifyTodo(todoId, request);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("해당 투두를 수정하였습니다.", null));
    }

    @PatchMapping("/todos/{todoId}/check")
    public ResponseEntity<CommonResponse<Void>> modifyTodoIsCompleted(@PathVariable(name = "todoId") Long todoId){
        todoService.modifyTodoIsCompleted(todoId);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("해당 투두 완료 유무를 수정하였습니다.", null));
    }

    @DeleteMapping("/todos/{todoId}")
    public ResponseEntity<CommonResponse<Void>> deleteTodo(@PathVariable(name = "todoId") Long todoId){
        todoService.deleteTodo(todoId);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("해당 투두를 삭제하였습니다.", null));
    }

    @PatchMapping("/todos/{todoId}/delay")
    public ResponseEntity<CommonResponse<Void>> delayTodo(@PathVariable(name = "todoId") Long todoId){
        todoService.delayTodo(todoId);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("해당 투두의 일정을 하루 미뤘습니다.", null));
    }



}
