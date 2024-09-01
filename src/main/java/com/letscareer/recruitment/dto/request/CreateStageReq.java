package com.letscareer.recruitment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CreateStageReq {

    @NotBlank(message = "전형명을 함께 입력해주세요.")
    private String stageName;
    @NotNull(message = "마감일을 함께 입력해주세요.")
    private LocalDate endDate;
    @NotNull(message = "최종 유무를 함께 전달해주세요.")
    private Boolean isFinal;
}
