package com.letscareer.review.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class InterviewReq {

        private int order;
        private String question;
        private String answer;

        public static InterviewReq of (int order, String question, String answer) {
            return InterviewReq.builder()
                    .order(order)
                    .question(question)
                    .answer(answer)
                    .build();
        }
}
