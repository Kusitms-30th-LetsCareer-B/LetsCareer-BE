package com.letscareer.recruitment.domain.repository;

import com.letscareer.recruitment.domain.Recruitment;
import com.letscareer.recruitment.domain.repository.custom.RecruitmentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitmentRepository extends JpaRepository<Recruitment,Long>, RecruitmentRepositoryCustom {
}
