package com.letscareer.global.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor(staticName = "of")
@Schema(description = "공통 응답 DTO")
public class ResponseDto<D> {

    @Schema(description = "시스템 코드", example = "200")
    private final String systemCode;

    @Schema(description = "메시지", example = "요청 성공")
    private final String message;

    @Schema(description = "데이터")
    private final D data;

    public static <D> ResponseDto<D> ofSuccess(String message, D data) {
        return new ResponseDto<>("200", message, data);
    }

    public static <D> ResponseDto<D> ofFailure(String message, D data) {
        return new ResponseDto<>("400", message, data);
    }

    public static <D> ResponseDto<D> ofError(String message, D data) {
        return new ResponseDto<>("500", message, data);
    }
}
