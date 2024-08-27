package com.letscareer.global.handler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class SuccessResponse {
    private final int code = HttpStatus.OK.value();
    private final String message = "요청 성공";
    private final Object data;

    public static SuccessResponse of(Object data) {
        return new SuccessResponse(data);
    }
}
