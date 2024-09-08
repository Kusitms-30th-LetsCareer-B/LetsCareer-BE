package com.letscareer.introduce.presentation;

import com.letscareer.global.domain.CommonResponse;
import com.letscareer.introduce.application.IntroduceService;
import com.letscareer.introduce.dto.request.IntroduceReq;
import com.letscareer.introduce.dto.request.ReactionReq;
import com.letscareer.introduce.dto.response.GetAdditionalIntroduceRes;
import com.letscareer.introduce.dto.response.GetIntroduceRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class IntroduceController {

    private final IntroduceService introduceService;

    @PutMapping("/introduces")
    public ResponseEntity<CommonResponse<Void>> createOrModifyIntroduce(@RequestParam Long recruitmentId,
                                                                @RequestBody List<IntroduceReq> request){
        introduceService.createOrModifyIntroduce(recruitmentId, request);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("자기소개서 작성이 되었습니다", null));
    }

    @GetMapping("/introduces")
    public ResponseEntity<CommonResponse<List<GetIntroduceRes>>> getIntroduces(@RequestParam Long recruitmentId){
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("자기소개서 조회가 되었습니다", introduceService.getIntroduces(recruitmentId)));
    }

    @DeleteMapping("/introduces/{introduceId}")
    public ResponseEntity<CommonResponse<Void>> deleteIntroduce(@PathVariable Long introduceId,
                                                                @RequestParam Long recruitmentId){
        introduceService.deleteIntroduce(introduceId, recruitmentId);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("자기소개서 질문 삭제가 되었습니다", null));
    }

    @PatchMapping("/introduces/{introduceId}/reaction")
    public ResponseEntity<CommonResponse<Void>> modifyReaction(@PathVariable Long introduceId,@RequestBody ReactionReq request){
        introduceService.modifyReaction(introduceId, request);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("자기소개서 질문에 대한 반응을 남겼습니다", null));
    }

    @GetMapping("/introduces/additional")
    public ResponseEntity<CommonResponse<List<GetAdditionalIntroduceRes>>> getAdditionalIntroduces(@RequestParam Long recruitmentId){
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("다시 보면 좋을 자기소개서 질문 조회를 하였습니다..", introduceService.getAdditionalIntroduces(recruitmentId)));
    }


}
