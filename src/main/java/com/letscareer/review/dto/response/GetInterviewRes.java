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
public class GetInterviewRes {

    private Long interviewId;
    private int order;
    private String question;
    private String answer;
    private String type;

    public static GetInterviewRes of (Interview interview) {
        return GetInterviewRes.builder()
                .interviewId(interview.getId())
                .order(interview.getOrderIndex())
                .question(interview.getQuestion())
                .answer(interview.getAnswer())
                .type(interview.getType() != null ? interview.getType().getName() : null)
                .build();
    }
}
