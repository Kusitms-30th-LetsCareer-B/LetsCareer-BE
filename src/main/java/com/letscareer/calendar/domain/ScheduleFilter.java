package com.letscareer.calendar.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ScheduleFilter {

    START("시"),
    FINISH("끝"),
    INTERVIEW("면"),
    OTHER("기");

    private final String name;
}
