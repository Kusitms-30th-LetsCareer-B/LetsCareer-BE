package com.letscareer.introduce.domain;

import com.letscareer.global.exception.CustomException;
import com.letscareer.global.exception.ExceptionContent;
import com.letscareer.recruitment.domain.StageStatusType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IntroduceStatusType {

    GOOD("잘했어요"),
    BAD("아쉬워요");

    private final String name;

    public static IntroduceStatusType of(String name) {
        for (IntroduceStatusType status : IntroduceStatusType.values()){
            if (status.getName().equalsIgnoreCase(name)){
                return status;
            }
        }
        throw new CustomException(ExceptionContent.BAD_REQUEST_INTRODUCE_STATUS_TYPE);
    }
}
