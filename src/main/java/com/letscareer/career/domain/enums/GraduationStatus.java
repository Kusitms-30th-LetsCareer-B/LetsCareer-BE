package com.letscareer.career.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GraduationStatus {

    IN_PROGRESS("재학중"),
    GRADUATED("졸업"),
    COMPLETED("수료"),
    EXPECTED("졸업예정"),
    DROPPED_OUT("중퇴"),
    WITHDRAWN("학사");

    private final String name;
}

