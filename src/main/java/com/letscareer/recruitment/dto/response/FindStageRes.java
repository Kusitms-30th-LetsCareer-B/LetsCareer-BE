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
public class FindStageRes {

    private String stageName;
    private LocalDate startDate;
    private LocalDate endDate;
    private StageStatusType status;
    private Boolean isFinal;

    public static FindStageRes from(Stage stage) {
        return FindStageRes
                .builder()
                .stageName(stage.getStageName())
                .startDate(stage.getStartDate())
                .endDate(stage.getEndDate())
                .status(stage.getStatus())
                .isFinal(stage.getIsFinal())
                .build();
    }
}
