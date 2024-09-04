package com.letscareer.career.dto.request;

import com.letscareer.career.domain.enums.ExperienceType;

public record SpecialSkillRequest(
        String title,
        String content,
        String experienceType // 한글로 받은 경험 타입
) {
}
