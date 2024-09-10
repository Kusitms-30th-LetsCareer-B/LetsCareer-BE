package com.letscareer.career.domain.enums;

import com.letscareer.global.exception.CustomException;
import com.letscareer.global.exception.ExceptionContent;
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

    public static GraduationStatus of(String name) {
        for (GraduationStatus type : GraduationStatus.values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        throw new CustomException(ExceptionContent.BAD_REQUEST_GRADUATION_STATUS);
    }
}

