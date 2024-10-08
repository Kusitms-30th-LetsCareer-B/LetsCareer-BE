package com.letscareer.calendar.presentation;

import com.letscareer.calendar.application.PersonalScheduleService;
import com.letscareer.calendar.dto.request.PersonalScheduleRequest;
import com.letscareer.calendar.dto.request.UpdatePersonalScheduleRequest;
import com.letscareer.calendar.dto.response.PersonalScheduleResponse;
import com.letscareer.calendar.dto.response.ScheduleResponse;
import com.letscareer.global.domain.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calendars/personal")
public class PersonalScheduleController {
    private final PersonalScheduleService personalScheduleService;

    /**
     * 사용자의 개인 일정을 추가하는 API
     *
     * @param userId  the user id
     * @param request the request
     * @return the response entity
     */
    @PostMapping("")
    public ResponseEntity<CommonResponse<?>> addPersonalSchedule(
            @RequestParam Long userId,
            @RequestBody PersonalScheduleRequest request) {

        personalScheduleService.addPersonalSchedule(userId, request);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("개인 일정 추가에 성공하였습니다.", null));
    }

    /**
     * 월에 해당하는 개인 일정을 가져오는 API
     *
     * @param userId the user id
     * @param year   the year
     * @param month  the month
     * @return the schedule for month
     */
    @GetMapping("")
    public ResponseEntity<CommonResponse<List<PersonalScheduleResponse>>> getScheduleForMonth(
            @RequestParam(name = "userId") Long userId,
            @RequestParam(name = "year") int year,
            @RequestParam(name = "month") int month) {
        return ResponseEntity.ok().body(CommonResponse.ofSuccess(" 개인 일정 조회에 성공하였습니다.", personalScheduleService.getPersonalScheduleForMonth(userId, year, month)));
    }

    /**
     * 특정 날짜에 해당하는 개인 일정을 가져오는 API
     *
     * @param userId the user id
     * @param date   the date
     * @return the schedule for date
     */
    @GetMapping("/date")
    public ResponseEntity<CommonResponse<List<PersonalScheduleResponse>>> getScheduleForDate(
        @RequestParam(name = "userId") Long userId,
        @RequestParam(name = "date") LocalDate date) {
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("해당 날짜의 개인 일정 조회에 성공하였습니다.", personalScheduleService.getPersonalScheduleForDate(userId, date)));
    }


    /**
     * 개인 일정 수정 API
     *
     * @param personalScheduleId the personal schedule id
     * @param request            the request
     * @return the response entity
     */
    @PatchMapping("/{personalScheduleId}")
    public ResponseEntity<CommonResponse<?>> updatePersonalSchedule(
        @PathVariable Long personalScheduleId,
        @RequestBody UpdatePersonalScheduleRequest request) {

        personalScheduleService.updatePersonalSchedule(personalScheduleId, request);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("개인 일정 수정에 성공하였습니다.", null));
    }


    /**
     * 개인 일정 삭제 API
     *
     * @param personalScheduleId the personal schedule id
     * @return the response entity
     */
    @DeleteMapping("/{personalScheduleId}")
    public ResponseEntity<CommonResponse<?>> deletePersonalSchedule(
        @PathVariable Long personalScheduleId) {

        personalScheduleService.deletePersonalSchedule(personalScheduleId);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("개인 일정 삭제에 성공하였습니다.", null));
    }
}
