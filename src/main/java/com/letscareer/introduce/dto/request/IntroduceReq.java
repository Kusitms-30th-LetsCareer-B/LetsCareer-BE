package com.letscareer.introduce.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class IntroduceReq {

        private int order;
        private String question;
        private String answer;

        public static IntroduceReq of (int order, String question, String answer) {
            return IntroduceReq.builder()
                    .order(order)
                    .question(question)
                    .answer(answer)
                    .build();
        }
}
