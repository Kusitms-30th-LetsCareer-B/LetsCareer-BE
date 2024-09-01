package com.letscareer.recruitment.dto.request;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class EnrollRecruitmentReq {

    private String companyName;
    private Boolean isFavorite;
    private String task;
    private Boolean isRemind;
    private String announcementUrl;
    private LocalDate stageStartDate;
    private LocalDate stageEndDate;

}
