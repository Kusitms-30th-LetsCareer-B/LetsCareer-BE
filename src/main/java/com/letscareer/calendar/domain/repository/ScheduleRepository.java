package com.letscareer.calendar.domain.repository;

import com.letscareer.calendar.domain.Schedule;
import com.letscareer.recruitment.domain.Stage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByUserIdAndDateBetweenOrderByDateAsc(Long userId, LocalDate startDate, LocalDate endDate);
    void deleteByStageId(Long stageId);

    List<Schedule> findAllByUserIdAndDate(Long userId, LocalDate date);
}
