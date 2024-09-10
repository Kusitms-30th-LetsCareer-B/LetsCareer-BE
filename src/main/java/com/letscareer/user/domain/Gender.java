package com.letscareer.user.domain;

import com.letscareer.career.domain.enums.GraduationStatus;
import com.letscareer.global.exception.CustomException;
import com.letscareer.global.exception.ExceptionContent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {
    MALE("남자"),
    FEMALE("여자");

    private final String name;

    public static Gender of(String name) {
        for (Gender type : Gender.values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        throw new CustomException(ExceptionContent.BAD_REQUEST_GENDER);
    }
}
