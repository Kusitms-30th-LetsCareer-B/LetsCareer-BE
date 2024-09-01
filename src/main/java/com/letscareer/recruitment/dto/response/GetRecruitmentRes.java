package com.letscareer.recruitment.dto.response;

import com.letscareer.recruitment.domain.Recruitment;
import com.letscareer.recruitment.domain.Stage;
import com.letscareer.recruitment.domain.StageStatusType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetRecruitmentRes {
    private String companyName;
    private Boolean isFavorite;
    private String task;
    private String announcementUrl;
    private List<StageRes> stages;

    public static GetRecruitmentRes of (Recruitment recruitment, List<StageRes> stages) {
        return GetRecruitmentRes.builder()
                .companyName(recruitment.getCompanyName())
                .isFavorite(recruitment.getIsFavorite())
                .task(recruitment.getTask())
                .announcementUrl(recruitment.getAnnouncementUrl())
                .stages(stages)
                .build();
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    public static class StageRes {
        private String stageName;
        private LocalDate startDate;
        private LocalDate endDate;
        private StageStatusType status;
        private Boolean isFinal;

        public static StageRes from(Stage stage){
            return StageRes.builder()
                    .stageName(stage.getStageName())
                    .startDate(stage.getStartDate())
                    .endDate(stage.getEndDate())
                    .status(stage.getStatus())
                    .isFinal(stage.getIsFinal())
                    .build();
        }
    }
}
