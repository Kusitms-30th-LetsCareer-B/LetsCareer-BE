package com.letscareer.todo.presentation;

import com.letscareer.global.domain.CommonResponse;
import com.letscareer.todo.application.RoutineService;
import com.letscareer.todo.dto.request.CreateRoutineReq;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RoutineController {
    private final RoutineService routineService;

    /**
     * 루틴 생성
     * @param userId
     * @param recruitmentId
     * @param request
     * @return
     */
    @PostMapping("/routines")
    public ResponseEntity<CommonResponse<Void>> createRoutine(@RequestParam(name="userId") Long userId,
                                                              @RequestParam(name="recruitmentId") Long recruitmentId,
                                                              @RequestBody CreateRoutineReq request){
        routineService.createRoutine(userId, recruitmentId, request);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("루틴를 추가하였습니다.", null));
    }

    /**
     * 루틴 삭제
     * @param routineId
     * @return
     */
    @DeleteMapping("/routines/{routineId}")
    public ResponseEntity<CommonResponse<Void>> deleteRoutine(@PathVariable(name="routineId") Long routineId){
        routineService.deleteRoutine(routineId);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("해당 루틴를 삭제하였습니다.", null));
    }
}
