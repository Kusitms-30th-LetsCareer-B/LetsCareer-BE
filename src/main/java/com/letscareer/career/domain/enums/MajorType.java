package com.letscareer.career.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MajorType {

    MAJOR("주전공"),
    MINOR("부전공"),
    DOUBLE_MAJOR("이중전공"),
    CONVERGED_MAJOR("복수전공");

    private final String name;
}
