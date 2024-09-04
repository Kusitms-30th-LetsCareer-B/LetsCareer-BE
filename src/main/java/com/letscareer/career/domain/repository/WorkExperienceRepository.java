package com.letscareer.career.domain.repository;

import com.letscareer.career.domain.WorkExperience;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkExperienceRepository extends JpaRepository<WorkExperience, Long> {
}