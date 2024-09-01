package com.letscareer.recruitment.presentation;

import com.letscareer.global.domain.ResponseDto;
import com.letscareer.recruitment.application.RecruitmentService;
import com.letscareer.recruitment.dto.request.EnrollRecruitmentReq;
import com.letscareer.recruitment.dto.response.GetRecruitmentRes;
import com.letscareer.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RecruitmentController {

    private final RecruitmentService recruitmentService;
    private final UserRepository userRepository;

    /**
     * 채용일정 등록 api
     * @param userId 유저id
     * @return null
     */
    @PostMapping("/recruitments")
    public ResponseEntity<ResponseDto<Void>> enrollRecruitment(@RequestParam(name = "userId") Long userId, @RequestBody EnrollRecruitmentReq request){
        recruitmentService.enrollRecruitment(userId, request);
        return ResponseEntity.ok().body(ResponseDto.ofSuccess("채용일정을 등록하였습니다.",null));
    }

    /**
     * 채용일정 단일 조회 api
     * @param recruitmentId 채용일정id
     * @return GetRecruitmentRes
     */
    @GetMapping("/recruitments")
    public ResponseEntity<ResponseDto<GetRecruitmentRes>> findRecruitment(@RequestParam(name = "recruitmentId") Long recruitmentId){
        return ResponseEntity.ok().body(ResponseDto.ofSuccess("채용 일정을 조회하였습니다.", recruitmentService.findRecruitment(recruitmentId)));
    }

    /**
     *
     * @param recruitmentId 채용일정id
     * @return null
     *
     */
    @DeleteMapping("/recruitments")
    public ResponseEntity<ResponseDto<Void>> deleteRecruitment(@RequestParam(name = "recruitmentId") Long recruitmentId){
        recruitmentService.deleteRecruitment(recruitmentId);
        return ResponseEntity.ok().body(ResponseDto.ofSuccess("채용 일정을 삭제하였습니다.", null));
    }




}
