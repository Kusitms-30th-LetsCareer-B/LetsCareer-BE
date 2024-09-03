package com.letscareer.career.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActivityType {

    EXTRACURRICULAR("대외활동"),
    VOLUNTEER("봉사"),
    AWARD("수상"),
    OTHER("기타");

    private final String name;
}

