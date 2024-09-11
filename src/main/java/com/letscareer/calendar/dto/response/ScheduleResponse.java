package com.letscareer.calendar.dto.response;

import com.letscareer.calendar.domain.Schedule;
import com.letscareer.calendar.domain.ScheduleFilter;

import java.time.LocalDate;

public record ScheduleResponse(Long scheduleId, LocalDate date, String filter, String companyName, Long recruitmentId) {
    public ScheduleResponse(Schedule schedule) {
        this(
                schedule.getId(),
                schedule.getDate(),
                schedule.getFilter().getName(), // ScheduleFilter의 name을 가져와서 String으로 변환
                schedule.getCompanyName(),
                schedule.getStage().getRecruitment().getId()
        );
    }
}
