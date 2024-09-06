package com.letscareer.recruitment.domain.repository.custom;

import com.letscareer.recruitment.domain.Recruitment;

import java.time.LocalDate;
import java.util.List;

public interface RecruitmentRepositoryCustom {
    Recruitment findRecruitmentWithStagesByAsc(Long recruitmentId);
    List<Recruitment> findRecruitmentsWithUpcomingStages(Long userId, LocalDate today, long offset, long limit);
    Long countRecruitmentsWithUpcomingStages(Long userId, LocalDate today);

}
