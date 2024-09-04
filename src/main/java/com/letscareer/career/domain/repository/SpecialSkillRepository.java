package com.letscareer.career.domain.repository;

import com.letscareer.career.domain.SpecialSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpecialSkillRepository extends JpaRepository<SpecialSkill, Long> {
    List<SpecialSkill> findByUserId(Long userId);
}
