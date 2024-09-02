package com.letscareer.calendar.domain.repository;

import com.letscareer.calendar.domain.PersonalSchedule;
import com.letscareer.calendar.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PersonalScheduleRepository extends JpaRepository<PersonalSchedule, Long> {
    List<PersonalSchedule> findAllByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);
}
