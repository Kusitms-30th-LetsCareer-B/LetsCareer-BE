package com.letscareer.career.domain.repository;

import com.letscareer.career.domain.Education;
import com.letscareer.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationRepository extends JpaRepository<Education, Long> {
	List<Education> findByUser(User user);
}