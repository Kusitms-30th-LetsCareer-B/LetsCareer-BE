package com.letscareer.recruitment.dto.request;

import com.letscareer.recruitment.domain.StageStatusType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ModifyStageReq {

    @NotBlank(message = "전형명을 함께 전달해주세요.")
    private String stageName;
    @NotNull(message = "마감일을 함께 전달해주세요.")
    private LocalDate endDate;
    @NotNull(message = "채용 전형을 함께 전달해주세요.")
    private StageStatusType status;

}
