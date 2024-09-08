package com.letscareer.career.domain.repository;

import com.letscareer.career.domain.Education;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationRepository extends JpaRepository<Education, Long> {
}