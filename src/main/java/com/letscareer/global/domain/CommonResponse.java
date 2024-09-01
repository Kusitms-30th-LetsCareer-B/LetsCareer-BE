package com.letscareer.global.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor(staticName = "of")
public class CommonResponse<D> {

    private final int code;

    private final String message;

    private final D data;

    public static <D> CommonResponse<D> ofSuccess(String message, D data) {
        return new CommonResponse<>(200, message, data);
    }

    public static <D> CommonResponse<D> ofFailure(String message, D data) {
        return new CommonResponse<>(400, message, data);
    }

    public static <D> CommonResponse<D> ofError(String message, D data) {
        return new CommonResponse<>(500, message, data);
    }
}
