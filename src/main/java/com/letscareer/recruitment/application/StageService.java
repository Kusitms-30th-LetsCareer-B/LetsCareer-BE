package com.letscareer.recruitment.application;

import com.letscareer.calendar.application.ScheduleService;
import com.letscareer.global.exception.CustomException;
import com.letscareer.global.exception.ExceptionContent;
import com.letscareer.recruitment.domain.Recruitment;
import com.letscareer.recruitment.domain.Stage;
import com.letscareer.recruitment.domain.StageStatusType;
import com.letscareer.recruitment.domain.repository.RecruitmentRepository;
import com.letscareer.recruitment.domain.repository.StageRepository;
import com.letscareer.recruitment.dto.request.CreateStageReq;
import com.letscareer.recruitment.dto.request.ModifyStageReq;
import com.letscareer.recruitment.dto.response.FindStageRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StageService {

    private final ScheduleService scheduleService;
    private final StageRepository stageRepository;
    private final RecruitmentRepository recruitmentRepository;

    @Transactional
    public void createStage(Long recruitmentId, CreateStageReq request) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(()-> new CustomException(ExceptionContent.NOT_FOUND_RECRUITMENT));
        Stage savedStage = stageRepository.save(Stage.of(recruitment, request.getStageName(), null, request.getEndDate(), StageStatusType.of(request.getStatus()), request.getIsFinal()));
        scheduleService.addSchedule(recruitment.getUser().getId(), savedStage, recruitment.getCompanyName());
    }

    @Transactional(readOnly = true)
    public FindStageRes findStage(Long stageId) {
        Stage stage = stageRepository.findById(stageId)
                .orElseThrow(()-> new CustomException(ExceptionContent.NOT_FOUND_STAGE));
        return FindStageRes.from(stage);
    }

    @Transactional
    public void modifyStage(Long stageId, ModifyStageReq request) {
        Stage stage = stageRepository.findById(stageId)
                .orElseThrow(()-> new CustomException(ExceptionContent.NOT_FOUND_STAGE));
        stage.modifyStage(request);
    }

    @Transactional
    public void deleteStage(Long stageId) {
        Stage stage = stageRepository.findById(stageId)
                .orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_STAGE));
//        if (!stage.getStageName().equals("서류")){
//            stageRepository.delete(stage);
//        }
//        else{
//            throw new CustomException(ExceptionContent.BAD_REQUEST_DOCUMENT);
//        }
    }

}
