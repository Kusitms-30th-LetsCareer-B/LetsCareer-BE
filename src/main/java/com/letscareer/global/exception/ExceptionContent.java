package com.letscareer.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum ExceptionContent {

    /**
     * 계속해서 추가
     */

    BAD_REQUEST_STAGE_STATUS_TYPE(BAD_REQUEST, "잘못된 요청입니다. 유효하지 않은 채용 상태 상태입니다. (준비중, 합격, 불합격으로 입력해주세요.)"),

    NOT_FOUND_USER(NOT_FOUND, "존재하지 않는 사용자입니다."),
    NOT_FOUND_RECRUITMENT(NOT_FOUND, "존재하지 않는 일정입니다." ),
    NOT_FOUND_STAGE(NOT_FOUND, "존재하는 않는 일정 전형입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
