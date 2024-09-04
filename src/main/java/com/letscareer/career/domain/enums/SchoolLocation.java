package com.letscareer.career.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SchoolLocation {

    SEOUL("서울"),
    GYEONGGI("경기"),
    INCHEON("인천"),
    GANGWON("강원"),
    DAEJEON("대전"),
    SEJONG("세종"),
    DAEGU("대구"),
    BUSAN("부산"),
    ULSAN("울산"),
    GWANGJU("광주"),
    JEJU("제주");

    private final String name;
}
