package com.letscareer.calendar.dto.response;

import com.letscareer.calendar.domain.Schedule;
import com.letscareer.calendar.domain.ScheduleFilter;

import java.time.LocalDate;

public record ScheduleResponse(Long id, LocalDate date, String filter, String content) {
    public ScheduleResponse(Schedule schedule) {
        this(
                schedule.getId(),
                schedule.getDate(),
                schedule.getFilter().getName(), // ScheduleFilter의 name을 가져와서 String으로 변환
                schedule.getContent()
        );
    }
}
