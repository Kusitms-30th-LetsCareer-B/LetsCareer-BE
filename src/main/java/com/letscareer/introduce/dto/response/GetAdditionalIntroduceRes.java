package com.letscareer.introduce.dto.response;

import com.letscareer.introduce.domain.Introduce;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetAdditionalIntroduceRes {
    private Long introduceId;
    private String question;

    public static GetAdditionalIntroduceRes of (Introduce introduce) {
        return GetAdditionalIntroduceRes.builder()
                .introduceId(introduce.getId())
                .question(introduce.getQuestion())
                .build();
    }

}
