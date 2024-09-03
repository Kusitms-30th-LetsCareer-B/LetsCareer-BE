package com.letscareer.recruitment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class EnrollRecruitmentReq {

    @NotBlank(message = "기업명을 입력해주세요.")
    private String companyName;

    private Boolean isFavorite;

    @NotBlank(message = "직무를 입력해주세요.")
    private String task;

    private Boolean isRemind;

    private String announcementUrl;

    @NotNull(message = "서류 시작 날짜를 등록해주세요.")
    private LocalDate stageStartDate;

    @NotNull(message = "서류 마감 날짜를 등록해주세요.")
    private LocalDate stageEndDate;

}
