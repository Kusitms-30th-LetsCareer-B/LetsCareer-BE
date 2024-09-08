package com.letscareer.review.domain.enums;


import com.letscareer.global.exception.CustomException;
import com.letscareer.global.exception.ExceptionContent;
import com.letscareer.review.domain.Interview;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InterviewStatusType {

    GOOD("잘했어요"),
    BAD("아쉬워요");

    private final String name;

    public static InterviewStatusType of(String name) {
        for (InterviewStatusType status : InterviewStatusType.values()){
            if (status.getName().equalsIgnoreCase(name)){
                return status;
            }
        }
        throw new CustomException(ExceptionContent.BAD_REQUEST_INTERVIEW_STATUS_TYPE);
    }
}
