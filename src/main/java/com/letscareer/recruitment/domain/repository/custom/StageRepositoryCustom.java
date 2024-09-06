package com.letscareer.recruitment.domain.repository.custom;

import com.letscareer.recruitment.domain.Stage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

public interface StageRepositoryCustom {
    List<Stage> findAllStagesByRecruitmentId(Long recruitmentId);
}
