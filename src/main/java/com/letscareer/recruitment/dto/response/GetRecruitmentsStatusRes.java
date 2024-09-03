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
    private int progress;
    private int passed;
    private int failed;

    public static GetRecruitmentsStatusRes of(Integer total, Integer progress, Integer passed, Integer failed) {
        return GetRecruitmentsStatusRes
                .builder()
                .total(total)
                .progress(progress)
                .passed(passed)
                .failed(failed)
                .build();
    }
}
