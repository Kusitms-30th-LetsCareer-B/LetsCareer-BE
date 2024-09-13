package com.letscareer.calendar.domain.repository;

import com.letscareer.calendar.domain.Schedule;
import com.querydsl.jpa.impl.JPAQueryFactory;
	import lombok.RequiredArgsConstructor;
	import org.springframework.stereotype.Repository;
	import java.time.LocalDate;
import java.util.List;

import static com.letscareer.calendar.domain.QSchedule.schedule;
	import static com.letscareer.recruitment.domain.QStage.stage;

@Repository
@RequiredArgsConstructor
public class ScheduleRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public List<Schedule> findAllByUserIdAndDateBetweenWithStage(Long userId, LocalDate startDate, LocalDate endDate) {
		return queryFactory.selectFrom(schedule)
			.leftJoin(schedule.stage, stage).fetchJoin()
			.where(schedule.user.id.eq(userId)
				.and(schedule.date.between(startDate, endDate)))
			.orderBy(schedule.date.asc())
			.fetch();
	}
}
