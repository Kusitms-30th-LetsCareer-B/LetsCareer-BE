package com.letscareer.recruitment.domain.repository;

import com.letscareer.recruitment.domain.Recruitment;
import com.letscareer.recruitment.domain.Stage;
import com.letscareer.recruitment.domain.repository.custom.StageRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StageRepository extends JpaRepository<Stage, Long>, StageRepositoryCustom {

    List<Stage> findAllByRecruitmentOrderByEndDateAsc(Recruitment recruitment);
}
