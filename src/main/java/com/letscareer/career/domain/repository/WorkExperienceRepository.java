package com.letscareer.career.domain.repository;

import com.letscareer.career.domain.WorkExperience;
import com.letscareer.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkExperienceRepository extends JpaRepository<WorkExperience, Long> {
	List<WorkExperience> findByUser(User user);
}