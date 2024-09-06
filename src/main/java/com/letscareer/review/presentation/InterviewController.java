package com.letscareer.review.presentation;

import com.letscareer.global.domain.CommonResponse;
import com.letscareer.review.dto.request.ReactionReq;
import com.letscareer.review.application.InterviewService;
import com.letscareer.review.dto.request.InterviewReq;
import com.letscareer.review.dto.response.GetAdditionalInterviewRes;
import com.letscareer.review.dto.response.GetInterviewRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class InterviewController {

    private final InterviewService interviewService;

    /**
     * 특정 기업의 면접 질문 작성 및 수정
     *
     * @param recruitmentId the recruitment id
     * @param request       the request
     * @return the response entity
     */
    @PutMapping("/interviews")
    public ResponseEntity<CommonResponse<Void>> createOrModifyInterview(@RequestParam Long recruitmentId,
                                                                        @RequestBody List<InterviewReq> request){
        interviewService.createOrModifyInterview(recruitmentId, request);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("복기노트 면접 질문 작성이 되었습니다", null));
    }

    /**
     * 특정 기업의 면접 질문 리스트 조회
     *
     * @param recruitmentId the recruitment id
     * @return the response entity
     */
    @GetMapping("/interviews")
    public ResponseEntity<CommonResponse<List<GetInterviewRes>>> getInterviews(@RequestParam Long recruitmentId){
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("복기노트 면접 질문 조회가 되었습니다", interviewService.getInterviews(recruitmentId)));
    }

    /**
     * 특정 면접 질문 삭제
     *
     * @param interviewId   the interview id
     * @param recruitmentId the recruitment id
     * @return the response entity
     */
    @DeleteMapping("/interviews/{interviewId}")
    public ResponseEntity<CommonResponse<Void>> deleteInterview(@PathVariable Long interviewId,
                                                                @RequestParam Long recruitmentId){
        interviewService.deleteInterview(interviewId, recruitmentId);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("복기노트 면접 질문 삭제가 되었습니다", null));
    }

    /**
     * 면접 질문에 대한 반응 수정
     *
     * @param interviewId the interview id
     * @param request     the request
     * @return the response entity
     */
    @PatchMapping("/interviews/{interviewId}/reaction")
    public ResponseEntity<CommonResponse<Void>> modifyReaction(@PathVariable Long interviewId, @RequestBody ReactionReq request){
        interviewService.modifyReaction(interviewId, request);
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("복기노트 면접 질문에 대한 반응을 남겼습니다", null));
    }

    /**
     * 한번 더 보면 좋을 면접 질문 조회
     *
     * @param recruitmentId the recruitment id
     * @return the response entity
     */
    @GetMapping("/interviews/additional")
    public ResponseEntity<CommonResponse<List<GetAdditionalInterviewRes>>> getAdditionalInterviews(@RequestParam Long recruitmentId){
        return ResponseEntity.ok().body(CommonResponse.ofSuccess("복기노트 한번 더 보면 좋을 질문들 목록을 조회하였습니다.", interviewService.getAdditionalInterviews(recruitmentId)));
    }

}
