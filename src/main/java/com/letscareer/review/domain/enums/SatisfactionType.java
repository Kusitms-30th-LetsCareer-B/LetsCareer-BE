package com.letscareer.review.domain.enums;

import com.letscareer.global.exception.CustomException;
import com.letscareer.global.exception.ExceptionContent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SatisfactionType {

    SATISFIED("만족"),
    AVERAGE("보통"),
    DISSATISFIED("불만족");

    private final String name;

    public static SatisfactionType of(String name) {
        for (SatisfactionType type : SatisfactionType.values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        throw new CustomException(ExceptionContent.BAD_REQUEST_SATISFACTION_TYPE);
    }
}
