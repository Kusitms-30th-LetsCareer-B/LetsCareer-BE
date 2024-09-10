package com.letscareer.career.domain.enums;

import com.letscareer.global.exception.CustomException;
import com.letscareer.global.exception.ExceptionContent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EducationType {

    HIGH_SCHOOL("고등학교"),
    COLLEGE_2YR("대학교(2,3년)"),
    COLLEGE_4YR("대학교(4년)"),
    GRADUATE_MASTERS("대학원(석사)"),
    GRADUATE_PHD("대학원(박사)");

    private final String name;

    public static EducationType of(String name) {
        for (EducationType type : EducationType.values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        throw new CustomException(ExceptionContent.BAD_REQUEST_EDUCATION_TYPE);
    }
}
