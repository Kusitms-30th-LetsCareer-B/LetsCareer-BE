package com.letscareer.review.domain.enums;

import com.letscareer.global.exception.CustomException;
import com.letscareer.global.exception.ExceptionContent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReviewType {

    DOCUMENT("서류"),
    INTERVIEW("면접"),
    ETC("기타");

    private final String name;

    public static ReviewType of(String name) {
        for (ReviewType type : ReviewType.values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        throw new CustomException(ExceptionContent.BAD_REQUEST_REVIEW_TYPE); // 예외 처리
    }
}
