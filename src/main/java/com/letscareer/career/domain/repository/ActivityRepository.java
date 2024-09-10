package com.letscareer.career.domain.repository;

import com.letscareer.career.domain.Activity;
import com.letscareer.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
	List<Activity> findByUser(User user);
}