package com.letscareer.career.domain.enums;

import com.letscareer.global.exception.CustomException;
import com.letscareer.global.exception.ExceptionContent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExperienceType {

    SUCCESS("성공"),
    JOB("직무"),
    COLLABORATION("협업");

    private final String name;

    public static ExperienceType of(String name) {
        for (ExperienceType type : ExperienceType.values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        throw new CustomException(ExceptionContent.BAD_REQUEST_EXPERIENCE_TYPE); // 예외 처리
    }
}
