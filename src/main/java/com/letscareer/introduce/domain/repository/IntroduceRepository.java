package com.letscareer.introduce.domain.repository;

import com.letscareer.introduce.domain.Introduce;
import com.letscareer.recruitment.domain.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IntroduceRepository extends JpaRepository<Introduce, Long> {
    List<Introduce> findAllByRecruitmentId(Long recruitmentId);


    Optional<Introduce> findByRecruitmentAndOrderIndex(Recruitment recruitment, int order);
}