package com.letscareer.review.application;

import com.letscareer.global.exception.CustomException;
import com.letscareer.global.exception.ExceptionContent;
import com.letscareer.introduce.dto.request.ReactionReq;
import com.letscareer.recruitment.domain.Recruitment;
import com.letscareer.recruitment.domain.repository.RecruitmentRepository;
import com.letscareer.review.domain.Interview;
import com.letscareer.review.domain.repository.InterviewRepository;
import com.letscareer.review.dto.request.InterviewReq;
import com.letscareer.review.dto.response.GetInterviewRes;
import com.letscareer.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InterviewService {

    private final InterviewRepository interviewRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createOrModifyInterview(Long recruitmentId, List<InterviewReq> request) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_RECRUITMENT));

        request.forEach(e -> {
            Optional<Interview> existingInterview = interviewRepository.findByRecruitmentAndOrderIndex(recruitment, e.getOrder());

            if (existingInterview.isPresent()) {
                // 존재하면 수정
                Interview interview = existingInterview.get();
                interview.update(e.getQuestion(), e.getAnswer());
            } else {
                // 존재하지 않으면 새로 생성
                Interview newInterview = Interview.of(recruitment, e.getOrder(), e.getQuestion(), e.getAnswer());
                interviewRepository.save(newInterview);
            }
        });
    }

    @Transactional(readOnly = true)
    public List<GetInterviewRes> getInterviews(Long recruitmentId) {
        List<Interview> interviews = interviewRepository.findAllByRecruitmentId(recruitmentId);
        return interviews.stream().map(GetInterviewRes::of).toList();
    }

    @Transactional
    public void deleteInterview(Long interviewId, Long recruitmentId) {
        List<Interview> interviews = interviewRepository.findAllByRecruitmentId(recruitmentId);
        Interview interview = interviewRepository.findById(interviewId).orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_INTERVIEW));
        interviewRepository.deleteById(interviewId);
        for (Interview eachInterview : interviews) {
            if (eachInterview.getOrderIndex() > interview.getOrderIndex()){
                eachInterview.minusOrderIndex();
            }
        }
    }

    @Transactional
    public void modifyReaction(Long interviewId, ReactionReq request) {
        Interview interview = interviewRepository.findById(interviewId).orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_INTERVIEW));
        interview.modifyReaction(request);
    }


}