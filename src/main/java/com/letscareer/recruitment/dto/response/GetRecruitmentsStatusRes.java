package com.letscareer.recruitment.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetRecruitmentsStatusRes {

    private int total;
    private int document;
    private int interview;
    private int other;

    public static GetRecruitmentsStatusRes of(Integer total, Integer document, Integer interview, Integer other) {
        return GetRecruitmentsStatusRes
                .builder()
                .total(total)
                .document(document)
                .interview(interview)
                .other(other)
                .build();
    }
}
