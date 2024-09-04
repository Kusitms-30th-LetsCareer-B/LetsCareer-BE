package com.letscareer.career.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmploymentType {

    FULL_TIME("정규직"),
    CONTRACT("계약직"),
    PART_TIME("파견직"),
    FREELANCER("프리랜서"),
    SELF_EMPLOYED("개인사업"),
    INTERNSHIP("인턴");

    private final String name;
}

