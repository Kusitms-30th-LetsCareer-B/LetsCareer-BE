package com.letscareer.recruitment.presentation;

import com.letscareer.global.domain.ResponseDto;
import com.letscareer.recruitment.application.RecruitmentService;
import com.letscareer.recruitment.dto.request.EnrollRecruitmentReq;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RecruitmentController {

    private final RecruitmentService recruitmentService;

    /**
     * 채용일정 등록 api
     * @param userId 유저id
     * @param request 채용일정 정보
     * @return null
     */
    @PostMapping("/recruitments?userId={userId}")
    public ResponseEntity<ResponseDto<Void>> enrollRecruitment(@RequestParam Long userId, @RequestBody EnrollRecruitmentReq request){
        recruitmentService.enrollRecruitment(userId, request);
        return ResponseEntity.ok().body(ResponseDto.ofSuccess("채용일정을 등록하였습니다.",null));
    }

}
