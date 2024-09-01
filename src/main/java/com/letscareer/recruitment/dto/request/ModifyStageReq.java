package com.letscareer.recruitment.dto.request;

import com.letscareer.recruitment.domain.StageStatusType;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ModifyStageReq {

    private String stageName;
    private LocalDate endDate;
    private StageStatusType status;

}
