package com.letscareer.review.application;

import com.letscareer.global.exception.CustomException;
import com.letscareer.global.exception.ExceptionContent;
import com.letscareer.review.dto.request.ReactionReq;
import com.letscareer.recruitment.domain.Recruitment;
import com.letscareer.recruitment.domain.repository.RecruitmentRepository;
import com.letscareer.review.domain.Interview;
import com.letscareer.review.domain.enums.InterviewStatusType;
import com.letscareer.review.domain.repository.InterviewRepository;
import com.letscareer.review.dto.request.InterviewReq;
import com.letscareer.review.dto.response.GetAdditonalInterviewRes;
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

    /**
     * 특정 기업의 면접 질문을 생성하거나 수정
     *
     * @param recruitmentId the recruitment id
     * @param request       the request
     */
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

    /**
     * 특정 기업의 면접 질문을 조회
     *
     * @param recruitmentId the recruitment id
     * @return the interviews
     */
    @Transactional(readOnly = true)
    public List<GetInterviewRes> getInterviews(Long recruitmentId) {
        List<Interview> interviews = interviewRepository.findAllByRecruitmentId(recruitmentId);
        return interviews.stream().map(GetInterviewRes::of).toList();
    }

    /**
     * 특정 면접 질문 삭제
     *
     * @param interviewId   the interview id
     * @param recruitmentId the recruitment id
     */
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

    /**
     * 면접 질문의 잘했어요, 아쉬워요 수정
     *
     * @param interviewId the interview id
     * @param request     the request
     */
    @Transactional
    public void modifyReaction(Long interviewId, ReactionReq request) {
        Interview interview = interviewRepository.findById(interviewId).orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_INTERVIEW));
        interview.modifyReaction(request);
    }

    /**
     * 한번 더 보면 좋을 면접 질문
     *
     * @param recruitmentId the recruitment id
     * @return the additional interviews
     */
    @Transactional(readOnly = true)
    public List<GetAdditonalInterviewRes> getAdditionalInterviews(Long recruitmentId) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId).orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_RECRUITMENT));

        return interviewRepository.findAllByRecruitmentAndType(recruitment, InterviewStatusType.of("아쉬워요")).stream().map(GetAdditonalInterviewRes::of).toList();
    }
}