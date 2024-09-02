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

    BAD_REQUEST_STAGE_STATUS_TYPE(BAD_REQUEST, "잘못된 요청입니다. 유효하지 않은 채용 상태입니다."),
    NOT_FOUND_USER(NOT_FOUND, "존재하지 않는 사용자입니다."),
    NOT_FOUND_RECRUITMENT(NOT_FOUND, "존재하지 않는 일정입니다."),
    NOT_FOUND_ARCHIVING(NOT_FOUND, "존재하지 않는 아카이빙입니다."),
    NOT_FOUND_FILE(NOT_FOUND,"S3 파일을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
