package com.letscareer.recruitment.domain.repository.custom;

import com.letscareer.recruitment.domain.Recruitment;

public interface RecruitmentRepositoryCustom {
    Recruitment findRecruitmentWithStagesByAsc(Long recruitmentId);
}
