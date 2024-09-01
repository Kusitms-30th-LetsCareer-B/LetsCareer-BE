package com.letscareer.recruitment.presentation;

import com.letscareer.global.domain.ResponseDto;
import com.letscareer.recruitment.application.StageService;
import com.letscareer.recruitment.dto.request.ModifyStageReq;
import com.letscareer.recruitment.dto.response.FindStageRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class StageController {

    private final StageService stageService;

    /**
     * 특정 채용 전형 단일 조회
     * @param stageId 채용전형id
     * @return FindStageRes
     */
    @GetMapping("/stages")
    public ResponseEntity<ResponseDto<FindStageRes>> findStage(@RequestParam(name= "stageId") Long stageId){
        return ResponseEntity.ok().body(ResponseDto.ofSuccess("해당 채용전형을 조회하였습니다.",stageService.findStage(stageId)));
    }

    @PatchMapping("/stages")
    public ResponseEntity<ResponseDto<Void>> patchStage(@RequestParam(name= "stageId") Long stageId, @RequestBody ModifyStageReq request){
        stageService.modifyStage(stageId, request);
        return ResponseEntity.ok().body(ResponseDto.ofSuccess("채용 전형을 수정하였습니다.", null));
    }
}
