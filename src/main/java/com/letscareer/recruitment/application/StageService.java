package com.letscareer.recruitment.application;

import com.letscareer.global.exception.CustomException;
import com.letscareer.global.exception.ExceptionContent;
import com.letscareer.recruitment.domain.Stage;
import com.letscareer.recruitment.domain.repository.StageRepository;
import com.letscareer.recruitment.dto.request.ModifyStageReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StageService {

    private final StageRepository stageRepository;

    public void modifyStage(Long stageId, ModifyStageReq request) {
        Stage stage = stageRepository.findById(stageId)
                .orElseThrow(()-> new CustomException(ExceptionContent.NOT_FOUND_STAGE));
        stage.modifyStage(request);
    }
}
