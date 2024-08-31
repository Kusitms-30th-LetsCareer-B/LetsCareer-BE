package com.letscareer.recruitment.dto.request;

import com.letscareer.recruitment.domain.Recruitment;
import com.letscareer.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class EnrollRecruitmentReq {

    private String companyName;
    private Boolean isFavorite;
    private String task;
    private Boolean isRemind;
    private String announcementUrl;
    private String stageName = "서류";
    private LocalDate startDate;
    private LocalDate endDate;

}
