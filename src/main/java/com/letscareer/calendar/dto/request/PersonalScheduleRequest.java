package com.letscareer.calendar.dto.request;

import java.time.LocalDate;

public record PersonalScheduleRequest(LocalDate date, String content) {
}
