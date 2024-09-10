package com.letscareer.recruitment.domain.repository.custom;

import com.letscareer.recruitment.domain.Recruitment;
import com.letscareer.recruitment.domain.StageStatusType;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;


import static com.letscareer.recruitment.domain.QRecruitment.recruitment;
import static com.letscareer.recruitment.domain.QStage.stage;


@RequiredArgsConstructor
public class RecruitmentRepositoryImpl implements RecruitmentRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Recruitment findRecruitmentWithStagesByAsc(Long recruitmentId) {
        return jpaQueryFactory
                .selectFrom(recruitment)
                .distinct()
                .leftJoin(recruitment.stages, stage)
                .fetchJoin()
                .where(recruitment.id.eq(recruitmentId))
                .orderBy(stage.endDate.asc())
                .fetchOne();
    }

    @Override
    public List<Recruitment> findRecruitmentsWithUpcomingStages(Long userId, LocalDate today, long offset, long limit) {
        return jpaQueryFactory
                .selectFrom(recruitment)  // Recruitment 명시적으로 선택
                .distinct()
                .leftJoin(recruitment.stages, stage)
                .fetchJoin()
                .where(recruitment.user.id.eq(userId)
                        .and(stage.endDate.after(today)))  // 종료일이 오늘 이후인 것만 필터링
                .orderBy(stage.endDate.asc())
                .offset(offset)
                .limit(limit)
                .fetch();
    }

    @Override
    public Long countRecruitmentsWithUpcomingStages(Long userId, LocalDate today) {
        return jpaQueryFactory
                .select(recruitment.countDistinct())
                .from(recruitment)
                .leftJoin(recruitment.stages, stage)
                .where(recruitment.user.id.eq(userId)
                        .and(stage.endDate.after(today)))
                .fetchOne();
    }

    @Override
    public List<Recruitment> findRecruitmentsByTypeAndUser(String type, Long userId, LocalDate today, long offset, long limit) {
        BooleanBuilder condition = new BooleanBuilder();

        if (type.equalsIgnoreCase("progress")) {
            condition.and(stage.status.eq(StageStatusType.PROGRESS)
                    .or(stage.status.eq(StageStatusType.PASSED).and(stage.isFinal.eq(false))));
        } else if (type.equalsIgnoreCase("consequence")) {
            condition.and(stage.status.eq(StageStatusType.FAILED)
                    .or(stage.status.eq(StageStatusType.PASSED).and(stage.isFinal.eq(true))));
        }

        return jpaQueryFactory.
                selectFrom(recruitment)
                .distinct()
                .leftJoin(recruitment.stages, stage)
                .fetchJoin()
                .where(recruitment.user.id.eq(userId)
                        .and(condition))
                .offset(offset)
                .limit(limit)
                .fetch();
    }

    @Override
    public Long countRecruitmentsByTypeAndUser(String type, Long userId, LocalDate today) {
        BooleanBuilder condition = new BooleanBuilder();

        if (type.equalsIgnoreCase("progress")) {
            condition.and(stage.status.eq(StageStatusType.PROGRESS)
                    .or(stage.status.eq(StageStatusType.PASSED).and(stage.isFinal.eq(false))));
        } else if (type.equalsIgnoreCase("consequence")) {
            condition.and(stage.status.eq(StageStatusType.FAILED)
                    .or(stage.status.eq(StageStatusType.PASSED).and(stage.isFinal.eq(true))));
        }

        return jpaQueryFactory
                .select(recruitment.countDistinct())
                .from(recruitment)
                .leftJoin(recruitment.stages, stage)
                .where(recruitment.user.id.eq(userId)
                        .and(condition))
                .fetchOne();
    }

}
