package com.letscareer.global.handler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {
    private final int code;
    private final String msg;
    private final Object data;

    public static ErrorResponse of(HttpStatus code, String msg, Object data) {
        return new ErrorResponse(code.value(), msg, data);
    }
}
