package com.letscareer.recruitment.domain.repository;

import com.letscareer.recruitment.domain.Recruitment;
import com.letscareer.recruitment.domain.repository.custom.RecruitmentRepositoryCustom;
import com.letscareer.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecruitmentRepository extends JpaRepository<Recruitment,Long>, RecruitmentRepositoryCustom {
    List<Recruitment> findAllByUserId(Long userId);

    List<Recruitment> findAllByUserIdOrderByIdDesc(Long userId);

    List<Recruitment> findAllByUser(User user);
}
