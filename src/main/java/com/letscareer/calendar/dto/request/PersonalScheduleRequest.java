package com.letscareer.calendar.dto.request;

import java.time.LocalDate;

public record PersonalScheduleRequest(Long userId, LocalDate date, String content) {
}
