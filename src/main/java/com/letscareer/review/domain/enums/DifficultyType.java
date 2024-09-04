package com.letscareer.review.domain.enums;

import com.letscareer.global.exception.CustomException;
import com.letscareer.global.exception.ExceptionContent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DifficultyType {

    HIGH("상"),
    MEDIUM("중"),
    LOW("하");

    private final String name;

    public static DifficultyType of(String name) {
        for (DifficultyType type : DifficultyType.values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        throw new CustomException(ExceptionContent.BAD_REQUEST_DIFFICULTY_TYPE);
    }
}
