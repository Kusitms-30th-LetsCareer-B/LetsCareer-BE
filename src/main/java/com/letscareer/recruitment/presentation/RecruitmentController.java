package com.letscareer.recruitment.presentation;

import com.letscareer.global.domain.CommonResponse;
import com.letscareer.recruitment.application.RecruitmentService;
import com.letscareer.recruitment.dto.request.EnrollRecruitmentReq;
import com.letscareer.recruitment.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<CommonResponse<Void>> enrollRecruitment(@RequestParam(name = "userId") Long userId, @RequestBody EnrollRecruitmentReq request){
        recruitmentService.enrollRecruitment(userId, request);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("채용일정을 등록하였습니다.",null));
    }

    /**
     * 채용일정 단일 조회
     * @param recruitmentId 채용일정id
     * @return GetRecruitmentRes
     */
    @GetMapping("/recruitments/{recruitmentId}")
    public ResponseEntity<CommonResponse<FindRecruitmentRes>> findRecruitment(@PathVariable(name = "recruitmentId") Long recruitmentId){
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("채용 일정을 조회하였습니다.", recruitmentService.findRecruitment(recruitmentId)));
    }

    /**
     * 채용 일정 삭제
     * @param recruitmentId 채용일정id
     * @return null
     *
     */
    @DeleteMapping("/recruitments")
    public ResponseEntity<CommonResponse<Void>> deleteRecruitment(@RequestParam(name = "recruitmentId") Long recruitmentId){
        recruitmentService.deleteRecruitment(recruitmentId);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("채용 일정을 삭제하였습니다.", null));
    }

    /**
     * 유저 총 채용일정의 상태 개수를 반환한다.
     * @param userId 유저id
     * @return GetRecruitmentsStatusRes
     */
    @GetMapping("/statuses")
    public ResponseEntity<CommonResponse<GetRecruitmentsStatusRes>> getRecruitmentsStatus(@RequestParam(name = "userId") Long userId){
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("유저의 총 채용일정 상태 개수가 반환되었습니다.",  recruitmentService.getRecruitmentsStatus(userId)));
    }

    /**
     * 유저의 채용일정 리스트들을 반환한다. (관심기업 먼저, 마감일 적게 남은 순)
     * @param userId 유저id
     * @return FindAllRecruitmentsRes
     */
    @GetMapping("/recruitments")
    public ResponseEntity<CommonResponse<FindAllRecruitmentsRes>> findAllRecruitments(@RequestParam(name = "userId") Long userId,
                                                                                      @RequestParam(name = "page") Long page){
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("유저의 채용일정 리스트가 반환되었습니다.", recruitmentService.findAllRecruitments(userId, page)));
    }

    /**
     * 해당 타입의 채용일정 리스트를 반환한다.
     * @param type
     * @param userId
     * @return
     */
    @GetMapping("/recruitments/status")
    public ResponseEntity<CommonResponse<FindAllRecruitmentsByTypeRes>> findRecruitmentsByType(@RequestParam(name = "type") String type,
                                                                                               @RequestParam(name = "userId") Long userId,
                                                                                               @RequestParam(name = "page") Long page){
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("유저 채용일정 리스트가 반환되었습니다.", recruitmentService.findRecruitmentsByType(type, userId, page)));
    }

    /**
     * 특정 기업의 관심 여부 변경
     *
     * @param recruitmentId the recruitment id
     * @return the response entity
     */
    @PatchMapping("/recruitments/{recruitmentId}/favorite")
    public ResponseEntity<CommonResponse<Void>> modifyRecruitmentFavorite(@PathVariable Long recruitmentId){
        recruitmentService.modifyRecruitmentFavorite(recruitmentId);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("관심 여부를 변경하였습니다", null));
    }

    /**
     * 유저의 기업명 리스트 조회
     * @param userId
     * @return
     */
    @GetMapping("/recruitments/name")
    public ResponseEntity<CommonResponse<List<GetRecruitmentsNameRes>>> getRecruitmentsName(@RequestParam Long userId){
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("유저의 기업명을 조회하였습니다.", recruitmentService.getRecruitmentsName(userId)));
    }

}
