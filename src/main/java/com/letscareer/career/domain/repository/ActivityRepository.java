package com.letscareer.career.domain.repository;

import com.letscareer.career.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
}