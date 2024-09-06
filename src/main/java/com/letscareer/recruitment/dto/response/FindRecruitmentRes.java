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
public class FindRecruitmentRes {
    private Long recruitmentId;
    private String companyName;
    private Boolean isFavorite;
    private String task;
    private String announcementUrl;
    private String stageName;
    private StageStatusType status;
    private long daysUntilEnd;
    private List<StageRes> stages;


    public static FindRecruitmentRes of (Recruitment recruitment, List<StageRes> stages, String stageName, StageStatusType status, Long daysUntilEnd) {
        return FindRecruitmentRes.builder()
                .recruitmentId(recruitment.getId())
                .companyName(recruitment.getCompanyName())
                .isFavorite(recruitment.getIsFavorite())
                .task(recruitment.getTask())
                .announcementUrl(recruitment.getAnnouncementUrl())
                .stageName(stageName)
                .status(status)
                .daysUntilEnd(daysUntilEnd)
                .stages(stages)
                .build();
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    public static class StageRes {
        private Long stageId;
        private String stageName;
        private LocalDate startDate;
        private LocalDate endDate;
        private StageStatusType status;
        private Boolean isFinal;

        public static StageRes from(Stage stage){
            return StageRes.builder()
                    .stageId(stage.getId())
                    .stageName(stage.getStageName())
                    .startDate(stage.getStartDate())
                    .endDate(stage.getEndDate())
                    .status(stage.getStatus())
                    .isFinal(stage.getIsFinal())
                    .build();
        }
    }
}
