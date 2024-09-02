package com.letscareer.calendar.dto.response;

import com.letscareer.calendar.domain.PersonalSchedule;
import com.letscareer.calendar.domain.Schedule;

import java.time.LocalDate;

public record PersonalScheduleResponse(Long PersonalScheduleId, LocalDate date, String content) {
    public PersonalScheduleResponse(PersonalSchedule schedule) {
        this(
                schedule.getId(),
                schedule.getDate(),
                schedule.getContent()
        );
    }
}
