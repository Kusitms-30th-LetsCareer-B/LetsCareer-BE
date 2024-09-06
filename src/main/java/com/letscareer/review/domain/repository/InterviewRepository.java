package com.letscareer.review.domain.repository;

import com.letscareer.introduce.domain.Introduce;
import com.letscareer.recruitment.domain.Recruitment;
import com.letscareer.review.domain.Interview;
import com.letscareer.review.domain.enums.InterviewStatusType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface InterviewRepository extends JpaRepository<Interview, Long> {
    List<Interview> findAllByRecruitmentId(Long recruitmentId);
    Optional<Interview> findByRecruitmentAndOrderIndex(Recruitment recruitment, int order);
    List<Interview> findAllByRecruitmentAndType(Recruitment recruitment, InterviewStatusType statusType);
}
