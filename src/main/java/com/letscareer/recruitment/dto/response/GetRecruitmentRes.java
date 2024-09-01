package com.letscareer.recruitment.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.letscareer.recruitment.domain.Stage;
import com.letscareer.recruitment.domain.StageStatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class GetRecruitmentRes {
    private String companyName;
    private Boolean isFavorite;
    private String task;
    private String announcementUrl;
    private List<Stage> stages;

    @Getter
    @AllArgsConstructor
    public static class Stage {
        private String stageName;
        private LocalDate startDate;
        private LocalDate endDate;
        private StageStatusType status;
        private Boolean isFinal;
    }
}
