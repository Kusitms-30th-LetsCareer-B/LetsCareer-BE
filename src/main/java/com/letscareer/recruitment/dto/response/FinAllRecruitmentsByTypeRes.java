package com.letscareer.recruitment.dto.response;

import com.letscareer.recruitment.domain.Recruitment;
import com.letscareer.recruitment.domain.StageStatusType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class FinAllRecruitmentsByTypeRes {

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    public static class RecruitmentInfo {
        private Long recruitmentId;
        private String companyName;
        private String task;
        private Boolean isFavorite;
        private String announcementUrl;
        private String stageName;
        private StageStatusType status;
        private LocalDate endDate;

        public static FinAllRecruitmentsByTypeRes.RecruitmentInfo of(Recruitment recruitment) {
            return RecruitmentInfo
                    .builder()
                    .recruitmentId(recruitment.getId())
                    .build();
        }
    }


}
