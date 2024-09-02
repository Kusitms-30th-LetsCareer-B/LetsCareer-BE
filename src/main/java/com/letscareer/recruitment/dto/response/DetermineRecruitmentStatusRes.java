package com.letscareer.recruitment.dto.response;

import com.letscareer.recruitment.domain.Stage;
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
    private long daysUntilFinal;

    public static DetermineRecruitmentStatusRes from(Stage stage) {
        LocalDate today = LocalDate.now();
        return DetermineRecruitmentStatusRes
                .builder()
                .stageName(stage.getStageName())
                .status(stage.getStatus())
                .endDate(stage.getEndDate())
                .isFinal(stage.getIsFinal())
                .daysUntilFinal(stage.getEndDate().toEpochDay() - today.toEpochDay())
                .build();
    }
}
