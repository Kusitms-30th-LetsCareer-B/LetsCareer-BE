package com.letscareer.recruitment.domain.repository;

import com.letscareer.recruitment.domain.Stage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StageRepository extends JpaRepository<Stage, Long> {

    List<Stage> findAllByRecruitmentIdOrderByEndDateDesc(Long recruitmentId);
}
