package com.letscareer.calendar.domain.repository;

import com.letscareer.calendar.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
