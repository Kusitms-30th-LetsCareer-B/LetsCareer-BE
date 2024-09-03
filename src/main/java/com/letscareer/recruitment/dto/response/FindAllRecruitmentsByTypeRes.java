package com.letscareer.recruitment.dto.response;

import com.letscareer.recruitment.domain.Recruitment;
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
public class FindAllRecruitmentsByTypeRes {

    private List<RecruitmentInfo> recruitments;

    public static FindAllRecruitmentsByTypeRes of(List<RecruitmentInfo> recruitments) {
        return FindAllRecruitmentsByTypeRes
                .builder()
                .recruitments(recruitments)
                .build();
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    public static class RecruitmentInfo {
        private Long recruitmentId;
        private String companyName;
        private String task;
        private Boolean isFavorite;
        private String stageName;
        private StageStatusType status;
        private LocalDate endDate;
        private Long daysUntilEnd;

        public static FindAllRecruitmentsByTypeRes.RecruitmentInfo of(Recruitment recruitment, String stageName, StageStatusType status, LocalDate endDate, Long daysUntilEnd) {
            return RecruitmentInfo
                    .builder()
                    .recruitmentId(recruitment.getId())
                    .companyName(recruitment.getCompanyName())
                    .task(recruitment.getTask())
                    .isFavorite(recruitment.getIsFavorite())
                    .stageName(stageName)
                    .status(status)
                    .endDate(endDate)
                    .daysUntilEnd(daysUntilEnd)
                    .build();
        }
    }

}
