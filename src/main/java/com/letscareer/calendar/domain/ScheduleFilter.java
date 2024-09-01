package com.letscareer.calendar.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ScheduleFilter {

    PERSONAL("개인"),
    START("시"),
    FINISH("끝"),
    WRITTEN("필"),
    INTERVIEW("면"),
    OTHER("기");

    private final String name;
}
