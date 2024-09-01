package com.letscareer.recruitment.domain.repository.custom;

import com.letscareer.recruitment.domain.Recruitment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.letscareer.recruitment.domain.QRecruitment.recruitment;
import static com.letscareer.recruitment.domain.QStage.stage;

@RequiredArgsConstructor
public class RecruitmentRepositoryImpl implements RecruitmentRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Recruitment findRecruitmentWithStagesByAsc(Long recruitmentId) {
        return jpaQueryFactory
                .selectFrom(recruitment)
                .leftJoin(recruitment.stages, stage)
                .fetchJoin()
                .where(recruitment.id.eq(recruitmentId))
                .orderBy(stage.endDate.asc())
                .fetchOne();
    }
}
