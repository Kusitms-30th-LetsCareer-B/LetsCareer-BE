package com.letscareer.recruitment.presentation;

import com.letscareer.global.domain.ResponseDto;
import com.letscareer.recruitment.application.RecruitmentService;
import com.letscareer.recruitment.dto.request.EnrollRecruitmentReq;
import com.letscareer.recruitment.dto.response.FindAllRecruitmentsRes;
import com.letscareer.recruitment.dto.response.FindRecruitmentRes;
import com.letscareer.recruitment.dto.response.GetRecruitmentsStatusRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RecruitmentController {

    private final RecruitmentService recruitmentService;

    /**
     * 채용일정 등록
     * @param userId 유저id
     * @return null
     */
    @PostMapping("/recruitments")
    public ResponseEntity<ResponseDto<Void>> enrollRecruitment(@RequestParam(name = "userId") Long userId, @RequestBody EnrollRecruitmentReq request){
        recruitmentService.enrollRecruitment(userId, request);
        return ResponseEntity.ok().body(ResponseDto.ofSuccess("채용일정을 등록하였습니다.",null));
    }

    /**
     * 채용일정 단일 조회
     * @param recruitmentId 채용일정id
     * @return GetRecruitmentRes
     */
    @GetMapping("/recruitments/{recruitmentId}")
    public ResponseEntity<ResponseDto<FindRecruitmentRes>> findRecruitment(@PathVariable(name = "recruitmentId") Long recruitmentId){
        return ResponseEntity.ok().body(ResponseDto.ofSuccess("채용 일정을 조회하였습니다.", recruitmentService.findRecruitment(recruitmentId)));
    }

    /**
     * 채용 일정 삭제
     * @param recruitmentId 채용일정id
     * @return null
     *
     */
    @DeleteMapping("/recruitments")
    public ResponseEntity<ResponseDto<Void>> deleteRecruitment(@RequestParam(name = "recruitmentId") Long recruitmentId){
        recruitmentService.deleteRecruitment(recruitmentId);
        return ResponseEntity.ok().body(ResponseDto.ofSuccess("채용 일정을 삭제하였습니다.", null));
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

    /**
     * 유저의 채용일정 리스트들을 반환한다.
     * @param userId 유저id
     * @return
     */
    @GetMapping("/recruitments")
    public ResponseEntity<ResponseDto<FindAllRecruitmentsRes>> findAllRecruitments(@RequestParam(name = "userId") Long userId){
        return ResponseEntity.ok().body(ResponseDto.ofSuccess("유저의 채용일정 리스트가 반환되었습니다.", recruitmentService.findAllRecruitments(userId)));
    }


}
