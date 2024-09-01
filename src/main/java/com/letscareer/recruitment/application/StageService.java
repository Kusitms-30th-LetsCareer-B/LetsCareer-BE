package com.letscareer.recruitment.application;

import com.letscareer.global.exception.CustomException;
import com.letscareer.global.exception.ExceptionContent;
import com.letscareer.recruitment.domain.Stage;
import com.letscareer.recruitment.domain.repository.StageRepository;
import com.letscareer.recruitment.dto.request.ModifyStageReq;
import com.letscareer.recruitment.dto.response.FindStageRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StageService {

    private final StageRepository stageRepository;

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
        stageRepository.delete(stage);
    }
}
