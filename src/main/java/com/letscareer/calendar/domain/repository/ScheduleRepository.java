package com.letscareer.calendar.domain.repository;

import com.letscareer.calendar.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);
}
