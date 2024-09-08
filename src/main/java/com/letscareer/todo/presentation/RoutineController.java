package com.letscareer.todo.presentation;

import com.letscareer.global.domain.CommonResponse;
import com.letscareer.todo.application.RoutineService;
import com.letscareer.todo.dto.request.RoutineReq;
import com.letscareer.todo.dto.response.RoutineRes;
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
                                                              @RequestBody RoutineReq request){
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

    /**
     * 루틴 조회
     * @param routineId
     * @return
     */
    @GetMapping("/routines/{routineId}")
    public ResponseEntity<CommonResponse<RoutineRes>> getRoutine(@PathVariable(name="routineId") Long routineId){
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("해당 루틴을 조회하였습니다.", routineService.getRoutine(routineId)));
    }

    /**
     * 루틴 수정
     * @param routineId
     * @param request
     * @return
     */
    @PatchMapping("/routines/{routineId}")
    public ResponseEntity<CommonResponse<Void>> modifyRoutine(@PathVariable(name="routineId") Long routineId,
                                                              @RequestBody RoutineReq request) {
        routineService.modifyRoutine(routineId, request);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("해당 루틴를 수정하였습니다.", null));
    }
}
