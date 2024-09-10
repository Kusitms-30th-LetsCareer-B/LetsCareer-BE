package com.letscareer.career.domain.enums;

import com.letscareer.global.exception.CustomException;
import com.letscareer.global.exception.ExceptionContent;
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

    public static ActivityType of(String name) {
        for (ActivityType type : ActivityType.values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        throw new CustomException(ExceptionContent.BAD_REQUEST_ACTIVITY_TYPE);
    }
}

