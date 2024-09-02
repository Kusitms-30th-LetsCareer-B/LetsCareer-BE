package com.letscareer.recruitment.dto.response;

import com.letscareer.recruitment.domain.StageStatusType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class DetermineRecruitmentStatusRes {

    private String stageName;
    private StageStatusType status;

    public static DetermineRecruitmentStatusRes of(String stageName, StageStatusType status) {
        return DetermineRecruitmentStatusRes
                .builder()
                .stageName(stageName)
                .status(status)
                .build();
    }
}
