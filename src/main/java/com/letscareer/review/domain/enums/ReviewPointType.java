package com.letscareer.review.domain.enums;

import com.letscareer.global.exception.CustomException;
import com.letscareer.global.exception.ExceptionContent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReviewPointType {

    MOTIVATION("지원동기"),
    INDUSTRY_UNDERSTANDING("산업 이해도"),
    LOGIC_STRUCTURE("논리적 구성"),
    PROACTIVITY("적극성"),
    EXPERIENCE_UTILIZATION("경험 활용"),
    COMPANY_UNDERSTANDING("회사 이해도"),
    CLEAR_EXPRESSION("명확한 표현"),
    GROWTH_POTENTIAL("성장 가능성"),
    JOB_FIT("직무 적합성"),
    TASK_UNDERSTANDING("직무 이해도"),
    DIFFERENTIATION("차별화"),
    PASSION_EXPRESSION("열정 표현");

    private final String name;

    public static ReviewPointType of(String name) {
        for (ReviewPointType type : ReviewPointType.values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        throw new CustomException(ExceptionContent.BAD_REQUEST_REVIEW_POINT_TYPE);
    }
}
