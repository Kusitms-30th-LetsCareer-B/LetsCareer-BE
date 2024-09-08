package com.letscareer.todo.domain.repository;

import com.letscareer.todo.domain.Routine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoutineRepository extends JpaRepository<Routine, Long> {
}
