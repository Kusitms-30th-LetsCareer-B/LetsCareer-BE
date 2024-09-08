package com.letscareer.recruitment.dto.response;

import com.letscareer.recruitment.domain.Recruitment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetRecruitmentsNameRes {

    private Long recruitmentId;
    private String companyName;

    public static GetRecruitmentsNameRes from(Recruitment recruitment) {
        return GetRecruitmentsNameRes.builder()
                .recruitmentId(recruitment.getId())
                .companyName(recruitment.getCompanyName())
                .build();
    }
}
