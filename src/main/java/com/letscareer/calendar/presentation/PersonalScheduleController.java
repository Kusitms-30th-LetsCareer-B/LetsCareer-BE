package com.letscareer.calendar.presentation;

import com.letscareer.calendar.application.PersonalScheduleService;
import com.letscareer.calendar.dto.request.PersonalScheduleRequest;
import com.letscareer.calendar.dto.response.PersonalScheduleResponse;
import com.letscareer.global.domain.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calendars/personal")
public class PersonalScheduleController {
    private final PersonalScheduleService personalScheduleService;

    // 개인 일정 추가 API
    @PostMapping("")
    public ResponseEntity<CommonResponse<?>> addPersonalSchedule(
        @RequestParam Long userId,
        @RequestBody PersonalScheduleRequest request) {

        personalScheduleService.addPersonalSchedule(userId, request);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("개인 일정 추가에 성공하였습니다.", null));
    }

    // 월에 해당하는 개인 일정 조회 API
    @GetMapping("")
    public ResponseEntity<CommonResponse<List<PersonalScheduleResponse>>> getScheduleForMonth(
        @RequestParam(name = "userId") Long userId,
        @RequestParam(name = "year") int year,
        @RequestParam(name = "month") int month) {
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("개인 일정 조회에 성공하였습니다.", personalScheduleService.getPersonalScheduleForMonth(userId, year, month)));
    }

    // 개인 일정 수정 API
    @PutMapping("/{personalScheduleId}")
    public ResponseEntity<CommonResponse<?>> updatePersonalSchedule(
        @PathVariable Long personalScheduleId,
        @RequestBody PersonalScheduleRequest request) {

        personalScheduleService.updatePersonalSchedule(personalScheduleId, request);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("개인 일정 수정에 성공하였습니다.", null));
    }

    // 개인 일정 삭제 API
    @DeleteMapping("/{personalScheduleId}")
    public ResponseEntity<CommonResponse<?>> deletePersonalSchedule(
        @PathVariable Long personalScheduleId) {

        personalScheduleService.deletePersonalSchedule(personalScheduleId);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("개인 일정 삭제에 성공하였습니다.", null));
    }
}
