package com.letscareer.recruitment.domain.repository.custom;

import com.letscareer.recruitment.domain.Stage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.letscareer.recruitment.domain.QStage.stage;

@RequiredArgsConstructor
public class StageRepositoryImpl implements StageRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    // 특정 채용에 대한 모든 단계를 종료일 기준으로 오름차순으로 조회
    public List<Stage> findAllStagesByRecruitmentId(Long recruitmentId) {
        return jpaQueryFactory.selectFrom(stage)
                .where(stage.recruitment.id.eq(recruitmentId))
                .orderBy(stage.endDate.asc())  // 종료일 기준 오름차순 정렬
                .fetch();
    }

}
