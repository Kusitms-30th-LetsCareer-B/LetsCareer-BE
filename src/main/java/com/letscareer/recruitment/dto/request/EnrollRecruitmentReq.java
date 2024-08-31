package com.letscareer.recruitment.dto.request;

import com.letscareer.recruitment.domain.Recruitment;
import com.letscareer.user.domain.User;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
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
