package com.letscareer.recruitment.application;

import com.letscareer.global.exception.CustomException;
import com.letscareer.global.exception.ExceptionContent;
import com.letscareer.recruitment.domain.Recruitment;
import com.letscareer.recruitment.domain.Stage;
import com.letscareer.recruitment.domain.StageStatusType;
import com.letscareer.recruitment.domain.repository.RecruitmentRepository;
import com.letscareer.recruitment.domain.repository.StageRepository;
import com.letscareer.recruitment.dto.request.EnrollRecruitmentReq;
import com.letscareer.recruitment.dto.response.DetermineRecruitmentStatusRes;
import com.letscareer.recruitment.dto.response.FindRecruitmentRes;
import com.letscareer.recruitment.dto.response.GetRecruitmentsStatusRes;
import com.letscareer.user.domain.User;
import com.letscareer.user.domain.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
                .orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_USER));
        Recruitment recruitment = recruitmentRepository.save(Recruitment.of(user, request.getCompanyName(), request.getIsFavorite(), request.getTask(), request.getIsRemind(), request.getAnnouncementUrl()));
        stageRepository.save(Stage.of(recruitment,"서류", request.getStageStartDate(), request.getStageEndDate(), StageStatusType.PROGRESS,false));
    }

    @Transactional(readOnly = true)
    public FindRecruitmentRes findRecruitment(Long recruitmentId) {
        try{
            Recruitment recruitment = recruitmentRepository.findRecruitmentWithStagesByAsc(recruitmentId);
            List<FindRecruitmentRes.StageRes> stageResponses = recruitment.getStages().stream()
                    .map(FindRecruitmentRes.StageRes::from)
                    .toList();
            return FindRecruitmentRes.of(recruitment, stageResponses);
        }
        catch(Exception e){
            throw new CustomException(ExceptionContent.NOT_FOUND_RECRUITMENT);
        }

    }

    @Transactional
    public void deleteRecruitment(Long recruitmentId) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_RECRUITMENT));
        recruitmentRepository.delete(recruitment);
    }

    @Transactional(readOnly = true)
    public GetRecruitmentsStatusRes getRecruitmentsStatus(Long userId) {
        List<Recruitment> recruitments = recruitmentRepository.findAllByUserId(userId);
        int total=recruitments.size();
        int progress=0, passed=0, failed=0;

        for (Recruitment recruitment : recruitments) {
            List<Stage> stages = stageRepository.findAllByRecruitmentIdOrderByEndDateDesc(recruitment.getId());
            DetermineRecruitmentStatusRes recruitmentStatus = determineRecruitmentStatus(stages);
            switch (recruitmentStatus.getStatus()){
                case PROGRESS -> ++progress;
                case PASSED -> ++passed;
                case FAILED -> ++failed;
            }
        }
        return GetRecruitmentsStatusRes.of(total, progress, passed, failed);
    }

    private DetermineRecruitmentStatusRes determineRecruitmentStatus(List<Stage> stages){
        for (Stage stage : stages) {
            if (stage.getStatus().equals(StageStatusType.FAILED)) {
                return DetermineRecruitmentStatusRes.of(stage.getStageName(), StageStatusType.FAILED);
            }
            else if (stage.getStatus().equals(StageStatusType.PASSED)) {
                return DetermineRecruitmentStatusRes.of(stage.getStageName(), StageStatusType.PASSED);
            }
        }
        return DetermineRecruitmentStatusRes.of("서류", StageStatusType.PROGRESS);
    }


}
