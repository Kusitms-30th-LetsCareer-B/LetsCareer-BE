package com.letscareer.calendar.presentation;

import com.letscareer.calendar.application.ScheduleService;
import com.letscareer.calendar.dto.request.PersonalScheduleRequest;
import com.letscareer.calendar.dto.response.ScheduleResponse;
import com.letscareer.global.domain.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calendar/recruitment")
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

}
