package com.letscareer.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {
    private final ExceptionContent content;

    @Override
    public String getMessage() {
        return content.getMessage();
    }
}
