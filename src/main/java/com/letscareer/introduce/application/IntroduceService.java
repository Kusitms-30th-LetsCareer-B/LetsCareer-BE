package com.letscareer.introduce.application;

import com.letscareer.global.exception.CustomException;
import com.letscareer.global.exception.ExceptionContent;
import com.letscareer.introduce.domain.Introduce;
import com.letscareer.introduce.domain.repository.IntroduceRepository;
import com.letscareer.introduce.dto.request.IntroduceReq;
import com.letscareer.introduce.dto.request.ReactionReq;
import com.letscareer.introduce.dto.response.GetIntroduceRes;
import com.letscareer.recruitment.domain.Recruitment;
import com.letscareer.recruitment.domain.repository.RecruitmentRepository;
import com.letscareer.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IntroduceService {

    private final IntroduceRepository introduceRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createOrModifyIntroduce(Long recruitmentId, List<IntroduceReq> request) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_RECRUITMENT));

        request.forEach(e -> {
            Optional<Introduce> existingIntroduce = introduceRepository.findByRecruitmentAndOrderIndex(recruitment, e.getOrder());

            if (existingIntroduce.isPresent()) {
                // 존재하면 수정
                Introduce introduce = existingIntroduce.get();
                introduce.update(e.getQuestion(), e.getAnswer());
            } else {
                // 존재하지 않으면 새로 생성
                Introduce newIntroduce = Introduce.of(recruitment, e.getOrder(), e.getQuestion(), e.getAnswer());
                introduceRepository.save(newIntroduce);
            }
        });
    }

    @Transactional(readOnly = true)
    public List<GetIntroduceRes> getIntroduces(Long recruitmentId) {
        List<Introduce> introduces = introduceRepository.findAllByRecruitmentId(recruitmentId);
        return introduces.stream().map(GetIntroduceRes::of).toList();
    }

    @Transactional
    public void deleteIntroduce(Long introduceId, Long recruitmentId) {
        List<Introduce> introduces = introduceRepository.findAllByRecruitmentId(recruitmentId);
        Introduce introduce = introduceRepository.findById(introduceId).orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_INTRODUCE));
        introduceRepository.deleteById(introduceId);
        for (Introduce eachIntroduce : introduces) {
            if (eachIntroduce.getOrderIndex() > introduce.getOrderIndex()){
                eachIntroduce.minusOrderIndex();
            }
        }
    }

    @Transactional
    public void modifyReaction(Long introduceId, ReactionReq request) {
        Introduce introduce = introduceRepository.findById(introduceId).orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_INTRODUCE));
        introduce.modifyReaction(request);
    }


}
