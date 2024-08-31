package com.letscareer.recruitment.domain;

import com.letscareer.global.exception.CustomException;
import com.letscareer.global.exception.ExceptionContent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StageStatusType {

    PENDING("시작전"),
    PROGRESS("진행중"),
    PASSED("합격"),
    FAILED("불합격");

    private final String name;

    public static StageStatusType of(String name) {
        for (StageStatusType status : StageStatusType.values()){
            if (status.getName().equals(name)){
                return status;
            }
        }
        throw new CustomException(ExceptionContent.BAD_REQUEST_STAGE_STATUS_TYPE);
    }
}