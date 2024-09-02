package com.letscareer.recruitment.presentation;

import com.letscareer.global.domain.ResponseDto;
import com.letscareer.recruitment.application.RecruitmentService;
import com.letscareer.recruitment.application.StageService;
import com.letscareer.recruitment.dto.request.CreateStageReq;
import com.letscareer.recruitment.dto.request.ModifyStageReq;
import com.letscareer.recruitment.dto.response.FindStageRes;
import com.letscareer.recruitment.dto.response.GetRecruitmentsStatusRes;
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
    public ResponseEntity<ResponseDto<Void>> createStage(@RequestParam(name = "recruitmentId") Long recruitmentId, @RequestBody CreateStageReq request){
        stageService.createStage(recruitmentId, request);
        return ResponseEntity.ok().body(ResponseDto.ofSuccess("채용전형을 추가하였습니다.", null));
    }

    /**
     * 특정 채용 전형 단일 조회
     * @param stageId 채용전형id
     * @return FindStageRes
     */
    @GetMapping("/stages")
    public ResponseEntity<ResponseDto<FindStageRes>> findStage(@RequestParam(name = "stageId") Long stageId){
        return ResponseEntity.ok().body(ResponseDto.ofSuccess("해당 채용전형을 조회하였습니다.", stageService.findStage(stageId)));
    }

    /**
     * 채용 전형 수정
     * @param stageId
     * @param request
     * @return null
     */
    @PatchMapping("/stages")
    public ResponseEntity<ResponseDto<Void>> modifyStage(@RequestParam(name = "stageId") Long stageId, @RequestBody ModifyStageReq request){
        stageService.modifyStage(stageId, request);
        return ResponseEntity.ok().body(ResponseDto.ofSuccess("채용 전형을 수정하였습니다.", null));
    }

    /**
     * 채용 전형 삭제
     * @param stageId
     * @return null
     */
    @DeleteMapping("/stages")
    public ResponseEntity<ResponseDto<Void>> deleteStage(@RequestParam(name = "stageId") Long stageId){
        stageService.deleteStage(stageId);
        return ResponseEntity.ok().body(ResponseDto.ofSuccess("채용 전형을 삭제하였습니다.", null));
    }

    /**
     * 유저 총 채용일정의 상태 개수를 반환한다.
     * @param userId 유저id
     * @return
     */
    @GetMapping("/recruitments/status")
    public ResponseEntity<ResponseDto<GetRecruitmentsStatusRes>> getRecruitmentsStatus(@RequestParam(name = "userId") Long userId){
        return ResponseEntity.ok().body(ResponseDto.ofSuccess("유저의 총 채용일정 상태 개수가 반환되었습니다.",  recruitmentService.getRecruitmentsStatus(userId)));
    }
}
