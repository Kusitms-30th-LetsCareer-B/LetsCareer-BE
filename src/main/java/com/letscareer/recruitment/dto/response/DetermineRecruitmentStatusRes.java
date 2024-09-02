package com.letscareer.recruitment.dto.response;

import com.letscareer.recruitment.domain.StageStatusType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class DetermineRecruitmentStatusRes {

    private String stageName;
    private StageStatusType status;
    private LocalDate endDate;
    private Boolean isFinal;

    public static DetermineRecruitmentStatusRes of(String stageName, StageStatusType status, LocalDate endDate, Boolean isFinal) {
        return DetermineRecruitmentStatusRes
                .builder()
                .stageName(stageName)
                .status(status)
                .endDate(endDate)
                .isFinal(isFinal)
                .build();
    }
}
