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
public class FindAllRecruitmentsRes {

    private List<RecruitmentInfo> recruitments;

    public static FindAllRecruitmentsRes of(List<RecruitmentInfo> recruitments) {
        return FindAllRecruitmentsRes
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
        private Boolean isRemind;
        private String announcementUrl;
        private String stageName;
        private StageStatusType status;
        private LocalDate endDate;

        public static RecruitmentInfo of(Recruitment recruitment, String stageName, StageStatusType status, LocalDate endDate){
            return RecruitmentInfo
                    .builder()
                    .recruitmentId(recruitment.getId())
                    .companyName(recruitment.getCompanyName())
                    .task(recruitment.getTask())
                    .isFavorite(recruitment.getIsFavorite())
                    .isRemind(recruitment.getIsRemind())
                    .announcementUrl(recruitment.getAnnouncementUrl())
                    .stageName(stageName)
                    .status(status)
                    .endDate(endDate)
                    .build();
        }
    }


}
