package com.letscareer.recruitment.presentation;

import com.letscareer.global.domain.ResponseDto;
import com.letscareer.recruitment.application.StageService;
import com.letscareer.recruitment.dto.request.ModifyStageReq;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StageController {

    private final StageService stageService;

    @PatchMapping("/stages")
    public ResponseEntity<ResponseDto<Void>> patchStage(@RequestParam(name= "stageId") Long stageId, @RequestBody ModifyStageReq request){
        stageService.modifyStage(stageId, request);
        return ResponseEntity.ok().body(ResponseDto.ofSuccess("채용 전형을 수정하였습니다.", null));
    }
}
