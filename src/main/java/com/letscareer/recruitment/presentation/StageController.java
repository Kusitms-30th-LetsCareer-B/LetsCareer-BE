package com.letscareer.recruitment.presentation;

import com.letscareer.global.domain.CommonResponse;
import com.letscareer.recruitment.application.RecruitmentService;
import com.letscareer.recruitment.application.StageService;
import com.letscareer.recruitment.dto.request.CreateStageReq;
import com.letscareer.recruitment.dto.request.ModifyStageReq;
import com.letscareer.recruitment.dto.response.FindStageRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class StageController {

    private final StageService stageService;
    private final RecruitmentService recruitmentService;

    /**
     * 채용 전형 추가
     * @param recruitmentId 채용일정id
     * @param request
     * @return null
     */
    @PostMapping("/stages")
    public ResponseEntity<CommonResponse<Void>> createStage(@RequestParam(name = "recruitmentId") Long recruitmentId, @RequestBody CreateStageReq request){
        stageService.createStage(recruitmentId, request);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("채용전형을 추가하였습니다.", null));
    }

    /**
     * 특정 채용 전형 단일 조회
     * @param stageId 채용전형id
     * @return FindStageRes
     */
    @GetMapping("/stages")
    public ResponseEntity<CommonResponse<FindStageRes>> findStage(@RequestParam(name = "stageId") Long stageId){
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("해당 채용전형을 조회하였습니다.", stageService.findStage(stageId)));
    }

    /**
     * 채용 전형 수정
     * @param stageId
     * @param request
     * @return null
     */
    @PatchMapping("/stages")
    public ResponseEntity<CommonResponse<Void>> modifyStage(@RequestParam(name = "stageId") Long stageId, @RequestBody ModifyStageReq request){
        stageService.modifyStage(stageId, request);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("채용 전형을 수정하였습니다.", null));
    }

    /**
     * 채용 전형 삭제
     * @param stageId
     * @return null
     */
    @DeleteMapping("/stages")
    public ResponseEntity<CommonResponse<Void>> deleteStage(@RequestParam(name = "stageId") Long stageId){
        stageService.deleteStage(stageId);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("채용 전형을 삭제하였습니다.", null));
    }

}
