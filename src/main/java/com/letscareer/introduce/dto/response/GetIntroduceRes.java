package com.letscareer.introduce.dto.response;

import com.letscareer.introduce.domain.Introduce;
import com.letscareer.introduce.domain.IntroduceStatusType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetIntroduceRes {

    private Long introduceId;
    private int order;
    private String question;
    private String answer;
    private String type;

    public static GetIntroduceRes of (Introduce introduce) {
        return GetIntroduceRes.builder()
                .introduceId(introduce.getId())
                .order(introduce.getOrderIndex())
                .question(introduce.getQuestion())
                .answer(introduce.getAnswer())
                .type(introduce.getType() != null ? introduce.getType().getName() : null)
                .build();
    }
}
