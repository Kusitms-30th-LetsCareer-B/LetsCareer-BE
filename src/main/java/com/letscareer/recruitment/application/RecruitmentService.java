package com.letscareer.recruitment.application;

import com.letscareer.global.exception.CustomException;
import com.letscareer.global.exception.ExceptionContent;
import com.letscareer.recruitment.domain.Recruitment;
import com.letscareer.recruitment.domain.repository.RecruitmentRepository;
import com.letscareer.recruitment.dto.request.EnrollRecruitmentReq;
import com.letscareer.user.domain.User;
import com.letscareer.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecruitmentService {

    private final UserRepository userRepository;
    private final RecruitmentRepository recruitmentRepository;

    @Transactional
    public void enrollRecruitment(Long userId, EnrollRecruitmentReq request) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new CustomException(ExceptionContent.NOT_FOUND_USER));
        recruitmentRepository.save(Recruitment.of(user, request.getCompanyName(), request.getIsFavorite(), request.getTask(), request.getIsRemind(), request.getAnnouncementUrl()));
    }
}
