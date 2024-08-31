package com.letscareer.recruitment.application;

import com.letscareer.global.exception.CustomException;
import com.letscareer.global.exception.ExceptionContent;
import com.letscareer.recruitment.domain.Recruitment;
import com.letscareer.recruitment.domain.Stage;
import com.letscareer.recruitment.domain.StageStatusType;
import com.letscareer.recruitment.domain.repository.RecruitmentRepository;
import com.letscareer.recruitment.domain.repository.StageRepository;
import com.letscareer.recruitment.dto.request.EnrollRecruitmentReq;
import com.letscareer.user.domain.User;
import com.letscareer.user.domain.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecruitmentService {

    private final UserRepository userRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final StageRepository stageRepository;

    @PostConstruct
    public void init() {
        userRepository.save(User.builder()
                .name("하이")
                .email("jh981109@naver.com")
                .build());
        userRepository.save(User.builder()
                .name("하이2")
                .email("wnsgud@naver.com")
                .build());
    }

    @Transactional
    public void enrollRecruitment(Long userId, EnrollRecruitmentReq request) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new CustomException(ExceptionContent.NOT_FOUND_USER));
        Recruitment recruitment = recruitmentRepository.save(Recruitment.of(user, request.getCompanyName(), request.getIsFavorite(), request.getTask(), request.getIsRemind(), request.getAnnouncementUrl()));
        stageRepository.save(Stage.of(recruitment,"서류", request.getStageStartDate(), request.getStageEndDate(), StageStatusType.PENDING,false));
    }
}
