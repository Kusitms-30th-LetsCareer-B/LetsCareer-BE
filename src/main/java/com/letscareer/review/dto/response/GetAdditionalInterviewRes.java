package com.letscareer.review.dto.response;

import com.letscareer.review.domain.Interview;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetAdditionalInterviewRes {
    private Long interviewId;
    private String question;

    public static GetAdditionalInterviewRes of (Interview interview) {
        return GetAdditionalInterviewRes.builder()
                .interviewId(interview.getId())
                .question(interview.getQuestion())
                .build();
    }

}
