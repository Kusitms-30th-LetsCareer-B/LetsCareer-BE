package com.letscareer.calendar.presentation;

import com.letscareer.calendar.application.ScheduleService;
import com.letscareer.calendar.dto.request.PersonalScheduleRequest;
import com.letscareer.calendar.dto.response.ScheduleContentResponse;
import com.letscareer.calendar.dto.response.ScheduleResponse;
import com.letscareer.global.domain.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calendars/recruitment")
public class ScheduleController {
    private final ScheduleService scheduleService;

    /**
     * 월에 해당하는 채용 전형을 가져오는 API
     *
     * @param userId the user id
     * @param year   the year
     * @param month  the month
     * @return the schedule for month
     */
    @GetMapping("")
    public ResponseEntity<CommonResponse<List<ScheduleResponse>>> getScheduleForMonth(
            @RequestParam(name = "userId") Long userId,
            @RequestParam(name = "year") int year,
            @RequestParam(name = "month") int month) {
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("캘린더 조회에 성공하였습니다.", scheduleService.getScheduleForMonth(userId, year, month)));
    }

    /**
     * 특정 날짜에 해당하는 커리어 일정을 가져오는 API
     *
     * @param userId   the user id
     * @param date     the date (yyyy-MM-dd)
     * @return the schedule for the date
     */
    @GetMapping("/date")
    public ResponseEntity<CommonResponse<List<ScheduleContentResponse>>> getScheduleForDate(
        @RequestParam(name = "userId") Long userId,
        @RequestParam(name = "date") LocalDate date) {
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("해당 날짜의 스케줄 조회에 성공하였습니다.", scheduleService.getScheduleForDate(userId, date)));
    }

}
