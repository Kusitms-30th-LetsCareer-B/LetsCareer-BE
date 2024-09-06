package com.letscareer.review.dto.response;

import com.letscareer.review.domain.Interview;
import com.letscareer.review.domain.enums.InterviewStatusType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetAdditonalInterviewRes {
    private Long interviewId;
    private String question;

    public static GetAdditonalInterviewRes of (Interview interview) {
        return GetAdditonalInterviewRes.builder()
                .interviewId(interview.getId())
                .question(interview.getQuestion())
                .build();
    }

}
